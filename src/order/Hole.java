package order;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Hole extends Pane {

    private Text letter = null;
    private boolean lock = false;
    private final Guess guess;

    public Hole(Guess guess) {
        super();
        this.guess = guess;
        this.setPrefSize(50, 50);
        this.setStyle("-fx-border-color: black;");

        this.setOnMouseDragEntered(event -> {
            if (!lock) {
                this.setOpacity(0.5);
            }
            event.consume();
        });

        this.setOnMouseDragExited(event -> {
            if (!lock) {
                this.setOpacity(1);
            }
            event.consume();
        });

        this.setOnMouseDragReleased(event -> {
            if (!lock) {
                lock = true;
                Letter source = (Letter) event.getGestureSource();
                this.setLetter(source.getLetter());
                source.setFit(this);
                this.setOpacity(1);
                this.guess.notifyGuess();
                event.consume();
            }
        });
    }

    public String getLetter() {
        return this.letter.getText();
    }

    public void setLetter(String letter) {
        this.letter = new Text(letter);
        this.letter.setX(20);
        this.letter.setY(30);
        this.getChildren().add(this.letter);
    }

    public void clear() {
        this.letter = null;
        this.getChildren().removeAll();
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
