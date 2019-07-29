package com.stackroute.service;

import com.stackroute.Exception.TrackAlreadyExistException;
import com.stackroute.Exception.TrackNotFoundException;
import com.stackroute.domain.Track;
import com.stackroute.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    TrackRepository trackRepository;

    // Providing implementation for all methods of track
    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }


    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistException
    {
        if (trackRepository.existsById(track.getId())) {
            throw new TrackAlreadyExistException("Track Already exist");
        }
        Track savetrack = trackRepository.save(track);

        if (savetrack == null) {
            throw new TrackAlreadyExistException("Track already present");
        }
        return savetrack;
    }

    @Override
    public List<Track> getAllTracks()
    {
        return trackRepository.findAll();
    }

    @Override
    public Track updateTrack(Track track) throws TrackNotFoundException
    {
        /*if (trackRepository.existsById(track.getId())) {
            Track trackobj = trackRepository.findById(track.getId()).get();
            trackobj.setComment(track.getComment());
            trackRepository.save(trackobj);
            return trackobj;
        } else {
            throw new TrackNotFoundException("Track not found");
        }*/

        Track track1=new Track();
        if (trackRepository.existsById(track.getId())) {
            track1.setName(track.getName());
            trackRepository.save(track1);
            System.out.println(track1);
            return track1;
        } else {
            throw new TrackNotFoundException("Track not found");
        }
    }

    @Override
    public boolean deleteTrack(int id)
    {
        boolean result;
        if(trackRepository.existsById(id))
        {
            trackRepository.deleteById(id);
            result=true;
        }
        else
        {
            result=false;
        }
        return result;
    }
}
