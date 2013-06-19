package sepm.ss13.e1058208.gui;

import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import sepm.ss13.e1058208.entities.Rechnung;
import sepm.ss13.e1058208.entities.Therapieeinheit;
import sepm.ss13.e1058208.service.Service;
import sepm.ss13.e1058208.service.ServiceException;

public class RechnungsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	//private static final Logger log = Logger.getLogger(RechnungsTableModel.class);
	
	//private Guido parent;
	private Service s;
	private String[] columnNames = {"Id", "Erstellungsdatum", "Summe"};
	private Object[][] data = new Object[0][columnNames.length];

	public RechnungsTableModel(Guido parent, Service s) throws ServiceException {
		//this.parent = parent;
		this.s = s;
		fetchData();
	}

	public void fetchData() throws ServiceException {
		Collection<Rechnung> col = s.listRechnungs();
		data = new Object[col.size()][columnNames.length];
		int i = 0;
		
		for(Rechnung r : col) {
			float summe = 0.0f;
			for(Therapieeinheit t : r.getEinheiten().values()) {
				summe += t.getPreis()*t.getStunden();
			}
			
			data[i][0] = r.getId();
			data[i][1] = r.getDat();
			data[i][2] = summe;
			i++;
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
    
	@Override
    public Class<? extends Object> getColumnClass(int c) {
        if(getValueAt(0, c) != null)
        	return getValueAt(0, c).getClass();
        return "".getClass();
    }
	
    public Object[] getRow(int row) {
    	if(getRowCount() > 0)
    		return data[row];
    	return null;
    }
}
