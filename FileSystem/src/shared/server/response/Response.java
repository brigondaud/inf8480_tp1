package shared.server.response;

/**
 * A reponse sent by the file server to the client. It is used to factorize
 * the different responses sent by the server.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public abstract class Response {
	
	/**
	 * Each response has to override toString so that it can be
	 * directly printed to the user.
	 */
	@Override
	public abstract String toString();
}
