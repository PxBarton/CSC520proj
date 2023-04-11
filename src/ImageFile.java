
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;


public class ImageFile {
    FileInputStream fileStream = new FileInputStream("src\\waveworld5.png");
    Image img = new Image(fileStream);


    public ImageFile() throws FileNotFoundException {
    }
}

//save image file
//import java.io.*;

/**

public class SaveImageFile {
    public static void main(String[] args) {
        try {
            FileOutputStream fos = new FileOutputStream("src\\waveworld5.png");
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.close();
            System.out.println("File successfully saved. ");
        }
        catch (IOExcetpion e){
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
 */
