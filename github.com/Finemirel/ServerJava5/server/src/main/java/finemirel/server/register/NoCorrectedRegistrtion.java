package finemirel.server.register;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoCorrectedRegistrtion implements UserRegistration {
	private Logger log = LogManager.getLogger(NoCorrectedRegistrtion.class);
	
	private static final String REG_AGENT_CMD = "/register agent ";
	private static final String REG_CLIENT_CMD = "/register client ";

	@Override
	public void registerUser(Socket socket, String helloMsg, NeedConnectedUser need) {
		try {
			need.setNeedConnectedUser(true);
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
					true);
			log.info("Send msg to user");
			out.println(helloMsg + " To enter the chat enter: " + REG_CLIENT_CMD + " or " + REG_AGENT_CMD + " and your name!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
