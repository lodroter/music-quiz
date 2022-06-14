package cz.cvut.fel.pjv.quiz.app.server;

import cz.cvut.fel.pjv.quiz.app.client.MainStage;
import cz.cvut.fel.pjv.quiz.app.compute.QuizServer;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Remote {

    private static final java.util.logging.Logger logger = Logger.getLogger(MainStage.class.getName());

    public static void main(String[] args) {
        try{

            connectDb();
            QuizServerImpl quizServerImpl = new QuizServerImpl();

            QuizServer quizServer = (QuizServer) UnicastRemoteObject.exportObject(quizServerImpl,Registry.REGISTRY_PORT);

            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            registry.rebind("quiz",quizServer);

            logger.log(Level.INFO,"Server is running");
        } catch (Exception e){
            logger.log(Level.SEVERE,"Data exception ",e.getCause());
        }
    }

    public static Connection connectDb() throws SQLException {

        String path = "jdbc:postgresql://localhost:6455/quiz";
        String username = "quiz";
        String password = "quiz";
        String driver = "org.postgresql.Driver";

        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(path,username,password);
            if(connection != null)
                logger.log(Level.INFO,"Database is connected");
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
