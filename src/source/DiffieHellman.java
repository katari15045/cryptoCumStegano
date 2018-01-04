// Tutorial -> https://docs.oracle.com/javase/9/security/java-cryptography-architecture-jca-reference-guide.htm#JSSEC-GUID-98B5A57E-E5BA-46F2-BE35-2056F43C58A4

import java.security.KeyPairGenerator;
import java.security.KeyPair;
import javax.crypto.KeyAgreement;
import java.util.Base64;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.lang.Exception;
import java.lang.Thread;
import javafx.concurrent.Task;

public class DiffieHellman extends Task<Void>
{
	private KeyPairGenerator generator = null;
	private String algo = "DH";
	private KeyPair keyPair = null;

	private KeyAgreement keyAgreement = null;
	private Thread socketThread = null;
	private MySocket socket = null;
	private byte[] secret = null;

	public DiffieHellman(Thread socketThread, MySocket socket)
	{
		this.socketThread = socketThread;
		this.socket = socket;
	}

	@Override
	public Void call()
	{	
		String dataToSend = null;
		String dataReceived = null;

		try
		{
			updateMessage("Connecting to " + EmailCumIPCollectorGUI.receiverIP + " on port " + Constants.SOCK_PORT + "...");
			System.out.println("connecting to " + EmailCumIPCollectorGUI.receiverIP + "...");
			socket.setMode( MySocket.CONNECT );		
			socketThread.start();
			socketThread.join();

			updateMessage("Generating Diffie Hellman keys...");
			System.out.println("Generating Diffie Hellman keys...");
			dataToSend = start();

			updateMessage("Sending Diffie Hellman's public key to " + EmailCumIPCollectorGUI.receiverIP + "...");
			System.out.println("Sending DH public key to " + EmailCumIPCollectorGUI.receiverIP + "...");
			MySocket socketWrite = new MySocket( socket.getSocket() );
			socketWrite.setData(dataToSend);
			socketWrite.setMode( MySocket.WRITE );
			socketThread = new Thread(socketWrite);
			socketThread.start();
			socketThread.join();
	
			updateMessage("Receiving Diffie Hellman's public Key from " + EmailCumIPCollectorGUI.receiverIP + "...");
			System.out.println("Receiving Diffie Hellman's public Key from " + EmailCumIPCollectorGUI.receiverIP + "...");
			MySocket socketRead = new MySocket( socketWrite.getSocket() );
			socketRead.setMode( MySocket.READ );
			socketThread = new Thread(socketRead);
			socketThread.start();
			socketThread.join();

			dataReceived = socketRead.getData();
			updateMessage("Extracting secret using Diffie Hellman...");
			System.out.println("Extracting secret using Diffie Hellman...");
			secret = end(dataReceived);
			System.out.println("Secret extracted!\n");		
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;		
	}

	String start()
	{
		String pubKeyStr = null;

		generateKeyPair();
		signTheAgreement();
		pubKeyStr = getPubKeyInStr();

		return pubKeyStr;
	}

	byte[] end(String dstPubKeyStr)
	{
		PublicKey pubKey = null;
		byte[] secret = null;

		pubKey = getPubKeyFromBytes( Base64.getDecoder().decode( dstPubKeyStr.getBytes() ) );	

		try
		{
			keyAgreement.doPhase(pubKey, true);
			secret = keyAgreement.generateSecret();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}					
		
		return secret;	
	}

	

	private PublicKey getPubKeyFromBytes(byte[] pubKeyBytes)
	{
		X509EncodedKeySpec x509 = null;
		KeyFactory keyFactory = null;

		try
		{
			x509 = new X509EncodedKeySpec(pubKeyBytes);
			keyFactory = KeyFactory.getInstance(algo);		

			return keyFactory.generatePublic(x509);
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}	

		return null;
	}

	private void signTheAgreement()
	{
		try
		{
			keyAgreement = KeyAgreement.getInstance(algo);
			keyAgreement.init( keyPair.getPrivate() );
		}

		catch(Exception e)
		{	
			e.printStackTrace();
		}
	}

	private void generateKeyPair()
	{
		try
		{
			generator = KeyPairGenerator.getInstance(algo);
			generator.initialize(Constants.DH_KEY_SIZE);
			keyPair = generator.generateKeyPair();
		}

		catch(Exception e)
		{		
			e.printStackTrace();
		}
	}

	private String getPubKeyInStr()
	{
		byte[] pubKeyBytes = null;
		
		pubKeyBytes = keyPair.getPublic().getEncoded();
		System.out.println("dh_pub_key : " + pubKeyBytes.length + " bytes!");
		System.out.println("after encoding : " + Base64.getEncoder().encodeToString(pubKeyBytes).getBytes().length + " bytes");
		return Base64.getEncoder().encodeToString(pubKeyBytes);
	}

	@Override
        protected void updateMessage(String message)
        {
                super.updateMessage(message);
        }

	byte[] getSecret()
	{
		return secret;
	}
}



