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
	private String imageString = null;

	private DataInputStream dataInputStream = null;
	private DataOutputStream dataOutputStream = null;

	private int mode = 0;
	static final int CONNECT = 1;
	static final int READ = 2;
	static final int WRITE = 3;
	static final int POST_SYM_KEY = 4;

	static int status;
	static final int FINE = 0;
	static final int SERVER_UNAVAILABLE = 1;
	
	public MySocket()
	{
	
	}

	public MySocket(Socket socket)
	{
		this.socket = socket;
	}

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

		else if(mode == MySocket.POST_SYM_KEY)
		{
			receiveDataWithSymKey();
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
		
			System.out.println("\nWaiting for server " + EmailCumIPCollectorGUI.senderIP + " on port " + Constants.SERVER_PORT + "...");
			socket = new Socket(EmailCumIPCollectorGUI.senderIP, Constants.SERVER_PORT);
			System.out.println("Connected to server on port " + socket.getPort());
		}

		catch(Exception e)
		{	
			e.printStackTrace();
			MySocket.status = MySocket.SERVER_UNAVAILABLE;
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
			decrData = MyRSA.decryptWithPrivKey(encrDataWithPubKey);
			decrHash = MyRSA.decryptWithPubKey(signedHash, PublicKeyCollectorGUI.srcPubKey);
			decrDataHash = MyHash.hash(decrData);

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

	private void receiveDataWithSymKey()
	{
		String encrLocStr = null, encrImgStr = null;
		String signedHashLocStr = null, signedHashImgStr = null;
		String hashedLocStr = null, hashedImgStr = null;
		String decrLocStr = null, decrImgStr = null;
		String decrHashLocStr = null, decrHashImgStr = null;
		String hashLocStr = null, hashImgStr = null;

		try
		{
			dataInputStream = new DataInputStream( socket.getInputStream() );

			encrLocStr = read(dataInputStream);
			signedHashLocStr = read(dataInputStream);
			encrImgStr = read(dataInputStream);
			signedHashImgStr = read(dataInputStream);

			decrLocStr = MyAES.decrypt(encrLocStr, SecureTunnelCreator.symKey);
			decrHashLocStr = MyRSA.decryptWithPubKey(signedHashLocStr, PublicKeyCollectorGUI.srcPubKey);
			hashLocStr = MyHash.hash(decrLocStr);

			decrImgStr = MyAES.decrypt(encrImgStr, SecureTunnelCreator.symKey);
			decrHashImgStr = MyRSA.decryptWithPubKey(signedHashImgStr, PublicKeyCollectorGUI.srcPubKey);
			hashImgStr = MyHash.hash(decrImgStr);

			if( decrHashLocStr.equals(hashLocStr) && decrHashImgStr.equals(hashImgStr) )
			{
				System.out.println("Hashes matched!");
				data = decrLocStr;
				imageString = decrImgStr;
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private String read(DataInputStream dis)
	{
		int len;
		byte[] bytes = null;
		String str = null;

		try
		{
			len = dis.readInt();
			bytes = new byte[len];
			dis.readFully(bytes);
			str = new String(bytes, "UTF-8");
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return str;
	}

	private void sendDataToServer()
	{
		String encrDataWithDstPubKey = null;
		String hashedData = null;
		String signedHash = null;

		try
		{
			dataOutputStream = new DataOutputStream( socket.getOutputStream() );

			encrDataWithDstPubKey = MyRSA.encryptWithPubKey(data, PublicKeyCollectorGUI.srcPubKey);
			hashedData = MyHash.hash(data);
			signedHash = MyRSA.encryptWithPrivKey(hashedData);

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

	String getImageString()
	{
		return imageString;
	}

	void setMode(int mode)
	{
		this.mode = mode;
	}

	Socket getSocket()
	{
		return socket;
	}
}



