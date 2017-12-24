// Tutorial -> https://www.javamex.com/tutorials/cryptography/rsa_encryption.shtml

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.lang.Exception;
import java.math.BigInteger;

public class RSA
{
	private static KeyPair keyPair;

	public static void start()
	{
		keyPair = getKeyPair();
		storeKeys(keyPair);
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
}