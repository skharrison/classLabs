package lab4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class SeqCount implements Comparable<SeqCount> 
{
	int count;
	String seqName;
	
	SeqCount(String seqName, int count) 
	{
		this.seqName = seqName;
		this.count = count;
	}

	@Override
	public int compareTo(SeqCount s1) 
	{
		return this.count - s1.count;
	}
	
	@Override
	public String toString()
	{
		return String.format(this.seqName + ":" + this.count);
	}
	
	public static void writeCountFasta(List<SeqCount> seqC, String outFile) throws Exception
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)));
		for (SeqCount s : seqC)
		{
			writer.write(">" + s.count + "\n" + s.seqName + "\n");
		}
		writer.close();
	}

}
