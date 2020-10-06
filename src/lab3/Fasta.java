package lab3;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;

public class Fasta
{
	File input;
	// public boolean validate = false;
	
	Fasta(File file) 
	{
		this.input = file;
	}
	
	public void writeCounts(Map<String, Integer>counter, String header, BufferedWriter writer, String[] seqTypes, String seq) throws Exception
	{
		if (counter != null && header != null)
		{
			Integer c;
			writer.write(header);
			
			for (String s : seqTypes)
			{
				c = counter.get(s);
				writer.write("\t" + c);
			}
			
			writer.write("\t" + seq.length() + "\t" + seq + "\n");
		}
		else 
		{
			System.out.println("ERROR: Improper file format ");
		}	
	}
	public void countSeqs(String line, Map<String, Integer> counter)
	{
		if (counter != null && line != null)
		{
			String[] seqs = line.split("");
			
			for (String s : seqs)
			{
				Integer count = counter.get(s);
				if (count != null)
				{
					int value = count.intValue();
					value++;
					counter.put(s, value);	
				}
			}
		}
		else
		{
			System.out.println("Error: Counter or line is null when CountSeqs function being called!");
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
	
	public void writeHeader(BufferedWriter writer, String[] seqTypes) throws Exception
	{
		writer.write("SeqID");
		
		for (String s : seqTypes)
		{
			writer.write("\t" + s);
		}
		writer.write("\tSeqLen\tSeq\n");
	
	}
	
	public void count(File outFile,  String[] seqTypes) throws Exception
	{
		long startT = System.currentTimeMillis();
		
		Map<String, Integer> counter = new HashMap<String, Integer>();
		
	    String header = null;
	    String seq = "";
	    boolean writeH = true;
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		BufferedReader reader = new BufferedReader(new FileReader(this.input));
		
		System.out.println("----------------------------------");
		System.out.println("Starting sequence counts of fasta for " + this.input + "\n");
		
		while(true)
		{
			String line = reader.readLine();
		
			if (line == null && header != null)
			{
				writeCounts(counter, header, writer, seqTypes,seq);
				break;
		    } 
			
			else if (line != null && line.startsWith(">"))
			{
				if (header != null)
				{
					writeCounts(counter, header, writer, seqTypes, seq);
				}
				
				header = line.substring(1);
				counter = resetCount(counter, seqTypes);
				seq = "";
				
				if (writeH == true)
				{
					writeHeader(writer, seqTypes);
					writeH = false;
				}
		     } 
			 
			else if (line != null && header != null)
			{
				String trimSeq = line.trim();
				seq += trimSeq;
				countSeqs(line, counter);
		     }
			
			else
			{
				System.out.println("ERROR: Malformed file!");
				break;
			 }
		}
		
		long endT = System.currentTimeMillis();
		double totalTsec = (endT - startT)/ 1000d;
		
		System.out.println("...Finished counting fasta sequences in " + totalTsec + " seconds");
		System.out.println("---> Sequence counts saved to: " + outFile);
		
		writer.close();
		reader.close();
		
	}
	
	public static void main(String[] args) throws Exception
	{
		File fileOut = new File("/home/sarah/school/adv_program/testFastaCounts.txt");
		File fileIn = new File("/home/sarah/school/adv_program/testFasta.fasta");
		
		String[] seqTypes = {"A", "T", "C", "G"};
		Fasta fasta = new Fasta(fileIn);
		fasta.count(fileOut, seqTypes);
		
			
	}

}
