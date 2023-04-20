
import java.io.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.embed.swing.SwingFXUtils;
import java.util.logging.Logger;
import java.util.logging.Level;


public class ImageFile {
    FileInputStream fileStream = new FileInputStream("src\\waveworld5.png");
    Image img;


    public ImageFile(Image source) throws FileNotFoundException {
        img = source;
    }

    public void setImage(Image image) {
        img = image;
        System.out.println("image file set" );
    }

    public void setImage(WritableImage image) {
        img = image;
    }



    public Image getImage() {
        System.out.println("image used");
        return img;

    }

    static File fileIn;
    static final FileChooser fileChooser = new FileChooser();
    static ImageView display = new ImageView();

    public void openFile(Stage stage, ImageView view) throws FileNotFoundException {
        setExtFilters();
        fileIn = fileChooser.showOpenDialog(stage);
        fileStream = new FileInputStream(fileIn);
        Image newImage = new Image(fileStream);
        img = newImage;

        view.setImage(img);
    }

    private void setExtFilters() {
        ImageFile.fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPEG Files", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG Files", ".png"),
            new FileChooser.ExtensionFilter("GIF Files", "*.gif")
        );
    }

    //Save Image File
    public static void saveFile(/*ImageFile content,*/ Stage stage, ImageView view) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.setInitialDirectory(new File("C://"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            String name = file.getName();
            String extension = name.substring(1+name.lastIndexOf(".")).toLowerCase();
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(view.getImage(),
                        null), extension /*"png"*/, file);
            } catch (IOException ex) {
                Logger.getLogger(
                        FileChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

