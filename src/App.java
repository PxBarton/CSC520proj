import java.io.FileNotFoundException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        // just for basic test run, to display an image from a file
        // create a new ImageFile, this is our class
        ImageFile file = new ImageFile();
        // img is a public class variable of class ImageFile
        // img holds the actual image data
        // Image is the JavaFX image class object
        Image imgFile = file.img;
        ImageFile.updateD(stage, imgFile);
    }
}