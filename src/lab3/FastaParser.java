package lab3;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class FastaParser 
{
	File input;
	FastaParser(File file) 
	{
		this.input = file;
	}
	
	
	public void writeCounts(Map<String, Integer>counter, String header, BufferedWriter writer, String[] seqTypes) throws Exception
	{
		if (counter!= null && header != null)
		{
			Integer c;
			writer.write(header + "\t");
			for (String s : seqTypes)
			{
				c = counter.get(s);
				writer.write(c + "\t");
			}
			
			writer.write("\n");
		}
		else 
		{
			// do nothing
		}
			
	}
	public void countSeqs(String line, Map<String, Integer> counter)
	{
		if (counter != null && line != null)
		{
			String[] seqs = line.split("");
			
			for (String s : seqs)
			{
				//if (counter.containsKey(s))
				//{
					Integer count = counter.get(s);
					int value = count.intValue();
					value++;
					counter.put(s, value);	
				//}
				//else 
				//{
					//Integer count = Integer.valueOf(1);
					//counter.put(s,  count);
				//}
				
			
			}
		}
		else
		{
			//should throw an error because something wrong with file
		}
	}
	
	public Map<String, Integer> resetCount( Map<String, Integer> counter , String[] seqTypes)
	{
		for (String s : seqTypes)
		{
			Integer count = Integer.valueOf(0);
			counter.put(s, count);
		}
		return counter;
	}
	

	public void count(File outFile,  String[] seqTypes) throws Exception
	{
		Map<String, Integer> counter = new HashMap<String, Integer>();
		// counter = null;
	    boolean done = false;
	    String header = null;
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		BufferedReader reader = new BufferedReader(new FileReader(this.input));
		
		if (header == null)
		{
			System.out.println("----------------------------------");
			System.out.println("Starting sequence counts of fasta");
			System.out.println("----------------------------------\n");
			writer.write("SeqID\t");
			for (String s : seqTypes)
			{
				writer.write(s + "\t");
			}
			
			writer.write("\n");
		}
		
		while(!done)
		{
			String line = reader.readLine();
			 if (line == null) 
			 {
				 writeCounts(counter, header, writer, seqTypes);
				 System.out.println("...Finished parsing Fasta sequence");
				 System.out.println("---> Sequence counts saved to: " + outFile);
				 done = true;
		     } 
			 else if (line.startsWith(">")) 
			 {
				 writeCounts(counter, header,writer,seqTypes);
				 header = line.substring(1);
				 counter = resetCount(counter, seqTypes);
		     } 
			 else 
			 {
				 countSeqs(line, counter);
		     }
			
			
		}
		
		writer.close();
		reader.close();
		
	}
	
	public static void main(String[] args) throws Exception
	{
		File fileOut = new File("/home/sarah/school/adv_program/sequenceCounts.txt");
		File fileIn = new File("/home/sarah/school/adv_program/testFasta.fasta");
		
		String[] seqTypes = {"A", "T", "C", "G"};
		FastaParser fasta = new FastaParser(fileIn);
		fasta.count(fileOut, seqTypes);
		
			
	}

}
