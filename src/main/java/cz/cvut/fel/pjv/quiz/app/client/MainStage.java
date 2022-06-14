package cz.cvut.fel.pjv.quiz.app.client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main Stage created with run of application
 */
public class MainStage {

    private static final Logger logger = Logger.getLogger(MainStage.class.getName());

    public void init(Stage primaryStage, String username) {

        Button play = initButton();
        play.setText("PLAY");
        Button settings = initSettingsImg();
        Button back = SettingsScene.initBackImg();
        back.setVisible(false);

        settings.setStyle("-fx-background-color: transparent;");

        play.setOnAction(e -> {
            ChooseScene chooseScene = new ChooseScene();
            chooseScene.init(primaryStage, username);
        });

        settings.setOnAction(e -> {
            SettingsScene settingsScene = new SettingsScene();
            settingsScene.init(primaryStage, username);
        });

        play.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().setAll(play);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(35);

        BorderPane borderPane = new BorderPane();

        borderPane.rightProperty().setValue(settings);
        borderPane.leftProperty().setValue(back);
        borderPane.centerProperty().setValue(vBox);
        borderPane.setStyle("-fx-background-color: #222222;");

        Scene scene = new Scene(borderPane, 1024, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Inits and sets up view of settings button with image
     * @return imaged settings button
     */
    public static Button initSettingsImg(){
        ImageView settingsImgView;
        Image settingsImg = null;

        Button settings = new Button();

        try {
            settingsImg = new Image("/graphics/outline_settings_white_24dp.png");

        } catch (Exception e) {
            logger.log(Level.SEVERE,"Cannot find the image");
        }

        settingsImgView = new ImageView(settingsImg);

        settingsImgView.setFitHeight(30);
        settingsImgView.setFitWidth(30);
        settings.setGraphic(settingsImgView);
        settings.setStyle("-fx-background-color: transparent;");
        settings.setPrefSize(75,75);

        return settings;
    }

    /**
     * Inits and sets up view of non image buttons
     * @return set up view of button
     */
    public static Button initButton(){

        Button button = new Button();
        button.setStyle("-fx-background-radius: 10;-fx-background-color: #203354; -fx-text-fill: #dddddd");
        button.setMinSize(100,50);
        return button;
    }

}
