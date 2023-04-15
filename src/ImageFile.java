
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

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
}