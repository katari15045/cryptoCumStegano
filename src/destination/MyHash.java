import java.security.MessageDigest;
import java.lang.Exception;
import java.util.Base64;
import javax.crypto.SecretKey;

public class MyHash
{
        private static MessageDigest messageDigest = null;

        static String hash(String plainText)
        {
		byte[] hashBytes = null;

		try
		{
			messageDigest = MessageDigest.getInstance(Constants.HASH_ALGO);
			messageDigest.update( plainText.getBytes() );
			hashBytes = messageDigest.digest();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return Base64.getEncoder().encodeToString(hashBytes);
	}

	static String hash(SecretKey key)
	{
		return hash( Base64.getEncoder().encodeToString( key.getEncoded() ) );
	}
        
}
