import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javafx.scene.image.*;
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
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
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
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //String file ="src\\waveworld5.png";
        //src = Imgcodecs.imread(file);
        //Mat mat = new Mat(20, 20, CvType.CV_8UC3);
        src = FileUtilities.convertToMat(img);
        WritableImage writableImage = FileUtilities.matToImage(src);
        //Setting the image view;
        ImageView imageView = new ImageView(writableImage);
        //ImageView imageView = new ImageView(img);
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


        //vbox.getChildren().add(imageView);

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
    public WritableImage matToImage(Mat image) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, matOfByte);
        //Storing the encoded Mat in a byte array
        byte[] byteArray = matOfByte.toArray();
        //Displaying the image
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = ImageIO.read(in);
        System.out.println("Image Loaded");
        WritableImage writableImage = SwingFXUtils.toFXImage(bufImage, null);
        return writableImage;
    }

    public static Mat convertToMat(Image src) {
        int width = (int)src.getWidth();
        int height = (int)src.getHeight();
        byte[] buffer = new byte[width * height *4];
        PixelReader reader= src.getPixelReader();
        WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
        reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);
        Mat mat = new Mat(height, width, CvType.CV_8U);
        mat.put(0, 0, buffer);
        return mat;
    }

}