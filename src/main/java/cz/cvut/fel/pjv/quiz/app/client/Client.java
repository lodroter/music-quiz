package cz.cvut.fel.pjv.quiz.app.client;

import cz.cvut.fel.pjv.quiz.app.compute.QuizServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client{

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        QuizServer quizServer = (QuizServer) registry.lookup("quiz");
    }
}