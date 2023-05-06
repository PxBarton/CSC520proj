
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

public class ImageFilter {


    Mat src;
    Slider slider1;

    VBox vbox = new VBox();

    WritableImage currentImage;

    public void ImageFilter() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //this.img = img;
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));
    }

    public VBox blur(ImageDisplay imageDisplay, ImageFile original, Stage window, ImageView mainDisplay) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Image img = imageDisplay.getImage();
        src = FileUtilities.convertToMat(img);
        WritableImage writableImage = FileUtilities.matToImage(src);

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

            }
        });

        vbox.getChildren().addAll(slider1, imageView, bt1, bt2);
        return vbox;

    }



}