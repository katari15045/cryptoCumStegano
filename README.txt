Documentation : https://github.com/katari15045/cryptoCumStegano/wiki

What is it?

Say you have some data to send over internet. This application provides "2 layer - Cryptography & Steganography (~hiding your data in an image)"  protection to your data that's being sent.

Demo is available at https://youtu.be/Jo4CBxnJubw

Features :

-> Forward Secrecy - New keys are generated at each session; past sessions can't be compromised due to a future compromise
-> The public key of the other party is verified without any CA (Certification Authority) or self signing.
-> Unlimited data can be hidden in ANY image except a white or black image. PSNR ---> infinity i.e no bit in the image is modified.
-> All 4 properties of security - "CIA triad & non-repudiation" hold.

How is it done?

Cryptography :
-------------

It mainly involves 4 steps -

1. Generating Asymmetric Keys (RSA) - To establish a secure channel so that "Diffie-Hellman data" can be exchanged.
2. Verifying other party's public key - To prevent man-in-the-middle attacks
3. Diffie-Hellman key exchange to establish a shared secret (Symmetric Key) on both sides
4. Transfer data by proper hashing, signing and encryption - explained in the below sections.

Data have to be securely transferred over internet; cryptography can be used.

Asymmetric or Symmetric cryptography?

Asymmetric cryptography - RSA can't handle huge data :

When your RSA key size is k bits then it can only encrypt a message whose length is <= (k/8)-11 bytes
i.e if 8192 is your key size then the max size of a message it can encrypt is (1024-11) = 1013 bytes

RSA Key size			Max Data in bytes		
------------			-----------------		

1024					117				

2048					245				

4096					501				

8192					1013				

source: [1] [2]

Symmetric cryptography - insecure to transfer symmetric key over internet :

Instead of transferring symmetric key over internet, we can use DH (Diffie-Hellman [3] [4]) key exchange to establish a shared secret between two parties. To provide extra security, the data that are exchanged during DH is encrypted using asymmetric cryptography

Our strategy :

Generate 8192-bit Asymmetric Keys that are capable of encrypting DH data (> 800 bytes).

Verify the public key of other party. How? The Email ID of the other party is known to you. A public key from the other party's email ID is sufficient to verify the public key.

Start DH key exchange with a security level ~ 3072-bit keys in RSA. To exchange DH data, 
encrypt_with_destination_pub_key(dh_data) ------ x, to ensure confidentiality
hash(dh_data) ---------- y, to ensure integrity (SHA3-384)
encrypt_with_your_priv_key( hashed_dh_data i.e y ) --------- z, to ensure non-repudiation

Source : [5]

Transfer x & z to the destination. Similarly, destination sends it's DH data. Now, both source and destination can generate the same 256-bit symmetric key (AES) from the shared secret.

Why so strict in choosing key-sizes?

Super Computers vs time
-----------------------

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

source : [6]

Recommended key sizes in bits & hashing algos 
---------------------------------------------

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

Source : [7], [8], [9], [10] & [11]

To transfer data, 

encrypt_with_destination_public_key(data) --- p, to ensure confidentiality
hash(data) --- q, to ensure integrity
encrypt_with_your_private_key( hashed_data i.e q ) --- r, to ensure non-repudiation

Transfer p & r to the destination.

We've used -
9216-bit RSA keys
256-bit AES keys
SHA3-384
DH security level ~ 3072-bit RSA keys

Steganography
-------------

To hide some data in an image, convert the data to 8-bit ascii values and then to a binary string.
Now, to hide a binary bit in an image, 

1. Generate 4 random values - say, a, b, c & d to identify a bit-location in an image; 
	a - row of an image
	b - column
	c - one among R, G, & B components
	d - Within a component, say Red, which has an 8-bit value, to identify a bit among 8 bits.

2. If the bit referenced in step 1 is same as the binary bit that we are trying to hide, store a, b, c & d in location_string.

3. Else, repeat step 1 & 2 until you generate a, b, c & d which satisfies the if condition in step 2.

Similarly, map all the bits of data to the image by appending the location of each bit to location_string.
Finally, using our strategy that is explained in the cryptography section, transfer location_string and the image to the destination.
The reverse of this process can be applied to extract the hidden data at the destination.

References :
[1] 	https://security.stackexchange.com/a/41226/167165
[2] 	https://stackoverflow.com/a/10007285/8279892
[3] 	https://www.geeksforgeeks.org/implementation-diffie-hellman-algorithm/
[4] 	https://docs.oracle.com/javase/7/docs/technotes/guides/security/crypto/CryptoSpec.html#DH2Ex
[5] 	https://crypto.stackexchange.com/questions/12768/why-hash-the-message-before-signing-it-with-rsa/25953#25953
[6] 	https://en.wikipedia.org/wiki/TOP500#Top_10_ranking
[7] 	https://en.wikipedia.org/wiki/Key_size
[8]		https://en.wikipedia.org/wiki/Cryptographic_hash_function#Cryptographic_hash_algorithms
[9]		https://en.wikipedia.org/wiki/Cryptographic_hash_function#/media/File:Cryptographic_Hash_Function.svg
[10]	https://en.wikipedia.org/wiki/SHA-3
[11]	https://en.wikipedia.org/wiki/SHA-2
