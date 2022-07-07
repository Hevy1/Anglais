package order;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.lucene.document.Document;
import utils.Constant;
import utils.Tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Guess extends VBox {

    private String word;
    private int id;
    private String def;
    private List<String> random;
    private List<Hole> guess;
    private List<Letter> pool;
    private Text feedback;
    private Button skip;
    private Button guesss;

    public Guess(Document doc) {
        super();
        initialize(doc);
    }

    public void initialize(Document doc) {
        this.word = doc.get("Word").toUpperCase();
        this.def = doc.get("Definition").replaceAll(this.word.toLowerCase(), "____");
        this.setPrefSize(1000,700);
        this.setSpacing(50);
        this.setAlignment(Pos.CENTER);
        randomize();
        loadPage();
    }

    public void loadPage() {
        int n = word.length();
        guess = new ArrayList<>();
        pool = new ArrayList<>();
        for (int i=0; i<n; i++) {
            guess.add(new Hole(this));
            pool.add(new Letter(random.get(i)));
        }

        //Now creating the interface
        Text desc = new Text("In this page, you have to find a word by putting back its letters in the right order. " +
                "You have two clues to help you find the word : a definition of the word, and its letters. " +
                "The 'Hint' button will automatically put one letter at the right position, if it can do so. " +
                "The 'Reset' button resets all the letter, it allows to reset if you have made a mistake.");
        desc.setWrappingWidth(500);

        HBox letters = new HBox();
        letters.setSpacing(5);
        letters.setAlignment(Pos.CENTER);
        letters.getChildren().addAll(pool);

        HBox holes = new HBox();
        holes.setSpacing(5);
        holes.setAlignment(Pos.CENTER);
        holes.getChildren().addAll(guess);

        Label defT = new Label(this.def);
        defT.setWrapText(true);

        this.feedback = new Text("Try finding the word");

        HBox buttons = new HBox();
        buttons.setSpacing(50);
        buttons.setAlignment(Pos.CENTER);

        Button hint = new Button("Hint");
        hint.setPrefSize(120, 50);
        hint.setOnAction(event -> {
            if (!fullHoles()) {
                int guessIndex = getRandomIndex();
                Hole selectedHole = guess.get(guessIndex);
                Letter matchingLetter = null;
                for (Letter letter : pool) {
                    if (letter.getLetter().equals(word.toUpperCase().charAt(guessIndex) + "")) {
                        if (letter.getFit() == null) {
                            matchingLetter = letter;
                            break;
                        }
                    }
                }
                if (matchingLetter == null) {
                    feedback.setText("Hint has failed, you may have made a mistake, press the 'Reset' button to retry");
                } else {
                    selectedHole.setLetter(matchingLetter.getLetter());
                    selectedHole.setLock(true);
                    matchingLetter.setFit(selectedHole);
                    this.notifyGuess();
                    matchingLetter.setOpacity(0);
                    matchingLetter.setMouseTransparent(true);
                }
            } else {
                feedback.setText("You already have completed the word, check your guess with the 'Guess' button");
            }
        });

        Button reset = new Button("Reset");
        reset.setPrefSize(120, 50);
        reset.setOnAction(event -> {
            this.getChildren().clear();
            this.loadPage();
        });

        guesss = new Button("Guess");
        guesss.setPrefSize(120, 50);
        guesss.setOnAction(event -> {
            if (checkWord()) {
                Constant.record.add(word);
                feedback.setText("Congratulations ! You found the word !");
                skip.setText("Next word");
            } else {
                feedback.setText("This isn't the right word, try again !");
            }
        });
        guesss.setDisable(true);

        this.skip = new Button("Skip word");
        this.skip.setPrefSize(120, 50);
        this.skip.setOnAction(event -> {
            try {
                this.getChildren().clear();
                Document doc = Tools.random();
                this.initialize(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        buttons.getChildren().addAll(hint, reset, guesss, skip);

        this.getChildren().addAll(desc, letters, holes, defT, feedback, buttons);
    }

    public boolean checkWord() {
        boolean b = true;
        Hole current;
        int n = this.word.length();
        for (int i=0; i<n; i++) {
            current = this.guess.get(i);
            if (!current.isLock()) {
                b = false;
            } else {
                char c = current.getLetter().charAt(0);
                if (word.charAt(i) != c) {
                    b = false;
                }
            }
        }
        return b;
    }

    public void randomize() {
        int n = word.length();
        random = new ArrayList<>();
        for (int i=0; i<n; i++) {
            random.add("" + word.charAt(i));
        }
        Collections.shuffle(random);
        while (evaluate(random).equals(word))
            Collections.shuffle(random);
    }

    public String evaluate(List<String> str) {
        StringBuilder eval = new StringBuilder();
        for (String s : str) {
            eval.append(s);
        }
        return eval.toString();
    }

    public void notifyGuess() {
        if(fullHoles()) {
            guesss.setDisable(false);
        }
    }

    public boolean fullHoles() {
        boolean b = true;
        for (Hole hole : guess) {
            if (!hole.isLock()) {
                b = false;
                break;
            }
        }
        return b;
    }

    private int getRandomIndex() {
        Random r = new Random();
        int i = r.nextInt(word.length());
        while (guess.get(i).isLock()) {
            i = r.nextInt(word.length());
        }
        return i;
    }

}
