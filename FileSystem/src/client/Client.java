package client;

import shared.auth.Credentials;
import shared.server.FileServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * The Client manages user commands and send requests to the file server,
 *
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class Client {

    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.getRegistry("132.207.12.87");
            FileServerInterface fileManager = (FileServerInterface) registry.lookup("server");
        } catch (Exception e) {
            System.err.println("Client exception: ");
            e.printStackTrace();
        }
    }

}
