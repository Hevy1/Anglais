import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;


public class control {

    public boolean lock = false;

    @FXML
    private void released(MouseEvent event) {
        System.out.println("Released circle");
        if(event.getSource() instanceof Circle)
        {
            Circle circle = (Circle)event.getSource();
            circle.setOpacity(1);
            circle.setMouseTransparent(false);
        }
        event.consume();
    }

    @FXML
    private void mouseDragged(MouseEvent event) {
        //System.out.println("Mouse drag detected");
        Circle circle=null;
        event.setDragDetect(true);

        if(event.getSource() instanceof Circle)
        {

            circle=(Circle)event.getSource();
            circle.setMouseTransparent(true);

            circle.setOpacity(0.25);
            if (!event.isControlDown())
            {
                circle.setCenterX(event.getX());
                circle.setCenterY(event.getY());
            }
        }
        event.consume();
    }

    @FXML
    private void startDrag(MouseEvent event)
    {
        if(event.getSource() instanceof  Circle)
        {
            Circle source=(Circle)event.getSource();
            source.startFullDrag();
        }
        event.consume();
    }

    public void dragOver(MouseDragEvent event) {
        //System.out.println("Dragged over");
        if (event.getGestureSource() instanceof Rectangle) {

        }
        event.consume();
    }


    // In those 2 functions, the Circle is in event.getGestureSource(), the rectangle is in event.getSource()
    public void trueEntered(MouseDragEvent event) {
        System.out.println("Circle entered");
        if (event.getSource() instanceof Rectangle && event.getGestureSource() instanceof Circle) {
            ((Rectangle) event.getSource()).setFill(((Circle) event.getGestureSource()).getFill());
        }
    }

    public void trueExited(MouseDragEvent event) {
        System.out.println("Circle exited");
        if (event.getSource() instanceof Rectangle) {
            ((Rectangle) event.getSource()).setFill(Color.WHITE);
        }
    }

    public void dragReleased(MouseDragEvent event) {
        System.out.println(event.getSource());
        System.out.println(event.getTarget());
        System.out.println(event.getGestureSource());

        if (event.getSource() instanceof Rectangle && event.getGestureSource() instanceof Circle) {
            Circle c = (Circle) event.getGestureSource();
            Rectangle r = (Rectangle) event.getSource();

        }
        event.consume();
    }
}