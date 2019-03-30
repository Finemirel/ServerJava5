package finemirel.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import finemirel.server.register.ConnectedUser;

public class Server {
	private Logger log = LogManager.getLogger(Server.class);
	
	public static final int PORT = 8085;
	
	private ServerSocket serverSocket; 
	
	public Server() {
		try {
			log.info("Server created");
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		log.info("Server started");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				log.info("new client connection");
				new Thread(new ConnectedUser(socket)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
