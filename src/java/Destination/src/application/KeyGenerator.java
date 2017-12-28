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
	
	private static KeyPair getKeyPair()
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
	
	private static void storeKeys(KeyPair keyPair)
	{
		KeyFactory factory = null;
		RSAPublicKeySpec pubKeySpec = null;
		RSAPrivateKeySpec privKeySpec = null;
		
		try
		{
			factory = KeyFactory.getInstance("RSA");
			pubKeySpec = factory.getKeySpec( keyPair.getPublic(), RSAPublicKeySpec.class );
			privKeySpec = factory.getKeySpec( keyPair.getPrivate(), RSAPrivateKeySpec.class );
		
			writeKeyToFile("private.key", pubKeySpec.getModulus(), pubKeySpec.getPublicExponent());
			writeKeyToFile("public.key", privKeySpec.getModulus(), privKeySpec.getPrivateExponent());
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void writeKeyToFile(String fileName, BigInteger modulus, BigInteger exponent)
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
		
		otpSenderGUI = new OTPSenderGUI();
		otpSenderGUI.start(stage);
	}
}
