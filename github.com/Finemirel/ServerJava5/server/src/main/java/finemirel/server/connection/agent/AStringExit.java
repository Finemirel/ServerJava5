package finemirel.server.connection.agent;

import finemirel.server.connection.IStringCommand;
import finemirel.server.connection.client.ConnectionClient;
import finemirel.server.register.MappingClientWithAgent;
import finemirel.server.register.RegisterUsers;

public class AStringExit implements IStringCommand {

	public AStringExit() {
		
	}

	@Override
	public void execute(ConnectionClient client, ConnectionAgent agent, boolean needConnection) {
		deleteSome(agent);
		agent.setClient(null);
		client.setAgent(null);
		client.setNeedConnection(true);
		needConnection = false;
	}

	synchronized private void deleteSome(ConnectionAgent agent) {
		RegisterUsers.deleteConnectionAgent(agent);
		for (MappingClientWithAgent map : RegisterUsers.getMappingUser()) {
			if (map.getAgent().equals(agent)) {
				RegisterUsers.deleteMappingUser(map);
			}
		}
	}
}
