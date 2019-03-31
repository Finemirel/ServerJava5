package finemirel.server.register;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import finemirel.server.connection.agent.ConnectionAgent;

public class RegisterUsers {
	
	private volatile static List<ConnectionAgent> connectionsAgent = Collections.synchronizedList(new LinkedList<>());
	private volatile static List<MappingClientWithAgent> mappingUser = Collections.synchronizedList(new LinkedList<>());
	
	public static List<ConnectionAgent> getConnectionsAgent() {
		return connectionsAgent;
	}
	
	public static void addConnectionAgent(ConnectionAgent connectionAgent) {
		connectionsAgent.add(connectionAgent);
	}
	
	public static void deleteConnectionAgent(ConnectionAgent connectionAgent) {
		connectionsAgent.remove(connectionAgent);
	}

	public static List<MappingClientWithAgent> getMappingUser() {
		return mappingUser;
	}
	
	public static void addMappingUser(MappingClientWithAgent mapping) {
		mappingUser.add(mapping);
	}
	
	public static void deleteMappingUser(MappingClientWithAgent mapping) {
		mappingUser.remove(mapping);
	}
		
}
