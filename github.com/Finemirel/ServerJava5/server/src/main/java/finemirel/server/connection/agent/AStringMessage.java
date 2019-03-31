package finemirel.server.connection.agent;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import finemirel.server.connection.IStringCommand;
import finemirel.server.connection.client.ConnectionClient;

public class AStringMessage implements IStringCommand {
	
	String msg;
	
	public AStringMessage(String msg) {
		this.msg = msg;
	}

	@Override
	public void execute(ConnectionClient client, ConnectionAgent agent, boolean needConnection) {
		try {
			PrintWriter out = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(client.getSocket().getOutputStream())), true);
			out.println(agent.getName() + "(agent): " + msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
