import java.io.FileNotFoundException;
import java.io.IOException;
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
    //public App() throws FileNotFoundException {
    //}


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        // just for basic test run, to display an image from a file
        // create a new ImageFile, this is our class
        FileInputStream fileStream = new FileInputStream("src\\waveworld5.png");
        Image source = new Image(fileStream);
        ImageFile file = new ImageFile(source);
        // img is a public class variable of class ImageFile
        // img holds the actual image data
        // Image is the JavaFX image class object
        ImageDisplay imageDisplay = new ImageDisplay();
        imageDisplay.setImage(file.getImage());
        ImageView view = new ImageView(file.getImage());
        view.setX(50);
        view.setY(25);
        view.setFitHeight(400);
        view.setFitWidth(500);
        view.setPreserveRatio(true);

        stage.setTitle("Menu Sample");
        Scene scene = new Scene(new VBox(), 1000, 800);

        MenuBar menuBar = new MenuBar();

        //GridPane pane = new GridPane();
        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));


        // Menu creates each option across the top menu bar
        Menu menuFile = new Menu("File");

        // MenuItem creates the submenu entries in a dropdown
        // visible when the Menu is clicked
        MenuItem openFile = new MenuItem("Open");
        openFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // code to open a file with a file picker would go here
                // maybe using methods from ImageFile class
                try {
                    file.openFile(stage, view);
                }
                catch (FileNotFoundException x) {}
                imageDisplay.setImage(file.getImage());
            }
        });


        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                file.saveFile(/*file, */stage, view);
                System.out.println("Image Saved");

            }
        });

        MenuItem closeFile = new MenuItem("Close");
        closeFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            }
        });

        // places the submenu MenuItems in the dropdown of the Menu
        menuFile.getItems().addAll(openFile, saveFile, closeFile);

        // --- Menu Edit
        Menu menuImage = new Menu("Image");

        // --- Menu View
        Menu menuFilter = new Menu("Filter");

        MenuItem blurImage = new MenuItem("Blur");
        blurImage.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // filter, popup
                Stage blurWindow = new Stage();
                ImageFilter blurImage = new ImageFilter();

                try {

                    VBox filterBox = blurImage.blur(imageDisplay, file, blurWindow, view);
                    Scene blurScene = new Scene(filterBox, 600, 600);
                    blurWindow.setScene(blurScene);
                    blurWindow.show();


                }
                catch (IOException e) {}
                //display.setImage(file.getImage());
            }
        });

        menuFilter.getItems().addAll(blurImage);

        // combines the Menus on the MenuBar
        menuBar.getMenus().addAll(menuFile, menuImage, menuFilter);

        // combines the menuBar, the Vbox and the ImageView
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, vbox, view);
        stage.setScene(scene);
        stage.show();
    }
    //public static void main(String[] args) {
    //    launch(args);
    // }
}