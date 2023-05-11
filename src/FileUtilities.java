import java.io.FileInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;

public class FileUtilities {

    public static Mat convertToMat(Image src) {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        int width = (int)src.getWidth();
        int height = (int)src.getHeight();

        byte[] buffer = new byte[width * height *4];
        PixelReader reader= src.getPixelReader();
        WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
        reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);
        Mat mat = new Mat(height, width, CvType.CV_8UC4);
        mat.put(0, 0, buffer);
        return mat;
    }

    public static WritableImage matToImage(Mat image) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, matOfByte);
        //Storing the encoded Mat in a byte array
        byte[] byteArray = matOfByte.toArray();
        //Displaying the image
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = ImageIO.read(in);
        System.out.println("Image Loaded");
        WritableImage w_Image = SwingFXUtils.toFXImage(bufImage, null);
        return w_Image;
    }

    public static VBox resizeImage(ImageFile original, Stage window, Label wLabel, Label hLabel)
            throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Image image  = original.getImage();
        Mat imageMat = convertToMat(original.getImage());
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        int width = original.width;
        int height = original.height;

        // file path
        final HBox field1 = new HBox();
        field1.setAlignment(Pos.CENTER);
        field1.setSpacing(20);
        field1.setPadding(new Insets(5, 10, 5, 10));

        Text fileNameText1 = new Text("File Name: ");
        Text fileNameText2 = new Text(original.filePath);

        // file width
        final HBox field2 = new HBox();
        field2.setAlignment(Pos.CENTER);
        field2.setSpacing(20);
        field2.setPadding(new Insets(5, 10, 5, 10));

        Text fileWidthLabel1 = new Text("Width: ");
        Text fileWidthLabel2 = new Text(String.valueOf(width));

        // file height
        final HBox field3 = new HBox();
        field3.setAlignment(Pos.CENTER);
        field3.setSpacing(20);
        field3.setPadding(new Insets(5, 10, 5, 10));

        Text fileHeightLabel1 = new Text("Height: ");
        Text fileHeightLabel2 = new Text(String.valueOf(height));

        // text field and set/reset  buttons
        final HBox field4 = new HBox();
        field4.setAlignment(Pos.CENTER);
        field4.setSpacing(20);
        field4.setPadding(new Insets(5, 10, 5, 10));

        TextField textField1 = new TextField("100");
        //TextField textField2 = new TextField();
        textField1.setEditable(true);
        //textField1.setEditable(true);

        Button bt1 = new Button("Set");
        Button bt2 = new Button("Reset");
        Button bt3 = new Button("OK");
        Button bt4 = new Button("Cancel");

        field1.getChildren().addAll(fileNameText1, fileNameText2);
        field2.getChildren().addAll(fileWidthLabel1, fileWidthLabel2);
        field3.getChildren().addAll(fileHeightLabel1, fileHeightLabel2);
        field4.getChildren().addAll(textField1, bt1, bt2);

        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //fileWidthLabel2 = new Text(String.valueOf(finalNewWidth));
                //fileHeightLabel2 = new Text(String.valueOf(finalNewHeight));
                int newWidth = (int)(.01 * width * Integer.valueOf(textField1.getText()));
                int newHeight = (int)(.01 * height * Integer.parseInt(textField1.getText()));
                fileWidthLabel2.setText(String.valueOf(newWidth));
                fileHeightLabel2.setText(String.valueOf(newHeight));
                Mat resized = new Mat();
                Size newSize = new Size(newWidth, newHeight);
                Imgproc.resize(imageMat, resized, newSize);
            }
        });

        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                fileWidthLabel2.setText(String.valueOf(original.width));
                fileHeightLabel2.setText(String.valueOf(original.height));
                Mat resized = new Mat();
                Size newSize = new Size(original.width, original.height);
                Imgproc.resize(imageMat, resized, newSize);
            }
        });

        //int newWidth;
        //newHeight;
        bt3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int newWidth = (int)(.01 * width * Integer.valueOf(textField1.getText()));
                int newHeight = (int)(.01 * height * Integer.parseInt(textField1.getText()));
                fileWidthLabel2.setText(String.valueOf(newWidth));
                fileHeightLabel2.setText(String.valueOf(newHeight));
                Mat resized = new Mat();
                Size newSize = new Size(newWidth, newHeight);
                Imgproc.resize(imageMat, resized, newSize);
                try {
                    WritableImage newImage = matToImage(resized);
                    original.width = (int)newImage.getWidth();
                    original.height = (int)newImage.getHeight();
                    System.out.println(original.width);
                }
                catch (IOException x) {}
                //imageDisplay.setImage(original.getImage());
                wLabel.setText(String.valueOf(original.width));
                hLabel.setText(String.valueOf(original.height));
                window.close();
            }
        });

        bt4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                window.close();
            }
        });

        vbox.getChildren().addAll(field1, field2, field3, field4, bt3, bt4);
        return vbox;
    }



    public static VBox resizeCanvas(ImageDisplay imageDisplay, ImageFile original, Stage window, ImageView mainDisplay)
            throws IOException {
        VBox vbox = new VBox();
        return vbox;
    }
    public static void cropImage(ImageDisplay imageDisplay, ImageFile original, Stage window, ImageView mainDisplay)
            throws IOException {
        Image image  = imageDisplay.getImage();
        Mat imageMat = convertToMat(image);
        WritableImage writableImage = FileUtilities.matToImage(imageMat);
        Rect rect;
    }

    public static VBox flipImage(ImageDisplay img, ImageFile original, Stage window, ImageView mainDisplay) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Image image  = img.getImage();
        Mat src = convertToMat(original.getImage());
        WritableImage writableImage = FileUtilities.matToImage(src);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        Button bt1 = new Button("OK");
        Button bt2 = new Button("Cancel");

        ImageView imageView = new ImageView(writableImage);
        imageView.setX(50);
        imageView.setY(25);
        imageView.setFitHeight(400);
        imageView.setFitWidth(550);
        imageView.setPreserveRatio(true);

        Mat dest = new Mat(src.rows(), src.cols(), src.type());
        Core.flip(src, dest, -1);
        imageView.setImage(FileUtilities.matToImage(dest));



        //int newWidth;
        //newHeight;
        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                original.setImage(imageView.getImage());
                window.close();
                mainDisplay.setImage(original.getImage());
                img.setImage(original.getImage());


            }
        });

        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                window.close();
            }
        });

        vbox.getChildren().addAll(imageView, bt1, bt2);
        return vbox;
    }
}