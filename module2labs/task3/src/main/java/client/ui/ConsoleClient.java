package client.ui;

import client.ui.socket.SocketClient;

import java.io.IOException;

public class ConsoleClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SocketClient client = new SocketClient();
        System.out.println(client.getAllProfessors());
        System.out.println(client.getAllSubjects());
    }
}
