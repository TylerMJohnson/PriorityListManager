package src.team22.submission;

import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SubFrameAddEntry extends JFrame {

	static int entriesAdded = 0;
	TextField description = new TextField("Description");
	JButton addEntry = new JButton("Add Entry");
	private JTextField monthField;
	private JTextField dayField;
	private JTextField yearField;

	JButton cancelEntry = new JButton("Cancel");

	public SubFrameAddEntry() {
		setResizable(false);
		setBounds(250, 200, 501, 261);
		setTitle("Add Entry");
		setAlwaysOnTop(true);
		Font font1 = new Font("SansSerif", Font.PLAIN, 20);
		description.setFont(font1);
		description.setBounds(40, 26, 403, 109);
		addEntry.setBounds(264, 181, 180, 30);
		getContentPane().setLayout(null);
		getContentPane().add(description);
		getContentPane().add(addEntry);

		cancelEntry.setBounds(40, 181, 180, 30);
		getContentPane().add(cancelEntry);

		JLabel lblDueDate = new JLabel("Due Date");
		lblDueDate.setBounds(40, 141, 72, 14);
		getContentPane().add(lblDueDate);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(40, 11, 100, 14);
		getContentPane().add(lblDescription);

		JLabel lblMonth = new JLabel("Month");
		lblMonth.setBounds(50, 156, 62, 14);
		getContentPane().add(lblMonth);

		monthField = new JTextField();
		monthField.setBounds(87, 153, 37, 20);
		getContentPane().add(monthField);
		monthField.setColumns(10);

		JLabel lblDay = new JLabel("Day");
		lblDay.setBounds(134, 156, 37, 14);
		getContentPane().add(lblDay);

		dayField = new JTextField();
		dayField.setBounds(162, 153, 46, 20);
		getContentPane().add(dayField);
		dayField.setColumns(10);

		JLabel lblYear = new JLabel("Year");
		lblYear.setBounds(218, 156, 46, 14);
		getContentPane().add(lblYear);

		yearField = new JTextField();
		yearField.setBounds(249, 153, 67, 20);
		getContentPane().add(yearField);
		yearField.setColumns(10);
		addEntry.addActionListener(new SubframeButtonListener());
		cancelEntry.addActionListener(new SubframeButtonListener());
		setVisible(false);
	}

	public void hideNewEntryFrame() {
		setVisible(false);
	}

	public void showNewEntryFrame() {
		setVisible(true);
	}
	
	public class SubframeButtonListener implements ActionListener {
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent source) {
			if (source.getSource() == addEntry) {
				if (monthField.getText() == null || dayField.getText() == null || yearField.getText() == null
						|| monthField.getText() == "" || dayField.getText() == "" || yearField.getText() == "") {
					JOptionPane.showMessageDialog(null, "Invalid Date", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int month = 0;
				int day = 0;
				int year = 0;
				try {
					month = Integer.parseInt(monthField.getText());
					day = Integer.parseInt(dayField.getText());
					year = Integer.parseInt(yearField.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Date", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (month < 1 || month > 12) {
					JOptionPane.showMessageDialog(null, "Invalid Month", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (day < 1 || day > 31) {
					JOptionPane.showMessageDialog(null, "Invalid Day", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (year < 2019) {
					JOptionPane.showMessageDialog(null, "Invalid Year", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(description.getText().contains(":")) {
					JOptionPane.showMessageDialog(null, "You can't use colons (:) in your description.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Date date = null;
				try {
					int priority = 0;
					date = new Date(year - 1900, month - 1, day);
					String pattern = "MM-dd-yyyy";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					//public Entry(int priority, String description, String dueDate, String status, String started, String finished) {
					if(GUI.masterEntryList.size() >= 1) {
						priority = GUI.masterEntryList.get(GUI.masterEntryList.size()-1).getPriority()+1;
					}
					else {
						priority = 1;
					}
					
					Entry tempEntry = new Entry(priority, description.getText(), simpleDateFormat.format(date), "incomplete", "NA", "NA");
					
					for(int i = 0; i < GUI.masterEntryList.size(); i++) {
						if(description.getText().equalsIgnoreCase(GUI.masterEntryList.get(i).getDescription())) {
							JOptionPane.showMessageDialog(null, "Entry with description already exists.", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					GUI.masterEntryList.add(tempEntry);
					//TablePanel.addEntry(tempEntry);
					GUI.updateList();
					System.out.println(GUI.masterEntryList.toString());
					hideNewEntryFrame();
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(null, "Invalid Date", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else if (source.getSource() == cancelEntry) {
				setVisible(false);
			}
		}
	}
	
}
