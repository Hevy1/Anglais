package record;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import utils.Constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RecordView extends ListView<WordView> {

    public RecordView() {
        super();
        this.setPrefSize(1300, 720);
        HashSet<String> record = Constant.record.getRecord();
        List<WordView> words = new ArrayList<>();
        for (String word : record) {
            words.add(new WordView(word));
        }
        ObservableList<WordView> list = FXCollections.observableArrayList(words);
        this.setFixedCellSize(100);
        this.setItems(list);
    }
}
