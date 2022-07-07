package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Constant;

import java.io.IOException;

public class MainPageController {

    public void orderPage(ActionEvent event) throws IOException {
        Parent orderPage = FXMLLoader.load(getClass().getResource("/xmls/OrderPage.fxml"));
        Scene home = ((Node) event.getSource()).getScene();
        Constant.previous = home;
        Stage stageApp = (Stage) home.getWindow();
        stageApp.setScene(new Scene(orderPage, 1500, 800));
    }

    public void historyPage(ActionEvent event) throws IOException {
        Parent histoPage = FXMLLoader.load(getClass().getResource("/xmls/RecordPage.fxml"));
        Scene home = ((Node) event.getSource()).getScene();
        Constant.previous = home;
        Stage stageApp = (Stage) home.getWindow();
        stageApp.setScene(new Scene(histoPage, 1500, 800));
    }

    public void remindPage(ActionEvent event) throws IOException {
        Parent histoPage = FXMLLoader.load(getClass().getResource("/xmls/RemindPage.fxml"));
        Scene home = ((Node) event.getSource()).getScene();
        Constant.previous = home;
        Stage stageApp = (Stage) home.getWindow();
        stageApp.setScene(new Scene(histoPage, 1500, 800));
    }

}
