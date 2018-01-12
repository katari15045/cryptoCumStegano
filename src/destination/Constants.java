final class Constants 
{
	static final String definition = new StringBuilder()
						.append("Transfer data securely over internet with 2 layer\nprotection - ")
						.append("Cryptography and Steganography.").toString();
	static final int WIND_COLS = 640;
	static final int WIND_ROWS = 360;
	static final double HEAD_SIZE = 20.0;
	static final String HEAD_COLOR = "#ad1f13";

	static final int SERVER_PORT = 7132;
	static final String SYM_ALGO = "AES";
	static final int SYM_KEY_SIZE = 256;
	static final String ASYM_ALGO = "RSA";
	static final int ASYM_KEY_SIZE = 9216;
	static final String HASH_ALGO = "SHA3-384";
}
