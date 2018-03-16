import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringJoiner;


/**
 * Created by Zhiyuan Fang on 2018/2/22.
 */
public class UI {

	private JButton buttonChooseInput, buttonCheck, leftalign, rightalign, save_result;
	private JLabel labelInputResult, labelChooseInput;
	private JPanel lineInput, mainPanel, centerPanel, rightPanel, topPanel, bottomPanel;
	private JScrollPane scrollPanel1, scrollPanel2;
	private JTextArea textArea1, textArea3;
	private JFrame jFrame;
	private TextFormatter text;
	private Stats left_stats, right_stats;
	private ArrayList<String> left, right;
	private JTextPane textPane;
	private int CheckEnable;
	private boolean left_algin = true;
	private String file_name = "";
	public UI() {
		initGUI();
	}
	
	public void changeCheck(int a){
		CheckEnable = a;
	}
	public int getCheck(){
		return CheckEnable;
	}

	private void initGUI() {
		text = new TextFormatter();
		jFrame = new JFrame();
		mainPanel = new JPanel();
		centerPanel = new JPanel(new GridLayout(1, 2));
		rightPanel = new JPanel(new BorderLayout());
		lineInput = new JPanel(new GridLayout(1, 3));
		labelInputResult = new JLabel();
		topPanel = new JPanel(new BorderLayout());
		bottomPanel = new JPanel(new GridLayout(1, 3));
		labelChooseInput = new JLabel("Choose input file(s):");
		buttonChooseInput = createSimpleButton("Browse");
		leftalign = createSimpleButton("Left Align");
		rightalign = createSimpleButton("Right Align");		
		buttonCheck = createSimpleButton("Format Text");
		save_result = createSimpleButton("Save Output");

		textArea1 = new JTextArea("Reading Text:");
		textArea3 = new JTextArea("Analysis Result:");

		// add display board here
	    StyleContext context = new StyleContext();
	    StyledDocument document = new DefaultStyledDocument(context);
	    Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
	    StyleConstants.setAlignment(style, StyleConstants.ALIGN_RIGHT);
	    try {
			document.insertString(document.getLength(), "", style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	    
	    textPane = new JTextPane(document);
	    textPane.setEditable(false);
		scrollPanel1 = new JScrollPane(textArea1);
		scrollPanel2 = new JScrollPane(textPane);

		Font font = new Font("Segoe Script", Font.PLAIN, 20);
		Font font2 = new Font("Segoe Script", Font.BOLD, 12);
		
		changeCheck(0);

		textArea1.setEditable(false);
		textArea1.setFont(font);
		textArea3.setBackground(Color.LIGHT_GRAY);
		textArea3.setPreferredSize(new Dimension(150, 150));
		textArea3.setEditable(false);
		textArea3.setFont(font2);
		lineInput.setPreferredSize(new Dimension(50, 50));
		buttonCheck.setPreferredSize(new Dimension(50, 50));
		save_result.setPreferredSize(new Dimension(50, 50));
		leftalign.setPreferredSize(new Dimension(50, 50));
		rightalign.setPreferredSize(new Dimension(50, 50));
		jFrame.setSize(1400, 600);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainPanel.setLayout(new BorderLayout());
		lineInput.setAlignmentX(Component.CENTER_ALIGNMENT);
		scrollPanel1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		buttonCheck.setEnabled(false);
		save_result.setEnabled(false);
		leftalign.setEnabled(false);
		rightalign.setEnabled(false);
		// Read the input file after we click the botton
		buttonChooseInput.addActionListener(new chooseButtonListener());

		buttonCheck.addActionListener(new checkButtonListener());
		leftalign.addActionListener(new leftButtonListener());
		save_result.addActionListener(new saveButtonListener());
		rightalign.addActionListener(new rightButtonListener());
		bottomPanel.add(buttonCheck);
		bottomPanel.add(leftalign);
		bottomPanel.add(rightalign);
		bottomPanel.add(save_result);

		lineInput.add(labelChooseInput);
		lineInput.add(buttonChooseInput);
		lineInput.add(labelInputResult);
		rightPanel.add(scrollPanel2, BorderLayout.CENTER);
		rightPanel.add(textArea3, BorderLayout.SOUTH);

		centerPanel.add(scrollPanel1);
		centerPanel.add(rightPanel);

		topPanel.add(lineInput, BorderLayout.NORTH );
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(topPanel, BorderLayout.NORTH);

		jFrame.setContentPane(mainPanel);
		jFrame.setResizable(false);
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);
	}

	class checkButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){

			String result = "Word processed:\t"+ left_stats.words_processed()+'\n'+
					"Line processed:\t" + left_stats.lines_processed() + '\n' +
					"Empty Lines:\t\t" + left_stats.empty_liens() + '\n' +
					"Avg words per line:\t" + left_stats.avg_words_per_line() + '\n'+
							"Avg line length:\t" + left_stats.avg_line_length();
			
			textArea3.setText(result);
			String l = "";
	        for (String line: left) {
	            l +=  line + "\n";
	        }
	        
			// add display board here
		    StyleContext context = new StyleContext();
		    StyledDocument document = new DefaultStyledDocument(context);
		    Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
		    StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
		    try {
				document.insertString(document.getLength(), l, style);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		    textPane.setDocument(document);
			changeCheck(0);
			buttonCheck.setEnabled(false);
			save_result.setEnabled(true);
		}
	}
	
	
	class rightButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			left_algin = false;
			String l = "";
	        for (String line: right) {
	            l +=  line + "\n";
	        }
			// add display board here
		    StyleContext context = new StyleContext();
		    StyledDocument document = new DefaultStyledDocument(context);
		    Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
		    StyleConstants.setAlignment(style, StyleConstants.ALIGN_RIGHT);
		    try {
				document.insertString(document.getLength(), l, style);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		    textPane.setDocument(document);			
		    changeCheck(0);		
		}
		}
	
	
	class leftButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			left_algin = true;
			String l = "";
	        for (String line: left) {
	            l +=  line + "\n";
	        }
			// add display board here
		    StyleContext context = new StyleContext();
		    StyledDocument document = new DefaultStyledDocument(context);
		    Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
		    StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
		    try {
				document.insertString(document.getLength(), l, style);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		    textPane.setDocument(document);	
			changeCheck(0);		
		}
		}
	
	class saveButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String l = "";
			if (left_algin == true){
		        for (String line: left) {
		            l +=  line + "\n";
		        }
			}
			else{
		        for (String line: right) {
		            l +=  line + "\n";
		        }
			}
			File selectedFile = null;
			JFileChooser chooser = new JFileChooser();
	        TextFormatter TF = new TextFormatter();		
	        String PATH = "";
	        do
			{
	        	chooser = new JFileChooser();
				File workingDirectory = new File(System.getProperty("user.dir"));
				chooser.setCurrentDirectory(workingDirectory);
				int returnVal = chooser.showOpenDialog(null);
				selectedFile = chooser.getSelectedFile();
				PATH = selectedFile.getParentFile().toString() +'/'+ selectedFile.getName();
				
				if(selectedFile.getName().equals(file_name)){
				    final JPanel panel = new JPanel();
				    JOptionPane.showMessageDialog(panel, "Cannot overrite the source txt file!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				if (!selectedFile.getName().endsWith(".txt")){
				    final JPanel panel = new JPanel();
				    JOptionPane.showMessageDialog(panel, "Please selet a txt file!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			while(selectedFile.getName().equals(file_name) || !selectedFile.getName().endsWith(".txt"));
	        System.out.println(PATH);
	        try{
	        	    File f = new File(PATH);
					FileWriter fileWriter = new FileWriter(f);
					fileWriter.write(l);
					fileWriter.flush();
					fileWriter.close();
				    final JPanel panel = new JPanel();
				    JOptionPane.showMessageDialog(panel, "The output has been saved to " + selectedFile.getName() + " successfully!", "NOTE", JOptionPane.INFORMATION_MESSAGE);
		        
	        } catch(Exception e2){

	        }
		}
		}
	
	class chooseButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				File selectedFile = null;
				JFileChooser chooser = new JFileChooser();
		        TextFormatter TF = new TextFormatter();		
		        String PATH = "";
		        do
				{

					File workingDirectory = new File(System.getProperty("user.dir"));
					chooser.setCurrentDirectory(workingDirectory);
					int returnVal = chooser.showOpenDialog(null);
					selectedFile = chooser.getSelectedFile();
					file_name  = selectedFile.getName();
					labelInputResult.setText("     " + file_name);
					//System.out.println(selectedFile.getParentFile());
					PATH = selectedFile.getParentFile().toString() +'/'+ file_name;
					if (!selectedFile.getName().endsWith(".txt")){
					    final JPanel panel = new JPanel();
					    JOptionPane.showMessageDialog(panel, "Please selet a txt file!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				while(!selectedFile.getName().endsWith(".txt"));

		        left = TF.format(PATH, "left");
		        left_stats = TF.getStats();
		        right = TF.format(PATH, "right");
		        right_stats = TF.getStats();
		        
				// Read plain text from the file
				BufferedReader br  = new BufferedReader(new FileReader(selectedFile));
				try {
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();

					while (line != null) {
						sb.append(line);
						sb.append(System.lineSeparator());
						line = br.readLine();
					}

					textArea1.setText("Reading Text:\n" + sb.toString());
					changeCheck(1);
					buttonCheck.setEnabled(true);
					leftalign.setEnabled(true);
					rightalign.setEnabled(true);
					save_result.setEnabled(true);
				} finally {
					br.close();
				}
				System.out.print(br);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}


	public static void main(String[] args) {
		new UI();
	}

	private static JButton createSimpleButton(String text) {
		JButton button = new JButton(text);
		button.setForeground(Color.BLACK);
		button.setBackground(Color.WHITE);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		button.setBorder(compound);
		return button;
	}



}