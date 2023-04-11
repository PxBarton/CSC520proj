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
//        ImageView display = new ImageView(imgFile);
//        display.setX(50);
//        display.setY(25);
//        display.setFitHeight(400);
//        display.setFitWidth(500);
//        display.setPreserveRatio(true);
        ImageFile.updateD(stage, imgFile);

//        stage.setTitle("Menu Sample");
//        Scene scene = new Scene(new VBox(), 1000, 800);
//
//        MenuBar menuBar = new MenuBar();
//
//        //GridPane pane = new GridPane();
//        final VBox vbox = new VBox();
//        vbox.setAlignment(Pos.CENTER);
//        vbox.setSpacing(10);
//        vbox.setPadding(new Insets(0, 10, 0, 10));
//
//
//        // Menu creates each option across the top menu bar
//        Menu menuFile = new Menu("File");
//
//        // MenuItem creates the submenu entries in a dropdown
//        // visible when the Menu is clicked
//        MenuItem openFile = new MenuItem("Open");
//        openFile.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                // code to open a file with a file picker would go here
//                // maybe using methods from ImageFile class
//                try {
//                    ImageFile.openFile(stage);
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//
//        MenuItem saveFile = new MenuItem("Save");
//        saveFile.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                // code to save a file would go here
//
//            }
//        });
//
//        MenuItem closeFile = new MenuItem("Close");
//        closeFile.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//            }
//        });
//
//        // places the submenu MenuItems in the dropdown of the Menu
//        menuFile.getItems().addAll(openFile, saveFile, closeFile);
//
//        // --- Menu Edit
//        Menu menuImage = new Menu("Image");
//
//        // --- Menu View
//        Menu menuFilter = new Menu("Filter");
//
//        // combines the Menus on the MenuBar
//        menuBar.getMenus().addAll(menuFile, menuImage, menuFilter);
//
//        // combines the menuBar, the Vbox and the ImageView
//        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, vbox, display);
//        stage.setScene(scene);
//        stage.show();
    }
    //public static void main(String[] args) {
    //    launch(args);
    // }
}