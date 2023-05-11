import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import org.opencv.core.Core;

public class App extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException {

        // create a new ImageFile, this is our class

        // test filters
        String path = "src\\waveworld5.png";
        FileInputStream fileStream = new FileInputStream(path);
        Image testImage = new Image(fileStream);
        ImageDisplay testDisplay = new ImageDisplay();
        testDisplay.setImage(testImage);
        ImageFilter test = new ImageFilter();
        try {
            test.filterTests(testDisplay);
        }
        catch (IOException e) {};

        //ImageFile testFile = new ImageFile(testImage);
        //file.filePath = path;
        // img is a public class variable of class ImageFile
        // img holds the actual image data
        // Image is the JavaFX image class object
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //blank default image when opening app
        WritableImage blank = new WritableImage(500, 500);
        ImageFile file = new ImageFile(blank);
        ImageDisplay imageDisplay = new ImageDisplay();
        //imageDisplay.setImage(file.getImage());
        //ImageView view = new ImageView(file.getImage());
        imageDisplay.setImage(blank);
        ImageView view = new ImageView(blank);
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

        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(60, 10, 0, 10));

        final VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setSpacing(10);
        vbox2.setPadding(new Insets(100, 10, 0, 10));

        // The main area under the menu
        // Image on the left, image size & display slider on the right
        final HBox mainHbox = new HBox();
        mainHbox.setAlignment(Pos.CENTER);
        mainHbox.setSpacing(20);
        mainHbox.setPadding(new Insets(5, 10, 5, 10));

        // HBox for file name
        final HBox field1 = new HBox();
        field1.setAlignment(Pos.CENTER);
        field1.setSpacing(20);
        field1.setPadding(new Insets(5, 10, 5, 10));

        Label fileNameLabel1 = new Label("File Name: ");
        Label fileNameLabel2 = new Label(file.filePath);

        // HBox for image width
        final HBox field2 = new HBox();
        field2.setAlignment(Pos.CENTER);
        field2.setSpacing(20);
        field2.setPadding(new Insets(5, 10, 5, 10));

        Label fileWidthLabel1 = new Label("Width: ");
        Label fileWidthLabel2 = new Label(String.valueOf(file.width));

        // HBox for image height
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

        // open a file
        MenuItem openFile = new MenuItem("Open");
        openFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
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

        // saving a file
        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // code to save a file would go here
                file.saveFile(stage, view);

            }
        });

        // closing a file
        MenuItem closeFile = new MenuItem("Close");
        closeFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            }
        });

        // places the submenu MenuItems in the dropdown of the Menu
        menuFile.getItems().addAll(openFile, saveFile, closeFile);

        // --- Menu Edit
        Menu menuImage = new Menu("Image");

        // change the size of an image
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

        // flips the image vertically
        MenuItem flipVert = new MenuItem("Flip Vertical");
        flipVert.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Stage flipWindow = new Stage();

                try {
                    VBox flipBox = FileUtilities.flipImage(imageDisplay, file, flipWindow, view);
                    Scene resizeScene = new Scene(flipBox, 600, 600);
                    flipWindow.setScene(resizeScene);
                    flipWindow.show();
                }
                catch (IOException e) {}


                System.out.println(file.width);
            }
        });

        menuImage.getItems().addAll(imageSize, flipVert);

        // --- Menu Filter
        Menu menuFilter = new Menu("Filter");

        // blur an image
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

        // change the brightness and contrast of an image
        MenuItem imageBrightness = new MenuItem("Brightness/Contrast");
        imageBrightness.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // filter, popup
                Stage brightnessWindow = new Stage();
                ImageFilter brightnessFilter = new ImageFilter();

                try {

                    VBox filterBox = brightnessFilter.brightness(imageDisplay, file, brightnessWindow, view);
                    Scene blurScene = new Scene(filterBox, 600, 600);
                    brightnessWindow.setScene(blurScene);
                    brightnessWindow.show();


                }
                catch (IOException e) {}
                //display.setImage(file.getImage());

            }
        });

        // change the HSV settings of an image
        MenuItem imageHSV = new MenuItem("Hue Saturation Value ");
        imageHSV.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // filter, popup
                Stage hsvWindow = new Stage();
                ImageFilter hsvFilter = new ImageFilter();

                try {

                    VBox filterBox = hsvFilter.hueSatVal(imageDisplay, file, hsvWindow, view);
                    Scene desatScene = new Scene(filterBox, 600, 600);
                    hsvWindow.setScene(desatScene);
                    hsvWindow.show();


                }
                catch (IOException e) {}
                //display.setImage(file.getImage());

            }
        });

        // desaturates an image
        // does not actually convert color model to greyscale
        MenuItem imageDesat = new MenuItem("Desaturate");
        imageDesat.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // filter, popup
                Stage desatWindow = new Stage();
                ImageFilter desatFilter = new ImageFilter();

                try {

                    VBox filterBox = desatFilter.desaturate(imageDisplay, file, desatWindow, view);
                    Scene desatScene = new Scene(filterBox, 600, 600);
                    desatWindow.setScene(desatScene);
                    desatWindow.show();


                }
                catch (IOException e) {}
                //display.setImage(file.getImage());

            }
        });

        // inverts the RGB colors of an image
        MenuItem imageInvert = new MenuItem("Invert");
        imageInvert.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // filter, popup
                Stage invertWindow = new Stage();
                ImageFilter invertFilter = new ImageFilter();

                try {

                    VBox filterBox = invertFilter.invert(imageDisplay, file, invertWindow, view);
                    Scene invertScene = new Scene(filterBox, 600, 600);
                    invertWindow.setScene(invertScene);
                    invertWindow.show();


                }
                catch (IOException e) {}
                //display.setImage(file.getImage());

            }
        });



        menuFilter.getItems().addAll(blurImage, imageBrightness, imageDesat, imageInvert);

        // combines the Menus on the MenuBar
        menuBar.getMenus().addAll(menuFile, menuImage, menuFilter);

        // slider for the display size
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

        // VBox for the display size slider
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

        // left VBox containing the image in the scrollpane
        vbox.getChildren().addAll(sp, field1);

        // right VBox containing image size info and slider
        vbox2.getChildren().addAll(field2, field3, zoomBox);

        // HBox containing laft and right VBoxes
        mainHbox.getChildren().addAll(vbox, vbox2);

        // combines the menuBar, the Vbox and the ImageView
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, mainHbox);
        stage.setScene(scene);
        stage.show();
    }

    public class Main {
        public void main(String[] args) {
            launch(args);
        }
    }

}