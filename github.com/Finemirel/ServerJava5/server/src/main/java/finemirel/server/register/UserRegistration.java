package finemirel.server.register;

import java.net.Socket;

public interface UserRegistration {
	
	public void registerUser(Socket socket, String helloMsg, NeedConnectedUser need);

}
