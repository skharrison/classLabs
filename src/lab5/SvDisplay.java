package lab5;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumnModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;   

public class SvDisplay extends JFrame 
{
	private static final long serialVersionUID = -2577401354224614779L;
	private JCheckBox addNorm;
	
	public SvDisplay()
	{
		super("SV VISUALIZATION");
		setLocationRelativeTo(null);
		setSize(1000,500);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setJMenuBar(createMenuBar());
		setVisible(true);
		setLayout(new BorderLayout());
		
	}
	
	private JMenuBar createMenuBar() 
	{
		JMenuBar menuBar;
		
		menuBar = new JMenuBar();
		JMenu Imenu = new JMenu("IGV Viewer");
		menuBar.add(Imenu);
		JMenuItem openFiles = new JMenuItem("Open Images");
		Imenu.add(openFiles);
		openFiles.setMnemonic('O');
		JMenuItem saveFile = new JMenuItem("Save");
		Imenu.add(saveFile);
		saveFile.setMnemonic('S');
		
		openFiles.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try {
					loadFromFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		saveFile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				saveState();
			}
		});
		
		JMenu covMenu = new JMenu("Coverage Analysis");
		menuBar.add(covMenu);
		JMenuItem compCov = new JMenuItem("Compute");
		covMenu.add(compCov);
		compCov.setMnemonic('C');
		JMenuItem saveBed = new JMenuItem("Save");
		saveBed.setMnemonic('S');
		covMenu.add(saveBed);
		this.addNorm = new JCheckBox("Normalize Coverage");
		addNorm.setMnemonic('C');
		covMenu.add(addNorm);
		JMenu Cmenu = new JMenu("Display Comparisons");
		menuBar.add(Cmenu);
		
		return menuBar;
		
	}
	
	private void saveState()
	{
		JFileChooser jfc = new JFileChooser();	
		if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		
		if(jfc.getSelectedFile().exists())
		{
			String message = "File " + jfc.getSelectedFile().getName() + " exists";
			
			if( JOptionPane.showConfirmDialog(this,  message) != JOptionPane.YES_OPTION)
				return;
		}
		
	}
	
	
	/* TODO
		- file chooser to remember last known directory
		- make sure that images in proper format (horizontal)
		-add in sample ID's as header for IGV image column
		-want to base size of image render on how many samples there are in image
		-split this up into multiple methods not just all in one
		-enable user to save as one big image?? not sure 
		-fix that must make frame bigger for info to display when first upload images
	*/
	
	private void loadFromFile() throws IOException
	{
		
		JFileChooser jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(true);
	
		if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		
		if( jfc.getSelectedFile() == null)
		{
			return;
		}
		
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		jPanel.add(Box.createVerticalGlue());
		File[] files = jfc.getSelectedFiles();
		ArrayList<BedRegion> bedList = new ArrayList<BedRegion>();
		
		for (File f : files)
		{
			String fName = f.getName();
			String[] info = fName.split("_");
			String chrom = info[0] + "_" + info[1];
			String start = info[2];
			String stop = info[3];
			Image img = ImageIO.read(f);
			Image scale = getScaledImage(img, 1000,200);
			ImageIcon imgBed = new ImageIcon(scale);
			BedRegion myBed = new BedRegion(chrom, start, stop);
			myBed.setImage(imgBed);
			bedList.add(myBed);
		}
		
		BedTableModel bedTable = new BedTableModel(bedList);
		JTable table = new JTable(bedTable);
		table.setRowHeight(100);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(3).setPreferredWidth(1000);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		this.add(new JScrollPane(table));
		
	}
	
	private Image getScaledImage(Image srcImg, int w, int h)
	{
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	public static void main(String[] args)
	{
		new SvDisplay();
	}

}
