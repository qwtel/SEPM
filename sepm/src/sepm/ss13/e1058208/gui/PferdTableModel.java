package sepm.ss13.e1058208.gui;

import java.sql.Date;
import java.util.Collection;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Therapieart;
import sepm.ss13.e1058208.service.Service;

class PferdTableModel extends AbstractTableModel implements TableModelListener {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(PferdTableModel.class);
	
	private Service s;
    private Object[][] data; 
	private String[] columnNames = {"Id", "Name", "Typ", "Preis", "Datum"};
	
	public PferdTableModel(Service s) {
		this.s = s;
		fetchData();
	}

	public void fetchData() {
		Collection<Pferd> col = s.listPferds();
		data = new Object[col.size()][columnNames.length];
		int i = 0;
		for(Pferd p : s.listPferds()) {
			data[i++] = p.toArray();
		}
	}
	
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }
    
    public Object[] getRow(int row) {
    	return data[row];
    }

    public Class<? extends Object> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        if (col > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		/*
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        log.info(this.data[row][1]);
        */
        Pferd p = new Pferd(this.data[row]);
        s.editPferd(p);
	}
}
