package lab4;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class FastaSequence {
	
	String header;
	String sequence;
	
	FastaSequence(String header, String sequence) 
	{
		this.header = header;
		this.sequence = sequence;
	}
	
	public static List<FastaSequence> readFastaFile(String filepath) throws Exception
	{
		List<FastaSequence> fastaList = new ArrayList<FastaSequence>();
		
		File fileIn = new File(filepath);
		BufferedReader reader = new BufferedReader(new FileReader(fileIn));
		
		String header = null;
		String seq = "";
		
		while(true)
		{
			String line = reader.readLine();
		
			if (line == null && header != null)
			{
				FastaSequence myFasta = new FastaSequence(header, seq);
				fastaList.add(myFasta);
				break;
			}
			
			else if (line != null && line.startsWith(">"))
			{
				if (header != null)
				{
					FastaSequence myFasta = new FastaSequence(header, seq);
					fastaList.add(myFasta);
				}
				
				header = line.substring(1);
				seq = "";
			}
			
			else if (line != null && header != null)
			{
				String trimSeq = line.trim();
				seq += trimSeq;
			}
			
			else
			{
				System.out.println("ERROR: Malformed file!");
				break;
			}
		}
		
		reader.close();
		return(fastaList);
		
	}
	
	public static void writeUnique(String inFile, String outFile) throws Exception 
	{
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(inFile);
		
		Map<String, Integer> counter = new HashMap<String, Integer>();
		
		for (FastaSequence fastSeq : fastaList)
		{
			String sequence = fastSeq.sequence;
			if (counter.containsKey(sequence)) 
			{
				Integer count = counter.get(sequence);
				int value = count.intValue();
				value++;
				counter.put(sequence, value);
			}
			else 
			{
				Integer count = 1;
				counter.put(sequence, count);
			}
		}
		
		List<Integer> countL = new ArrayList<Integer>();
		for (Map.Entry<String,Integer> entry : counter.entrySet())
		{
			countL.add(entry.getValue());
		}
		Map<String, Integer> sortedMap = sortMap(counter,countL);
		File outF = new File(outFile);
		writeNewFasta(sortedMap, outF);
	}
	
	public static void writeNewFasta(Map<String,Integer> seqMap, File outFile) throws Exception
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		for (Map.Entry<String,Integer> entry : seqMap.entrySet())
		{
			writer.write(">" + entry.getValue() + "\n" + entry.getKey() + "\n");
		}
		writer.close();
	}
	
	public static Map<String, Integer> sortMap(Map<String, Integer> counter, List<Integer> countL)
	{
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		Collections.sort(countL);
		for (Integer i : countL)
		{
			for(Map.Entry<String,Integer> entry : counter.entrySet())
			{
				if (entry.getValue().equals(i))
				{
					sortedMap.put(entry.getKey(), i);
				}
			}
		}
		return(sortedMap);
	}
	
	public String getHeader()
	{
		return(this.header);
	}
	
	public String getSequence()
	{
		return(this.sequence);
	}
	
	public float getGCRatio()
	{
		int gcCount = 0;
		
		String[] seqs = this.sequence.split("");
		
		for (String s : seqs)
		{
			if (s.equals("G") || s.equals("C"))
			{
				gcCount++;
			}
		}
		
		float gcRatio = (float) gcCount / seqs.length;
		
		return(gcRatio);
	}
	
	@Override
    public String toString() 
	{
		return String.format("Fasta Sequence with ID: " + this.header);
	}
	
	public static void main(String[] args) throws Exception
	{
		String fileIn = "/home/sarah/school/adv_program/testFasta.fasta";
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(fileIn);
		System.out.println(fastaList);
		
		for( FastaSequence fs : fastaList)
		{
		System.out.println(fs.getHeader());
		System.out.println(fs.getSequence());
		System.out.println(fs.getGCRatio());
		}
		
		String uniqueTest = "/home/sarah/school/adv_program/uniqueTest.fasta";
		String uniqueTestOut = "/home/sarah/school/adv_program/uniqueTestOut.fasta";
		writeUnique(uniqueTest, uniqueTestOut);
	}
}
