package lab2;

import java.util.Random;

import java.util.Scanner;
public class AminoQuiz
{
	public static String[] SHORT_NAMES = 
		{"A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };

	public static String[] FULL_NAMES = 
		{"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};
	
	public static int SECS;
	
	public static void StartQuiz(double timeLimit) 
	{
		boolean correct = true;
		System.out.println("~Begin Amino Acid Quiz~");
		
		while (correct)
		{
			long startT = System.currentTimeMillis();
			Scanner scan = new Scanner(System.in);
			int ansCorrect = 0;
			
			for(int i = 0; i >= 0; i ++)
			{
			
				Random r = new Random();
				int x = r.nextInt(19);
				System.out.println("AMINO: " + FULL_NAMES[x]);
				// String aString = System.console().readLine().toUpperCase();
				
				String ans = scan.next().toUpperCase();
				
				// String ans = "" + aString.charAt(0);
				
				String c = SHORT_NAMES[x];
				
				if (ans.equals(c) == true)
				{
					ansCorrect += 1;
					long elapsedT = System.currentTimeMillis();
					double timeCheck = (elapsedT - startT)/(1000d);
					if(timeCheck >= timeLimit)
					{
						System.out.println("-----------------------");
						System.out.println("OUT OF TIME...GAME OVER!");
						System.out.println("Correct answer: " + SHORT_NAMES[x]);
						break;
					}
				}
				
				else if(ans.equals("QUIT") == true)
				{
					System.out.println("-----------------------");
					System.out.println("GAME EXITED");
					break;
				}
				
				else
				{
					System.out.println("-----------------------");
					System.out.println("INCORRECT ANSWER.");
					System.out.println("Correct answer: " + SHORT_NAMES[x]);
					// System.out.println("Score: " + ansCorrect);
					break;
				}
			}
			long endT = System.currentTimeMillis();
			double totalTsec = (endT - startT)/ 1000d;
			System.out.println("Total time: " + totalTsec + " seconds");
			System.out.println("Score: " + ansCorrect);
			correct = false;
			scan.close();
		}
	}
	
	public static void main( String[] args )
	{
		// want to add error checking to ensure input is number
		int numSecs = 0;
		numSecs = Integer.parseInt(args[0]);
		StartQuiz(numSecs);		
	} 
}

