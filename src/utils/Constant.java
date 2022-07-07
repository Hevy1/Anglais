package utils;

import javafx.scene.Scene;
import javafx.stage.Stage;
import record.Record;

public class Constant {
    public static Scene previous;
    public static Stage primary;
    public static final String repo = System.getProperty("user.home") + "/.englishApp";
    public static final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static Record record = new Record();
}
