package jordan;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main entry point for the Jordan application.
 * <p>
 * This class launches the JavaFX GUI using FXML and initializes the primary stage.
 * </p>
 */
public class Main extends Application {

    private final Jordan jordan = new Jordan();

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setTitle("Jordan");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setJordan(jordan);  // inject the Jordan instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
