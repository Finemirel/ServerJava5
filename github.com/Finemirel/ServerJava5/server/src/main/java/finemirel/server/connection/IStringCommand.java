package finemirel.server.connection;

import finemirel.server.connection.agent.ConnectionAgent;
import finemirel.server.connection.client.ConnectionClient;

public interface IStringCommand {
	
	public void execute(ConnectionClient client, ConnectionAgent agent, boolean needConnection);

}
