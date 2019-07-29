package com.stackroute.repository;

import com.stackroute.domain.Track;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrackRepositoryTest
{

    @Autowired
    TrackRepository trackRepository;
    Track track;

    @Before
    public void setUp()
    {
        track = new Track();
        track.setId(101);
        track.setName("Repository");
        track.setComment("RepositoryTest");
    }

    @After
    public void tearDown(){

        trackRepository.deleteAll();
    }


    @Test
    public void testSaveTrack(){
        trackRepository.save(track);
        Track fetchTrack = trackRepository.findById(track.getId()).get();
        Assert.assertEquals(101,fetchTrack.getId());

    }

    @Test
    public void testSaveTrackFailure(){
        Track testTrack = new Track(103,"oohaa","IBM");
        trackRepository.save(track);
        Track fetchTrack = trackRepository.findById(track.getId()).get();
        Assert.assertNotSame(testTrack,track);
    }

    @Test
    public void testGetAllTracks(){
        Track track = new Track(104,"oohaa","GetTracks");
        Track track1 = new Track(105,"oohaa","GetTracks");
        trackRepository.save(track);
        trackRepository.save(track1);

        List<Track> list = trackRepository.findAll();
        Assert.assertEquals("oohaa",list.get(0).getName());
        Assert.assertEquals("oohaa",list.get(1).getName());

    }

    @Test
    public void updateTrackTest()
    {
      Track track=new Track(106,"Update","UpdatingTracks");
      trackRepository.save(track);
      trackRepository.findById(track.getId()).get().setName("UpdatedTrackInName");
      List<Track> list=trackRepository.findAll();
      Assert.assertEquals("UpdatedTrackInName",list.get(0).getName());
    }

    @Test
    public void deleteTrackTest()
    {
        Track track=new Track(107,"Delete","DeleteTrack");
        trackRepository.save(track);
        trackRepository.deleteById(107);
        boolean result=trackRepository.existsById(107);
        Assert.assertEquals(false,result);

    }


}

