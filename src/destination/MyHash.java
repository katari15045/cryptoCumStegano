import java.security.MessageDigest;
import java.lang.Exception;
import java.util.Base64;
import javax.crypto.SecretKey;

public class MyHash
{
        private MessageDigest messageDigest = null;
        private String algo = "SHA3-384";

        public String hash(String plainText)
        {
		byte[] hashBytes = null;

		try
		{
			messageDigest = MessageDigest.getInstance(algo);
			messageDigest.update( plainText.getBytes() );
			hashBytes = messageDigest.digest();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return Base64.getEncoder().encodeToString(hashBytes);
	}

	public String hash(SecretKey key)
	{
		return hash( Base64.getEncoder().encodeToString( key.getEncoded() ) );
	}
        
}
