import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.lucene.queryparser.classic.ParseException;
import utils.Constant;
import utils.Tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Constant.primary = primaryStage;

        if (!Files.exists(Paths.get(Constant.repo))) {
            initialize();
        } else {
            Tools.openIndex();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/xmls/MainPage.fxml"));
        Tools.load();
        primaryStage.setTitle("Learn Your Words");
        primaryStage.setScene(new Scene(root, 1500, 800));
        primaryStage.show();
    }

    private void initialize() throws IOException {
        Files.createDirectory(Paths.get(Constant.repo));
        Tools.indexCsv();
        File file = new File(Constant.repo + "/save.sav");
        file.createNewFile();
        File fileFav = new File(Constant.repo + "/favs.sav");
        fileFav.createNewFile();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
