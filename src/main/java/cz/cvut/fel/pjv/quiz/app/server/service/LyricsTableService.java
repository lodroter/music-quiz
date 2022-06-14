package cz.cvut.fel.pjv.quiz.app.server.service;


import cz.cvut.fel.pjv.quiz.app.server.Server;
import cz.cvut.fel.pjv.quiz.app.server.model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Gets data for games with song lyrics
 * Games: What's the lyric, Finish the lyric
 */
public class LyricsTableService {

    private String sql;
    List<Question> questions = new ArrayList<>();

    //method for Finish the lyric
    public List<Question> getQuestions(int difficulty, int num) {

        Random random = new Random();
        int j = 0;
        String[] options = new String[5];

        try {
            Connection conn = Server.connectDb();

            String sql1;
            if(difficulty == 1){
                sql = "SELECT lyrics.lyric,lyrics.lyric2,main.song,main.artist FROM lyrics, main WHERE lyrics.main_id = main.id ORDER BY RANDOM() LIMIT ?";
                sql1 = "SELECT lyric2 FROM lyrics WHERE id in(?,?,?)";
            }else if(difficulty == 2){
                sql = "SELECT lyrics.lyric3,lyrics.lyric4,main.song,main.artist FROM lyrics, main WHERE lyrics.main_id = main.id ORDER BY RANDOM() LIMIT ?";
                sql1 = "SELECT lyric4 FROM lyrics WHERE id in(?,?,?)";
            }else{
                sql = "SELECT lyrics.lyric5,lyrics.lyric6,main.song,main.artist FROM lyrics, main WHERE lyrics.main_id = main.id ORDER BY RANDOM() LIMIT ?";
                sql1 = "SELECT lyric6 FROM lyrics WHERE id in(?,?,?)";
            }
            PreparedStatement statement = Objects.requireNonNull(conn).prepareStatement(sql);
            statement.setInt(1,num);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Question question = new Question();
                question.setQuestion(resultSet.getString(1));
                question.setAnswer(resultSet.getString(2));
                question.setName(resultSet.getString(3));
                question.setArtist(resultSet.getString(4));
                j++;
                questions.add(question);
            }

            for (int i = 0; i < num; i++) {
                PreparedStatement preparedStatement = conn.prepareStatement(sql1);
                preparedStatement.setInt(1,random.nextInt(19));
                preparedStatement.setInt(2,random.nextInt(19));
                preparedStatement.setInt(3,random.nextInt(19));
                ResultSet resultSet1 = preparedStatement.executeQuery();
                int index = 0;
                while(resultSet1.next()){
                    options[index] = resultSet1.getString(1);
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

    //Method for What's the lyric
    public List<Question> getQuestions2(int difficulty, int num) {

        List<Question> questions = new ArrayList<>();
        int j = 0;

        try {
            Connection conn = Server.connectDb();

            if(difficulty == 1){
                sql = "SELECT lyric,lyric2,main_id FROM lyrics ORDER BY RANDOM() LIMIT ?";
            }else if(difficulty == 2){
                sql = "SELECT lyric3,lyric4,main_id FROM lyrics ORDER BY RANDOM() LIMIT ?";
            }else{
                sql = "SELECT lyric5,lyric6,main_id FROM lyrics ORDER BY RANDOM() LIMIT ?";
            }
            PreparedStatement statement = Objects.requireNonNull(conn).prepareStatement(sql);
            statement.setInt(1,num);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Question question = new Question();
                question.setQuestion(resultSet.getString(1));
                question.setAnswer(resultSet.getString(2));
                question.setId(resultSet.getInt(3));
                questions.add(question);
                j++;
            }
            String sql1 = "SELECT song,artist FROM main WHERE id=?";
            for (int i = 0; i < num; i++) {
                PreparedStatement preparedStatement = conn.prepareStatement(sql1);
                preparedStatement.setInt(1,questions.get(i).getId());
                ResultSet resultSet1 = preparedStatement.executeQuery();
                while(resultSet1.next()){
                    questions.get(i).setName(resultSet1.getString(1));
                    questions.get(i).setArtist(resultSet1.getString(2));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return questions;
    }

}
