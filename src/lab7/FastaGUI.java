package lab7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/*
 * Parses a fasta file and counts the occurances of desired string containing ATCG 
 * Put fasta file path in main method 
 * Test fasta is of fasta file that is 1.2 Gig
 * TODO: 
 * - add where user can upload a file
 * - checks to ensure file is in correct format and produce proper warning message
 * - add continuous output that stacks on top instead of just printing the total num at that time
 */
public class FastaGUI extends JPanel
{
	private static final long serialVersionUID = 6255331940674882923L;
	private final JButton startButton;
	private final JButton cancelButton;
	private JLabel output;
	private JLabel pattern;
	private JPanel panel;
	private String typedPattern;
	private List<FastaSequence> fastaList;
	private Thread counter;
	private boolean canceled = false;
	
	public FastaGUI(List<FastaSequence> fastaL)
	{
		this.fastaList = fastaL;
		setLayout(new BorderLayout());
		output = new JLabel("", JLabel.CENTER);
		output.setFont(new Font("Courier", Font.PLAIN, 12));
		output.setPreferredSize(new Dimension(500,200));
		add(output, BorderLayout.CENTER);
		startButton = new JButton("Start");
		panel = new JPanel();
		panel.add(startButton);
		panel.setLayout(new GridLayout(0,2));
		cancelButton = new JButton("Cancel?");
		cancelButton.setEnabled(false);
		panel.add(cancelButton);
		startButton.addActionListener(new java.awt.event.ActionListener() 
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
				startButton();
			}
		});
		
		cancelButton.addActionListener(new java.awt.event.ActionListener() 
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
				cancelButton();
			}
		});
		add(panel, BorderLayout.SOUTH);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(0,2));
		pattern = new JLabel("Pattern: ");
		pattern.setFont(new Font("Courier", Font.BOLD, 14));
		jPanel.add(pattern);
		jPanel.setBackground(Color.cyan);
		add(jPanel,BorderLayout.NORTH);
	}
	
	private void startButton()
	{
		output.setText("");
		canceled = false;
		String answer = JOptionPane.showInputDialog(panel,"Input sequence to search:", null);
		
		if (answer != null)
		{
			//checks to make sure only ATGC not case sensitive
			if (answer.matches("(?i)[ATCG]*"))
			{
				this.typedPattern = answer.toUpperCase();
				pattern.setText("Pattern: " + typedPattern);
				startCounting();
			}
			else 
			{
				JOptionPane.showMessageDialog(null,"INVALID INPUT! Start again. ONLY input A,T,C, and G's");
			}
		}
	}
	
	private void cancelButton()
	{
		canceled = true;
	}
	
	private void startCounting()
	{
		if (counter != null)
		{
			canceled = false;
		}
		cancelButton.setEnabled(true);
		SlowCalc counting = new SlowCalc();
		counter = new Thread(counting);
		counter.start();
	}
	
	private void endMessage(float totalTime, int totalNum)
	{
		String outputText = ("<html><center><b>Parsing finished in " + totalTime + " seconds" + "</b>"
				+ "<br><br>Total counts found: " + totalNum + "<br>");
		output.setText(outputText);
		startButton.setEnabled(true);
		cancelButton.setEnabled(false);
	}
	
	public static void main(String[] args) throws Exception
	{
		String fileIn = "/home/sarah/school/adv_program/lab7/GCF_011125445.2_MU-UCD_Fhet_4.1_genomic.fna";
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(fileIn);
		JFrame theWindow = new JFrame("FASTA PATTERN COUNTER");
		theWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		theWindow.setContentPane(new FastaGUI(fastaList));
		theWindow.pack();
		theWindow.setVisible(true);
	}

	private class SlowCalc implements Runnable
	{	
		private final int numIndex;
		private final long start;
		
		public SlowCalc()
		{
			numIndex = typedPattern.length();
			start = System.currentTimeMillis();
		}
		
		public void run()
		{
			try
			{
				long lastUpdate = System.currentTimeMillis();
				List<String> list = new ArrayList<String>();
				startButton.setEnabled(false);
				
				outerloop:
				for (FastaSequence f: fastaList)
				{
					int index = 0;
					while(index+numIndex <= f.sequence.length())
					{	
						if (canceled == true)
						{
							SwingUtilities.invokeLater(new Runnable()
							{
								@Override
								public void run()
								{
									if (canceled == true)
									{
										pattern.setText("Pattern: ");
										startButton.setEnabled(true);
										cancelButton.setEnabled(false);
									}
								}
							});
							break outerloop;
						}
						int theI = index;
						String sub = f.sequence.substring(theI,theI+numIndex).toUpperCase();
						if (sub.equals(typedPattern))
						{
							list.add(sub);
							
							if( (System.currentTimeMillis() - lastUpdate)/(1000f) > 5)
							{
								SwingUtilities.invokeLater(new Runnable()
								{
									@Override
									public void run()
									{
										if (canceled == false)
										{
											output.setText("Pattern found: " + list.size());
										}
									}
								});
								
								lastUpdate = System.currentTimeMillis();
							}
						}	
						
						index++;
					}
				}
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						float totalTime = (System.currentTimeMillis() - start)/(1000f);
						endMessage(totalTime, list.size());
					}
				});
			}
			
			catch (Exception e)
			{
				System.out.println("Error thrown in endtimer");
			}
		}
	}
}