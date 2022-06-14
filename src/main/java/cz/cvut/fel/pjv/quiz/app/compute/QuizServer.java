package cz.cvut.fel.pjv.quiz.app.compute;

import cz.cvut.fel.pjv.quiz.app.server.model.Decade;
import cz.cvut.fel.pjv.quiz.app.server.model.Genre;
import cz.cvut.fel.pjv.quiz.app.server.model.Question;
import cz.cvut.fel.pjv.quiz.app.server.model.QuizType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface QuizServer extends Remote {

    QuizType[] getQuizTypes() throws RemoteException;

    Decade[] getDecades() throws RemoteException;

    Genre[] getGenres() throws RemoteException;

    void setGenre(Genre genre) throws RemoteException;

    void setDecade(Decade decade) throws RemoteException;

    void setQuizType(QuizType quizType) throws RemoteException;

    void setNumOfQuestions(int number) throws RemoteException;

    List<Question> getQuestions() throws RemoteException;

    Genre getGenre() throws RemoteException;

    Decade getDecade() throws RemoteException;

    QuizType getQuizType() throws RemoteException;

    int getNumOfQuestions() throws RemoteException;

    void addUser(String name) throws RemoteException;

    int getPoints(String name) throws RemoteException;

    void updatePoints(String name, int points) throws RemoteException;

    int getSuccessRate(String name) throws RemoteException;

    void updateSuccessRate(String name) throws RemoteException;

    int getNumOfQuestions(String name) throws RemoteException;

    void updateNumOfQuestions(String name,int numOfQuestions) throws  RemoteException;

    void setDifficulty(int difficulty) throws RemoteException;

    int getDifficulty() throws RemoteException;

    void updateMusicVolume(String name, int volume) throws RemoteException;

    int getMusicVolume(String name) throws RemoteException;

}
