package finemirel.server.connection.client;

import java.io.IOException;

import finemirel.server.connection.IStringCommand;
import finemirel.server.connection.agent.ConnectionAgent;
import finemirel.server.register.MappingClientWithAgent;
import finemirel.server.register.RegisterUsers;

public class CStringExit implements IStringCommand {
	
	public CStringExit() {
		
	}

	@Override
	public void execute(ConnectionClient client, ConnectionAgent agent, boolean needConnection) {
		// Synchronize
		for (MappingClientWithAgent map : RegisterUsers.getMappingUser()) {
			if (map.getClient().equals(client)) {
				RegisterUsers.deleteMappingUser(map);
			}
		}
		try {
			client.getSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.setAgent(null);
		agent.setClient(null);
		agent.setNeedConnection(true);
		agent.setFree(true);
		needConnection = false;
	}

}
