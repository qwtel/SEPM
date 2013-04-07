package sepm.ss13.e1058208.gui;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

import org.apache.log4j.Logger;

import sepm.ss13.e1058208.service.ServiceException;

public class TableFileChooserEditor extends DefaultCellEditor implements TableCellEditor {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(TableFileChooserEditor.class);

	private JFileChooser popup;
	private String currentText = "";
	private JTextField editorComponent;
	
	public TableFileChooserEditor() {
	    super(new JTextField());
	    setClickCountToStart(2);
	    editorComponent = new JTextField(999);
	    popup = new JFileChooser();
	}
	
	public Object getCellEditorValue() {
	    return currentText;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	    SwingUtilities.invokeLater(new Runnable() {
			public void run() {
	        	int returnVal = popup.showOpenDialog(null);
	        	if(returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = popup.getSelectedFile();
	                currentText = file.getPath();
	        	}
	            fireEditingStopped();
	        }
	    });
	    editorComponent.setText(currentText);
	    return editorComponent;
	}
}