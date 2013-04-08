package sepm.ss13.e1058208.gui;

import java.awt.Component;
import java.io.File;

import javax.swing.DefaultCellEditor;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

/**
 * Tabellenzelleneditor der einen File Dialog öffnet.
 * Wird zusammen mit ImageRenderer verwendet.
 * 
 * @author Florian Klampfer
 */
public class TableFileChooserEditor extends DefaultCellEditor implements TableCellEditor {
	private static final long serialVersionUID = 1L;
	//private static final Logger log = Logger.getLogger(TableFileChooserEditor.class);

	private JFileChooser popup;
	private String currentText = "";
	private JTextField editorComponent;
	
	public TableFileChooserEditor() {
	    super(new JTextField());
	    setClickCountToStart(2);
	    editorComponent = new JTextField(999);
	    popup = new JFileChooser();
	}
	
	@Override
	public Object getCellEditorValue() {
	    return currentText;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	    SwingUtilities.invokeLater(new Runnable() {
	    	@Override
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