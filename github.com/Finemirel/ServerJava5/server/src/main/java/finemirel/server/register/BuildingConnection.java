package finemirel.server.register;

import java.net.Socket;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import finemirel.server.Server;
import finemirel.server.connection.NeedConnectedUser;

public class BuildingConnection {

	private Logger log = LogManager.getLogger(Server.class);

	private static final String REG_AGENT_CMD = "/register agent ";
	private static final String REG_CLIENT_CMD = "/register client ";
	
	private UserRegistration clientReg = new ClientRegistration();
	private UserRegistration agentReg = new AgentRegistration();
	private UserRegistration noCorrect = new NoCorrectedRegistrtion();
	
	private final HashMap<String, UserRegistration> reg = new HashMap<>();

	private Socket socket;

	public BuildingConnection(Socket socket, String helloMsg) {
		reg.put(REG_CLIENT_CMD, clientReg);
		reg.put(REG_AGENT_CMD, agentReg);
		if(!reg.containsKey(helloMsg)) {
			reg.put(helloMsg, noCorrect);
		}
		this.socket = socket;
	}

	public void build(String helloMsg, NeedConnectedUser need) {
		log.info("in building connection");
		reg.get(this.getCMDString(helloMsg)).registerUser(socket, helloMsg, need);
	}

	private String getCMDString(String str) {
		String[] strArray = str.split(" ");
		if (strArray.length >= 3 && (str.startsWith(REG_AGENT_CMD) || str.startsWith(REG_CLIENT_CMD))) {
			String cmd = strArray[0] + " " + strArray[1] + " ";
			return cmd;
		}
		return str;
	}

}
