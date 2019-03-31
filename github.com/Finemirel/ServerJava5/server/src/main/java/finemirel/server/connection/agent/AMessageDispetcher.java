package finemirel.server.connection.agent;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import finemirel.server.connection.IStringCommand;
import finemirel.server.connection.client.ConnectionClient;

public class AMessageDispetcher {
	private Logger log = LogManager.getLogger(AMessageDispetcher.class);

	private static final String EXIT = "/exit";

	private IStringCommand exit = new AStringExit();

	private final HashMap<String, IStringCommand> messageCommand = new HashMap<>();

	ConnectionClient client;
	ConnectionAgent agent;

	public AMessageDispetcher(ConnectionClient client, ConnectionAgent agent, String msg) {
		messageCommand.put(EXIT, exit);
		if (!messageCommand.containsKey(msg)) {
			messageCommand.put(msg, new AStringMessage(msg));
		}
		this.client = client;
		this.agent = agent;
	}

	public void executeMessage(String msg, boolean needConnection) {
		log.info("in AMessageDispetcher");
		messageCommand.get(this.getCMDString(msg)).execute(client, agent, needConnection);
	}
	
	private String getCMDString(String str) {
		if (str.startsWith(EXIT)) {
			return EXIT;
		}
		return str;
	}
}
