import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageFilter {

    //Image img = new Image;

    Mat src;
    Slider slider1;

    VBox vbox = new VBox();

    public void ImageFilter() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //this.img = img;
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));
    }

    public VBox blur(Image img) throws IOException {
        //Size ksize;
        src = FileUtilities.convertToMat(img);
        //src = Imgcodecs.imread(file);
        WritableImage writableImage = FileUtilities.matToImage(src);
        //Setting the image view;
        ImageView imageView = new ImageView(writableImage);
        imageView.setX(50);
        imageView.setY(25);
        imageView.setFitHeight(400);
        imageView.setFitWidth(550);
        imageView.setPreserveRatio(true);

        Label label1 = new Label("blur amount");
        //Setting the slider
        slider1 = new Slider(0.0, 5, 1);
        slider1.setShowTickLabels(true);
        slider1.setShowTickMarks(true);
        slider1.setMajorTickUnit(1);
        slider1.setBlockIncrement(0.05);

        slider1.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
            try {
                //label2.setText("");
                //amount = newValue.doubleValue();
                Mat dest = new Mat(src.rows(), src.cols(), src.type());
                Imgproc.blur(src, dest, new Size(newValue.doubleValue(), newValue.doubleValue()));
                //Core.addWeighted(src, alpha, dest, beta, 0, dest);
                imageView.setImage(FileUtilities.matToImage(dest));
            }
            catch(Exception e) {
                System.out.println("");
            }
        }
        });
        vbox.getChildren().addAll(slider1, imageView);
        return vbox;
        /*
+        final HBox hbox = new HBox();
+        hbox.setAlignment(Pos.CENTER);
+        hbox.setSpacing(10);
+        hbox.setPadding(new Insets(0, 10, 0, 10));
+        final Popup popup = new Popup();
+        final ImageView popImg = new ImageView(imgFile);
+        hbox.getChildren().add(popImg);
+        popup.getContent().add(hbox);
+        popImg.setFitHeight(400);
+        popImg.setFitWidth(400);
+        popup.show(App.start.stage);
+
+         */

                               /*
+        final Popup popup = new Popup();
+        //final ImageView popupImage = new ImageView(image.getImage());
+        popup.getContent().add(imageView);
+        popup.setOnShown(new EventHandler<WindowEvent>(){
+            @Override
+            public void handle(WindowEvent t) {
+                popupImage.setFitHeight(400);
+                popupImage.setFitWidth(400);
+
+            }
+        });
+        */
    }


}