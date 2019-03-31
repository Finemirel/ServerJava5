package finemirel.server.register;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import finemirel.server.connection.agent.ConnectionAgent;

public class AgentRegistration implements UserRegistration {
	private Logger log = LogManager.getLogger(AgentRegistration.class);
	
	public AgentRegistration() {
		
	}
	
	@Override
	public void registerUser(Socket socket, String helloMsg, NeedConnectedUser need) {
		try {
			need.setNeedConnectedUser(false);
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
					true);
			log.info("Register agent");
			// expected input format: REG_AGENT_CMD + name
			String name = getNameString(helloMsg);
			ConnectionAgent con = new ConnectionAgent(socket, name);
			synchRegisterAgent(con);
			out.println("You name: " + name + "! You agent");
			con.startChat();//TODO
		} catch (IOException e) {
			need.setNeedConnectedUser(true);
			e.printStackTrace();
		}

	}

	synchronized private void synchRegisterAgent(ConnectionAgent con) {
		RegisterUsers.addConnectionAgent(con);
	}
	
	private String getNameString(String str) {
		String[] strArray = str.split(" ");
		String name = strArray[2];
		return name;
	}

}
