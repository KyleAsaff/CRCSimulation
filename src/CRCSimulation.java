/*
 *      Kyle Asaff
 *      
 *      CRC_Simulation.java
 *      
 *      Randomly generates a burst error of > 32 bits in a binary string of 1520 bytes and performs CRC-32 check
 *      to see if the error is detected.
 *      
 */


import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CRCSimulation {
	
    // Error detection count
    public static int detected = 0;
	
	// Method to check if a message is error free with CRC
	public static void check(List<Integer> message, List<Integer> generator) {
		List<Integer> dividend = new ArrayList<Integer>(message);
		List<Integer> zerogenerator = new ArrayList<Integer>();
		
		// zerogenerator = generator with all 0 bits
		for(int i=0; i<generator.size(); i++) {
			zerogenerator.add(0);
		}
		// curr = the current bits to perform XOR on
		List<Integer> curr = dividend.subList(0, generator.size());
		
		// XOR = the bits to xor with curr
		List<Integer> xor = generator;
		List<Integer> result = new ArrayList<Integer>();
		
		for(int i=0; i<dividend.size()-generator.size()+1; i++) {
			
			// Perform xor opperation on bits
			for(int j=0; j<generator.size(); j++) {
				result.add(curr.get(j)^xor.get(j));
			}
			
			// Remove first bit on result for next division
			result.remove(0);
			
			// Add next bit to result for next division
			if(generator.size()+i < dividend.size()) {
				result.add(dividend.get(generator.size()+i));
			}
			
			// The curr is now the result of the xor division
			curr = new ArrayList<Integer>(result);
			
			// Clear old result
			result.clear();
			
			// Divide by generator or the zero generator depending on first bit
			if(curr.get(0) == 0)
				xor = zerogenerator;
			else
				xor = generator;
		}
		
		// Boolean for error check
		boolean error = false;
		
		for(int i=0; i<curr.size(); i++) {
			System.out.print(curr.get(i));
			if(curr.get(i) != 0)
				error = true;
		}
		if(error) {
			System.out.println("\nSince the remainder is non-zero, Error detected");
			detected++;
		}
		else
			System.out.println("\nSince the remainder is zero, No error detected");
	}
	
	// Return bit string with random error of size errorSize in it
	public static String randomError(String bitstring, int errorSize) {
		Random r = new Random();
		int lowest = 0;
		int highest = bitstring.length()-errorSize-1;
		
		// Random spot to insert error
		int random = r.nextInt(highest-lowest)+lowest;
		
		Random r2 = new Random();
		StringBuilder stringBuilder = new StringBuilder();
		// Generate random bit error sequence
		for(int i = 0; i<errorSize; i++) {
			stringBuilder.append(r2.nextInt(2));
		}
		String errorString = stringBuilder.toString();
		
		// Insert random bit error sequence
		String bitstring2 = bitstring.replace(bitstring.substring(random, random+errorSize),errorString);
		return(bitstring2);
		
	}
	
	// Method to read 1520 byte binary number from file into a string
	public static String readFile(String fileName) throws IOException {
        	return new String(Files.readAllBytes(Paths.get("src/SampleBinary.txt")));
	}
	
	public static void main(String[] args) throws IOException {
		String divisor = "100000100110000010001110110110111"; // Standard CRC-32 generator
		String bitstring;
		bitstring = readFile("SampleBinary.txt");
		
		List<Integer> generator = new ArrayList<Integer>();
		List<Integer> message = new ArrayList<Integer>();
        
		System.out.println("The binary number being checked with the standard CRC-32 generator is:");
		System.out.println(bitstring+"\n");
        
		// Perform the CRC check 50 times
        	for(int j=1; j<51; j++) {
    			Random r = new Random();
    			int lowest = 0;
    			int highest = bitstring.length()-1;
    		
    			//generate random burst error size
    			int randomErrorSize = r.nextInt(highest-lowest)+lowest;
    		
            		System.out.println("Size of burst error: "+randomErrorSize+" bits");
            		String errorBitStream = randomError(bitstring, randomErrorSize);
        		System.out.println("Experiment "+j+" out of 50");
				for(int i=0; i<bitstring.length(); i++)
					message.add(Character.getNumericValue(errorBitStream.charAt(i)));
				for(int i=0; i<divisor.length(); i++)
					generator.add(Character.getNumericValue(divisor.charAt(i)));
				check(message, generator);
				message.clear();
				generator.clear();
        	}
        	System.out.println("\nThe error was detected "+detected+" times out of the 50 experiments.");
	}
}
