package cz.cvut.fel.pjv.quiz.app.client;

import cz.cvut.fel.pjv.quiz.app.server.QuizServerImpl;
import cz.cvut.fel.pjv.quiz.app.server.model.Decade;
import cz.cvut.fel.pjv.quiz.app.server.model.Genre;
import cz.cvut.fel.pjv.quiz.app.server.model.Question;
import cz.cvut.fel.pjv.quiz.app.server.model.QuizType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main scene initializing game
 */
public class PlayScene{

    private static List<Question> questions;
    private static final Button option = MainStage.initButton();
    private static final Button option2 = MainStage.initButton();
    private static final Button option3 = MainStage.initButton();
    private static final Button option4 = MainStage.initButton();
    private static final TextField question = ChooseScene.initText();
    private static final TextField time = ChooseScene.initText();
    private static final TextField name = ChooseScene.initText();
    private static final TextField input = ChooseScene.initText();
    private static int index = 0;
    private static final QuizServerImpl quizServer = new QuizServerImpl();
    private static int seconds = 10;
    private static int differentType = 0;
    private static Stage stage;
    private static QuizType type;
    private static int numOf;
    private static final List<Integer> indexes = Arrays.asList(0,1,2,3);
    private static int correct;
    private static String user;
    private static MediaPlayer mediaPlayer;
    private static boolean stop = false;
    private static final Logger logger = Logger.getLogger(MainStage.class.getName());
    private static int i = 0;
    private static Button player;

    public void init(Stage primaryStage, QuizType quizType, Decade decade, Genre genre, int num, String username, int difficulty){

        name.setDisable(true);
        question.setDisable(true);
        time.setDisable(true);
        option.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
        option4.setVisible(true);

        option.setAlignment(Pos.CENTER);
        option2.setAlignment(Pos.CENTER);
        option3.setAlignment(Pos.CENTER);
        option4.setAlignment(Pos.CENTER);
        question.setAlignment(Pos.CENTER);
        name.setAlignment(Pos.CENTER);

        stage = primaryStage;
        type = quizType;
        numOf = num;
        correct = 0;
        user = username;

        //Shuffle for simulating random rendering correct answers
        Collections.shuffle(indexes);

        player = initPlayer();
        player.setVisible(false);

        question.setPadding(new Insets(20));
        question.setText("Question");

        quizServer.setDecade(decade);
        quizServer.setGenre(genre);
        quizServer.setQuizType(quizType);
        quizServer.setNumOfQuestions(num);
        quizServer.setDifficulty(difficulty);

        questions = quizServer.getQuestions();
        index = 0;

        //rendering options buttons by type of game
        if(quizType == QuizType.SONGAS || quizType == QuizType.SINGBY){
            differentType = 1;
            nextQuestion2();
            option.setVisible(false);
            option2.setVisible(false);
            option3.setVisible(false);
            option4.setVisible(false);
            input.setVisible(false);

        }else if(QuizType.WHATSTHELYRIC == type){
            nextQuestion3();

        }else{
            input.setVisible(false);
            nextQuestion();
            option.setOnAction(e -> {
                addCorrect(option);
                displayAnswer();
            });
            option2.setOnAction(e -> {
                addCorrect(option2);
                displayAnswer();
            });
            option3.setOnAction(e -> {
                addCorrect(option3);
                displayAnswer();
            });
            option4.setOnAction(e -> {
                addCorrect(option4);
                displayAnswer();
            });
        }



        VBox vbox = new VBox();
        if(QuizType.SINGBY == type){
            vbox.getChildren().addAll(player,question);
        }else{
            vbox.getChildren().addAll(name,question,input,option,option2,option3,option4);
            vbox.setSpacing(30);
        }
        vbox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.centerProperty().setValue(vbox);
        borderPane.topProperty().setValue(time);
        borderPane.setStyle("-fx-background-color: #222222;");

        Scene scene = new Scene(borderPane, 1024, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Method used to generate next question
     * For multiple options games
     */
    private static void nextQuestion(){

        name.setText("");

        if(index == numOf || questions.get(index).getQuestion() == null){
            ResultsScene resultsScene = new ResultsScene();
            resultsScene.init(stage,correct,numOf,user);
        }else{
            String[] op = new String[]{questions.get(index).getAnswer(),questions.get(index).getOption(),questions.get(index).getOption2(),questions.get(index).getOption3()};
            if(QuizType.FINISHLYRIC == type){
                name.setText(questions.get(index).getName() + " - " + questions.get(index).getArtist());
            }
            question.setText(questions.get(index).getQuestion());
            option.setText(op[indexes.get(0)]);
            option2.setText(op[indexes.get(1)]);
            option3.setText(op[indexes.get(2)]);
            option4.setText(op[indexes.get(3)]);

            Collections.shuffle(indexes);
            seconds = 10;
            timer.setCycleCount(10);
            timer.play();

        }
    }

    /**
     * Method used to generate next question
     * For non option games
     */
    private static void nextQuestion2(){

        option.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);

        if(index == numOf || questions.get(index).getQuestion() == null){
            ResultsScene resultsScene = new ResultsScene();
            resultsScene.init(stage,correct,numOf,user);
        }else{
            if(QuizType.SINGBY == type){
                player.setVisible(false);
                question.setText(questions.get(index).getQuestion() + " - " + questions.get(index).getAnswer());
                timer.setCycleCount(10);
                timer.play();
                stop = false;
                downloadThread();
            }else{
                question.setText(questions.get(index).getQuestion());
                seconds = 10;
                timer.setCycleCount(10);
                timer.play();
            }
        }
    }

    /**
     * Method used to generate next question
     * For user's input games
     */
    private static void nextQuestion3(){

        option.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);
        input.setVisible(true);
        input.setText("");

        if(index == numOf || questions.get(index).getQuestion() == null){
            ResultsScene resultsScene = new ResultsScene();
            resultsScene.init(stage,correct,numOf,user);
        }else{
            question.setText(questions.get(index).getQuestion());
            seconds = 30;
            check.setCycleCount(20);
            check.play();
        }
    }


    /**
     * Method used for an evaluation user's answer and updates statistics
     * Inits specific view of buttons and returns it to the original
     */
    public static void displayAnswer(){


        timer.stop();

        if(!questions.get(index).getAnswer().equals(option.getText())){
            option.setBorder(new Border(new BorderStroke(Color.web("#b53737"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }else{
            option.setBorder(new Border(new BorderStroke(Color.web("#4ab71d"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        }
        if(!questions.get(index).getAnswer().equals(option2.getText())){
            option2.setBorder(new Border(new BorderStroke(Color.web("#b53737"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }else{
            option2.setBorder(new Border(new BorderStroke(Color.web("#4ab71d"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
        if(!questions.get(index).getAnswer().equals(option3.getText())){
            option3.setBorder(new Border(new BorderStroke(Color.web("#b53737"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }else{
            option3.setBorder(new Border(new BorderStroke(Color.web("#4ab71d"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
        if(!questions.get(index).getAnswer().equals(option4.getText())){
            option4.setBorder(new Border(new BorderStroke(Color.web("#b53737"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }else{
            option4.setBorder(new Border(new BorderStroke(Color.web("#4ab71d"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }

        Timeline pause = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            seconds = 10;
            time.setText(String.valueOf(seconds));
            index++;
            option.setBorder(new Border(new BorderStroke(Color.web("#222222"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            option2.setBorder(new Border(new BorderStroke(Color.web("#222222"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            option3.setBorder(new Border(new BorderStroke(Color.web("#222222"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            option4.setBorder(new Border(new BorderStroke(Color.web("#222222"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            nextQuestion();

        }));
        pause.play();

    }

    /**
     * Method used for non option games
     * Inits multiple options or an audio
     */
    public static void displayOptions(){

        timer.stop();

        if(QuizType.SINGBY == type){
            stop = true;
            playMusic();
        }else{
            option.setVisible(true);
            option2.setVisible(true);
            option3.setVisible(true);

            option.setText(questions.get(index).getOption());
            option2.setText(questions.get(index).getOption2());
            option3.setText(questions.get(index).getOption3());
        }

        Timeline pause = new Timeline(new KeyFrame(Duration.seconds(7), e -> {
            seconds = 10;
            time.setText(String.valueOf(seconds));
            index++;
            if(QuizType.SINGBY == type){
                mediaPlayer.stop();
            }
            nextQuestion2();

        }));
        pause.play();
    }

    /**
     * Method used for an evaluation user's answer and updates statistics
     * Inits name of song and artist
     */
    public static void displayAnswer2(){

        timer.stop();
        check.stop();

        option.setVisible(true);
        option.setText(questions.get(index).getName() + " - " + questions.get(index).getArtist());

        Timeline pause = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            seconds = 30;
            time.setText(String.valueOf(seconds));
            index++;
            nextQuestion3();

        }));
        pause.play();
    }

    private void addCorrect(Button opt){
        if(opt.getText().equals(questions.get(index).getAnswer())){
            correct++;
        }
    }

    /**
     * Timer for answering questions by user
     */
    static Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

        seconds--;
        time.setText(String.valueOf(seconds));
        if(seconds <= 0){
            if(differentType == 1){
                displayOptions();
            }else{
                displayAnswer();
            }
        }
    }));

    /**
     * Checking user's input
     * Needed exact input as is name of a song
     */
    static Timeline check = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

        seconds--;
        time.setText(String.valueOf(seconds));
        if(input.getText().toLowerCase().equals(questions.get(index).getName().toLowerCase())){
            correct++;
        }
        if(input.getText().toLowerCase().equals(questions.get(index).getName().toLowerCase()) || seconds <= 1){
            displayAnswer2();
        }
    }));

    /**
     * Method creates new thread which is downloading audio from given URL
     * and saves it into file
     * In case of existing file it rewrites it
     */
    private static void downloadThread(){
        Thread download = new Thread(() -> {
            do{

                String path = questions.get(index).getOption();
                if(path == null){
                    logger.log(Level.SEVERE,"Path was not found in database");
                    path = "src/main/resources/audio/test0.mp3";
                }
                InputStream in;
                try {
                    in = new URL(path).openStream();
                    Files.copy(in, Paths.get("src/main/resources/audio/song_" + i + ".mp3"), StandardCopyOption.REPLACE_EXISTING);
                    logger.log(Level.INFO,"Audio file is downloaded");
                    stop = true;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }while(!stop);
        });
        download.start();
    }

    /**
     * Finds the file for given index corresponding to current question
     * Sets up audio volume by user's preference
     */
    private static void playMusic(){

        Media media = new Media(new File("src/main/resources/audio/song_" + i +".mp3").toURI().toString());
        i++;
        player.setVisible(true);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        if(user.equals("") || quizServer.getMusicVolume(user) == 0){
            mediaPlayer.setVolume(35);
        }else{
            mediaPlayer.setVolume(quizServer.getMusicVolume(user));
        }
        logger.log(Level.SEVERE,"Audio is played");
    }

    private static Button initPlayer(){
        ImageView nextImgView;
        Image nextImg = null;

        Button player = new Button();

        try {
            nextImg = new Image("/graphics/player.gif");

        } catch (Exception e) {
            logger.log(Level.SEVERE,"Cannot find the image");
        }

        nextImgView = new ImageView(nextImg);

        nextImgView.setFitHeight(100);
        nextImgView.setFitWidth(100);
        player.setGraphic(nextImgView);
        player.setStyle("-fx-background-color: transparent;");

        return player;
    }

}
