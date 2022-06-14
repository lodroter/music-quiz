package cz.cvut.fel.pjv.quiz.app;

import cz.cvut.fel.pjv.quiz.app.client.Client;
import cz.cvut.fel.pjv.quiz.app.client.MainStage;
import cz.cvut.fel.pjv.quiz.app.server.Server;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main extends Application  {


    public static void main(String[] args) throws RemoteException, NotBoundException {
        Server.main(args);
        Client.main(args);
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("MusicQuiz");
        MainStage mainStage = new MainStage();
        mainStage.init(primaryStage,"");
    }

}
