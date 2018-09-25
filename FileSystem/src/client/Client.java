package client;

import client.command.Command;
import client.command.CommandFactory;
import shared.auth.Credentials;
import shared.server.FileServerInterface;

import java.rmi.RemoteException;
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
            FileServerInterface fileServer = (FileServerInterface) registry.lookup("server");
            // Delegate the Command creation to Command factory
            CommandFactory factory = new CommandFactory();
            Command command = factory.createCommand(args);
            command.execute(fileServer);
        } catch (RemoteException e) {
            System.err.println("Remote exception during RMI call: ");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Client exception: ");
            e.printStackTrace();
        }
    }

}
