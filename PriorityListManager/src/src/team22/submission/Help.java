package src.team22.submission;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Help extends JFrame {

	static int entriesAdded = 0;
	// TextField name = new TextField("Name");
	//TextField description = new TextField("Description (Max 255 Characters)");
	JButton close = new JButton("close");
 
	//JButton prev = new JButton("previous");
	String textstr = "			Help Instructions \n"
			+ "Add Item\t: Click the Add Entry button type in a description and date.\n"
			+ "Delete Item\t: Highlight desired tasks to delete and hit delete, you can delete multiple.\n"
			+ "Edit Item\t: Select cell to edit and hit edit button.\n"
			+ "Save List\t: Click File then click Save. Note you can only save to .txt files.\n"
			+ "Load List\t: Click File then click Load. \n"
			+ "Print Report\t: Click File then click Print Report.\n";
	TextArea text = new TextArea(textstr);
	JScrollPane scroll = new JScrollPane();
	public Help() {
		setResizable(false);
		setBounds(250, 200, 520, 261);
		setTitle("Help");
		setAlwaysOnTop(true);
		Font font1 = new Font("SansSerif", Font.PLAIN, 20);
		text.setBounds(10, 10, 450, 150);
		//scroll = new JScrollPane(photo);
		
		//photo.add(add);
		//getContentPane().add(photo);
		// increased font size to 30pt
		// name.setFont(font1);
		//description.setFont(font1);
		// name.setBounds(15, 35, 205, 30);
		//description.setBounds(40, 26, 403, 109);
		getContentPane().add(text);
		close.setBounds(150, 181, 180, 30);
		getContentPane().setLayout(null);
		//getContentPane().add(description);
		getContentPane().add(close);
		
		//prev.setBounds(40, 181, 180, 30);
		//getContentPane().add(prev);


		close.addActionListener(new SubframeButtonListener());
		//prev.addActionListener(new SubframeButtonListener());
		setVisible(false);
	}

	public void hideNewEntryFrame() {
		setVisible(false);
	}

	public void showNewEntryFrame() {
		setVisible(true);
	}
	
	public class SubframeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent source) {
			// TODO Auto-generated method stub
			if (source.getSource() == close) {
				
				hideNewEntryFrame();
			} //else if (source.getSource() == prev) {
				//MainFrame.newEntryFrame.hideNewEntryFrame();
		//	}
		}
	}
	
}
