package shared.server.response;

/**
 * A reponse sent by the file server to the client. It is used to factorize
 * the different responses sent by the server. A response can have some tasks
 * to execute when being received.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public abstract class Response {
	
	/**
	 * A response is executed at its reception for possible client side
	 * actions and displaying the results.
	 * @return
	 */
	public String execute() {
		this.onReception();
		return this.toString();
	}
	
	/**
	 * The actions that must be performed on reception.
	 * Does nothing by default.
	 */
	protected void onReception() {
		// Does nothing by default
	}
	
	/**
	 * Each response has to override toString so that it can be
	 * directly printed to the user at the reception.
	 */
	@Override
	public abstract String toString();
}
