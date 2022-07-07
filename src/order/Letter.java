package order;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Letter extends Pane {

    private Hole fit = null;

    public Letter(String letter) {
        super();

        Text text = new Text(letter);
        text.setX(20); text.setY(30);
        this.setStyle("-fx-border-color: black;");
        this.getChildren().add(text);
        this.setPrefSize(50,50);

        this.setOnDragDetected(mouseEvent -> {
            this.startFullDrag();
            mouseEvent.consume();
        });

        this.setOnMouseDragged(mouseEvent -> {
            mouseEvent.setDragDetect(true);
            this.setMouseTransparent(true);
            this.setOpacity(0.25);
            if (!mouseEvent.isControlDown()) {
                this.relocate(mouseEvent.getX(), mouseEvent.getY());
            }
            mouseEvent.consume();
        });

        this.setOnMouseReleased(mouseEvent -> {
            if (this.fit != null) {
                this.setOpacity(0);
            } else {
                this.setOpacity(1);
                this.setMouseTransparent(false);
            }
            mouseEvent.consume();
        });
    }

    public String getLetter() {
        return ((Text) this.getChildren().get(0)).getText();
    }

    public void setLetter(String letter) {
        ((Text) this.getChildren().get(0)).setText(letter);
    }

    public Hole getFit() {
        return fit;
    }

    public void setFit(Hole fit) {
        this.fit = fit;
    }
}
