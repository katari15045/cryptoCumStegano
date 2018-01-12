import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.SecretKey;

import javafx.concurrent.Task;
import javafx.scene.control.Button;

class MyKeyGenerator extends Task<Void>
{	
	private static String publicKeyPath = null;
	private Button button = null;
	
	MyKeyGenerator(Button button)
	{
		this.button = button;
	}
	
	@Override
	public Void call()
	{
		KeyPair keyPair = null;
		
		System.out.println("\nGenerating keys...");
		updateMessage("Generating keys...");
		keyPair = MyRSA.generateKeyPair();
        MyRSA.setPrivKey( keyPair.getPrivate() );
        MyRSA.setPubKey( keyPair.getPublic() );
		
		System.out.println("Storing keys...");
		updateMessage("Storing keys...");
		MyRSA.storeKeys(keyPair);	
	
		System.out.println("Keys stored!\n");
		postSuccess();
		
		return null;
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
	
	static String getPublicKeyPath()
	{
		return publicKeyPath;
	}

	static void setPublicKeyPath(String publicKeyPath)
        {
                MyKeyGenerator.publicKeyPath = publicKeyPath;
        }

}
