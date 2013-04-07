package sepm.ss13.e1058208.gui;

import java.sql.Date;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
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
	
	private Guido parent;
	private Service s;
	
	private String query = null;
	private float maxPreis = 0.0f;
	private Therapieart typQuery = null;
	
    private Object[][] data; 
	private String[] columnNames = {"Id", "Name", "Typ", "Preis", "Datum", "Bild"};
	//model.addColumn("Icon", new Object[] { new ImageIcon("icon.gif") });
	
	public PferdTableModel(Guido parent, Service s) throws ServiceException {
		this.parent = parent;
		this.s = s;
		fetchData();
	}
	
	public void fetchData() throws ServiceException {
		Collection<Pferd> col = s.searchPferds(query, maxPreis, typQuery);
		data = new Object[col.size()][columnNames.length];
		int i = 0;
		for(Pferd p : col) {
			Object[] o = p.toArray();
			
			String url = "";
			if(o[5] != null)
				url = o[5].toString();
			o[5] = new ImageIcon(url);
			
			data[i++] = o;
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
    	if(col == 5)
    		data[row][col] = new ImageIcon(value.toString());
    	else
    		data[row][col] = value;
    	
        fireTableCellUpdated(row, col);
    }

	@Override
	public void tableChanged(TableModelEvent e) {
    	if(getRowCount() > 0) {
    		int row = e.getFirstRow();
    		Object[] array = data[row];
    		
	        Pferd p = new Pferd();
			p.setId((Integer)array[0]);
			p.setName((String)array[1]);
			p.setTyp(Therapieart.valueOf(array[2].toString()));
			p.setPreis((Float)array[3]);
			p.setDat((Date)array[4]);
			p.setImg((array[5] != null) ? array[5].toString() : null);
			
			try {
				s.editPferd(p);
			} catch (IllegalArgumentException e1) {
				parent.fehlerMeldung(e1.getMessage());
			} catch (ServiceException e1) {
				parent.fehlerMeldung(e1.getMessage());
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
