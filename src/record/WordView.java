package record;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.apache.lucene.document.Document;
import utils.Constant;
import utils.Tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WordView extends HBox {

    public WordView (String word) {
        super();
        Text wordT = new Text(word);
        wordT.setWrappingWidth(200);

        HashSet<Document> docs = Tools.getDocuments(word);
        ListView<HBox> descs = new ListView<>();
        descs.setPrefWidth(800);
        int i = 1;
        List<HBox> list = new ArrayList<>();
        for (Document doc : docs) {
            HBox desc = new HBox();

            Text number = new Text("def " + i);
            number.setWrappingWidth(50);

            Text defText = new Text(doc.get("Definition"));
            defText.setWrappingWidth(700);

            desc.getChildren().addAll(number, defText);
            list.add(desc);
            i++;
        }

        Button fav = new Button();
        if (Constant.record.containsFav(word)) {
            fav.setText("Remove from favourites");
        } else {
            fav.setText("Add to favourites");
        }
        fav.setPrefWidth(200);
        fav.setPrefHeight(50);
        fav.setOnAction(event -> {
            if (Constant.record.containsFav(word)) {
                Constant.record.removeFav(word);
                fav.setText("Add to favourites");
            } else {
                Constant.record.addFav(word);
                fav.setText("Remove from favourites");
            }
        });

        ObservableList<HBox> oList = FXCollections.observableArrayList(list);
        descs.setItems(oList);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().addAll(wordT, descs, fav);
    }
}
