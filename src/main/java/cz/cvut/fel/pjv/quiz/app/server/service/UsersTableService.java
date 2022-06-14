package cz.cvut.fel.pjv.quiz.app.server.service;

import cz.cvut.fel.pjv.quiz.app.server.Server;

import java.sql.*;
import java.util.Objects;

public class UsersTableService {

    public void addUser(String name){
        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();
            String sql = "INSERT INTO users (username,points,success,num,volume) VALUES (?,?,?,?,?) ON CONFLICT (username) DO NOTHING";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,0);
            preparedStatement.setInt(3,0);
            preparedStatement.setInt(4,0);
            preparedStatement.setInt(5,0);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getPoints(String name){
        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();
            String sql = "SELECT points FROM users WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                return result.getInt("points");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public int getSuccessRate(String name){
        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();
            String sql = "SELECT success FROM users WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                return result.getInt("success");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public int getNumOfQuestions(String name){
        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();
            String sql = "SELECT num FROM users WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                return result.getInt("num");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void setNumOfQuestions(String name, int numOfQuestions){
        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();
            String sql = "UPDATE users SET num=? WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,numOfQuestions);
            preparedStatement.setString(2,name);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setPoints(String name, int points){
        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();
            String sql = "UPDATE users SET points=? WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,points);
            preparedStatement.setString(2,name);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setSuccessRate(String name, int successRate){
        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();
            String sql = "UPDATE users SET success=? WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,successRate);
            preparedStatement.setString(2,name);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setMusicVolume(String name, int volume){
        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();
            String sql = "UPDATE users SET volume=? WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,volume);
            preparedStatement.setString(2,name);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getMusicVolume(String name){
        try {
            Connection conn = Server.connectDb();
            Objects.requireNonNull(conn).createStatement();
            String sql = "SELECT volume FROM users WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                return result.getInt("volume");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
