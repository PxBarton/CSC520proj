
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.control.Button;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * public class providing image filters
 */
public class ImageFilter {

    Mat src;
    //Slider slider1;

    double alpha;
    double beta;

    VBox vbox = new VBox();
    VBox sliderBox = new VBox();

    WritableImage currentImage;

    /**
     * blur filter
     * @param imageDisplay ImageDisplay image for working image
     * @param original ImageFile image updated upon confirmation
     * @param window the popup window
     * @param mainDisplay the ImageView object from App class, updated upon confirmation
     * @return VBox : VBox containing the image, sliders, and buttons
     * @throws IOException
     */
    public VBox blur(ImageDisplay imageDisplay, ImageFile original, Stage window, ImageView mainDisplay) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Image img = imageDisplay.getImage();
        src = FileUtilities.convertToMat(img);
        WritableImage writableImage = FileUtilities.matToImage(src);
        Slider slider1;

        ImageView imageView = new ImageView(writableImage);
        imageView.setX(50);
        imageView.setY(25);
        imageView.setFitHeight(400);
        imageView.setFitWidth(550);
        imageView.setPreserveRatio(true);

        Label label1 = new Label("blur amount");
        //Setting the slider
        slider1 = new Slider(0.0, 50, 0);
        slider1.setShowTickLabels(true);
        slider1.setShowTickMarks(true);
        slider1.setMajorTickUnit(5);
        slider1.setBlockIncrement(1);
        slider1.setPrefWidth(50.0);

        slider1.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
            try {
                //label2.setText("");
                Mat dest = new Mat(src.rows(), src.cols(), src.type());
                Imgproc.blur(src, dest, new Size(newValue.doubleValue(), newValue.doubleValue()));
                //Core.addWeighted(src, alpha, dest, beta, 0, dest);
                currentImage = FileUtilities.matToImage(dest);
                imageView.setImage(currentImage);
            }
            catch(Exception e) {
                System.out.println("");
            }
        }
        });

        Button bt1 = new Button("OK");
        Button bt2 = new Button("Cancel");

        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                original.setImage(imageView.getImage());
                window.close();
                mainDisplay.setImage(original.getImage());
                imageDisplay.setImage(original.getImage());

            }
        });

        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                window.close();
            }
        });


        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll( imageView, slider1, bt1, bt2);
        return vbox;

    }

    public VBox brightness(ImageDisplay imageDisplay, ImageFile original, Stage window, ImageView mainDisplay) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Image img = imageDisplay.getImage();
        src = FileUtilities.convertToMat(img);
        WritableImage writableImage = FileUtilities.matToImage(src);
        Slider slider1;
        Slider slider2;
        alpha = 1;
        beta = 0;

        ImageView imageView = new ImageView(writableImage);
        imageView.setX(50);
        imageView.setY(25);
        imageView.setFitHeight(400);
        imageView.setFitWidth(550);
        imageView.setPreserveRatio(true);

        Label label1 = new Label("blur amount");
        //Setting the slider
        slider1 = new Slider(-100, 100, 0);
        slider1.setShowTickLabels(true);
        slider1.setShowTickMarks(true);
        slider1.setMajorTickUnit(20);
        slider1.setBlockIncrement(2);
        slider1.setPrefWidth(50.0);

        slider2 = new Slider(0, 2, 1);
        slider2.setShowTickLabels(true);
        slider2.setShowTickMarks(true);
        slider2.setMajorTickUnit(.2);
        slider2.setBlockIncrement(.02);
        slider2.setPrefWidth(50.0);

        slider1.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                try {
                    //label2.setText("");
                    Mat dest = new Mat(src.rows(), src.cols(), src.type());
                    beta = newValue.doubleValue();
                    src.convertTo(dest, -1, alpha, newValue.doubleValue());
                    currentImage = FileUtilities.matToImage(dest);
                    imageView.setImage(currentImage);
                }
                catch(Exception e) {
                    System.out.println("");
                }
            }
        });

        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                try {
                    //label2.setText("");
                    Mat dest = new Mat(src.rows(), src.cols(), src.type());
                    //Imgproc.blur(src, dest, new Size(newValue.doubleValue(), newValue.doubleValue()));
                    //Core.addWeighted(src, alpha, dest, beta, 0, dest);
                    alpha = newValue.doubleValue();
                    src.convertTo(dest, -1, newValue.doubleValue(), beta);
                    currentImage = FileUtilities.matToImage(dest);
                    imageView.setImage(currentImage);
                }
                catch(Exception e) {
                    System.out.println("");
                }
            }
        });

        Button bt1 = new Button("OK");
        Button bt2 = new Button("Cancel");

        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                original.setImage(imageView.getImage());
                window.close();
                mainDisplay.setImage(original.getImage());
                imageDisplay.setImage(original.getImage());

            }
        });

        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                window.close();
            }
        });


        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(imageView, slider1, slider2, bt1, bt2);
        return vbox;

    }

    /**
     * desaturate filter (does not convert to greyscale)
     * @param imageDisplay
     * @param original
     * @param window
     * @param mainDisplay
     * @return
     * @throws IOException
     */
    public VBox desaturate(ImageDisplay imageDisplay, ImageFile original, Stage window, ImageView mainDisplay) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Image img = imageDisplay.getImage();
        src = FileUtilities.convertToMat(img);
        WritableImage writableImage = FileUtilities.matToImage(src);
        Button bt1 = new Button("OK");
        Button bt2 = new Button("Cancel");

        ImageView imageView = new ImageView(writableImage);
        imageView.setX(50);
        imageView.setY(25);
        imageView.setFitHeight(400);
        imageView.setFitWidth(550);
        imageView.setPreserveRatio(true);

        Label label1 = new Label("convert to greyscale:");

        Mat dest = new Mat(src.rows(), src.cols(), src.type());
        Imgproc.cvtColor(src, dest, Imgproc.COLOR_BGR2GRAY);
        currentImage = FileUtilities.matToImage(dest);
        imageView.setImage(currentImage);

        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                original.setImage(imageView.getImage());
                window.close();
                mainDisplay.setImage(original.getImage());
                imageDisplay.setImage(original.getImage());

            }
        });

        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                window.close();
            }
        });

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(imageView, bt1, bt2);
        return vbox;
    }


    public VBox invert(ImageDisplay imageDisplay, ImageFile original, Stage window, ImageView mainDisplay) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Image img = imageDisplay.getImage();
        src = FileUtilities.convertToMat(img);
        WritableImage writableImage = FileUtilities.matToImage(src);
        Button bt1 = new Button("OK");
        Button bt2 = new Button("Cancel");

        ImageView imageView = new ImageView(writableImage);
        imageView.setX(50);
        imageView.setY(25);
        imageView.setFitHeight(400);
        imageView.setFitWidth(550);
        imageView.setPreserveRatio(true);

        Label label1 = new Label("convert to greyscale:");

        Mat dest = new Mat(src.rows(), src.cols(), src.type());
        Core.bitwise_not(src, dest);
        currentImage = FileUtilities.matToImage(dest);
        imageView.setImage(currentImage);

        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                original.setImage(imageView.getImage());
                window.close();
                mainDisplay.setImage(original.getImage());
                imageDisplay.setImage(original.getImage());

            }
        });

        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                window.close();
            }
        });

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(imageView, bt1, bt2);
        return vbox;
    }


    public VBox hueSatVal(ImageDisplay imageDisplay, ImageFile original, Stage window, ImageView mainDisplay) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Image img = imageDisplay.getImage();
        src = FileUtilities.convertToMat(img);
        WritableImage writableImage = FileUtilities.matToImage(src);
        Slider slider1;
        Slider slider2;
        alpha = 1;
        beta = 0;

        ImageView imageView = new ImageView(writableImage);
        imageView.setX(50);
        imageView.setY(25);
        imageView.setFitHeight(400);
        imageView.setFitWidth(550);
        imageView.setPreserveRatio(true);

        Label label1 = new Label("blur amount");
        //Setting the slider
        slider1 = new Slider(-100, 100, 0);
        slider1.setShowTickLabels(true);
        slider1.setShowTickMarks(true);
        slider1.setMajorTickUnit(20);
        slider1.setBlockIncrement(2);
        slider1.setPrefWidth(50.0);

        slider2 = new Slider(0, 2, 1);
        slider2.setShowTickLabels(true);
        slider2.setShowTickMarks(true);
        slider2.setMajorTickUnit(.2);
        slider2.setBlockIncrement(.02);
        slider2.setPrefWidth(50.0);

        slider1.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                try {
                    //label2.setText("");
                    Mat dest = new Mat(src.rows(), src.cols(), src.type());
                    //Imgproc.blur(src, dest, new Size(newValue.doubleValue(), newValue.doubleValue()));
                    //Core.addWeighted(src, alpha, dest, beta, 0, dest);
                    beta = newValue.doubleValue();
                    src.convertTo(dest, -1, alpha, newValue.doubleValue());
                    currentImage = FileUtilities.matToImage(dest);
                    imageView.setImage(currentImage);
                }
                catch(Exception e) {
                    System.out.println("");
                }
            }
        });

        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                try {
                    //label2.setText("");
                    Mat dest = new Mat(src.rows(), src.cols(), src.type());
                    //Imgproc.blur(src, dest, new Size(newValue.doubleValue(), newValue.doubleValue()));
                    //Core.addWeighted(src, alpha, dest, beta, 0, dest);
                    alpha = newValue.doubleValue();
                    src.convertTo(dest, -1, newValue.doubleValue(), beta);
                    currentImage = FileUtilities.matToImage(dest);
                    imageView.setImage(currentImage);
                }
                catch(Exception e) {
                    System.out.println("");
                }
            }
        });

        Button bt1 = new Button("OK");
        Button bt2 = new Button("Cancel");

        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                original.setImage(imageView.getImage());
                window.close();
                mainDisplay.setImage(original.getImage());
                imageDisplay.setImage(original.getImage());

            }
        });

        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                window.close();
            }
        });


        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(slider1, slider2, imageView, bt1, bt2);
        return vbox;

    }

    /**
     * tests for each filter
     * @param imageDisplay
     * @return
     * @throws IOException
     */
    public boolean filterTests(ImageDisplay imageDisplay) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Image img = imageDisplay.getImage();
        src = FileUtilities.convertToMat(img);
        WritableImage writableImage = FileUtilities.matToImage(src);

        Mat dest = new Mat(src.rows(), src.cols(), src.type());
        Imgproc.blur(src, dest, new Size(2, 2));
        Imgproc.blur(src, dest, new Size(20, 20));
        Imgproc.blur(src, dest, new Size(20, 100));

        src.convertTo(dest, -1, alpha, 0);
        src.convertTo(dest, -1, alpha, -100);
        src.convertTo(dest, -1, alpha, -255);
        src.convertTo(dest, -1, alpha, 100);
        src.convertTo(dest, -1, alpha, 255);

        src.convertTo(dest, -1, 1.0, beta);
        src.convertTo(dest, -1, 0.0, beta);
        src.convertTo(dest, -1, -1.0, beta);
        src.convertTo(dest, -1, 2.0, beta);
        src.convertTo(dest, -1, 3.0, beta);

        Imgproc.cvtColor(src, dest, Imgproc.COLOR_BGR2GRAY);

        Core.bitwise_not(src, dest);

        currentImage = FileUtilities.matToImage(dest);
        System.out.println("Filter Tests Successful");
        return true;
    }

}