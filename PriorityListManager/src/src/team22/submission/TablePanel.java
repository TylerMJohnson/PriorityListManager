package src.team22.submission;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TablePanel extends JPanel {
	
	private static JTable table;

	public static JTable getTable() {
		return table;
	}

	static DefaultTableModel model = new DefaultTableModel() {

		   @Override
		   public boolean isCellEditable(int row, int column) {
			   return false;
		   }
		
		};
	
	public static DefaultTableModel getModel() {
		return model;
	}
	
    private TableRowSorter<TableModel> rowSorter;
	public TablePanel() {
		table = new JTable(model){
	        @Override
	        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex,
	                int columnIndex) {
	            JComponent component = (JComponent) super.prepareRenderer(renderer, rowIndex, columnIndex);  
	            if(getValueAt(rowIndex, 3).toString().equalsIgnoreCase("incomplete")) {
	                component.setBackground(new Color(255,102,102));
	            } else if(getValueAt(rowIndex, 3).toString().equalsIgnoreCase("inprogress")) {
	                component.setBackground(new Color(255,255,153));
	            } else if(getValueAt(rowIndex, 3).toString().equalsIgnoreCase("complete")){
	                component.setBackground(new Color(102,255,102));
	            }

	            return component;
	        }
		};
		table.setSize(800, 400);
		table.setDragEnabled(true);
		table.setDropMode(DropMode.INSERT_ROWS);
		table.setRowHeight(20);
		table.setRowSorter(rowSorter);
		model.addColumn("Priority");
		model.addColumn("Description");
		model.addColumn("Due Date");
		model.addColumn("Status");
		model.addColumn("Date Started");
		model.addColumn("Date Finished");
		add(table);
		rowSorter = new TableRowSorter<>(table.getModel());
		
		JScrollPane scroller = new JScrollPane(table);
		add(scroller);
	}
	
	public static void removeAllRows(){
		int rowCount = model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
		    model.removeRow(i);
		}
	}
	
	public static String printTable(){
		StringBuilder sb = new StringBuilder();
		sb.append("Task List: \n\n");
		for(int i = 0; i < GUI.masterEntryList.size(); i++){
			sb.append(GUI.masterEntryList.get(i).printLine() + "\n");
		}
		return sb.toString();
	}

	public static void addEntry(Entry entry) {
		getModel().addRow(new Object[]{entry.getPriority(), entry.getDescription(), entry.getDueDate(), entry.getStatus(), entry.getStarted(), entry.getFinished()});
	}



}
