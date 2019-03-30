package finemirel.server.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import finemirel.server.register.MappingClientWithAgent;
import finemirel.server.register.RegisterUsers;

public class ConnectionClient implements Runnable {

	private Logger log = LogManager.getLogger(ConnectionClient.class);

	private static final String EXIT = "/exit";
	//private static final String LEAVE = "/leave";

	private Socket socket;
	private boolean needConnection;
	private String name;

	public ConnectionClient(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
	}

	@Override
	public void run() {
		log.info("In run Connection. Name " + this.name);
		needConnection = true;
		ConnectionAgent agent = null;
		while (needConnection) {
			try {
				if (agent == null) {
					boolean goal = false;
					while (!goal) {
						Thread.sleep(300);
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
									goal = true;
								}
							}
						}
					}
				}

				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (in.ready()) {
					PrintWriter out = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(agent.getSocket().getOutputStream())), true);
					String msg = in.readLine();
					out.println(msg);
					if (msg.equals(EXIT)) {
						// Synchronize
						for (MappingClientWithAgent map : RegisterUsers.getMappingUser()) {
							if (map.getClient().equals(this)) {
								RegisterUsers.deleteMappingUser(map);
								break;
							}
						}

						agent.setFree(true);
						this.close();
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void close() {
		needConnection = false;
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


}
