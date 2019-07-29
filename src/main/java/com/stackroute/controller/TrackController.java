package com.stackroute.controller;

import com.stackroute.Exception.TrackAlreadyExistException;
import com.stackroute.Exception.TrackNotFoundException;
import com.stackroute.domain.Track;
import com.stackroute.repository.TrackRepository;
import com.stackroute.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1")
public class TrackController
{

    private TrackRepository trackRepository;

    // Declaration
    TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService)
    {
        this.trackService = trackService;
    }


    // Implementing POST method
    @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) throws TrackAlreadyExistException
    {
        ResponseEntity responseEntity;
        try{
            trackService.saveTrack(track);
            responseEntity=new ResponseEntity("Successfully created", HttpStatus.CREATED);
        }
        catch (TrackAlreadyExistException e)
        {
            responseEntity=new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;

    }

    // Implementing GET method
    @GetMapping("track")
    public ResponseEntity<?> getAllTracks()
    {
        System.out.println("inside");
        return new ResponseEntity<List<Track>>(trackService.getAllTracks(),HttpStatus.OK);
    }

    // Implementing PUT method
    @PutMapping("track")
    public ResponseEntity<?> updateTrack(@RequestBody Track track) throws TrackNotFoundException
    {
        ResponseEntity responseEntity;
        try{
            trackService.updateTrack(track);
            responseEntity=new ResponseEntity("Successfully updated", HttpStatus.CREATED);
        }
        catch (TrackNotFoundException e)
        {
            responseEntity=new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }


    // Implementing DELETE method
    @DeleteMapping(value="/track/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable("id") int id)
    {
        ResponseEntity responseEntity;
        try {
            trackService.deleteTrack(id);
            responseEntity = new ResponseEntity("Successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
}
