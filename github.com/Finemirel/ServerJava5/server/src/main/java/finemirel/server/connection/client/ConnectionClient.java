package finemirel.server.connection.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import finemirel.server.connection.agent.ConnectionAgent;
import finemirel.server.register.MappingClientWithAgent;
import finemirel.server.register.RegisterUsers;

public class ConnectionClient {

	private Logger log = LogManager.getLogger(ConnectionClient.class);
	
	private Socket socket;
	private boolean needConnection;
	private String name;
	private ConnectionAgent agent = null;

	public ConnectionClient(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
	}

	public void startChat() {
		log.info("In run Connection. Name " + this.name);
		needConnection = true;
		while (needConnection) {
			if (agent == null) {
				agent = searchAgent(agent);
			}
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while (in.ready() && agent != null) {
					String msg = in.readLine();
					CMessageDispetcher disp = new CMessageDispetcher(this, agent, msg);
					disp.executeMessage(msg, needConnection);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private ConnectionAgent searchAgent(ConnectionAgent agent) {
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<ConnectionAgent> listConnections = new LinkedList<>();
			//Synchronize
			listConnections = RegisterUsers.getConnectionsAgent();
			if (!listConnections.isEmpty()) {
				for (ConnectionAgent con : listConnections) {
					if (con.isFree()) {
						//Synchronize
						RegisterUsers.addMappingUser(new MappingClientWithAgent(this, con));
						con.setFree(false);
						agent = con;
						log.info("Agent search");
						return agent;
					}
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setAgent(ConnectionAgent agent) {
		this.agent = agent;
	}

}
