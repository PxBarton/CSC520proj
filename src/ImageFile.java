
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

public class ImageFile {
    FileInputStream fileStream = new FileInputStream("src\\waveworld5.png");
    Image img = new Image(fileStream);


    public ImageFile() throws FileNotFoundException {
    }
}