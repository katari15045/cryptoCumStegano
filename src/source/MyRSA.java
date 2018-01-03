/* 
 *	To generate key Pair and store them :  https://www.javamex.com/tutorials/cryptography/rsa_encryption.shtml
 *  To encrypt and decrypt data : http://niels.nu/blog/2016/java-rsa.html
 *  To get public key from a file : https://stackoverflow.com/questions/18757114/java-security-rsa-public-key-private-key-code-issue
*/
import java.security.PublicKey;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.security.spec.RSAPrivateKeySpec;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.lang.Exception;

public class MyRSA
{
	private KeyPair keyPair;
	
	private static String algo = "RSA";
	private int keySize = 3072;

	void start()
	{
		
		keyPair = generateKeyPair();
		//storeKeys(keyPair);
		//getPublicKey("public.key");
	}
	
	String encryptWithPubKey(String plainText)
	{
		Cipher cipher = null;
		byte[] encryptedText = null;
		
		try
		{
			cipher = Cipher.getInstance(algo);
			cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
			
			encryptedText = cipher.doFinal( plainText.getBytes() );
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(encryptedText);
	}

	String encryptWithPubKey(String plainText, PublicKey inpPubKey)
        {
                Cipher cipher = null;
                byte[] encryptedText = null;

                try
                {
                        cipher = Cipher.getInstance(algo);
                        cipher.init(Cipher.ENCRYPT_MODE, inpPubKey);

                        encryptedText = cipher.doFinal( plainText.getBytes() );
                }

                catch(Exception e)
                {
                        e.printStackTrace();
                }

                return Base64.getEncoder().encodeToString(encryptedText);
        }
	
	String encryptWithPrivKey(String plainText)
	{
		Cipher cipher = null;
		byte[] encryptedBytes = null;
		
		try
		{
			cipher = Cipher.getInstance(algo);
			cipher.init( Cipher.ENCRYPT_MODE, keyPair.getPrivate() );
			encryptedBytes = cipher.doFinal( plainText.getBytes() );
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}
	
	String decryptWithPrivKey(String encryptedData)
	{
		Cipher cipher = null;
		byte[] decryptedText = null;
		
		try
		{
			cipher = Cipher.getInstance(algo);
			cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
			decryptedText = cipher.doFinal( Base64.getDecoder().decode( encryptedData.getBytes() ) );
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(decryptedText);
	}
	
	String decryptWithPubKey(String encryptedData)
	{
		Cipher cipher = null;
		byte[] decryptedText = null;
		
		try
		{
			cipher = Cipher.getInstance(algo);
			cipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());
			decryptedText = cipher.doFinal( Base64.getDecoder().decode( encryptedData.getBytes() ) );
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return new String(decryptedText);
	}

	String decryptWithPubKey(String encryptedData, PublicKey pubKey)
        {
                Cipher cipher = null;
                byte[] decryptedText = null;

                try
                {
                        cipher = Cipher.getInstance(algo);
                        cipher.init(Cipher.DECRYPT_MODE, pubKey);
                        decryptedText = cipher.doFinal( Base64.getDecoder().decode( encryptedData.getBytes() ) );
                }

                catch(Exception e)
                {
                        e.printStackTrace();
                }

                return new String(decryptedText);
        }
	
	SecretKey decryptSymKeyWithPrivKey(String encryptedSymKey)
	{
		String decryptedkeyStr = null;
		byte[] keyBytes = null;
		
		decryptedkeyStr = decryptWithPrivKey(encryptedSymKey);
		keyBytes = Base64.getDecoder().decode(decryptedkeyStr);
		
		return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
	}
	
	SecretKey decryptSymKeyWithPubKey(String encryptedSymKey)
	{
		String decryptedkeyStr = null;
		byte[] keyBytes = null;
		
		decryptedkeyStr = decryptWithPubKey(encryptedSymKey);
		keyBytes = Base64.getDecoder().decode(decryptedkeyStr);
		
		return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
	}
	
	private KeyPair generateKeyPair()
	{
		KeyPairGenerator generator = null;
		KeyPair keyPair = null;

		try
		{
			generator = KeyPairGenerator.getInstance(algo);
			generator.initialize(keySize);
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

		try
		{
			factory = KeyFactory.getInstance(algo);
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

	static PublicKey getPublicKey(String fileName)
	{
		InputStream inputStream = null;
		ObjectInputStream oin = null;	
		BigInteger modulus = null;
		BigInteger exponent = null;
		KeyFactory factory = null;
		PublicKey publicKey = null;	

		try
		{
			inputStream = new FileInputStream(fileName);
			oin = new ObjectInputStream( new BufferedInputStream(inputStream) );
					
			modulus = (BigInteger)oin.readObject();
			exponent = (BigInteger)oin.readObject();
			factory = KeyFactory.getInstance(algo);
			publicKey = factory.generatePublic( new RSAPublicKeySpec(modulus, exponent) );
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		finally
		{
			try
			{	
				oin.close();
			}

			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return publicKey;
	}
	
	PublicKey getPublicKey()
	{
		return keyPair.getPublic();
	}

	String getPublicKeyInStr()
	{
		return Base64.getEncoder().encodeToString( keyPair.getPublic().getEncoded() );
	}
	
	PrivateKey getPrivateKey()
	{
		return keyPair.getPrivate();
	}

	String getPrivateKeyInStr()
	{
		return Base64.getEncoder().encodeToString( keyPair.getPrivate().getEncoded() );
	}
	
	void setKeySize(int keySize)
	{
		this.keySize = keySize;
	}
}
