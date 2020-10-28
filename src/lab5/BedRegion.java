package lab5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class BedRegion
{
	public final String chrom;
	public final String start;
	public final String stop;
	public ImageIcon igvImage;
	Boolean verified;
	
	
	BedRegion(String chrom, String start, String stop) 
	{
		this.chrom = chrom;
		this.start = start;
		this.stop = stop;
	}
	
	public void setImage(ImageIcon fImg)
	{
		this.igvImage = fImg;
	}
	
	// not necessary for GUI right now but thought could be helpful in future
	public static List<BedRegion> readBedFile(String filepath) throws Exception
	{
		List<BedRegion> bedList = new ArrayList<BedRegion>();
		
		File fileIn = new File(filepath);
		BufferedReader reader = new BufferedReader(new FileReader(fileIn));
		
		//add in error catching for malformed files instead of just breaking regardless
		
		
		while(true)
		{
			String line = reader.readLine();
			
			if (line != null)
			{
				if (line.startsWith("CHROM"))
				{
					//don't want to do anything because means bed file has a header
				}
				else
				{
					String trimLine = line.trim();
					String myVals[] = trimLine.split("\t");
					String ch = myVals[0];
					String sta = myVals[1];
					String stp = myVals[2]; 
					BedRegion myBed = new BedRegion(ch, sta, stp);
					bedList.add(myBed);
				}
			}
			else 
			{
				break;
			}
			
		}
		
		reader.close();
		return(bedList);
		
	}
}
