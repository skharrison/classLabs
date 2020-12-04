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
}
