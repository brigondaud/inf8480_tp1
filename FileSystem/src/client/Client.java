package client;

import client.command.Command;
import client.command.CommandFactory;
import shared.auth.AuthenticationInterface;
import shared.client.InvalidCommandException;
import shared.server.FileServerInterface;
import shared.server.exception.InvalidCredentialsException;
import shared.server.response.Response;

import java.io.IOException;
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

    public static final int commandIndex = 1;

    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String hostName = args[0];
            System.out.println(hostName);
        	Registry registry = LocateRegistry.getRegistry(hostName);
            FileServerInterface fileServer = (FileServerInterface) registry.lookup("server");
            AuthenticationInterface authServer = (AuthenticationInterface) registry.lookup("Authentication");
            
            CommandFactory factory = new CommandFactory(fileServer, authServer);
            Command command = factory.createCommand(args);
            Response response = command.execute();
            System.out.println(response.execute());
        } catch(InvalidCredentialsException e) {
        	System.err.println("Identifiants invalides.");
        } catch (RemoteException e) {
            System.err.println("Remote exception during RMI call: ");
            e.printStackTrace();
        } catch (InvalidCommandException icException) {
            printHelp();
        } catch (IOException ioException) {
            System.err.println("I/O exception during RMI call");
            ioException.printStackTrace();	
        } catch (Exception e) {
            System.err.println("client exception: ");
            e.printStackTrace();
        }
    }

    static private void printHelp() {
        System.out.println("./client.sh [--ip=remote_server_ip] command_name");
    }

}
