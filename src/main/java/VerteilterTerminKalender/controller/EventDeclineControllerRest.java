package VerteilterTerminKalender.controller;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static VerteilterTerminKalender.constants.Configuration.BASE_URL;

/**
 * Class fetches and send event decline to the server via REST API
 */
public class EventDeclineControllerRest
{

    private final String ENDPOINT_GET_EVENT_DECLINE ="/decline/";
    private final String ENDPOINT_GET_USER_WHO_DECLINED ="/decline/event/";
    private final String ENDPOINT_ADD_EVENT_DECLINE ="/decline/add/";
    private final String ENDPOINT_DELETE_DECLINE ="/decline/delete/";


    private Client client;
    private WebTarget target;


    public EventDeclineControllerRest()
    {
        client = ClientBuilder.newClient();
        client.target(BASE_URL);
        target = client.target(BASE_URL);

    }

    /**
     * Method fetches a event decline from server
     * @param declineId id of the event decline
     * @return String event decline as json string
     */
    public String getEventDeclineById(int declineId){

        String webContextPath = ENDPOINT_GET_EVENT_DECLINE + declineId;

        String result = target.path(webContextPath).request(MediaType.APPLICATION_JSON).get(String.class);

        return result;
    }

    /**
     * @param eventId
     * @return
     */
    public String getUserWhoDeclined(int eventId) {

        String webContextPath = ENDPOINT_GET_USER_WHO_DECLINED + eventId;
        String result = target.path(webContextPath).request(MediaType.APPLICATION_JSON).get(String.class);
       // System.out.println(result);
        return result;
    }

    /**
     * Creates a new event decline
     * @param eventDecline
     * @return
     */
    public Response newEventDecline(String eventDecline)
    {
        String webContextPath = ENDPOINT_ADD_EVENT_DECLINE;

        Response response = target.path(webContextPath).request().post(Entity.entity(eventDecline, MediaType.APPLICATION_JSON));


        return response;

    }

    /**
     * Deletes a event decline
     * @param declineId id of decline event
     * @return String
     */
    public String deleteEventDeclineById(int declineId) {

        String webContextPath = ENDPOINT_DELETE_DECLINE + declineId;

        try {
              target.path(webContextPath).request(MediaType.APPLICATION_JSON).delete(String.class);
              return "0";
        }catch (InternalServerErrorException e) {

        }

        return "-1";
    }

}





