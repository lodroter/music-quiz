package cz.cvut.fel.pjv.quiz.app.server.service;

import cz.cvut.fel.pjv.quiz.app.server.QuizServerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class UsersTableServiceTest {

    private static String name;

    @BeforeAll
    static void setUp(){
        QuizServerImpl quizServer = new QuizServerImpl();
        Random random = new Random();

        name = "Test" + random.nextInt();

        quizServer.addUser(name);

    }

    @Test
    public void SuccessRateTest_ZeroExpected(){
        QuizServerImpl quizServer = new QuizServerImpl();

        double points = quizServer.getPoints(name);
        int num = quizServer.getNumOfQuestions(name);
        int rate = (int) ((points/num)*100);

        quizServer.updateSuccessRate(name);

        Assertions.assertEquals(0,quizServer.getSuccessRate(name));
    }

    @Test
    public void NumOfQuestionsTest(){
        QuizServerImpl quizServer = new QuizServerImpl();
        Random random = new Random();

        int num = random.nextInt();

        quizServer.updateNumOfQuestions(name,num);

        Assertions.assertEquals(num,quizServer.getNumOfQuestions(name));
    }

    @Test
    public void PointsTest(){
        QuizServerImpl quizServer = new QuizServerImpl();
        Random random = new Random();

        int points = random.nextInt();

        quizServer.updatePoints(name,points);

        Assertions.assertEquals(points,quizServer.getPoints(name));
    }

    @Test
    public void VolumeTest(){
        QuizServerImpl quizServer = new QuizServerImpl();
        Random random = new Random();

        int volume = random.nextInt();

        quizServer.updateMusicVolume(name,volume);

        Assertions.assertEquals(volume,quizServer.getMusicVolume(name));
    }

}
