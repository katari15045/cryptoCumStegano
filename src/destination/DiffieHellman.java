// Tutorial -> https://docs.oracle.com/javase/9/security/java-cryptography-architecture-jca-reference-guide.htm#JSSEC-GUID-98B5A57E-E5BA-46F2-BE35-2056F43C58A4

import java.net.Socket;
import javax.crypto.spec.DHParameterSpec;
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
import javax.crypto.interfaces.DHPublicKey;

class DiffieHellman extends Task<Void>
{
	private String algo = "DH";
	private KeyPair keyPair = null;

	private KeyAgreement agreement = null;
	private Thread socketThread = null;
	private MySocket socket = null;
	private PublicKey srcPubKey = null;
	private byte[] secret = null;
        private static Socket activeSocket = null;

	DiffieHellman(Thread socketThread, MySocket socket)
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
			updateMessage("Connecting to " + EmailCumIPCollectorGUI.senderIP + " on port " + Constants.SERVER_PORT + "...");
			System.out.println("connecting to " + EmailCumIPCollectorGUI.senderIP + "...");
			socket.setMode( MySocket.CONNECT );		
                        MySocket.status = MySocket.FINE;
			socketThread.start();
			socketThread.join();

                        if( MySocket.status == MySocket.SERVER_UNAVAILABLE )
                        {
                                return null;
                        }

			updateMessage("Receiving Diffie Hellman's public Key from " + EmailCumIPCollectorGUI.senderIP + "...");
			System.out.println("Receiving Diffie Hellman's public Key from " + EmailCumIPCollectorGUI.senderIP + "...");	
			MySocket socketRead = new MySocket( socket.getSocket() );
			socketRead.setMode( MySocket.READ );
			socketThread = new Thread(socketRead);
			socketThread.start();
			socketThread.join();

			dataReceived = socketRead.getData();

			updateMessage("Generating Diffie Hellman keys...");
			System.out.println("Generating Diffie Hellman keys...");
			dataToSend = start(dataReceived);

			updateMessage("Sending Diffie Hellman's public key to " + EmailCumIPCollectorGUI.senderIP + "...");
			System.out.println("Sending DH public key to " + EmailCumIPCollectorGUI.senderIP + "...");
			MySocket socketWrite = new MySocket( socketRead.getSocket() );
			socketWrite.setData(dataToSend);
			socketWrite.setMode( MySocket.WRITE );
			socketThread = new Thread(socketWrite);
			socketThread.start();
			socketThread.join();
	
                        DiffieHellman.activeSocket = socketWrite.getSocket();
			updateMessage("Extracting secret using Diffie Hellman...");
			System.out.println("Extracting secret using Diffie Hellman...");
			end();
			System.out.println("Secret extracted!\n");		
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;		
	}

	String start(String srcPubKeyStr)
        {
                String pubKeyStr = null;
                DHParameterSpec dhSpec = null;
		
                srcPubKey = getPubKey(srcPubKeyStr);
                dhSpec = ((DHPublicKey) srcPubKey).getParams();
                generateKeys(dhSpec);
                signAgreement();
                pubKeyStr = getPubStr();

                return pubKeyStr;
        }

	
	void end()
        {
                try
                {
                        agreement.doPhase(srcPubKey, true);
                        secret = agreement.generateSecret();
                }

                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }

	private String getPubStr()
        {
                byte[] pubKeyBytes = null;

                pubKeyBytes = keyPair.getPublic().getEncoded();
                return Base64.getEncoder().encodeToString(pubKeyBytes);
        }

        private void signAgreement()
        {
                try
                {
                        agreement = KeyAgreement.getInstance(algo);
                        agreement.init( keyPair.getPrivate() );
                }

                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }

	private void generateKeys(DHParameterSpec dhSpec)
        {
                KeyPairGenerator generator = null;

                try
                {
                        generator = KeyPairGenerator.getInstance(algo);
                        generator.initialize(dhSpec);
                        keyPair = generator.generateKeyPair();
                }

                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }

        private PublicKey getPubKey(String pubKeyStr)
        {
                KeyFactory keyFactory = null;
                X509EncodedKeySpec x509 = null;

                try
                {
                        keyFactory = KeyFactory.getInstance(algo);
                        x509 = new X509EncodedKeySpec( Base64.getDecoder().decode( pubKeyStr.getBytes() ) );

                        return keyFactory.generatePublic(x509);
                }

                catch(Exception e)
                {
                        e.printStackTrace();
                }

                return null;
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

        static Socket getActiveSocket()
        {
                return DiffieHellman.activeSocket;
        }
}



