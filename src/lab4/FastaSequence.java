package lab4;

import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.BufferedReader;

public class FastaSequence {
	
	private final String header;
	private final String sequence;
	float gcRatio;
	
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
	
	public static void writeUnique(String outFile, List<FastaSequence> fastaList) throws Exception 
	{
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
		
		List<SeqCount> sortSeqCount = sortSeqs(counter);
		SeqCount.writeCountFasta(sortSeqCount, outFile);
		
	}
	
	public static List<SeqCount> sortSeqs(Map<String, Integer> counter)
	{
		List<SeqCount> seqC = new ArrayList<SeqCount>();
		for (Map.Entry<String,Integer> entry : counter.entrySet())
		{
			SeqCount s = new SeqCount(entry.getKey(), entry.getValue());
			seqC.add(s);
		}
		
		Collections.sort(seqC);
		return(seqC);
		
	}
	
	public String getHeader()
	{
		return("Header: " + this.header);
	}
	
	public String getSequence()
	{
		return("Sequence: " + this.sequence);
	}
	
	public String getGC()
	{
		return("GC Ratio: " + this.gcRatio);
	}
	
	public void computeGC()
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
		this.gcRatio = gcRatio;
		
	}
	
	@Override
	public String toString()
	{
		return String.format("Fasta Sequence with ID: " + this.header);
	}
	
	public static void main(String[] args) throws Exception
	{
		String fileIn = "/home/sarah/school/adv_program/uniqueTest.fasta";
		String fileOut = "/home/sarah/school/adv_program/uniqueTestOut.fasta";
		
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(fileIn);
		
		for(FastaSequence fs : fastaList)
		{
			fs.computeGC();
			System.out.println(fs.getHeader());
			System.out.println(fs.getSequence());
			System.out.println(fs.getGC());
		}

		writeUnique(fileOut, fastaList);
	}
}
