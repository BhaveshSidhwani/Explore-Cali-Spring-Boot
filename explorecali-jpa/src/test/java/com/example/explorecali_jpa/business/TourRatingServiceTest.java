package com.example.explorecali_jpa.business;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.explorecali_jpa.model.Tour;
import com.example.explorecali_jpa.model.TourRating;
import com.example.explorecali_jpa.repo.TourRatingRepository;
import com.example.explorecali_jpa.repo.TourRepository;

@ExtendWith(MockitoExtension.class)
public class TourRatingServiceTest {
    private static final int CUSTOMER_ID = 123;
    private static final int TOUR_ID = 1;
    private static final int TOUR_RATING_ID = 100;

    @Mock
    private TourRepository tourRepositoryMock;
    @Mock
    private TourRatingRepository tourRatingRepositoryMock;

    @InjectMocks 
    private TourRatingService service;

    @Mock
    private Tour tourMock;

    @Mock
    private TourRating tourRatingMock;

    @Test
    public void lookupRatingById() {
        when(tourRatingRepositoryMock.findById(TOUR_RATING_ID)).thenReturn(Optional.of(tourRatingMock));

        assertThat(service.lookupRatingById(TOUR_RATING_ID).get(), is(tourRatingMock));
    }

    @Test
    public void lookupAll() {
        when(tourRatingRepositoryMock.findAll()).thenReturn(Arrays.asList(tourRatingMock));

        assertThat(service.lookupAll().get(0), is(tourRatingMock));
    }

    @Test
    public void getAverageRating() {
        when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.of(tourMock));
        when(tourMock.getId()).thenReturn(TOUR_ID);
        when(tourRatingRepositoryMock.findByTourId(TOUR_ID)).thenReturn(Arrays.asList(tourRatingMock));
        when(tourRatingMock.getRating()).thenReturn(5);

        assertThat(service.getAverageRating(TOUR_ID), is(5.0));
    }

    @Test
    public void lookupRatings() {
        List<TourRating> list = mock(List.class);
        when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.of(tourMock));
        when(tourMock.getId()).thenReturn(TOUR_ID);

        when(tourRatingRepositoryMock.findByTourId(TOUR_ID)).thenReturn(list);

        assertThat(service.lookupRatings(TOUR_ID), is(list));
    }

    @Test
    public void delete() {
        when(tourRatingRepositoryMock.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID))
            .thenReturn(Optional.of(tourRatingMock));

        service.delete(1, CUSTOMER_ID);

        verify(tourRatingRepositoryMock).delete(any(TourRating.class));
    }

    @Test
    public void rateMany() {
        when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.of(tourMock));
        when(tourRatingRepositoryMock.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID))
            .thenReturn(Optional.empty());
        when(tourRatingRepositoryMock.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID + 1))
            .thenReturn(Optional.empty());

        service.rateMany(TOUR_ID, 10, List.of(CUSTOMER_ID, CUSTOMER_ID + 1));

        verify(tourRatingRepositoryMock, times(2)).save(any(TourRating.class));
    }

    @Test
    public void update() {
        when(tourRatingRepositoryMock.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID))
            .thenReturn(Optional.of(tourRatingMock));

        service.update(TOUR_ID, CUSTOMER_ID, 5, "great");

        verify(tourRatingRepositoryMock).save(any(TourRating.class));

        verify(tourRatingMock).setComment("great");
        verify(tourRatingMock).setRating(5);
    }

    @Test
    public void updateSome() {
        when(tourRatingRepositoryMock.findByTourIdAndCustomerId(TOUR_ID, CUSTOMER_ID))
            .thenReturn(Optional.of(tourRatingMock));

        service.updateSome(TOUR_ID, CUSTOMER_ID, Optional.of(1), Optional.of("awful"));

        verify(tourRatingRepositoryMock).save(any(TourRating.class));

        verify(tourRatingMock).setComment("awful");
        verify(tourRatingMock).setRating(1);
    }

    @Test
    public void createNew() {
        when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.of(tourMock));
        ArgumentCaptor<TourRating> tourRatingCaptor = ArgumentCaptor.forClass(TourRating.class);

        service.createNew(TOUR_ID, CUSTOMER_ID, 2, "ok");

        verify(tourRatingRepositoryMock).save(tourRatingCaptor.capture());

        assertThat(tourRatingCaptor.getValue().getTour(), is(tourMock));
        assertThat(tourRatingCaptor.getValue().getCustomerId(), is(CUSTOMER_ID));
        assertThat(tourRatingCaptor.getValue().getRating(), is(2));
        assertThat(tourRatingCaptor.getValue().getComment(), is("ok"));
    }

    @Test
    public void testNotFound() {
        when(tourRepositoryMock.findById(TOUR_ID)).thenReturn(Optional.empty());
        
        assertThrows(NoSuchElementException.class, () -> 
            service.lookupRatings(TOUR_ID)
        );
    }
}