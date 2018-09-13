package ca.polymtl.inf8480.tp1.client;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ca.polymtl.inf8480.tp1.shared.ServerInterface;

public class Client {
	public static void main(String[] args) {
		String distantHostname = null;
		int bytesPower = 1;

		if (args.length >= 1) {
			distantHostname = args[0];
			if (args.length != 1) bytesPower = Integer.parseInt(args[1]);
		}

		Client client = new Client(distantHostname);
		client.run(bytesPower);
	}

	FakeServer localServer = null; // Pour tester la latence d'un appel de
									// fonction normal.
	private ServerInterface localServerStub = null;
	private ServerInterface distantServerStub = null;

	public Client(String distantServerHostname) {
		super();

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		localServer = new FakeServer();
		localServerStub = loadServerStub("127.0.0.1");

		if (distantServerHostname != null) {
			distantServerStub = loadServerStub(distantServerHostname);
		}
	}

	private void run(int bytesPower) {
		appelNormal(bytesPower);

		if (localServerStub != null) {
			appelRMILocal(bytesPower);
		}

		if (distantServerStub != null) {
			appelRMIDistant(bytesPower);
		}
	}

	/**
	 * Generates a byte array of size 10^bytesPower.
	 * @param bytesPower A power between 1 and 7.
	 * @return A 10^bytesPower sized byte array.
	 */
	private byte[] generateBytes(int bytesPower) {
		int bytesLength = (int) Math.pow(10, bytesPower);
		byte[] byteArray = new byte[bytesLength];
		for(int i = 0; i < bytesLength; i++) {
			byteArray[i] = 80;
		}
		return byteArray;
	}

	private ServerInterface loadServerStub(String hostname) {
		ServerInterface stub = null;

		try {
			Registry registry = LocateRegistry.getRegistry(hostname);
			stub = (ServerInterface) registry.lookup("server");
		} catch (NotBoundException e) {
			System.out.println("Erreur: Le nom '" + e.getMessage()
					+ "' n'est pas défini dans le registre.");
		} catch (AccessException e) {
			System.out.println("Erreur: " + e.getMessage());
		} catch (RemoteException e) {
			System.out.println("Erreur: " + e.getMessage());
		}

		return stub;
	}

	private void appelNormal(int bytesPower) {
		long start = System.nanoTime();
		int result = localServer.execute(generateBytes(bytesPower));
		long end = System.nanoTime();

		System.out.println("Temps écoulé appel normal: " + (end - start)
				+ " ns");
		System.out.println("Résultat appel normal: " + result);
	}

	private void appelRMILocal(int bytesPower) {
		try {
			long start = System.nanoTime();
			int result = localServerStub.execute(generateBytes(bytesPower));
			long end = System.nanoTime();

			System.out.println("Temps écoulé appel RMI local: " + (end - start)
					+ " ns");
			System.out.println("Résultat appel RMI local: " + result);
		} catch (RemoteException e) {
			System.out.println("Erreur: " + e.getMessage());
		}
	}

	private void appelRMIDistant(int bytesPower) {
		try {
			long start = System.nanoTime();
			int result = distantServerStub.execute(generateBytes(bytesPower));
			long end = System.nanoTime();

			System.out.println("Temps écoulé appel RMI distant: "
					+ (end - start) + " ns");
			System.out.println("Résultat appel RMI distant: " + result);
		} catch (RemoteException e) {
			System.out.println("Erreur: " + e.getMessage());
		}
	}
}
