import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

/**
 * A public class representing the current opened image file
 */
public class ImageFile {

    private FileInputStream fileStream;
    private Image img;
    private WritableImage canvas;
    int width;
    int height;
    int canvasWidth;
    int canvasHeight;

    private File fileIn;
    private FileChooser fileChooser = new FileChooser();
    private ImageView display = new ImageView();

    String filePath;

    /**
     * constructor for ImageFile
     * @param source
     * @throws FileNotFoundException
     */
    public ImageFile(Image source) throws FileNotFoundException {
        img = source;
        width = (int)source.getWidth();
        height = (int)source.getHeight();
        canvasWidth = width;
        canvasHeight = height;
        System.out.println(width + " x " + height);
    }

    /**
     * sets the image for the ImageFile
     * @param image a JavaFX Image object
     */
    public void setImage(Image image) {
        img = image;
        System.out.println("image file set" );
    }

    /**
     * sets the image for the ImageFile
     * @param image a JavaFX WritableImage object
     */
    public void setImage(WritableImage image) {
        img = image;
        System.out.println("image file set" );
    }

    /**
     * returs the current image
     * @return
     */
    public Image getImage() {
        System.out.println("image used");
        return img;

    }

    /**
     * opens a file from a file chooser dialogue
     * @param stage
     * @param view
     * @throws FileNotFoundException
     */
    public void openFile(Stage stage, ImageView view) throws FileNotFoundException {
        fileIn = fileChooser.showOpenDialog(stage);
        fileStream = new FileInputStream(fileIn);
        Image newImage = new Image(fileStream);
        img = newImage;
        filePath = fileIn.getPath();
        width = (int)img.getWidth();
        height = (int)img.getHeight();


        view.setImage(img);
    }

    /**
     * helper function to provide file extension options during save
     */
    private void setExtFilters() {
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Files", ".png"),
                new FileChooser.ExtensionFilter("GIF Files", "*.gif")
        );
    }

    /**
     * save the file using the file chooser dialogue
     * renaming and extension options avaiable
     * @param stage
     * @param view
     */
    public void saveFile(/*ImageFile content,*/ Stage stage, ImageView view) {
        //FileChooser fileChooser = new FileChooser();
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



