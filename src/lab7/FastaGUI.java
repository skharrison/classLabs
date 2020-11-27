package lab7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class FastaGUI extends JPanel
{
	private static final long serialVersionUID = 6255331940674882923L;
	private JButton mainButton;
	private JButton cancelButton;
	private JLabel output;
	private JLabel pattern;
	private JPanel panel;
	private String typedPattern;
	private int patternCount;
	private List<FastaSequence> fastaList;
	
	public FastaGUI(List<FastaSequence> fastaL)
	{
		this.fastaList = fastaL;
		setLayout(new BorderLayout());
		output = new JLabel("", JLabel.CENTER);
		output.setFont(new Font("Courier", Font.PLAIN, 12));
		output.setPreferredSize(new Dimension(500,200));
		add(output, BorderLayout.CENTER);
		mainButton = new JButton("Start");
		panel = new JPanel();
		panel.add(mainButton);
		panel.setLayout(new GridLayout(0,2));
		cancelButton = new JButton("Cancel?");
		cancelButton.setEnabled(false);
		panel.add(cancelButton);
		mainButton.addActionListener(new java.awt.event.ActionListener() 
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) 
			{
				startButton();
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
		String answer = JOptionPane.showInputDialog(panel,"Input sequence to search:", null);
		//determine how to check to make sure the user is input only ATCG
		this.typedPattern = answer;
		pattern.setText("Pattern: " + typedPattern);
	}
	
	public static void main(String[] args) throws Exception
	{
		String fileIn = "/home/sarah/school/adv_program/lab7/GCF_000010545.1_ASM1054v1_genomic.fna";
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(fileIn);
		
		JFrame theWindow = new JFrame("FASTA PATTERN COUNTER");
		theWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		theWindow.setContentPane(new FastaGUI(fastaList));
		theWindow.pack();
		theWindow.setVisible(true);
	}
	
	private class SlowCalc implements Runnable
	{	
		@Override
		public void run()
		{
			try
			{
				for (FastaSequence f: fastaList)
				{
					
				}
			}
			catch (Exception e)
			{
				System.out.println("Error thrown in endtimer");
			}
//			endMessage();
		}	
	}
}
