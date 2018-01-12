// Tutorial -> https://www.quickprogrammingtips.com/java/how-to-encrypt-and-decrypt-data-in-java-using-aes-algorithm.html

import java.lang.Exception;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

class MyAES
{
	private static SecretKey key;
	
	static String decrypt(String cipherText)
	{
		Cipher cipher = null;
		byte[] decryptedTextBytes = null;

		try
		{
			cipher = Cipher.getInstance(Constants.SYM_ALGO);
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedTextBytes = cipher.doFinal( Base64.getDecoder().decode( cipherText.getBytes() ) );
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return new String(decryptedTextBytes);
	}
	
	static String decrypt(String cipherText, SecretKey symKey)
	{
		Cipher cipher = null;
		byte[] decryptedTextBytes = null;

		try
		{
			cipher = Cipher.getInstance(Constants.SYM_ALGO);
			cipher.init(Cipher.DECRYPT_MODE, symKey);
			decryptedTextBytes = cipher.doFinal( Base64.getDecoder().decode( cipherText.getBytes() ) );
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return new String(decryptedTextBytes);
	}
	
	static String encryptSymKeyWithPubKey(PublicKey publicKey)
	{
		Cipher cipher = null;
		byte[] encryptedBytes = null;
		
		try
		{
			cipher = Cipher.getInstance(Constants.ASYM_ALGO);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedBytes = cipher.doFinal( key.getEncoded() );
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}
	
	static String encryptSymKeyWithPrivKey(PrivateKey privKey)
	{
		Cipher cipher = null;
		byte[] encryptedBytes = null;
		
		try
		{
			cipher = Cipher.getInstance(Constants.ASYM_ALGO);
			cipher.init(Cipher.ENCRYPT_MODE, privKey);
			encryptedBytes = cipher.doFinal( key.getEncoded() );
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}
	
	static String encrypt(String plainText)
	{
		Cipher cipher = null;
		byte[] cipherTextBytes = null;

		try
		{
			cipher = Cipher.getInstance(Constants.SYM_ALGO);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherTextBytes = cipher.doFinal( plainText.getBytes() );
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return Base64.getEncoder().encodeToString(cipherTextBytes);
	}

	static SecretKey generateKey()
	{
		KeyGenerator generator = null;
		SecretKey key = null;

		try
		{
			generator = KeyGenerator.getInstance(Constants.SYM_ALGO);
			generator.init(Constants.SYM_KEY_SIZE);
			key = generator.generateKey();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return key;
	}
	
	static SecretKey getSymKey()
	{
		return key;
	}	

	static void setSymKey(SecretKey key)
	{
		MyAES.key = key;
	}
	
	static String getSymKeyInStr()
	{
		return Base64.getEncoder().encodeToString( key.getEncoded() );
	}
}









