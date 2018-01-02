// Tutorial -> https://docs.oracle.com/javase/9/security/java-cryptography-architecture-jca-reference-guide.htm#JSSEC-GUID-98B5A57E-E5BA-46F2-BE35-2056F43C58A4

import java.security.KeyPairGenerator;
import java.security.KeyPair;
import javax.crypto.KeyAgreement;
import java.util.Base64;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.lang.Exception;

public class DiffieHellman
{
	private KeyPairGenerator generator = null;
	private String algo = "DH";
	private int keySize = 3072;
	private KeyPair keyPair = null;

	private KeyAgreement keyAgreement = null;

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
			generator.initialize(keySize);
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
		return Base64.getEncoder().encodeToString(pubKeyBytes);
	}
}
