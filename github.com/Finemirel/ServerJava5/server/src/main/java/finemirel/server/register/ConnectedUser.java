package finemirel.server.register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import finemirel.server.connection.NeedConnectedUser;

public class ConnectedUser implements Runnable {
	private Logger log = LogManager.getLogger(ConnectedUser.class);

	private BuildingConnection buildingConnection;
	private Socket socket;
	private NeedConnectedUser need;

	public ConnectedUser(Socket socket) {
		this.socket = socket;
		need = new NeedConnectedUser();
	}

	@Override
	public void run() {
		while (need.isNeedConnectedUser()) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (in.ready()) {
					String helloMsg = in.readLine();
					log.info(helloMsg);
					buildingConnection = new BuildingConnection(socket, helloMsg);
					buildingConnection.build(helloMsg, need);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
