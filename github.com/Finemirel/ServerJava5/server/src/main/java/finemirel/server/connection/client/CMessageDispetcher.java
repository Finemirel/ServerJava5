package finemirel.server.connection.client;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import finemirel.server.connection.IStringCommand;
import finemirel.server.connection.agent.ConnectionAgent;

public class CMessageDispetcher {

	private Logger log = LogManager.getLogger(CMessageDispetcher.class);

	private static final String EXIT = "/exit";
	private static final String LEAVE = "/leave";

	private IStringCommand leave = new CStringLeave();
	private IStringCommand exit = new CStringExit();

	private final HashMap<String, IStringCommand> messageCommand = new HashMap<>();

	ConnectionClient client;
	ConnectionAgent agent;

	public CMessageDispetcher(ConnectionClient client, ConnectionAgent agent, String msg) {
		messageCommand.put(EXIT, exit);
		messageCommand.put(LEAVE, leave);
		if (!messageCommand.containsKey(msg)) {
			
			messageCommand.put(msg, new CStringMessage(msg));
		}
		this.client = client;
		this.agent = agent;
	}

	public void executeMessage(String msg, boolean needConnection) {
		log.info("in CMessageDispetcher");
		messageCommand.get(this.getCMDString(msg)).execute(client, agent, needConnection);
	}
	
	private String getCMDString(String str) {
		if (str.startsWith(EXIT)) {
			return EXIT;
		}
		if (str.startsWith(LEAVE)) {
			return LEAVE;
		}
		return str;
	}

}
