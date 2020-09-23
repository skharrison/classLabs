package lab1;

import java.util.Random;

public class LabOne 
{
	// method to randomly generate 3 nucleotide sequences, input desired number of repetitions 
	
	public static void makeUSeqs(int reps) 
	{
		// keeps track of number of AAA seqs
		
		int aaa = 0;
		
		// for loop runs until y equals input number of reps 
		
		for (int y=0 ; y <= reps; ++y)
		{
			String seq = new String();
			
			Random r = new Random();
			
			while (seq.length() < 3)
			{
				int nuc = r.nextInt(4);
				
				if (nuc == 0) 
					seq += "A";	
				
				else if (nuc == 1) 
					seq += "T";	
				
				else if (nuc == 2) 
					seq +=  "C";
				
				else if (nuc == 3) 
					seq += "G";
		
			
			}
			
			if (seq.equals("AAA"))
				aaa = aaa + 1;
						
			System.out.println(seq);
		}
		
	double ofreq = (double) aaa / reps;
	double efreq = .25 * .25 * .25;
	// prints the frequency that AAA was randomly generated. 
	System.out.println("Exjpected frequency of seq AAA: " + efreq);
	System.out.println("Observed frequency of seq AAA: " + ofreq);
		
	}
		
	public static void makePSeqs(int reps) 
	{
		// keeps track of number of AAA seqs
		
		int aaa = 0;
		
		// for loop runs until y equals input number of reps 
		
		for (int y=0 ; y <= reps; ++y)
		{
			String seq = new String();
			
			Random r = new Random();
			
			while (seq.length() < 3)
			{
				float nuc = r.nextFloat();
				
				if (nuc <= .11) 
					seq += "A";	
				
				else if ((nuc > .89) && (nuc <= 1.0)) 
					seq += "T";	
				
				else if ((nuc > .11) && (nuc <= .50)) 
					seq +=  "C";
				
				else if ((nuc > .50) && (nuc <= .89)) 
					seq += "G";
		
			
			}
			
			if (seq.equals("AAA"))
				aaa = aaa + 1;	
			System.out.println(seq);
		}
		
	double ofreq = (double) aaa / reps;
	double efreq = .12 * .12 * .12;
	// prints the frequency that AAA was randomly generated. 
	System.out.println("Expected frequency of seq AAA: " + efreq);
	System.out.println("Observed frequency of seq AAA: " + ofreq);
		
	}
			
	public static void main(String[] args) 
	{
		// prints a user defined # of 3bp sequences with uniform distribution of ATGC 
		makeUSeqs(1000);
		// prints a user defined # of 3bp sequences with a non-uniform distribution of ATGC 
		makePSeqs(1000);
	}
		
	
	
}