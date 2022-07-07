package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.lucene.document.Document;
import utils.Constant;
import utils.Tools;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;

public class RemindPageController implements Initializable {

    @FXML BorderPane bPane;
    @FXML MenuItem favMenu;
    private boolean favOption = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashSet<String> records = Constant.record.getRecord();
        HashSet<String> favs = Constant.record.getFav();
        VBox box = new VBox();

        Text desc = new Text("In this page, you will be able to revise the words you already guessed in the 'Guess' page. " +
                "As before, a definition of the word will be displayed, but this time not the letters. " +
                "Note that there are several definitions for each word, and only one will be chosen randomly.");
        desc.setWrappingWidth(500);

        Pane pane = new Pane();
        pane.setPrefHeight(100);

        String word = "";
        if (favOption) {
            word = chooseWord(favs);
        } else {
            word = chooseWord(records);
        }

        String def = getDef(word);
        Label defL = new Label(def);
        defL.setWrapText(true);

        TextField entry = new TextField();
        entry.setMaxWidth(200);

        Label feedback = new Label("Try finding the word");

        Button check = new Button("Check answer");
        String finalWord = word;
        String finalWord1 = word;
        check.setOnAction(event -> {
            String answer = entry.getText();
            if (answer.toUpperCase().equals(finalWord)) {
                feedback.setText("Congratulations ! You find the word");
            } else {
                feedback.setText("The word isn't right, the right answer was :\n" + finalWord1);
            }
        });

        Button next = new Button("Next word");
        next.setOnAction(event -> {
            box.getChildren().clear();
            initialize(null, null);
        });

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(check, next);

        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.getChildren().addAll(desc, pane, defL, entry, buttons, feedback);

        bPane.setCenter(box);
    }

    private String chooseWord(HashSet<String> hash) {
        int n = hash.size();
        Object[] array = hash.toArray();
        int rand = new Random().nextInt(n);
        return (String) array[rand];
    }

    private String getDef(String word) {
        HashSet<Document> docs = Tools.getDocuments(word);
        int n = docs.size();
        Object[] array = docs.toArray();
        int rand = new Random().nextInt(n);
        return ((Document) array[rand]).get("Definition");
    }

    public void save(ActionEvent event) {
        Tools.save();
    }

    public void saveQuit(ActionEvent event) {
        Tools.save();
        Constant.primary.close();
    }

    public void favourite(ActionEvent event) {
        if (favOption) {
            favOption = false;
            favMenu.setText("Activate favourites");
        } else {
            favOption = true;
            favMenu.setText("Disable favourites");
        }
    }
}
