
public final class Constants 
{
	static String definition = new StringBuilder()
						.append("Transfer data securely over internet with 2 layer\nprotection - ")
						.append("Cryptography and Steganography.").toString();
	static int WIND_COLS = 640;
	static int WIND_ROWS = 360;
	static double HEAD_SIZE = 20.0;
	static String HEAD_COLOR = "#ad1f13";

	static int SOCK_PORT = 7132;
	static final String SYM_ALGO = "AES";
	static int SYM_KEY_SIZE = 256;
        static String ASYM_ALGO = "RSA";
        static int ASYM_KEY_SIZE = 9216;
	static int DH_KEY_SIZE = 3072;
	static String HASH_ALGO = "SHA3-384";
}
