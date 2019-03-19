package VerteilterTerminKalender.util;

import VerteilterTerminKalender.MainApp;
import VerteilterTerminKalender.constants.FXConstants;
import VerteilterTerminKalender.i18n.I18nUtil;
import VerteilterTerminKalender.model.classes.EventFxImpl;
import VerteilterTerminKalender.model.interfaces.EventFx;
import VerteilterTerminKalender.model.interfaces.EventInvite;
import VerteilterTerminKalender.view.RegisterLayoutController;
import VerteilterTerminKalender.view.interfaces.FXMLController;
import VerteilterTerminKalender.view.interfaces.FXMLDialogController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static VerteilterTerminKalender.validators.ObjectValidator.isNotObjectNull;
import static VerteilterTerminKalender.validators.StringValidator.isNotStringEmptyOrNull;

/**
 * Contains utility-methods for JavaFX
 *
 * @author Michelle Blau
 */

public class FxUtil {

    public static List<EventInvite> convertJsonStringToEventInviteListe(String eventInviteAsJson){

        ObjectMapper objectMapper = new ObjectMapper();
        try {

           List<EventInvite> eventInviteList = objectMapper.readValue(eventInviteAsJson, new TypeReference<List<EventInvite>>(){});

            return  eventInviteList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static EventFx convertMapToEventFx(Map<String, Object> eventMap){
        EventFx eventFx = new EventFxImpl();

        //EventId
        IntegerProperty eventId = new SimpleIntegerProperty();
        eventId.setValue(Integer.valueOf(eventMap.get("eventId").toString()));
        eventFx.setEventId(eventId);

        //Location
        StringProperty location = new SimpleStringProperty();
        String locationString = eventMap.get("location").toString();
        if(isNotStringEmptyOrNull(locationString)){
            location.setValue(locationString);
        }

        eventFx.setLocation(location);

        //StartTime
        ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();

        ArrayList<Integer> startTimeArray = (ArrayList<Integer>) eventMap.get("startTime");

        if(isNotObjectNull(startTimeArray)) {

            LocalDateTime localDateTime = LocalDateTime.of(
                    startTimeArray.get(0),
                    startTimeArray.get(1),
                    startTimeArray.get(2),
                    startTimeArray.get(3),
                    startTimeArray.get(4)
            );

            startTime.setValue(localDateTime);
        }
        eventFx.setStartTime(startTime);


        //endTime
        ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();

        ArrayList<Integer> endTimeArray = (ArrayList<Integer>) eventMap.get("endTime");
        if(isNotObjectNull(endTimeArray)){
            LocalDateTime localDateTime = LocalDateTime.of(
                    endTimeArray.get(0),
                    endTimeArray.get(1),
                    endTimeArray.get(2),
                    endTimeArray.get(3),
                    endTimeArray.get(4)
            );


            endTime.setValue(localDateTime);
        }

        eventFx.setEndTime(endTime);


        //allDay
        BooleanProperty allDay = new SimpleBooleanProperty();

        String allDayString = eventMap.get("allDay").toString();
        if(isNotStringEmptyOrNull(allDayString)){

            allDay.setValue(Boolean.valueOf(allDayString));
        }

        eventFx.setAllDay(allDay);


        //repeat
        //TODO Serverteam fragen, ob repeat null werden kann
        IntegerProperty repeat = new SimpleIntegerProperty();
        repeat.setValue(Integer.valueOf(eventMap.get("repeat").toString()));
        eventFx.setRepeat(repeat);


        //note
        StringProperty note = new SimpleStringProperty();
        String noteString = eventMap.get("note").toString();
        if(isNotStringEmptyOrNull(noteString)){
            note.setValue(noteString);
        }

        //userID
        StringProperty userId = new SimpleStringProperty();
        String userIDString = eventMap.get("userId").toString();
        if(isNotStringEmptyOrNull(userIDString)){
            userId.setValue(userIDString);
        }

        eventFx.setNote(note);

        //duration
        //TODO abklären in welchem Format. Danach implementieren

        return eventFx;
    }


    /**
     * Transfer a Map (from JSON) to a String
     * @param dateTimeArray
     * @return
     */
    public static String convertMapWithTimeAndDateToString(ArrayList<Integer> dateTimeArray){

        StringBuilder builder = new StringBuilder();

        //TODO Eventuell in einer For Schleife machen, wenn Format klar ist
        builder.append(dateTimeArray.get(0).toString());
        builder.append("-");
        builder.append(dateTimeArray.get(1).toString());
        builder.append("-");
        builder.append(dateTimeArray.get(2).toString());
        builder.append("-");
        builder.append(dateTimeArray.get(3).toString());
        builder.append("-");
        builder.append(dateTimeArray.get(4).toString());

       return builder.toString();

    }

    /**
     * Sets an existing label as visible/red, showing an error message
     * @param label existing label with error message
     */
    public static void showErrorLabel(Label label){
        label.setTextFill(Color.RED);
        label.setVisible(true);
    }


    /**
     * Sets an existing label as visible/green, showing a message
     * @param label existing label with success message
     */
    public static void showSuccessLabel(Label label){
        label.setTextFill(Color.GREEN);
        label.setVisible(true);
    }


    /**
     * Creates a new Scene and places it inside the primary Stage of mainApp
     * @param mainApp mainApp-Object with primary Stage
     * @param bundle i18n-Bundle
     * @param fxmlPath Path to the FXML file of the Scene
     */
    public static void showScene(MainApp mainApp, ResourceBundle bundle, String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource(fxmlPath));
            loader.setResources(bundle);

            Pane pane = loader.load();
            Scene scene = new Scene(pane);

            Stage primaryStage = mainApp.getPrimaryStage();
            primaryStage.setScene(scene);

            FXMLController controller = loader.getController();
            controller.setMainApp(mainApp);

            primaryStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Creates a new Stage and shows it inside a new window
     * @param mainApp mainApp-Object with primary Stage
     * @param bundle i18n-Bundle
     * @param fxmlPath Path to the FXML file of the shown Scene
     */
    public static void showStage(MainApp mainApp, ResourceBundle bundle, String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource(fxmlPath));
            loader.setResources(bundle);

            Pane pane = loader.load();
            Scene scene = new Scene(pane);

            Stage dialogStage = new Stage();
            dialogStage.setScene(scene);

            FXMLDialogController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.setDialogStage(dialogStage);

            dialogStage.setTitle(FXConstants.APPLICATION_NAME);
            dialogStage.getIcons().add(new Image(FXConstants.PATH_APPLICATION_IMAGE));
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Sets the position of the stage to the center of the screen.
     *
     * @param primaryStage
     */
    public static void putStageInCenterOfScreen(Stage primaryStage) {

        Dimension d= Toolkit.getDefaultToolkit().getScreenSize(); // get screen size
//        primaryStage.show(); //show stage because you wouldn't be able to get Height & width of the stage
        primaryStage.setX(d.width/2-(primaryStage.getWidth()/2));
        primaryStage.setY(d.height/2-(primaryStage.getHeight()/2));
    }
}
