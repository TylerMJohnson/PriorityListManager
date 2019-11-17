package src.team22.submission;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GUI extends JFrame {

	private JPanel contentPane;

	
	public static ArrayList<Entry> masterEntryList = new ArrayList<Entry>();
	static SubFrameAddEntry newEntryFrame = new SubFrameAddEntry();
	static SubFrameEditEntry editEntryFrame = new SubFrameEditEntry();
	static PrintScreen printScreen = new PrintScreen();
	static JPanel panel = new TablePanel();
	static int SORTCONST = 0;
	JLabel lblSort;
	
	static Help help = new Help();
	
	private static GUI ui; 
	
	public static GUI getGUI() {
		return ui;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public GUI() {
		this.setTitle("Team 22 - Priority List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 803, 570);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Load");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				fc.setFileFilter(filter);
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				  File file = fc.getSelectedFile();
				  if(!file.exists()){
						JOptionPane.showMessageDialog(null, "File does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					BufferedReader reader;
					try {
						TablePanel.removeAllRows();
						masterEntryList.clear();
						reader = new BufferedReader(new FileReader(file));
						
						String line = reader.readLine();
						while (line != null) {
							System.out.println(line);
							String[] parts = line.split(":");
							System.out.println(parts[0]);
							Entry tempEntry = new Entry(Integer.parseInt(parts[0]), parts[1], parts[2],parts[3],parts[4],parts[5]);
							GUI.masterEntryList.add(tempEntry);
							GUI.updateList();
							line = reader.readLine();
						}
						reader.close();
					} catch (IOException ee) {
						ee.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "No existing file selected.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		mnFile.add(mntmNewMenuItem);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser fc = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
					fc.setFileFilter(filter);
					if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					  String filename = fc.getSelectedFile().toString();
					  if (!filename.endsWith(".txt")) {
						        filename += ".txt";
					  }
					  File file = new File(filename);
					  if(!file.exists()){
							file.createNewFile();
					  }
					  	FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						
						TableModel model = TablePanel.getModel();
						for( int i = 0; i < model.getRowCount(); i++ ){
							Entry entry = new Entry();
							entry.setPriority((int) model.getValueAt(i, 0));
							entry.setDescription((String) model.getValueAt(i, 1));
							entry.setDueDate((String) model.getValueAt(i, 2));
							entry.setStatus((String) model.getValueAt(i, 3));
							entry.setStarted((String) model.getValueAt(i, 4));
							entry.setFinished((String) model.getValueAt(i, 5));
							bw.write(entry.toString());
						    bw.write("\n");
						}
						bw.close();
						fw.close();
						JOptionPane.showMessageDialog(null, "Data saved to: " + file.getName());
					}
				} catch (IOException ex) {
		            ex.printStackTrace();
		        }
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmPrintReport = new JMenuItem("Print Report");
		mntmPrintReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printScreen.printList();
				printScreen.setVisible(true);
			}
		});
		mnFile.add(mntmPrintReport);
		
		JMenuItem mntmClear = new JMenuItem("Clear List");
		mntmClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				masterEntryList.clear();
				GUI.updateList();
			}
		});
		mnFile.add(mntmClear);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				help.setVisible(true);
			}
		});
		mnFile.add(mntmHelp);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setBounds(42, 11, 58, 14);
		contentPane.add(lblOptions);
		
		JLabel lblTasks = new JLabel("Priority List");
		lblTasks.setBounds(345, 11, 101, 14);
		contentPane.add(lblTasks);
		
		JButton btnNewEntry = new JButton("New Entry");
		btnNewEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newEntryFrame.setVisible(true);
			}
		});
		btnNewEntry.setBounds(11, 36, 157, 23);
		contentPane.add(btnNewEntry);
		
		JButton btnDeleteEntry = new JButton("Delete Entry");
		btnDeleteEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = TablePanel.getTable().getSelectedRows();
				System.out.println(rows.length);
				if(rows.length == 0){
					JOptionPane.showMessageDialog(null, "Please select a row to delete.");
					return;
				}
				int amount = rows.length;
				for(int i = rows[rows.length-1]; i >= rows[0] ; i--){
					masterEntryList.remove(i);
				}
				updateList();
			}
		});
		btnDeleteEntry.setBounds(11, 70, 157, 23);
		contentPane.add(btnDeleteEntry);
		
		//JPanel panel = new TablePanel();
		panel.setBounds(190, 34, 596, 450);
		contentPane.add(panel);
		
		JButton btnNewButton = new JButton("Toggle Completion");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = TablePanel.getTable().getSelectedRows();
				System.out.println(rows.length);
				if(rows.length == 0){
					JOptionPane.showMessageDialog(null, "No row(s) selected");
					return;
				}
				String pattern = "MM-dd-yyyy";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				for(int i = rows[0]; i <= rows[rows.length-1]; i++){
					if(masterEntryList.get(i).getStatus().equals("incomplete")) {
						masterEntryList.get(i).setStatus("inprogress");
						masterEntryList.get(i).setStarted(simpleDateFormat.format(new Date()));
					} else if(masterEntryList.get(i).getStatus().equals("inprogress")) {
						masterEntryList.get(i).setStatus("complete");
						masterEntryList.get(i).setFinished(simpleDateFormat.format(new Date()));
					} else if (masterEntryList.get(i).getStatus().equals("complete")) {
						masterEntryList.get(i).setStatus("incomplete");
						masterEntryList.get(i).setStarted("NA");
						masterEntryList.get(i).setFinished("NA");
					}
				}
				updateList();
			}
		});
		btnNewButton.setBounds(11, 104, 157, 23);
		contentPane.add(btnNewButton);
		btnNewButton.setVisible(true);
		
		JButton btnEditEntry = new JButton("Edit Entry");
		btnEditEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selection = TablePanel.getTable().getSelectedRow();
				editEntryFrame.showNewEntryFrame(selection);
			}
		});
		btnEditEntry.setBounds(11, 138, 157, 23);
		contentPane.add(btnEditEntry);
		
		JButton btnSort = new JButton("Change Sort:");
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(SORTCONST == 0) {
					Collections.sort(GUI.masterEntryList, GUI.compareByDate);
					GUI.updateList();
					SORTCONST = 1;
					lblSort.setText("Due Date");
				}
				else if(SORTCONST == 1) {
					Collections.sort(GUI.masterEntryList, GUI.compareByDesc);
					GUI.updateList();
					SORTCONST = 2;
					lblSort.setText("Description");
				}
				else if(SORTCONST == 2) {
					Collections.sort(GUI.masterEntryList, GUI.compareById);
					GUI.updateList();
					SORTCONST = 0;
					lblSort.setText("Priority");
				}
				
			}
		});
		btnSort.setBounds(11, 172, 157, 23);
		contentPane.add(btnSort);
		
		lblSort = new JLabel("Priority", SwingConstants.CENTER);
		lblSort.setBounds(11, 195, 157, 23);
		contentPane.add(lblSort);
	}
	
	public static void updateList() {
		DefaultTableModel dm = TablePanel.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged(); 
		
		for(int i = 0; i < masterEntryList.size(); i++) {
			TablePanel.addEntry(masterEntryList.get(i));
		}
	}
	
	public static Comparator<Entry> compareById = new Comparator<Entry>() {
	    @Override
	    public int compare(Entry o1, Entry o2) {
	    	if(o1.getPriority() > o2.getPriority()) {
	        	return 1;
	        }
	        else if (o1.getPriority() < o2.getPriority()) {
	        	return -1;
	        }
	        else {
	        	return 0;
	        }
	    }
	};
	
	public static Comparator<Entry> compareByDate = new Comparator<Entry>() {
	    @Override
	    public int compare(Entry o1, Entry o2) {
	    	String[] temp1 = o1.getDueDate().split("\\-"); 
	    	String[] temp2 = o2.getDueDate().split("\\-"); 
	    	int d1 = Integer.parseInt(temp1[0]);
	    	int d2 = Integer.parseInt(temp2[0]);
	    	int m1 = Integer.parseInt(temp1[1]);
	    	int m2 = Integer.parseInt(temp2[1]);
	    	int y1 = Integer.parseInt(temp1[2]);
	    	int y2 = Integer.parseInt(temp2[2]);
	    	if(y1 > y2) {
	    		return 1;
	    	}
	    	else if (y1 < y2) {
	    		return -1;
	    	}
	    	else {
	    		if(m1 > m2) {
		    		return 1;
		    	}
		    	else if (m1 < m2) {
		    		return -1;
		    	}
		    	else {
		    		if(d1 > d2) {
			    		return 1;
			    	}
			    	else if (d1 < d2) {
			    		return -1;
			    	}
			    	else {	
			    		return 0;
			    	}
		    	}
	    	}
	    }
	};
	
	public static Comparator<Entry> compareByDesc = new Comparator<Entry>() {
	    @Override
	    public int compare(Entry o1, Entry o2) {
	    	return o1.getDescription().compareToIgnoreCase(o2.getDescription());
	    }
	};

}
