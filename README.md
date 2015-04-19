# CRCSimulation
Randomly generates a burst error of > 32 bits in a binary string of 1520 bytes and performs a CRC-32 check
to test the reliability of CRC-32 by checking to see if the burst error is detected.

# Features
- Uses the standard CRC-32 generaton polynomial
- Reads a 1520 byte binary number from a SampleBinary.txt
- Finds the remainder (4 bytes)
- Introduces a random burst error of length > 32 bits in the frame of 1524 bytes
- Checks to see if the error is detected to test the realiability of CRC-32 error checks
