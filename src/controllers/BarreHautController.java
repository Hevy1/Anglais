package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import utils.Constant;
import utils.Tools;


public class BarreHautController {

    public void save(ActionEvent event) {
        Tools.save();
    }

    public void saveQuit(ActionEvent event) {
        Tools.save();
        Constant.primary.close();
    }
}
