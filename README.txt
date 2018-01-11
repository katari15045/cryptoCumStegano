=======================================================================================================================

1. Super Computers vs time
--------------------------

(approximated to next multiple of 10)

2^10		10^3
2^20		10^6
2^30		10^9
2^32		10^12
2^64		10^21
2^128		10^39
2^256		10^78
2^512		10^156
2^1024		10^309

i.e if 2^x = 10^y, 
    y ~ 3*(x/10)

Worlds fastest super computer ~ 10^18 FlOPS (As of 2nd Jan, 2018)

(approximation : 1 year = 10^8 seconds)

key_size_in_bits		approx_time_for_the_fastest_super_computer_to_get_all_combinations
----------------		------------------------------------------------------------------
32				< 1 second
64				17 minutes
128				10^13 years (10^21 seconds)
256				10^52 years (10^60 seconds)

source : https://en.wikipedia.org/wiki/TOP500#Top_10_ranking

=======================================================================================================================

2. Recommended key sizes in bits & hashing algos 
------------------------------------------------

RSA
---

2048	 -	 Safe until 2030
2048	 -	 Recommended by NIST
3072	 -	 if security is required beyond 2030

AES
---

128	-	Safe until quantum computers become available
256	-	Quantum computing resistsant
256	-	Recommended by NSA

Diffie Hellman
--------------

Similar to RSA

Hashing
-------

SHA 2	-	Prone to length extension attacks
SHA 3 	-	recommended to ensure long term security

Source : https://en.wikipedia.org/wiki/Key_size
	 https://en.wikipedia.org/wiki/Cryptographic_hash_function#Cryptographic_hash_algorithms
	 https://en.wikipedia.org/wiki/Cryptographic_hash_function#/media/File:Cryptographic_Hash_Function.svg
	 https://en.wikipedia.org/wiki/SHA-3
	 https://en.wikipedia.org/wiki/SHA-2



=======================================================================================================================

3. Drawback of Asymmetric Algo - RSA
------------------------------------

When your key size is k bits then it can only encrypt a message whose length is <= (k/8)-11 bytes
i.e if 8192 is your key size then the max size of a message it can encrypt is (1024-11) = 1013 bytes

RSA Key size			Max Data in bytes		
------------			-----------------		

1024					117				

2048					245				

4096					501				

8192					1013				

source: https://security.stackexchange.com/a/41226/167165
	https://stackoverflow.com/a/10007285/8279892

=======================================================================================================================

4. Ensuring security
--------------------

While transferring the sym_key, first encr_with_src_priv_key( hash(secret) ) to ensure authenticity (signing) and integrity (hashing) and then encr_with_dst_pub_key(sym_key) to ensure confidentiality and to receive actual data (availability) i.e ensuring all 4 properties of information security -

        Confidentiality
        Integrity
        Availability
        Non-repudiation (accountability)

CIA model cum non-repudiation

Source : https://crypto.stackexchange.com/questions/12768/why-hash-the-message-before-signing-it-with-rsa/25953#25953

=======================================================================================================================

5. Understanding Diffie-Hellman Algo
------------------------------------

https://www.geeksforgeeks.org/implementation-diffie-hellman-algorithm/
https://docs.oracle.com/javase/7/docs/technotes/guides/security/crypto/CryptoSpec.html#DH2Ex

=======================================================================================================================


