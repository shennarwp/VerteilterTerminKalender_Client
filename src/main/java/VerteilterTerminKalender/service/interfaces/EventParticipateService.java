package VerteilterTerminKalender.service.interfaces;

import VerteilterTerminKalender.model.interfaces.EventParticipate;

import java.util.ArrayList;

public interface EventParticipateService {
    public EventParticipate getParticipate(int participateID);
    public ArrayList getParticipants(int eventID);
    public boolean createParticipate(EventParticipate eventParticipate);
    public boolean deleteParticipate(int participateID);
}