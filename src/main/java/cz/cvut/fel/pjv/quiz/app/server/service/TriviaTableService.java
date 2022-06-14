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
 * Gets data for game Trivia
 */

public class TriviaTableService {

    public List<Question> getQuestions(int difficulty, int num) {

        Random random = new Random();
        List<Question> questions = new ArrayList<>();
        String[] options = new String[5];
        String q;
        String a;

        String sql = "SELECT question,answer FROM trivia WHERE difficulty=? ORDER BY RANDOM() LIMIT ?";

        int j = 0;
        try {
            Connection conn = Server.connectDb();
            PreparedStatement statement = Objects.requireNonNull(conn).prepareStatement(sql);
            statement.setInt(1,difficulty);
            statement.setInt(2,num);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Question question = new Question();
                question.setQuestion(resultSet.getString("question"));
                question.setAnswer(resultSet.getString("answer"));
                questions.add(question);
                j++;
            }

            for (int i = 0; i < num; i++) {
                String sql1 = "SELECT answer FROM trivia WHERE id in(?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql1);
                preparedStatement.setInt(1,random.nextInt(36));
                preparedStatement.setInt(2,random.nextInt(36));
                preparedStatement.setInt(3,random.nextInt(36));
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
}
