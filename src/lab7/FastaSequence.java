package lab7;

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
	public final String sequence;
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
		StringBuffer seq = new StringBuffer();
		
		while(true)
		{
			String line = reader.readLine();
		
			if (line == null && header != null)
			{
				String strSeq = seq.toString();
				FastaSequence myFasta = new FastaSequence(header, strSeq);
				fastaList.add(myFasta);
				break;
			}
			
			else if (line != null && line.startsWith(">"))
			{
				if (header != null)
				{
					String strSeq = seq.toString();
					FastaSequence myFasta = new FastaSequence(header, strSeq);
					fastaList.add(myFasta);
				}
				
				header = line.substring(1);
				seq = new StringBuffer();
			}
			
			else if (line != null && header != null)
			{
				String trimSeq = line.trim();
				seq.append(trimSeq);
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
	
	// takes fasta list and map where k is sequence and value is SeqCount object
	// SeqCount object increments count if comes across sequence
//	public static void writeUnique(String outFile, List<FastaSequence> fastaList) throws Exception 
//	{
//		Map<Integer, SeqCount> counter = new HashMap<Integer, SeqCount>();
//		
//		for (FastaSequence fastSeq : fastaList)
//		{
//			//Saving string as hashKey in map to save memory if large seqs
//			String sequence = fastSeq.sequence;
//			int hashKey = sequence.hashCode();
//			if (counter.containsKey(hashKey)) 
//			{
//				SeqCount count = counter.get(hashKey);
//				count.incrementCount();
//			}
//			else 
//			{
//				SeqCount count = new SeqCount(sequence, 1);
//				counter.put(hashKey, count);
//			}
//		}
//		
//		List<SeqCount> sortSeqCount = sortSeqs(counter);
//		SeqCount.writeCountFasta(sortSeqCount, outFile);    // calls static SeqCount method to make new count fasta
//		
//	}
//	
//	// adds each SeqCount object to a list and returns sorted list based on comparable of SeqCount
//	public static List<SeqCount> sortSeqs(Map<Integer, SeqCount> counter)
//	{
//		List<SeqCount> seqC = new ArrayList<SeqCount>();
//		for (SeqCount entry : counter.values())
//		{
//			seqC.add(entry);
//		}
//		
//		Collections.sort(seqC);
//		return(seqC);
//		
//	}
//	
//	public String getHeader()
//	{
//		return(this.header);
//	}
//	
//	public String getSequence()
//	{
//		return(this.sequence);
//	}
//	
//	public float getGCRatio()
//	{
//		return(this.gcRatio);
//	}
//	
//	public void computeGC()
//	{
//		int gcCount = 0;
//		
//		String[] seqs = this.sequence.split("");
//		
//		for (String s : seqs)
//		{
//			if (s.equals("G") || s.equals("C"))
//			{
//				gcCount++;
//			}
//		}
//		
//		float gcRatio = (float) gcCount / seqs.length;
//		this.gcRatio = gcRatio;
//		
//	}
//	
//	@Override
//	public String toString()
//	{
//		return String.format("Fasta Sequence with ID: " + this.header);
//	}
//	
//	public static void main(String[] args) throws Exception
//	{
//		String fileIn = "/home/sarah/school/adv_program/uniqueTest.fasta";
//		String fileOut = "/home/sarah/school/adv_program/uniqueTestOut.fasta";
//		
//	
//		List<FastaSequence> fastaList = FastaSequence.readFastaFile(fileIn);
//		
//		for(FastaSequence fs : fastaList)
//		{
//			fs.computeGC();
//			System.out.println(fs.getHeader());
//			System.out.println(fs.getSequence());
//			System.out.println(fs.getGCRatio());
//		}
//
//		writeUnique(fileOut, fastaList);
//	}
}
