# CRCSimulation
Randomly generates a burst error of > 32 bits in a binary string of 1520 bytes and performs a CRC-32 check
to see if an error is detected.

# Features
- Uses the standard CRC-32 generaton polynomial. 
- Reads the a 1520 byte binary number from a SampleBinary.txt. 
- Finds the remainder (4 bytes). 
- Introduces a random burst error of length > 32 bits in the frame of 1524 bytes 50 times.
- Checks to see if the errors are detected to see how reliable CRC-32 is.
