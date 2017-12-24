package application;

public class Binary 
{
	public static String getBinaryFromChars(String inp)
	{
		StringBuilder stringBuilder;
		int currentIndex = 0;
		int currentAscii;
		String currentBinary;

		stringBuilder = new StringBuilder();

		while( currentIndex < inp.length() )
		{
			currentAscii = (int) inp.charAt(currentIndex);
			currentBinary = getBinaryFromDecimal(currentAscii);
			stringBuilder.append(currentBinary);

			currentIndex = currentIndex + 1;
		}

		return stringBuilder.toString();
	}

	public static String getBinaryFromDecimal(int decimal)
	{
		// decimal must be in [0, 255]

		if( decimal == 0 )
		{
			return "00000000";
		}

		if( decimal == 1 )
		{
			return "00000001";
		}

		char[] binary = {'0', '0', '0', '0', '0', '0', '0', '0'};
		int exponent, value;

		do
		{
			exponent = getNearestPowerOfTwo(decimal);
			binary[7-exponent] = '1';
			value = (int)Math.pow(2, exponent);
			decimal = decimal - value;
		}
		while( decimal > 0 );

		return new String(binary);
	}

	public static int getDecimalFromBinary(String binary)
	{
		int currentIndex, currentBit;
		int exponent = 0, value;
		int decimal = 0;

		currentIndex = binary.length()-1;

		while( currentIndex >= 0 )
		{
			value = (int)Math.pow(2, exponent);
			currentBit = Integer.valueOf( String.valueOf( binary.charAt(currentIndex) ) );
			decimal = decimal + (value*currentBit);

			exponent = exponent + 1;
			currentIndex = currentIndex - 1;
		}

		return decimal;
	}
	
	private static int getNearestPowerOfTwo(int num)
	{
		// num should be atleast 1

		int value = -2;
		int exponent = 0;

		do
		{
			exponent = exponent + 1;
			value = (int)Math.pow(2, exponent);
		}
		while(value <= num);

		return exponent - 1;
	}
}
