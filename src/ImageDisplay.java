import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;

public class ImageDisplay {

    private Image source;
    private Image image;

    public void setImage(Image source) {
        this.source = source;
        this.image = source;
    }

    public Image getImage () {
        return this.image;
    }
}