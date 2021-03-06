package sepm.ss13.e1058208.gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import sepm.ss13.e1058208.entities.Rechnung;
import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Therapieeinheit;
import sepm.ss13.e1058208.service.Service;
import sepm.ss13.e1058208.service.ServiceException;

public class TPETableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private Guido parent;
	private Service s;
	private String[] columnNames = {"Pferd", "Stunden", "Preis"};
	private Object[][] data = new Object[0][columnNames.length];
	private ArrayList<Rechnung> rechnungs;

	public TPETableModel(Guido parent, Service s) throws ServiceException {
		this.parent = parent;
		this.s = s;
		fetchData();
	}

	public void fetchData() throws ServiceException {
		rechnungs = new ArrayList<Rechnung>(s.listRechnungs());
		int row = parent.getRechnungsTable().getSelectedRow();
		if(row >= 0) {
			HashMap<Pferd, Therapieeinheit> einheiten = rechnungs.get(row).getEinheiten();
			
			data = new Object[einheiten.size()][columnNames.length];
			int i = 0;
			for(Pferd p : einheiten.keySet()) {
				Therapieeinheit t = einheiten.get(p);
				data[i][0] = p.getName();
				data[i][1] = t.getStunden();
				data[i][2] = t.getPreis();
				i++;
			}
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
