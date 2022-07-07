package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import record.RecordView;

import java.net.URL;
import java.util.ResourceBundle;

public class RecordPageController implements Initializable {


    @FXML private VBox info;
    @FXML private BorderPane bPane;

    @Override
    public void  initialize(URL url, ResourceBundle resourceBundle) {
        Label desc = new Label("In this page, you will find a record of all the words you have successfully guessed on the 'Guess' page. " +
                "Besides, you will be able to find all the definitions available of any given word of that list, including the one used" +
                "as a hint in the 'Guess' page");
        desc.setWrapText(true);
        info.getChildren().add(desc);
        info.setAlignment(Pos.CENTER);

        bPane.setCenter(new RecordView());
    }

}
