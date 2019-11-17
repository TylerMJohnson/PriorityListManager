package src.team22.submission;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JButton;
import javax.swing.JEditorPane;

public class PrintScreen extends JFrame {

	private JPanel contentPane;
	private String data;
	
	JTextPane textPane;
	
	public PrintScreen() {
		this.setTitle("Print Report");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 527);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		this.setVisible(false);
		
		JButton btnPrint = new JButton("Print List (Only print to PDF supported)");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textPane.setText(data);
					textPane.print();
				} catch (PrinterException er) {
					JOptionPane.showMessageDialog(null, "An error has occured while attempting to print, blame java.");
					er.printStackTrace();
				}
			}
		});
		contentPane.add(btnPrint, BorderLayout.SOUTH);
	}

	public void printList() {
		data = TablePanel.printTable();
		textPane.setContentType("text/plain");
		textPane.setText(data);
	}

}
