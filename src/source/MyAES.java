// Tutorial -> https://www.quickprogrammingtips.com/java/how-to-encrypt-and-decrypt-data-in-java-using-aes-algorithm.html

import java.lang.Exception;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MyAES
{
	private String algo = null;
	private int keySize = 0;
	private SecretKey key;

	MyAES()
	{
		algo = "AES";
		keySize = 256;
	}
	
	void start()
	{
		key = generateKey();
	}

	String decrypt(String cipherText)
	{
		Cipher cipher = null;
		byte[] decryptedTextBytes = null;

		try
		{
			cipher = Cipher.getInstance(algo);
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedTextBytes = cipher.doFinal( Base64.getDecoder().decode( cipherText.getBytes() ) );
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return new String(decryptedTextBytes);
	}
	
	String decrypt(String cipherText, SecretKey symKey)
	{
		Cipher cipher = null;
		byte[] decryptedTextBytes = null;

		try
		{
			cipher = Cipher.getInstance(algo);
			cipher.init(Cipher.DECRYPT_MODE, symKey);
			decryptedTextBytes = cipher.doFinal( Base64.getDecoder().decode( cipherText.getBytes() ) );
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return new String(decryptedTextBytes);
	}
	
	String encryptSymKeyWithPubKey(PublicKey publicKey)
	{
		Cipher cipher = null;
		byte[] encryptedBytes = null;
		
		try
		{
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedBytes = cipher.doFinal( key.getEncoded() );
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}
	
	String encryptSymKeyWithPrivKey(PrivateKey privKey)
	{
		Cipher cipher = null;
		byte[] encryptedBytes = null;
		
		try
		{
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privKey);
			encryptedBytes = cipher.doFinal( key.getEncoded() );
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}
	
	String encrypt(String plainText)
	{
		Cipher cipher = null;
		byte[] cipherTextBytes = null;

		try
		{
			cipher = Cipher.getInstance(algo);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherTextBytes = cipher.doFinal( plainText.getBytes() );
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return Base64.getEncoder().encodeToString(cipherTextBytes);
	}

	private SecretKey generateKey()
	{
		KeyGenerator generator = null;
		SecretKey key = null;

		try
		{
			generator = KeyGenerator.getInstance(algo);
			generator.init(keySize);
			key = generator.generateKey();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return key;
	}
	
	void setKeySize(int keySize)
	{
		this.keySize = keySize;
	}
	
	SecretKey getSymKey()
	{
		return key;
	}	
	
	String getSymKeyInStr()
	{
		return Base64.getEncoder().encodeToString( key.getEncoded() );
	}
}









