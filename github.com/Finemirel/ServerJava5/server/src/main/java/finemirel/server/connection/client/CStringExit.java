package finemirel.server.connection.client;

import finemirel.server.connection.IStringCommand;
import finemirel.server.connection.agent.ConnectionAgent;
import finemirel.server.register.MappingClientWithAgent;
import finemirel.server.register.RegisterUsers;

public class CStringExit implements IStringCommand {
	
	public CStringExit() {
		
	}

	@Override
	public void execute(ConnectionClient client, ConnectionAgent agent, boolean needConnection) {
		deleteSome(client);
		agent.setClient(null);
		agent.setNeedConnection(true);
		agent.setFree(true);
		client.setAgent(null);
		needConnection = false;
	}

	synchronized private void deleteSome(ConnectionClient client) {
		for (MappingClientWithAgent map : RegisterUsers.getMappingUser()) {
			if (map.getClient().equals(client)) {
				RegisterUsers.deleteMappingUser(map);
			}
		}
	}

}
