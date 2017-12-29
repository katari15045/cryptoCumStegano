package application;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javafx.concurrent.Task;
import javafx.scene.control.Button;

public class KeyGenerator extends Task<Void>
{	
	private static String publicKeyPath = null;
	private Button button = null;
	
	public KeyGenerator(Button button)
	{
		this.button = button;
	}
	
	@Override
	public Void call()
	{
		KeyPair keyPair;
		
		System.out.println("\nGenerating keys...");
		updateMessage("Generating keys...");
		keyPair = getKeyPair();
		
		System.out.println("Storing keys...");
		updateMessage("Storing keys...");
		storeKeys(keyPair);
		
		System.out.println("Keys stored!\n");
		postSuccess();
		
		return null;
	}
	
	private KeyPair getKeyPair()
	{
		KeyPairGenerator generator = null;
		KeyPair keyPair = null;
		
		try
		{
			generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			keyPair = generator.genKeyPair();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return keyPair;
	}
	
	private void storeKeys(KeyPair keyPair)
	{
		KeyFactory factory = null;
		RSAPublicKeySpec pubKeySpec = null;
		RSAPrivateKeySpec privKeySpec = null;
		String publicKeyFileName = "destination_public_key.txt";
		String privateKeyFileName = "destination_private_key.txt";
		
		try
		{
			factory = KeyFactory.getInstance("RSA");
			pubKeySpec = factory.getKeySpec( keyPair.getPublic(), RSAPublicKeySpec.class );
			privKeySpec = factory.getKeySpec( keyPair.getPrivate(), RSAPrivateKeySpec.class );
		
			writeKeyToFile(publicKeyFileName, pubKeySpec.getModulus(), pubKeySpec.getPublicExponent());
			writeKeyToFile(privateKeyFileName, privKeySpec.getModulus(), privKeySpec.getPrivateExponent());
			
			publicKeyPath = System.getProperty("user.dir") + "/" + privateKeyFileName;
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void writeKeyToFile(String fileName, BigInteger modulus, BigInteger exponent)
	{
		ObjectOutputStream oos = null;
		
		try
		{
			oos = new ObjectOutputStream (new BufferedOutputStream( new FileOutputStream(fileName) ));
			oos.writeObject(modulus);
			oos.writeObject(exponent);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				oos.close();
			}
		
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void postSuccess()
	{	
		updateMessage("Keys Generated Successfully!");
		button.setDisable(false);
		updateProgress(1.0, 1.0);
	}
	
	@Override
	protected void updateProgress(double workDone, double max) 
	{
		super.updateProgress(workDone, max);
	}
	
	@Override
	protected void updateMessage(String message) 
	{
		super.updateMessage(message);
	}
	
	public static String getPublicKeyPath()
	{
		return publicKeyPath;
	}
}
