package cz.cvut.fel.pjv.quiz.app.server.service;

import cz.cvut.fel.pjv.quiz.app.server.QuizServerImpl;
import cz.cvut.fel.pjv.quiz.app.server.model.Decade;
import cz.cvut.fel.pjv.quiz.app.server.model.Genre;
import cz.cvut.fel.pjv.quiz.app.server.model.Question;
import cz.cvut.fel.pjv.quiz.app.server.model.QuizType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

public class Services {

    @ParameterizedTest()
    @CsvSource({"GUESSSONG,D2000s,POP,3,2","GUESSARTIST,D1990s,ROCK,2,4","SINGBY,RANDOM,RANDOM,4,1"})
    public void mainTableServiceParametrizedTest(String type, String decade, String genre, int num, int difficulty) throws RemoteException {
        QuizServerImpl quizServer = new QuizServerImpl();

        quizServer.setQuizType(QuizType.valueOf(type));
        quizServer.setDecade(Decade.valueOf(decade));
        quizServer.setGenre(Genre.valueOf(genre));
        quizServer.setNumOfQuestions(num);
        quizServer.setDifficulty(difficulty);
        List<Question> questions = quizServer.getQuestions();

        Assertions.assertEquals(num,questions.size());

    }

    @Test
    public void wordTableServiceTest() throws RemoteException {
        QuizServerImpl quizServer = new QuizServerImpl();
        Random random = new Random();

        int num = random.nextInt(2);
        quizServer.setQuizType(QuizType.SONGAS);
        quizServer.setNumOfQuestions(num);
        List<Question> questions = quizServer.getQuestions();

        Assertions.assertEquals(num,questions.size());

    }

    @Test
    public void triviaTableServiceTest() throws RemoteException {
        QuizServerImpl quizServer = new QuizServerImpl();
        Random random = new Random();

        int num = random.nextInt(8);
        int diff = random.nextInt(4);
        quizServer.setQuizType(QuizType.TRIVIA);
        quizServer.setNumOfQuestions(num);
        quizServer.setDifficulty(diff);
        List<Question> questions = quizServer.getQuestions();

        Assertions.assertEquals(num,questions.size());

    }

    @Test
    public void lyricsTableServiceTest() throws RemoteException {
        QuizServerImpl quizServer = new QuizServerImpl();
        Random random = new Random();

        int num = random.nextInt(8);
        quizServer.setQuizType(QuizType.FINISHLYRIC);
        quizServer.setNumOfQuestions(num);
        List<Question> questions = quizServer.getQuestions();

        Assertions.assertEquals(num,questions.size());

    }


}
