import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class App extends Application {
    //public App() throws FileNotFoundException {
    //}


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        // just for basic test run, to display an image from a file
        // create a new ImageFile, this is our class
        String path = "src\\waveworld5.png";
        FileInputStream fileStream = new FileInputStream(path);
        Image source = new Image(fileStream);
        ImageFile file = new ImageFile(source);
        file.filePath = path;
        // img is a public class variable of class ImageFile
        // img holds the actual image data
        // Image is the JavaFX image class object
        ImageDisplay imageDisplay = new ImageDisplay();
        imageDisplay.setImage(file.getImage());
        ImageView view = new ImageView(file.getImage());
        view.setX(50);
        view.setY(25);
        int viewHeight = 500;
        int viewWidth = 700;
        view.setFitHeight(viewHeight);
        view.setFitWidth(viewWidth);
        view.setPreserveRatio(true);

        final ScrollPane sp = new ScrollPane();
        sp.setPrefViewportHeight(600);
        sp.setPrefViewportWidth(800);
        sp.setVmax(1500);
        sp.setHmax(1500);

        stage.setTitle("Image Editor App");
        Scene scene = new Scene(new VBox(),1200, 800);

        MenuBar menuBar = new MenuBar();

        //GridPane pane = new GridPane();
        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(100, 10, 0, 10));

        final VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setSpacing(10);
        vbox2.setPadding(new Insets(100, 10, 0, 10));

        final HBox mainHbox = new HBox();
        mainHbox.setAlignment(Pos.CENTER);
        mainHbox.setSpacing(20);
        mainHbox.setPadding(new Insets(5, 10, 5, 10));

        final HBox field1 = new HBox();
        field1.setAlignment(Pos.CENTER);
        field1.setSpacing(20);
        field1.setPadding(new Insets(5, 10, 5, 10));

        Label fileNameLabel1 = new Label("File Name: ");
        Label fileNameLabel2 = new Label(file.filePath);

        final HBox field2 = new HBox();
        field2.setAlignment(Pos.CENTER);
        field2.setSpacing(20);
        field2.setPadding(new Insets(5, 10, 5, 10));

        Label fileWidthLabel1 = new Label("Width: ");
        Label fileWidthLabel2 = new Label(String.valueOf(file.width));

        final HBox field3 = new HBox();
        field3.setAlignment(Pos.CENTER);
        field3.setSpacing(20);
        field3.setPadding(new Insets(5, 10, 5, 10));

        Label fileHeightLabel1 = new Label("Height: ");
        Label fileHeightLabel2 = new Label(String.valueOf(file.height));

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
                fileNameLabel2.setText(file.filePath);
                fileWidthLabel2.setText(String.valueOf(file.width));
                fileHeightLabel2.setText(String.valueOf(file.height));
            }
        });


        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // code to save a file would go here

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

        MenuItem imageSize = new MenuItem("Image Size");
        imageSize.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Stage resizeWindow = new Stage();

                try {
                    VBox resizeBox = FileUtilities.resizeImage(file, resizeWindow, fileWidthLabel2, fileHeightLabel2);
                    Scene resizeScene = new Scene(resizeBox, 400, 300);
                    resizeWindow.setScene(resizeScene);
                    resizeWindow.show();
                }
                catch (IOException e) {}


                System.out.println(file.width);
            }
        });


        MenuItem canvasSize = new MenuItem("Canvas Size");

        menuImage.getItems().addAll(imageSize, canvasSize);

        // --- Menu Filter
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

        Slider sliderZoom = new Slider(10, 500, 100);
        sliderZoom.setShowTickLabels(true);
        sliderZoom.setShowTickMarks(true);
        sliderZoom.setMajorTickUnit(100);
        sliderZoom.setBlockIncrement(10);

        sliderZoom.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue){
                try {
                    //label2.setText("");
                    view.setFitHeight(viewHeight  * newValue.doubleValue() * .01);
                    view.setFitWidth(viewWidth  * newValue.doubleValue() * .01);
                }
                catch(Exception e) {
                    System.out.println("");
                }
            }
        });

        final VBox zoomBox = new VBox();
        zoomBox.setAlignment(Pos.CENTER);
        zoomBox.setSpacing(10);
        zoomBox.setPadding(new Insets(20, 10, 0, 10));

        Label zoomLabel = new Label("Display Size:");

        field1.getChildren().addAll(fileNameLabel1, fileNameLabel2);
        field2.getChildren().addAll(fileWidthLabel1, fileWidthLabel2);
        field3.getChildren().addAll(fileHeightLabel1, fileHeightLabel2);
        zoomBox.getChildren().addAll(zoomLabel, sliderZoom);

        VBox viewBox = new VBox();
        viewBox.getChildren().addAll(view);
        viewBox.setAlignment(Pos.CENTER);
        sp.setContent(viewBox);
        vbox.getChildren().addAll(sp);

        vbox2.getChildren().addAll(field1, field2, field3, zoomBox);

        mainHbox.getChildren().addAll(vbox, vbox2);

        // combines the menuBar, the Vbox and the ImageView
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, mainHbox);
        stage.setScene(scene);
        stage.show();
    }
    //public static void main(String[] args) {
    //    launch(args);
    // }
}