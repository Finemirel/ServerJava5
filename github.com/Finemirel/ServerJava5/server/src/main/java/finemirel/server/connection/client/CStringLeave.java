package finemirel.server.connection.client;

import finemirel.server.connection.IStringCommand;
import finemirel.server.connection.agent.ConnectionAgent;
import finemirel.server.register.MappingClientWithAgent;
import finemirel.server.register.RegisterUsers;

public class CStringLeave implements IStringCommand{
	
	public CStringLeave() {
		
	}

	@Override
	public void execute(ConnectionClient client, ConnectionAgent agent, boolean needConnection) {
		// Synchronize
				for (MappingClientWithAgent map : RegisterUsers.getMappingUser()) {
					if (map.getClient().equals(client)) {
						RegisterUsers.deleteMappingUser(map);
					}
				}
				client.setAgent(null);
				agent.setClient(null);
				agent.setFree(true);
				agent.setNeedConnection(true);
				needConnection = true;
		
	}

}
