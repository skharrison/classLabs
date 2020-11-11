package lab6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class AminoGUI extends JPanel 
{
	private static final long serialVersionUID = -7461729393418745871L;
	private int numCorrect;
	private int numIncorrect;
	private JLabel output;
	private JTextField answer;
	private JButton mainButton;
	private JButton cancelButton;
	private int totalQuestion = 1;
	private JLabel ansCorrect ;
	private JLabel ansIncorrect;
	private JLabel timerAmount;
	private int question;
	private boolean start = true;
	private Thread endTimer;
	
	public static String[] SHORT_NAMES = 
		{"A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };

	public static String[] FULL_NAMES = 
		{"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};
	
	
	public static void main(String[] args)
	{
		JFrame theWindow = new JFrame("AMINO ACID QUIZ");
		theWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		theWindow.setContentPane(new AminoGUI());
		theWindow.pack();
		theWindow.setVisible(true);
	}
	
	public AminoGUI() 
	{
		setLayout(new BorderLayout());
		output = new JLabel("<html><center><big><b>Ready to learn the amino acids???!</b></big>"
		+ "<br><br>Click \"Begin\" to start.<br>", JLabel.CENTER);
		output.setFont(new Font("Courier", Font.PLAIN, 12));
		output.setPreferredSize(new Dimension(500,200));
		add(output, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter Answer Here: "));
		answer = new JTextField(10);
		answer.setEnabled(false);
		panel.add(answer);
		mainButton = new JButton("Begin");
		panel.add(mainButton);
		cancelButton = new JButton("Cancel?");
		cancelButton.setEnabled(false);
		panel.add(cancelButton);
		mainButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					startButton();
					
				}
			});
		
		cancelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				cancelButton();
			}
		});
		add(panel, BorderLayout.SOUTH);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(0,3));
		ansCorrect = new JLabel("Correct Answers: " + numCorrect);
		ansIncorrect = new JLabel("Incorrect Answers: " + numIncorrect);
		timerAmount = new JLabel("");
		jPanel.add(ansCorrect);
		jPanel.add(ansIncorrect);
		jPanel.add(timerAmount);
		jPanel.setBackground(Color.cyan);
		add(jPanel,BorderLayout.NORTH);
	}
	
	private void startButton()
	{
		if (start == true)
		{
			answer.setEnabled(true);
			cancelButton.setEnabled(true);
			mainButton.setText("Submit");
			notStart();
			getQuestion();
			if (endTimer != null)
			{
				endTimer.stop();
			}
			EndTimer timeStop = new EndTimer(this);
			endTimer = new Thread(timeStop);
			endTimer.start();
			
		}
		
		else
		{
			String userAnswer = answer.getText().toUpperCase();
			
			if (userAnswer.equals(SHORT_NAMES[question]))
			{
				updateCorrect();
			}
			else if (userAnswer.length() == 0)
			{
				///do nothing 
			}
			else if (!userAnswer.equals(SHORT_NAMES[question]))
			{
				updateIncorrect();
			}
			
		}
	}
	
	private synchronized void cancelButton()
	{
		totalQuestion = 1;
		numCorrect = 0;
		numIncorrect = 0;
		timerAmount = new JLabel("");
		endTimer.stop();
		ansIncorrect.setText("Incorrect Answers: " + numIncorrect);
		ansCorrect.setText("Correct Answers: " + numCorrect);
		output.setText("<html><center><big><b>Ready to learn the amino acids???!</b></big>"
				+ "<br><br>Click \"Begin\" to start.<br>");
		start = true;
		mainButton.setText("Begin");
		answer.setEnabled(false);
	}
	
	private synchronized void updateCorrect()
	{
		numCorrect++;
		ansCorrect.setText("Correct Answers: " + numCorrect);
		totalQuestion++; 
		getQuestion();
	}
	private synchronized void updateIncorrect()
	{
		numIncorrect++;
		ansIncorrect.setText("Incorrect Answers: " + numIncorrect);
		totalQuestion++; 
		getQuestion();
	}
	private synchronized void getQuestion()
	{
		Random r = new Random();
		int x = r.nextInt(19);
		question = x;
		output.setText("<html><center><i>Question " + totalQuestion + "</i><br><br><big><b><font color=blue>"
				+ FULL_NAMES[question] + "</font></b></big></center></html>");
		answer.setText("");
		answer.requestFocus();
	}
	
	private synchronized void notStart()
	{
		start = false;
	}
	
	private synchronized void endMessage()
	{
		timerAmount.setText("");
		//ansIncorrect.setText("");
		//ansCorrect.setText("");
		output.setText("<html><center><b><font color=fuchsia>Times up!</b></font><br><br><b><font color=blue>"
				+ "Correct Answers: " +numCorrect + "</font></b></center><br><b><font color=red> " + 
						"Incorrect Answers: " +numIncorrect + "</font></b></center></html>");
		start = true;
		mainButton.setText("Keep Going?");
		answer.setEnabled(false);
	}
	
	private class EndTimer implements Runnable
	{	
		private AminoGUI myGui;
		
		EndTimer(AminoGUI me)
		{
			myGui = me;
		}
		@Override
		public void run()
		{
			try
			{
				for (int i = 30; i > 0; i--)
				{
					myGui.timerAmount.setText("Time Left: " + i);
					myGui.timerAmount.setForeground(Color.red);
					Thread.sleep(1000);
				}
			}
			catch (Exception e)
			{
				System.out.println("EndTimer error " + e);
			}
			myGui.endMessage();
		}
	}
}
