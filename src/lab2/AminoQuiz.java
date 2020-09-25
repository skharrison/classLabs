package lab2;

import java.util.Random;

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
	
	public static void StartQuiz() 
	{
		long startT = System.currentTimeMillis();
		boolean correct = true;
		
		System.out.println("~Begin Amino Acid Quiz~");
		
		while (correct)
		{
			int ansCorrect = 0;
			long timeLimit = 30000;
			for(int i = 0; i < 20; i ++)
			{
			
				Random r = new Random();
				int x = r.nextInt(19);
				System.out.println("AMINO: " + FULL_NAMES[x]);
				String aString = System.console().readLine().toUpperCase();
				String ans = "" + aString.charAt(0);
				String c = SHORT_NAMES[x];
				
				if (ans.equals(c) == true)
					ansCorrect += 1;
				
				else
				{
					System.out.println("INCORRECT ANSWER! GAME OVER!");
					System.out.println("-----------------------");
					break;
				}
				
				
				long elapsedT = System.currentTimeMillis();
				long timeCheck = elapsedT - startT;
				
				System.out.println(timeCheck);
			
				if(timeCheck >= timeLimit)
				{
					System.out.println("OUT OF TIME! GAME OVER!");
					System.out.println("-----------------------");
					break;
				}
				
			
				
			}
			// long elapsedT = (System.currentTimeMillis())/(1000l);
			
			long endT = (System.currentTimeMillis())/(1000l);
			double totalTsec = (endT - startT)/ 1000d;
			
			System.out.println("Total time: " + totalTsec + " seconds");
			System.out.println("# Of Correct Answers: " + ansCorrect);
			correct = false;

		}
		
	}
		
	public static void main( String[] args )
	{
		StartQuiz();
	}
		
		
		// boolean correct = true;
		// while(correct)
			
			
		
		
		
		
	

}