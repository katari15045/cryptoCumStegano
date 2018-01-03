import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javafx.concurrent.Task;

public class MySocket extends Task<Void>
{
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private String data = null;

	private DataInputStream dataInputStream = null;
	private DataOutputStream dataOutputStream = null;

	private int mode = 0;
	static final int CONNECT = 1;
	static final int READ = 2;
	static final int WRITE = 3;
	static final int DISCONNECT = 4;

	@Override
	public Void call()
	{
		if(mode == MySocket.CONNECT)
		{
			connectToClient();
		}

		else if(mode == MySocket.READ)
		{
			receiveDataFromClient();
		}

		else if(mode == MySocket.WRITE)
		{
			sendDataToClient();
		}

		else if(mode == MySocket.DISCONNECT)
		{
			closeConnection();
		}

		else
		{
			System.out.println("Invalid Mode!");
		}

		return null;
	}

	private void connectToClient()
	{
		try
		{
			serverSocket = new ServerSocket(Constants.SOCK_PORT);
			System.out.printf("\nWaiting for client on port " + serverSocket.getLocalPort() + "...");
			socket = serverSocket.accept();
			System.out.println("Client -> " + socket.getInetAddress() + ":" + socket.getPort() +  " Connected\n");
		}

		catch(Exception e)
		{	
			e.printStackTrace();
		}		

	}

	private void receiveDataFromClient()
	{
		try
		{	
			dataInputStream = new DataInputStream( socket.getInputStream() );
			data = dataInputStream.readUTF();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void sendDataToClient()
	{
		try
		{
			dataOutputStream = new DataOutputStream( socket.getOutputStream() );
			dataOutputStream.writeUTF(data);
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void closeConnection()
	{
		try
		{
			dataInputStream.close();
			socket.close();
		serverSocket.close();
			System.out.println("Client Disconnected!");
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	String getData()
	{
		return data;
	}

	void setData(String data)
	{
		this.data = data;
	}

	void setMode(int mode)
	{
		this.mode = mode;
	}
}



