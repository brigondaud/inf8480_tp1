package client;

import client.command.Command;
import client.command.CommandFactory;
import shared.auth.AuthenticationInterface;
import shared.server.FileServerInterface;
import shared.server.response.Response;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * The client manages user commands and send requests to the file server
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
            Registry registry = LocateRegistry.getRegistry();
            FileServerInterface fileServer = (FileServerInterface) registry.lookup("server");
            AuthenticationInterface authServer = (AuthenticationInterface) registry.lookup("Authentication");
            // Delegate the Command creation to Command factory
            CommandFactory factory = new CommandFactory(fileServer, authServer);
            Command command = factory.createCommand(args);
            // Printing the response from the server.
            System.out.println(command.execute());
        } catch (RemoteException e) {
            System.err.println("Remote exception during RMI call: ");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("client exception: ");
            e.printStackTrace();
        }
    }

}
