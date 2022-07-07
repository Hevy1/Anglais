package controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import order.Guess;
import org.apache.lucene.document.Document;
import utils.Tools;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderPageController implements Initializable {

    @FXML BorderPane bPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Document doc = null;
        try {
            doc = Tools.random();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bPane.setCenter(new Guess(doc));
    }
}
