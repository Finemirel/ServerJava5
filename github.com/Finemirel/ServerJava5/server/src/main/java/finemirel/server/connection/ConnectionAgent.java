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

public class ConnectionAgent implements Runnable {
	private Logger log = LogManager.getLogger(ConnectionAgent.class);

	private static final String EXIT = "/exit";

	private boolean needConnection;
	private ConnectionClient client;
	private Socket socket;
	private String name;
	private boolean free;

	public ConnectionAgent(Socket socket, String name, boolean free) {
		this.socket = socket;
		this.name = name;
		this.free = free;
	}

	@Override
	public void run() {
		log.info("In run ConnectionAgent" + this.name);
		client = null;
		needConnection = true;
		while (needConnection) {
			try {
				if (client == null) {
					boolean goal = false;
					while (!goal) {
						//Synchronaize
						Thread.sleep(300);
						List<MappingClientWithAgent> mappingUser = new LinkedList<>();
						mappingUser = RegisterUsers.getMappingUser();
						if (!mappingUser.isEmpty()) {
							for (MappingClientWithAgent map : mappingUser) {
								if (map.getAgent().equals(this)) {
									client = map.getClient();
									log.info("Client call");
									goal = true;
								}
							}
						}
					}
				}

				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (in.ready()) {
					PrintWriter out = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(client.getSocket().getOutputStream())), true);
					String msg = in.readLine();
					out.println(msg);
					//Synchronaize
					if (msg.equals(EXIT)) {
						for (MappingClientWithAgent map : RegisterUsers.getMappingUser()) {
							if (map.getAgent().equals(this)) {
								RegisterUsers.deleteMappingUser(map);
								RegisterUsers.deleteConnectionAgent(this);
								break;
							}
						}
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
	
	public Socket getSocket() {
		return socket;
	}


}
