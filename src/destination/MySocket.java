import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javafx.concurrent.Task;

public class MySocket extends Task<Void>
{
	private String serverIPAddress;

	private Socket socket = null;
	private String data = null;
	private String dataHash = null;

	private DataInputStream dataInputStream = null;
	private DataOutputStream dataOutputStream = null;

	private MyRSA rsa = null;
	private MyHash hash = null;

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
			connectToServer();
		}

		else if(mode == MySocket.READ)
		{
			receiveDataFromServer();
		}

		else if(mode == MySocket.WRITE)
		{
			sendDataToServer();
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

	private void connectToServer()
	{
		try
		{
		
			System.out.println("\nWaiting for server - " + EmailCumIPCollectorGUI.senderIP + " on port " + Constants.SERVER_PORT + "...");
			socket = new Socket(EmailCumIPCollectorGUI.senderIP, Constants.SERVER_PORT);
			System.out.println("Connected to server on port " + socket.getPort());
		}

		catch(Exception e)
		{	
			e.printStackTrace();
		}		

	}

	private void receiveDataFromServer()
	{
		String encrDataWithPubKey = null;
                String hashedData = null;
                String signedHash = null;
		String decrData = null;
		String decrHash = null;
		String decrDataHash = null;

		try
		{	
			dataInputStream = new DataInputStream( socket.getInputStream() );

			encrDataWithPubKey = dataInputStream.readUTF();
			signedHash = dataInputStream.readUTF();

			decrData = SecureTunnelCreator.rsa.decryptWithPrivKey(encrDataWithPubKey);
			decrHash = SecureTunnelCreator.rsa.decryptWithPubKey(signedHash, PublicKeyCollectorGUI.srcPubKey);
			decrDataHash = hash.hash(decrData);

                        if( decrHash.equals(decrDataHash) )
                        {
                                System.out.println("Hash matched!");
                                data = decrData;
                        }

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void sendDataToServer()
	{
		String encrDataWithDstPubKey = null;
		String hashedData = null;
		String signedHash = null;

		try
		{
			dataOutputStream = new DataOutputStream( socket.getOutputStream() );

			encrDataWithDstPubKey = SecureTunnelCreator.rsa.encryptWithPubKey(data, PublicKeyCollectorGUI.srcPubKey);
			hashedData = SecureTunnelCreator.hash.hash(data);
			signedHash = SecureTunnelCreator.rsa.encryptWithPrivKey(hashedData);

			dataOutputStream.writeUTF(encrDataWithDstPubKey);
			dataOutputStream.writeUTF(signedHash);
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
			dataOutputStream.close();
			socket.close();
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



