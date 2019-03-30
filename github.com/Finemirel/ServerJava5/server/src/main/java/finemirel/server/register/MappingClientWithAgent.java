package finemirel.server.register;

import finemirel.server.connection.ConnectionAgent;
import finemirel.server.connection.ConnectionClient;

public class MappingClientWithAgent {
	private ConnectionClient client;
	private ConnectionAgent agent;
	
	public MappingClientWithAgent(ConnectionClient client, ConnectionAgent agent) {
		this.client = client;
		this.agent = agent;
	}

	public ConnectionClient getClient() {
		return client;
	}

	public void setClient(ConnectionClient client) {
		this.client = client;
	}

	public ConnectionAgent getAgent() {
		return agent;
	}

	public void setAgent(ConnectionAgent agent) {
		this.agent = agent;
	}
}
