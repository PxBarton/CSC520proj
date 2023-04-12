import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class ImageFile extends App {
    static FileInputStream fileStream;

    static {
        try {
            fileStream = new FileInputStream("src\\waveworld5.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static Image img = new Image(fileStream);
    static File fileIn, fileOut;
    static final FileChooser fileChooser = new FileChooser();
    static ImageView display = new ImageView();

    public static void openFile(Stage stage) throws FileNotFoundException {
        fileIn = fileChooser.showOpenDialog(stage);
        fileOut = fileIn;
        fileStream = new FileInputStream(fileIn);
        img = new Image(fileStream);
        ImageFile iFile = new ImageFile();
        // img is a public class variable of class ImageFile
        // img holds the actual image data
        // Image is the JavaFX image class object
        Image imgFile = iFile.img;
        updateD(stage, imgFile);
    }

    public static void updateD(Stage stage, Image imgFile) {
        //Setting image to the image view
        display.setImage(imgFile);
        //Setting the image view parameters
        display.setX(50);
        display.setY(25);
        display.setFitHeight(400);
        display.setFitWidth(500);
        display.setPreserveRatio(true);
        //Setting the Scene object
        stage.setTitle("Menu Sample");
        Scene scene = new Scene(new VBox(), 1000, 800);

        MenuBar menuBar = new MenuBar();

        //GridPane pane = new GridPane();
        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));


        // Menu creates each option across the top menu bar
        Menu menuFile = new Menu("File");

        // MenuItem creates the submenu entries in a dropdown
        // visible when the Menu is clicked
        MenuItem openFile = new MenuItem("Open");
        openFile.setOnAction(t -> {
            // code to open a file with a file picker would go here
            // maybe using methods from ImageFile class
            try {
                ImageFile.openFile(stage);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        MenuItem saveFile = new MenuItem("Save");
        saveFile.setOnAction(t -> {
            // code to save a file would go here
            ImageFile.saveImageFile();
        });

        MenuItem closeFile = new MenuItem("Close");
        closeFile.setOnAction(t -> Platform.exit());

        // places the submenu MenuItems in the dropdown of the Menu
        menuFile.getItems().addAll(openFile, saveFile, closeFile);

        // --- Menu Edit
        Menu menuImage = new Menu("Image");

        // --- Menu View
        Menu menuFilter = new Menu("Filter");

        // combines the Menus on the MenuBar
        menuBar.getMenus().addAll(menuFile, menuImage, menuFilter);

        // combines the menuBar, the Vbox and the ImageView
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, vbox, display);
        stage.setScene(scene);
        stage.show();
    }

    //Save Image File
    public static void saveImageFile() {
        try {
            FileOutputStream fos = new FileOutputStream(fileOut);
            String content = "test";
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.close();
            System.out.println("File successfully saved. ");
        }
        catch (IOException e){
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
