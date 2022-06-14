package cz.cvut.fel.pjv.quiz.app.client;

import cz.cvut.fel.pjv.quiz.app.server.QuizServerImpl;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Scene for game statistics
 */

public class ResultsScene {

    public void init(Stage primaryStage, int correct, int numOfQuestions, String username){

       Button back = SettingsScene.initBackImg();
       Button settings = MainStage.initSettingsImg();
       TextField answers = ChooseScene.initText();
       TextField numOf = ChooseScene.initText();
       TextField points = ChooseScene.initText();
       TextField success = ChooseScene.initText();
       TextField totalNum = ChooseScene.initText();

       QuizServerImpl quizServer = new QuizServerImpl();

       back.setOnAction(e -> {
           ChooseScene chooseScene = new ChooseScene();
           chooseScene.init(primaryStage, username);
       });
       settings.setOnAction(e -> {
           SettingsScene settingsScene = new SettingsScene();
           settingsScene.init(primaryStage, username);
       });

       //statistics are wrong
       if(username.equals("")){
           answers.setText("Correct answers: " + correct);
           numOf.setText("Number of questions: " + numOfQuestions);
       }else{
           quizServer.updateNumOfQuestions(username,numOfQuestions);
           quizServer.updatePoints(username,correct);
           quizServer.updateSuccessRate(username);
           int p = quizServer.getPoints(username);
           int r = quizServer.getSuccessRate(username);
           int t = quizServer.getNumOfQuestions(username);
           answers.setText("Correct answers: " + correct);
           numOf.setText("Number of questions: " + numOfQuestions);
           points.setText("Total points: " + p);
           success.setText("Success rate: " + r + " %");
           totalNum.setText("Total num: " + t);

       }


       VBox results = new VBox();
       results.getChildren().addAll(answers,numOf,points,success,totalNum);
       results.setAlignment(Pos.CENTER);
       results.setSpacing(35);

       BorderPane borderPane = new BorderPane();
       borderPane.leftProperty().setValue(back);
       borderPane.rightProperty().setValue(settings);
       borderPane.centerProperty().setValue(results);
       borderPane.setStyle("-fx-background-color: #222222;");

       Scene scene = new Scene(borderPane,1024,720);
       primaryStage.setScene(scene);
       primaryStage.show();
   }


}
