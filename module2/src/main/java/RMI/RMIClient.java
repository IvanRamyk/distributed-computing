package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {

    private RMIClient() {}

    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry(1235);
            RMIServerInterface stub = (RMIServerInterface) registry.lookup("films");
            String response = stub.getActors().toString();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}