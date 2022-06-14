package cz.cvut.fel.pjv.quiz.app.client;

import cz.cvut.fel.pjv.quiz.app.server.QuizServerImpl;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Scene sets up user's data
 */
public class SettingsScene {

    private static final Logger logger = Logger.getLogger(MainStage.class.getName());

    public void init(Stage primaryStage, String username){

        Button back = initBackImg();
        QuizServerImpl quizServer = new QuizServerImpl();
        Button settings = MainStage.initSettingsImg();
        settings.setVisible(false);

        TextField name = ChooseScene.initText();
        TextField volume = ChooseScene.initText();
        name.setDisable(false);
        volume.setDisable(false);
        volume.setPromptText("Music volume");
        name.setPromptText("Username");

        back.setOnAction(e -> {
            MainStage mainStage = new MainStage();
            if(name.getText().equals("")){
                mainStage.init(primaryStage,name.getText());
            }else{
                if(volume.getText().equals("")){
                    quizServer.addUser(name.getText());
                }else{
                    quizServer.addUser(name.getText());
                    quizServer.updateMusicVolume(name.getText(), Integer.parseInt(volume.getText()));
                }
                mainStage.init(primaryStage,name.getText());
            }
        });


        name.setText(username);

        VBox options = new VBox();
        options.getChildren().addAll(name,volume);
        options.setAlignment(Pos.CENTER);
        options.setSpacing(35);

        BorderPane borderPane = new BorderPane();
        borderPane.leftProperty().setValue(back);
        borderPane.centerProperty().setValue(options);
        borderPane.rightProperty().setValue(settings);
        borderPane.setStyle("-fx-background-color: #222222;");

        Scene scene = new Scene(borderPane,1024,720);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Inits and sets up view of back button with image
     * @return imaged back button
     */
    public static Button initBackImg(){
        ImageView backImgView;
        Image backImg = null;

        Button back = new Button();

        try {
            backImg = new Image("/graphics/outline_arrow_back_white_24dp.png");

        } catch (Exception e) {
            logger.log(Level.SEVERE,"Cannot find the image");
        }

        backImgView = new ImageView(backImg);

        backImgView.setFitHeight(30);
        backImgView.setFitWidth(30);
        back.setGraphic(backImgView);
        back.setStyle("-fx-background-color: transparent;");
        back.setPrefSize(75,75);

        return back;
    }

}
