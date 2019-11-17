package src.team22.submission;

import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import src.team22.submission.SubFrameAddEntry.SubframeButtonListener;

public class SubFrameEditEntry extends JFrame {

	static int entriesAdded = 0;
	// TextField name = new TextField("Name");
	TextField description = new TextField("Description");
	JButton addEntry = new JButton("Edit Entry");
	private JTextField monthField;
	private JTextField dayField;
	private JTextField yearField;
	private JTextField priorityField;
	private int select;

	JButton cancelEntry = new JButton("Cancel");

	public SubFrameEditEntry() {
		setResizable(false);
		setBounds(250, 200, 501, 261);
		setTitle("Edit Entry");
		setAlwaysOnTop(true);
		Font font1 = new Font("SansSerif", Font.PLAIN, 20);

		// increased font size to 30pt
		// name.setFont(font1);
		description.setFont(font1);
		// name.setBounds(15, 35, 205, 30);
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
		
		JLabel lblPriority = new JLabel("Priority");
		lblPriority.setBounds(330, 156, 46, 14);
		getContentPane().add(lblPriority);

		priorityField = new JTextField();
		priorityField.setBounds(380, 153, 67, 20);
		getContentPane().add(priorityField);
		priorityField.setColumns(10);
		addEntry.addActionListener(new SubframeButtonListener());
		cancelEntry.addActionListener(new SubframeButtonListener());
		setVisible(false);
	}
	
	public interface Comparable<T> {
		   public int compareTo(T other);
	}

	public void hideNewEntryFrame() {
		setVisible(false);
	}

	public void showNewEntryFrame(int selection) {
		if(selection == -1) {
			JOptionPane.showMessageDialog(null, "Please select an entry", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else {
			boolean flag = false;
			select = selection;
			//master list SHOULD be sorted by this point.
			int prio = GUI.masterEntryList.get(select).getPriority();
			description.setText(GUI.masterEntryList.get(select).getDescription());
			String[] temp = GUI.masterEntryList.get(select).getDueDate().split("\\-"); 
			dayField.setText(temp[0]);
			monthField.setText(temp[1]);
			yearField.setText(temp[2]);
			priorityField.setText(Integer.toString(prio));
			
			if(GUI.SORTCONST == 1 || GUI.SORTCONST == 2) { //list is currently sorted by date so we need to sort by priority and preserve index
				//sort list by priority
				Collections.sort(GUI.masterEntryList, GUI.compareById);
				//then search the list for our priority that we saved above
				for(int i = 0; i < GUI.masterEntryList.size(); i++) {
					if(prio == GUI.masterEntryList.get(i).getPriority()) {
						flag = true;
						select = i; //store index of priority overlap
					}
				}
				if(!flag) {
					JOptionPane.showMessageDialog(null, "Selected element not found in backend list, restart program. (this is bad.)", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			setVisible(true);
		}
	}
	
	public class SubframeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent source) {
			// TODO Auto-generated method stub
			if (source.getSource() == addEntry) {
				if (monthField.getText() == null || dayField.getText() == null || yearField.getText() == null
						|| monthField.getText() == "" || dayField.getText() == "" || yearField.getText() == "") {
					JOptionPane.showMessageDialog(null, "Invalid Date", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int month = 0;
				int day = 0;
				int year = 0;
				int priority = 0;
				boolean flag = false;
				int ptemp = -1;
				try {
					month = Integer.parseInt(monthField.getText());
					day = Integer.parseInt(dayField.getText());
					year = Integer.parseInt(yearField.getText());
					priority = Integer.parseInt(priorityField.getText());
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
				Entry tempEntry = new Entry(priority, description.getText(), day + "-" + month + "-" + year, GUI.masterEntryList.get(select).getStatus(), GUI.masterEntryList.get(select).getStarted(), GUI.masterEntryList.get(select).getFinished());
				//GUI.masterEntryList.get(select).setPriority(priority);
				//GUI.masterEntryList.get(select).setDueDate(day + "-" + month + "-" + year);
				GUI.masterEntryList.remove(select); //remove node
				//others stay the same, now update priorities of rest of list.
				//first check if new priority exists in the list, if it does we're done here.
				for(int i = 0; i < GUI.masterEntryList.size(); i++) {
					if(priority == GUI.masterEntryList.get(i).getPriority()) {
						flag = true;
						ptemp = i; //store index of priority overlap
					}
				}
				if(!flag) { 
					GUI.masterEntryList.add(tempEntry);
					Collections.sort(GUI.masterEntryList, GUI.compareById);
					GUI.updateList();
					setVisible(false);
					return; 
				}
				else {
					GUI.masterEntryList.add(ptemp, tempEntry);
					for(int i = ptemp+1; i < GUI.masterEntryList.size()-1; i++) {
						if(GUI.masterEntryList.get(i+1).getPriority() - GUI.masterEntryList.get(i).getPriority() <= 1) {
							GUI.masterEntryList.get(i).setPriority(GUI.masterEntryList.get(i).getPriority()+1);
						}
						else {
							GUI.masterEntryList.get(i).setPriority(GUI.masterEntryList.get(i).getPriority()+1);
							break;
						}
					}
					//perform last check
					if(GUI.masterEntryList.get(GUI.masterEntryList.size()-1).getPriority() == GUI.masterEntryList.get(GUI.masterEntryList.size()-2).getPriority()) {
						GUI.masterEntryList.get(GUI.masterEntryList.size()-1).setPriority(GUI.masterEntryList.get(GUI.masterEntryList.size()-1).getPriority()+1);
					}
					//now sort our list to the proper method before returning it
					if(GUI.SORTCONST == 0) {
						Collections.sort(GUI.masterEntryList, GUI.compareById);
					}
					else if (GUI.SORTCONST == 1) {
						Collections.sort(GUI.masterEntryList, GUI.compareByDate);
					}
					else if (GUI.SORTCONST == 2) {
						Collections.sort(GUI.masterEntryList, GUI.compareByDesc);
					}
					GUI.updateList();
				}
				//if priority 
				setVisible(false);
			} else if (source.getSource() == cancelEntry) {
				setVisible(false);
			}
		}
	}
	
}