package itemsets;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private ChampionService championService;

    @FXML
    private Button startButton;
    @FXML
    private ProgressBar progress;
    @FXML
    private TextField dirInput;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        championService = new ChampionService();
        progress.progressProperty().bind(championService.progressProperty());

        startButton.disableProperty().bind(dirInput.textProperty().isEmpty());
    }

    public void processChampions(ActionEvent e) {
        championService.restart();
    }

    public void leagueChooser(ActionEvent e) {
        Window mainStage = progress.getScene().getWindow();

        File directory = new File("C:\\Riot Games\\League of Legends");

        FileChooser fileChooser = new FileChooser();
        if (directory.exists())
            fileChooser.setInitialDirectory(directory);

        fileChooser.setTitle("LoL Directory (lol.launcher.exe");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Executable (lol.launcher.exe)", "lol.launcher.exe"));

        File launcher = fileChooser.showOpenDialog(mainStage);

        if (launcher != null)
            championService.setLeagueDir(launcher.getParent());

        dirInput.setText(launcher.getParent().toString());


    }
}
