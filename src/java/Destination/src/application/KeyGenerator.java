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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class KeyGenerator implements EventHandler<ActionEvent>
{	
	private Stage stage = null;
	private String publicKeyPath = null;
	
	public KeyGenerator(Stage stage) 
	{
		this.stage = stage;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		KeyPair keyPair;
		
		keyPair = getKeyPair();
		storeKeys(keyPair);
		postSuccess();
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
		Alert alert = null;
		OTPSenderGUI otpSenderGUI = null;
		
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Step 1 completed!");
		alert.setHeaderText("Success!");
		alert.setContentText("Asymmetric keys have been created!");
		alert.show();
		
		otpSenderGUI = new OTPSenderGUI(publicKeyPath);
		otpSenderGUI.start(stage);
	}
}
