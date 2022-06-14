package cz.cvut.fel.pjv.quiz.app.client;

import cz.cvut.fel.pjv.quiz.app.server.QuizServerImpl;
import cz.cvut.fel.pjv.quiz.app.server.model.Decade;
import cz.cvut.fel.pjv.quiz.app.server.model.Genre;
import cz.cvut.fel.pjv.quiz.app.server.model.QuizType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Scene for parameterizing games by user
 */
public class ChooseScene {

    private static int currentPosition = 0;
    private static int currentPosition2 = 0;
    private static int currentPosition3 = 0;
    private static int currentPosition4 = 0;
    private static int currentPosition5 = 0;
    private static final TextField option = initText();
    private static final TextField option2 = initText();
    private static final TextField option3 = initText();
    private static final TextField option4 = initText();
    private static final TextField option5 = initText();
    private static Decade[] decadesList;
    private static Genre[] genresList;
    private static QuizType[] quizTypesList;
    private static final int[] numOfQuestions = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int[] levels = new int[]{1,2,3,4};
    private static HBox decades;
    private static HBox genres;
    private static HBox diff;
    private static final java.util.logging.Logger logger = Logger.getLogger(MainStage.class.getName());

    public void init(Stage primaryStage,String username) {

        Button settings = MainStage.initSettingsImg();
        Button back = SettingsScene.initBackImg();
        Button play = MainStage.initButton();

        Button prev = initPrevImg();
        Button next = initNextImg();

        Button prev2 = initPrevImg();
        Button next2 = initNextImg();

        Button prev3 = initPrevImg();
        Button next3 = initNextImg();

        Button prev4 = initPrevImg();
        Button next4 = initNextImg();

        Button prev5 = initPrevImg();
        Button next5 = initNextImg();

        decades = new HBox();
        genres = new HBox();
        diff = new HBox();
        HBox types = new HBox();
        HBox numbers = new HBox();

        VBox options = new VBox();

        play.setText("PLAY");

        Random random = new Random();
        QuizServerImpl quizServer = new QuizServerImpl();
        decadesList = quizServer.getDecades();
        genresList = quizServer.getGenres();
        quizTypesList = quizServer.getQuizTypes();

        //for rendering correct item of lists
        currentPosition = 0;
        currentPosition2 = 0;
        currentPosition3 = 0;
        currentPosition4 = 0;
        currentPosition5 = 0;

        settings.setOnAction(e -> {
            SettingsScene settingsScene = new SettingsScene();
            settingsScene.init(primaryStage, username);
        });
        back.setOnAction(e -> {
            MainStage mainStage = new MainStage();
            mainStage.init(primaryStage, username);
        });

        option.setText(String.valueOf(quizTypesList[0]));
        option2.setText(String.valueOf(decadesList[0]));
        option3.setText(String.valueOf(genresList[0]));
        option4.setText(String.valueOf(numOfQuestions[0]));
        option5.setText("Level " + levels[0]);
        option.setDisable(true);
        option2.setDisable(true);
        option3.setDisable(true);
        option4.setDisable(true);
        option5.setDisable(true);

        //actions for next/previous buttons
        next.setOnAction(e -> next("Types"));
        prev.setOnAction(e -> previous("Types"));
        next2.setOnAction(e -> next("Decades"));
        prev2.setOnAction(e -> previous("Decades"));
        next3.setOnAction(e -> next("Genres"));
        prev3.setOnAction(e -> previous("Genres"));
        next4.setOnAction(e -> next("Numbers"));
        prev4.setOnAction(e -> previous("Numbers"));
        next5.setOnAction(e -> next("Levels"));
        prev5.setOnAction(e -> previous("Levels"));

        check.setCycleCount(100);
        check.play();

        play.setOnAction(e -> {
            check.stop();

            //choosing random from lists if is chosen random option
            if(currentPosition3 == 6) {
                currentPosition3 = random.nextInt(7);
            }
            if(currentPosition == 7){
                currentPosition = random.nextInt(5);
            }
            if(currentPosition2 == 7){
                currentPosition2 = random.nextInt(5);
            }

            PlayScene playScene = new PlayScene();
            playScene.init(primaryStage,quizTypesList[currentPosition],decadesList[currentPosition2],genresList[currentPosition3], numOfQuestions[currentPosition4],username,levels[currentPosition5]);
        });

        types.getChildren().addAll(prev,option,next);
        decades.getChildren().addAll(prev2,option2,next2);
        genres.getChildren().addAll(prev3,option3,next3);
        numbers.getChildren().addAll(prev4,option4,next4);
        diff.getChildren().addAll(prev5,option5,next5);
        decades.setAlignment(Pos.CENTER);
        genres.setAlignment(Pos.CENTER);
        types.setAlignment(Pos.CENTER);
        numbers.setAlignment(Pos.CENTER);
        diff.setAlignment(Pos.CENTER);
        play.setAlignment(Pos.CENTER);

        options.setSpacing(35);

        options.getChildren().addAll(types,decades,genres,numbers,diff,play);
        options.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.centerProperty().setValue(options);
        borderPane.leftProperty().setValue(back);
        borderPane.rightProperty().setValue(settings);
        borderPane.setStyle("-fx-background-color: #222222;");

        Scene scene = new Scene(borderPane,1024,720);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Changes option for each list
     * Creates horizontal scrolling menu
     * @param type of list we are changing option
     */
    public static void next(String type) {
        switch (type) {
            case "Types":
                currentPosition = (currentPosition + 1) % quizTypesList.length;
                option.setText(String.valueOf(quizTypesList[currentPosition]));
                break;
            case "Decades":
                currentPosition2 = (currentPosition2 + 1) % decadesList.length;
                option2.setText(String.valueOf(decadesList[currentPosition2]));
                break;
            case "Genres":
                currentPosition3 = (currentPosition3 + 1) % genresList.length;
                option3.setText(String.valueOf(genresList[currentPosition3]));
                break;
            case "Numbers":
                currentPosition4 = (currentPosition4 + 1) % numOfQuestions.length;
                option4.setText(String.valueOf(numOfQuestions[currentPosition4]));
                break;
            default:
                currentPosition5 = (currentPosition5 + 1) % levels.length;
                option5.setText("Level " + levels[currentPosition5]);
                break;
        }
    }

    /**
     * Changes option for each list
     * Creates horizontal scrolling menu
     * Skips to the end of list
     * @param type of list we are changing option
     */
    public static void previous(String type) {
        if(currentPosition < 0) {
            currentPosition = quizTypesList.length;
        }
        if(currentPosition2  < 0) {
            currentPosition2 = decadesList.length;
        }
        if(currentPosition3  < 0){
            currentPosition3 = genresList.length;
        }
        if(currentPosition4  < 0){
            currentPosition4 = numOfQuestions.length;
        }
        if(currentPosition5  < 0){
            currentPosition5 = levels.length;
        }
        switch (type) {
            case "Types":
                currentPosition = (currentPosition - 1) % quizTypesList.length;
                option.setText(String.valueOf(quizTypesList[currentPosition]));
                break;
            case "Decades":
                currentPosition2 = (currentPosition2 - 1) % decadesList.length;
                option2.setText(String.valueOf(decadesList[currentPosition2]));
                break;
            case "Genres":
                currentPosition3 = (currentPosition3 - 1) % genresList.length;
                option3.setText(String.valueOf(genresList[currentPosition3]));
                break;
            case "Numbers":
                currentPosition4 = (currentPosition4 - 1) % numOfQuestions.length;
                option4.setText(String.valueOf(numOfQuestions[currentPosition4]));
                break;
            default:
                currentPosition5 = (currentPosition5 - 1) % levels.length;
                option5.setText("Level " + levels[currentPosition5]);
                break;
        }
    }

    /**
     * Inits and sets up view of previous button with image
     * @return imaged previous button
     */
    public static Button initPrevImg(){
        ImageView prevImgView;
        Image prevImg = null;

        Button prev = new Button();

        try {
            prevImg = new Image("/graphics/outline_chevron_left_white_24dp.png");

        } catch (Exception e) {
            logger.log(Level.SEVERE,"Cannot find the image");
        }

        prevImgView = new ImageView(prevImg);

        prevImgView.setFitHeight(40);
        prevImgView.setFitWidth(40);
        prev.setGraphic(prevImgView);
        prev.setStyle("-fx-background-color: transparent;");

        return prev;
    }

    /**
     * Inits and sets up view of next button with image
     * @return imaged next button
     */
    public static Button initNextImg(){
        ImageView nextImgView;
        Image nextImg = null;

        Button next = new Button();

        try {
            nextImg = new Image("/graphics/outline_chevron_right_white_24dp.png");

        } catch (Exception e) {
            logger.log(Level.SEVERE,"Cannot find the image");
        }

        nextImgView = new ImageView(nextImg);

        nextImgView.setFitHeight(40);
        nextImgView.setFitWidth(40);
        next.setGraphic(nextImgView);
        next.setStyle("-fx-background-color: transparent;");

        return next;
    }

    /**
     * Inits and sets up view of non image text fields
     * @return set up view of text field
     */
    public static TextField initText(){
         TextField text = new TextField();

         text.setStyle("-fx-background-color: #222222;-fx-text-fill: #dddddd ;-fx-font-size: 20;-fx-text-alignment: center;-fx-opacity: 1.0");
         text.setMinSize(175,40);
         text.setAlignment(Pos.CENTER);

         return text;
    }

    /**
     * Checks types of games to disable some parameters
     * which are not necessary for the chosen game
     */
    static Timeline check = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
        if(option.getText().equals(QuizType.GUESSARTIST.toString()) || option.getText().equals(QuizType.GUESSSONG.toString()) || option.getText().equals(QuizType.SINGBY.toString()) || option.getText().equals(QuizType.RANDOM.toString())){
            decades.setDisable(false);
            genres.setDisable(false);
            diff.setDisable(false);
        }else if(option.getText().equals(QuizType.TRIVIA.toString()) || option.getText().equals(QuizType.FINISHLYRIC.toString())){
            diff.setDisable(false);
            decades.setDisable(true);
            genres.setDisable(true);
        }else{
            decades.setDisable(true);
            genres.setDisable(true);
            diff.setDisable(true);
        }
    }));

}
