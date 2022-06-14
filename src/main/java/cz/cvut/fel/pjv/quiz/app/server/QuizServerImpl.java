package cz.cvut.fel.pjv.quiz.app.server;

import cz.cvut.fel.pjv.quiz.app.compute.QuizServer;
import cz.cvut.fel.pjv.quiz.app.server.model.Decade;
import cz.cvut.fel.pjv.quiz.app.server.model.Genre;
import cz.cvut.fel.pjv.quiz.app.server.model.Question;
import cz.cvut.fel.pjv.quiz.app.server.model.QuizType;
import cz.cvut.fel.pjv.quiz.app.server.service.*;

import java.util.List;

/**
 * Interface's implementation
 * Communicates with classes which getting data from database
 */

public class QuizServerImpl implements QuizServer {

    private Genre genre;
    private Decade decade;
    private QuizType quizType;
    private int numOfQuestions;
    private int difficulty;

    private final MainTableService mainTableService = new MainTableService();
    private final LyricsTableService lyricsTableService = new LyricsTableService();
    private final WordsTableService wordsTableService = new WordsTableService();
    private final TriviaTableService triviaTableService = new TriviaTableService();
    private final UsersTableService usersTableService = new UsersTableService();


    @Override
    public QuizType[] getQuizTypes() {
        return QuizType.values();
    }

    @Override
    public Decade[] getDecades() {
        return Decade.values();
    }

    @Override
    public Genre[] getGenres() {
        return Genre.values();
    }

    @Override
    public void setGenre(Genre genreInp) {
        genre = genreInp;
    }

    @Override
    public void setDecade(Decade decadeInp) {
        decade = decadeInp;
    }

    @Override
    public void setQuizType(QuizType quizTypeInp) {
        quizType = quizTypeInp;
    }

    @Override
    public void setNumOfQuestions(int number) {
        numOfQuestions = number;
    }

    //getting data needed for games
    @Override
    public List<Question> getQuestions() {
        List<Question> questions = null;

        switch (quizType){
            case FINISHLYRIC:
                questions = lyricsTableService.getQuestions(getDifficulty(),getNumOfQuestions());
                break;
            case WHATSTHELYRIC:
                questions = lyricsTableService.getQuestions2(getDifficulty(),getNumOfQuestions());
                break;
            case SONGAS:
                questions = wordsTableService.getQuestions(getNumOfQuestions());
                break;
            case TRIVIA:
                questions = triviaTableService.getQuestions(getDifficulty(),getNumOfQuestions());
                break;
            default:
                questions = mainTableService.getQuestions(getDecade(),getGenre(),getNumOfQuestions(),getDifficulty(),getQuizType());
        }

        return questions;
    }


    @Override
    public Genre getGenre() {
        return genre;
    }

    @Override
    public Decade getDecade() {
        return decade;
    }

    @Override
    public QuizType getQuizType() {
        return quizType;
    }

    @Override
    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int diff) { this.difficulty = diff; }

    @Override
    public void addUser(String name) {
        usersTableService.addUser(name);
    }

    @Override
    public int getPoints(String name) {
        return usersTableService.getPoints(name);
    }

    @Override
    public void updatePoints(String name, int points) {
        int p = usersTableService.getPoints(name);
        int sum = p + points;
        usersTableService.setPoints(name,sum);
    }

    @Override
    public int getSuccessRate(String name) {
        return usersTableService.getSuccessRate(name);
    }

    @Override
    public void updateSuccessRate(String name){
        double p = usersTableService.getPoints(name);
        int num = usersTableService.getNumOfQuestions(name);
        int rate = (int) ((p/num)*100);
        if(num != 0){
            usersTableService.setSuccessRate(name,rate);
        }
    }

    @Override
    public int getNumOfQuestions(String name){
        return usersTableService.getNumOfQuestions(name);
    }

    @Override
    public void updateNumOfQuestions(String name, int numOfQuestions){
        int num = usersTableService.getNumOfQuestions(name);
        int n = num + numOfQuestions;
        usersTableService.setNumOfQuestions(name,n);
    }

    @Override
    public void updateMusicVolume(String name, int volume){
        if(volume == 0 || volume < 0 || volume > 100){
            usersTableService.setMusicVolume(name,35);
        }
        usersTableService.setMusicVolume(name,volume);
    }

    @Override
    public int getMusicVolume(String name){
        return usersTableService.getMusicVolume(name);
    }


}
