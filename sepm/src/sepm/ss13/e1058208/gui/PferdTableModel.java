package sepm.ss13.e1058208.gui;

import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Therapieart;
import sepm.ss13.e1058208.entities.Therapieeinheit;
import sepm.ss13.e1058208.service.Service;
import sepm.ss13.e1058208.service.ServiceException;

class PferdTableModel extends AbstractTableModel implements TableModelListener {
	private static final long serialVersionUID = 1L;
	//private static final Logger log = Logger.getLogger(PferdTableModel.class);
	
	private Guido parent;
	private Service s;
	
	private String query = null;
	private float maxPreis = 0.0f;
	private Therapieart typQuery = null;
	
	private Collection<Pferd> col;
    private Object[][] data; 
	private String[] columnNames = {"Id", "Name", "Typ", "Preis", "Erstellungsdatum", "Bild", "Stunden"};
	
	private HashMap<Pferd, Integer> einheiten = new HashMap<Pferd, Integer>();
	
	public PferdTableModel(Guido parent, Service s) throws ServiceException {
		this.parent = parent;
		this.s = s;
		fetchData();
	}
	
	public void fetchData() throws ServiceException {
		col = s.searchPferds(query, maxPreis, typQuery);
		data = new Object[col.size()][columnNames.length];
		
		int i = 0;
		for(Pferd p : col) {
			if(!einheiten.keySet().contains(p)) {
				einheiten.put(p, 0);
			}
			
			Object[] o = new Object[7];
			o[0] = p.getId();
			o[1] = p.getName();
			o[2] = p.getTyp();
			o[3] = p.getPreis();
			o[4] = p.getDat();
			o[5] = p.getImg();
			o[6] = einheiten.get(p);
			
			String url = "";
			if(o[5] != null)
				url = o[5].toString();
			o[5] = new ImageIcon(url);
			
			data[i++] = o;
		}
	}
	
    @Override
	public int getColumnCount() {
        return columnNames.length;
    }

    @Override
	public int getRowCount() {
        return data.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
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

    @Override
    public Class<? extends Object> getColumnClass(int c) {
        if(getValueAt(0, c) != null)
        	return getValueAt(0, c).getClass();
        return "".getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (col > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
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
			
			einheiten.put(p, (Integer)array[6]);
			
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

	public HashMap<Pferd, Therapieeinheit> getEinheiten() {
		HashMap<Pferd, Therapieeinheit> res = new HashMap<Pferd, Therapieeinheit>();
		for(Pferd p : einheiten.keySet()) {
			int stunden = einheiten.get(p);
			if(stunden > 0) {
				Therapieeinheit t = new Therapieeinheit();
				t.setPreis(p.getPreis());
				t.setStunden(einheiten.get(p));
				res.put(p, t);
			}
		}
		return res;
	}

	public void setEinheiten(HashMap<Pferd, Integer> einheiten) {
		this.einheiten = einheiten;
	}
}
