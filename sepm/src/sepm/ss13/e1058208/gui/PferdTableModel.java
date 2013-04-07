package sepm.ss13.e1058208.gui;

import java.sql.Date;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Therapieart;
import sepm.ss13.e1058208.service.Service;
import sepm.ss13.e1058208.service.ServiceException;

class PferdTableModel extends AbstractTableModel implements TableModelListener {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(PferdTableModel.class);
	
	private Service s;
	
	private String query = null;
	private float maxPreis = 0.0f;
	private Therapieart typQuery = null;
	
    private Object[][] data; 
	private String[] columnNames = {"Id", "Name", "Typ", "Preis", "Datum", "Bild"};
	//model.addColumn("Icon", new Object[] { new ImageIcon("icon.gif") });
	
	public PferdTableModel(Service s) throws ServiceException {
		this.s = s;
		fetchData();
	}
	
	public void fetchData() throws ServiceException {
		Collection<Pferd> col = s.searchPferds(query, maxPreis, typQuery);
		data = new Object[col.size()][columnNames.length];
		int i = 0;
		for(Pferd p : col) {
			Object[] o = p.toArray();
			if(o[5] != null)
				o[5] = new ImageIcon((String)o[5]);
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
    	if(getRowCount() > 0)
        	return data[row][col];
    	return null;
    }
    
    public Object[] getRow(int row) {
    	if(getRowCount() > 0)
    		return data[row];
    	return null;
    }

    public Class<? extends Object> getColumnClass(int c) {
        if(getValueAt(0, c) != null)
        	return getValueAt(0, c).getClass();
        return "".getClass();
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
    	if(getRowCount() > 0) {
    		int row = e.getFirstRow();
	        Pferd p = new Pferd(this.data[row]);
	        try {
				s.editPferd(p);
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public float getMaxPreis() {
		return maxPreis;
	}

	public void setMaxPreis(float maxPreis) {
		this.maxPreis = maxPreis;
	}

	public Therapieart getTypQuery() {
		return typQuery;
	}

	public void setTypQuery(Therapieart typQuery) {
		this.typQuery = typQuery;
	}
}
