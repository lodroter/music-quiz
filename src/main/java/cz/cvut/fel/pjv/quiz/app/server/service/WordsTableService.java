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

/**
 * Gets data for game Song Association
 */

public class WordsTableService {

    public List<Question> getQuestions(int num) {

        List<Question> questions = new ArrayList<>();
        String[] options = new String[5];
        String w = null;

        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();

            int j = 0;

            String sql = "SELECT word FROM words ORDER BY RANDOM() LIMIT ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,num);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Question question = new Question();
                question.setQuestion(result.getString("word"));
                questions.add(question);
                j++;
            }
            for (int i = 0; i < num; i++) {
                String sql1 = "SELECT song,artist FROM main WHERE song like ? ORDER BY RANDOM() LIMIT 3";
                PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
                preparedStatement1.setString(1,'%' + questions.get(i).getQuestion() + '%');
                ResultSet resultSet = preparedStatement1.executeQuery();
                int index = 0;
                while(resultSet.next()){
                    options[index] = resultSet.getString("song") + " - " + resultSet.getString("artist");
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
}
