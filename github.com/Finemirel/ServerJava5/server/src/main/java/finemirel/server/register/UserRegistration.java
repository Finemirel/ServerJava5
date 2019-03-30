package finemirel.server.register;

import java.net.Socket;

import finemirel.server.connection.NeedConnectedUser;

public interface UserRegistration {
	
	public void registerUser(Socket socket, String helloMsg, NeedConnectedUser need);

}
