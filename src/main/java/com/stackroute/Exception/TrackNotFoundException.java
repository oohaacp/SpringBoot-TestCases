package com.stackroute.Exception;

public class TrackNotFoundException extends Exception
{
    private String exceptionmessage;

    public TrackNotFoundException()
    {
    }

    public TrackNotFoundException(String message)
    {
        super(message);
        this.exceptionmessage=message;
    }

}

