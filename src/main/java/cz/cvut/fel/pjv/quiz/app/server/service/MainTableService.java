package cz.cvut.fel.pjv.quiz.app.server.service;

import cz.cvut.fel.pjv.quiz.app.server.Server;
import cz.cvut.fel.pjv.quiz.app.server.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Main data table
 * Games: Guess the song, Guess the Artist, Sing a song
 */
public class MainTableService {

    private int beginYear;
    private int endYear;
    private int rangeStart;
    private int rangeEnd;
    private String g;
    private String q;
    private String a;


    public List<Question> getQuestions(Decade decade, Genre genre, int num, int difficulty, QuizType quizType) {
        Random random = new Random();

        // list of questions
        List<Question> questions = new ArrayList<>();
        String[] options = new String[5];

        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();

            setUpParameters(decade,genre,difficulty);

            int j = 0;
            String sql = "SELECT artist,song FROM main WHERE genre like ? AND decade BETWEEN ? AND ? AND difficulty BETWEEN ? AND ? AND audio IS NOT NULL ORDER BY RANDOM() LIMIT ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,"%" + g + "%");
            preparedStatement.setInt(4,rangeStart);
            preparedStatement.setInt(5,rangeEnd);
            preparedStatement.setString(2, String.valueOf(beginYear));
            preparedStatement.setString(3, String.valueOf(endYear));
            preparedStatement.setInt(6,num);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Question question = new Question();
                if(QuizType.GUESSARTIST == quizType){
                    q = result.getString("song");
                    a = result.getString("artist");
                }else{
                    q = result.getString("artist");
                    a = result.getString("song");
                }
                question.setQuestion(q);
                question.setAnswer(a);
                questions.add(question);
                j++;
            }
            String sql1;
            if(quizType == QuizType.GUESSARTIST){
                sql1 = "SELECT artist FROM main WHERE id in (?,?,?)";
            }else if(quizType == QuizType.SINGBY){
                sql1 = "SELECT audio FROM main WHERE song=? AND artist=?";
            }else if(quizType == QuizType.GUESSSONG){
                sql1 = "SELECT song FROM main WHERE id in (?,?,?)";
            }else{
                sql1 = "SELECT song FROM main WHERE id in (?,?,?)";
            }
            for (int i = 0; i < num; i++) {
                PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
                if(quizType == QuizType.SINGBY){
                    preparedStatement1.setString(1,questions.get(i).getAnswer());
                    preparedStatement1.setString(2,questions.get(i).getQuestion());
                }else{
                    //set up bounds from db
                    preparedStatement1.setInt(1,random.nextInt(3500));
                    preparedStatement1.setInt(2,random.nextInt(3500));
                    preparedStatement1.setInt(3,random.nextInt(3500));
                }
                ResultSet resultSet = preparedStatement1.executeQuery();
                int index = 0;
                while(resultSet.next()){
                    options[index] = resultSet.getString(1);
                    index++;
                }
                questions.get(i).setOption(options[0]);
                questions.get(i).setOption2(options[1]);
                questions.get(i).setOption3(options[2]);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return questions;
    }

    /**
     * Sets up given parameters for database format
     * @param decade enum format
     * @param genre enum format
     * @param difficulty enum format
     */
    private void setUpParameters(Decade decade, Genre genre, int difficulty){

        if(decade.equals(Decade.D1960s)){
            beginYear = 1960;
            endYear = 1969;
        }else if(decade.equals(Decade.D1970s)){
            beginYear = 1970;
            endYear = 1979;
        }else if(decade.equals(Decade.D1980s)){
            beginYear = 1980;
            endYear = 1989;
        }else if(decade.equals(Decade.D1990s)){
            beginYear = 1990;
            endYear = 1999;
        }else if(decade.equals(Decade.D2000s)){
            beginYear = 2000;
            endYear = 2009;
        }else if(decade.equals(Decade.D2010s)){
            beginYear = 2010;
            endYear = 2019;
        }else if(decade.equals(Decade.D2020s)){
            beginYear = 2020;
            endYear = 2029;
        }else{
            beginYear = (int) ((Math.random() * (2029 - 1960)) + 1960);
            endYear = beginYear + 10;
        }

        if(difficulty == 1){
            rangeStart = 75;
            rangeEnd = 100;
        }else if(difficulty == 2){
            rangeStart = 50;
            rangeEnd = 75;
        }else if(difficulty == 3){
            rangeStart = 35;
            rangeEnd = 50;
        }else{
            rangeStart = 0;
            rangeEnd = 35;
        }

        if(genre == Genre.CZSK){
            g = "" + genre.toString().charAt(0) + genre.toString().charAt(1);
        }else{
            g = String.valueOf(genre.toString().charAt(0));
        }
    }


}
