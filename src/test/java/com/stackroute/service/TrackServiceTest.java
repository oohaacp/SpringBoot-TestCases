package com.stackroute.service;

import com.stackroute.Exception.TrackAlreadyExistException;
import com.stackroute.Exception.TrackNotFoundException;
import com.stackroute.domain.Track;
import com.stackroute.repository.TrackRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TrackServiceTest {
    Track track;

    //Create a mock for UserRepository
    @Mock
    TrackRepository trackRepository;

    //Inject the mocks as dependencies into UserServiceImpl
    @InjectMocks
    TrackServiceImpl trackService;
    List<Track> list = null;


    @Before
    public void setUp() {
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        track = new Track();
        track.setId(101);
        track.setName("VVC");
        track.setComment("IBM");
        list = new ArrayList<>();
        list.add(track);
    }

    @Test
    public void saveTrackTestSuccess() throws TrackAlreadyExistException {

        when(trackRepository.save((Track) any())).thenReturn(track);
        Track savedTrack = trackService.saveTrack(track);
        Assert.assertEquals(track, savedTrack);

        //verify here verifies that trackRepository save method is only called once
        verify(trackRepository, times(1)).save(track);

    }

    @Test(expected = TrackAlreadyExistException.class)
    public void saveTrackTestFailure() throws TrackAlreadyExistException {
        when(trackRepository.save((Track) any())).thenReturn(null);
        Track savedTrack = trackService.saveTrack(track);
        System.out.println("savedTrack" + savedTrack);

    }

    @Test
    public void getAllTracks() {

        trackRepository.save(track);
        //stubbing the mock to return specific data
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> tracklist = trackService.getAllTracks();
        Assert.assertEquals(list, tracklist);
    }


    @Test
    public void testUpdateTrack() throws TrackNotFoundException{


        when(trackRepository.existsById(track.getId())).thenReturn(true);
        track.setName("Vishnu");
        Track track1=trackService.updateTrack(track);
        when(trackRepository.save((Track)any())).thenReturn(track1);
        Assert.assertEquals("Vishnu",track1.getName());
    }

    @Test(expected = TrackNotFoundException.class)
    public void testUpdateTrackFailure() throws TrackNotFoundException{

        when(trackRepository.findById(track.getId())).thenReturn(Optional.empty());
        track.setName("exception case");
        Track track1=trackService.updateTrack(track);
    }


    @Test
    public void deleteTrackTest()
    {
        Track track=new Track(57,"DeleteTrack","Deleted");
        trackRepository.delete(track);
        boolean result=trackRepository.existsById(57);
        Assert.assertEquals(false,result);

    }
}
