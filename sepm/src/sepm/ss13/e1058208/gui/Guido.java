package sepm.ss13.e1058208.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Rechnung;
import sepm.ss13.e1058208.entities.Therapieart;
import sepm.ss13.e1058208.entities.Therapieeinheit;
import sepm.ss13.e1058208.service.Service;
import sepm.ss13.e1058208.service.ServiceException;
import sepm.ss13.e1058208.service.SimpleService;

/**
 * Does the GUI. 
 * 
 * @author Florian Klampfer
 */
public class Guido extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Guido.class);
	
	private Service s;
	
    private JTabbedPane tabbedPain = new JTabbedPane();
	
    private JPanel pferdsPanel = new JPanel(new BorderLayout());
    private JPanel rechnungsPanel = new JPanel(new BorderLayout());
    
	private JToolBar toolbar;
	private JTable pferdTable;
	private JButton createButton;
	private JButton deleteButton;
	private JButton createRechnungButton;
	private JButton wucherButton;
	
	private JPanel searchBar = new JPanel();
	private JTextField searchQueryField = new JTextField(10);
	private JTextField maxPreisField = new JTextField(4);
	private JComboBox typQueryField = new JComboBox();
	private JTable rechnungsTable;
	private JTable tpeTable;
	
	public Guido() {
		log.debug("GUI DO!");
		try {
			s = new SimpleService();
		} catch (ServiceException e) {
			fehlerMeldung(e.getMessage());
		}
		initUI();
	}
	
	private void initUI() {
		this.setTitle("SEPM");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setPreferredSize(new Dimension(800, 600));
        this.setLocation(new Point(340, 120));
        this.setLayout(new BorderLayout());
        
        toolbar = new JToolBar();

        createButton = new JButton("Pferd hinzufügen");
        toolbar.add(createButton);
        createButton.addActionListener(new CreatePferdActionListener());
        
        deleteButton = new JButton("Pferd löschen");
        toolbar.add(deleteButton);
        deleteButton.addActionListener(new DeletePferdActionListener());
        
        createRechnungButton = new JButton("Rechnung erstellen");
        toolbar.add(createRechnungButton);
        createRechnungButton.addActionListener(new CreateRechnungActionListener());
        
        wucherButton = new JButton("Beliebte Pferde");
        toolbar.add(wucherButton);
        wucherButton.addActionListener(new WucherActionListener());

        pferdsPanel.add(toolbar, BorderLayout.NORTH);
        this.add(tabbedPain, BorderLayout.CENTER);
        
        initSearchBar();
        initPferdsUI();
        initRechnungsUI();
        
        this.pack();
	}
	
	private void initSearchBar() {
      	typQueryField.addItem("Alle");
        for(Therapieart t : Therapieart.values()) {
        	typQueryField.addItem(t.toString());
        }
        
        searchBar.add(new JLabel("Name"));
        searchBar.add(searchQueryField);
        searchBar.add(new JLabel("max. Preis"));
        searchBar.add(maxPreisField);
        searchBar.add(new JLabel("Therapieart"));
        searchBar.add(typQueryField);
        
        searchQueryField.addKeyListener(this);
        maxPreisField.addKeyListener(this);
        typQueryField.addActionListener(this);
        
        pferdsPanel.add(searchBar, BorderLayout.SOUTH);
	}
	
	private void initPferdsUI() {
		try {
			TableModel tableModel = new PferdTableModel(this, s);
	        pferdTable = new JTable(tableModel);
	        pferdTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
	        pferdTable.setFillsViewportHeight(true);
	        pferdTable.setRowHeight(50);
	        pferdTable.setAutoCreateRowSorter(true);
	        TableColumn typ = pferdTable.getColumnModel().getColumn(2);
	        JComboBox comboBox = new JComboBox();
	        for(Therapieart t : Therapieart.values()) {
	        	comboBox.addItem(t.toString());
	        }
	        DefaultCellEditor dce = new DefaultCellEditor(comboBox);
	        dce.setClickCountToStart(2);
	        typ.setCellEditor(dce);
	        
	        TableColumn img = pferdTable.getColumnModel().getColumn(5);
	        img.setCellEditor(new TableFileChooserEditor());
	        img.setCellRenderer(new ImageRenderer());
	        
	        pferdTable.getModel().addTableModelListener((TableModelListener)tableModel);
	        pferdsPanel.add(new JScrollPane(pferdTable));
        
	        tabbedPain.addTab("Pferde", new JScrollPane(pferdsPanel));
	        
		} catch (ServiceException e) {
			fehlerMeldung(e.getMessage());
		}
	}
	
	private void initRechnungsUI() {
		try {
			TableModel rechnungsTableModel = new RechnungsTableModel(this, s);
	        rechnungsTable = new JTable(rechnungsTableModel);
	        rechnungsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
	        rechnungsTable.setPreferredScrollableViewportSize(new Dimension(300, 50));
	        rechnungsTable.setAutoCreateRowSorter(true);
			TableModel tpeTableModel = new TPETableModel(this, s);
	        tpeTable = new JTable(tpeTableModel);
	        tpeTable.setAutoCreateRowSorter(true);
	        
	        getRechnungsTable().setFillsViewportHeight(true);
	        tpeTable.setFillsViewportHeight(true);
	        
	        rechnungsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            @Override
	            public void valueChanged(ListSelectionEvent e) {
					try {
						TPETableModel m = (TPETableModel)tpeTable.getModel();
						m.fetchData();
						m.fireTableDataChanged();
					} catch (ServiceException e1) {
						fehlerMeldung(e1.getMessage());
					}
	            }
	        });
	        
	        rechnungsPanel.add(new JScrollPane(getRechnungsTable()), BorderLayout.LINE_START);
	        rechnungsPanel.add(new JScrollPane(tpeTable));
	        
	        tabbedPain.addTab("Rechnungen", new JScrollPane(rechnungsPanel));
	        
		} catch (ServiceException e) {
			fehlerMeldung(e.getMessage());
		}
	}
	
    private boolean askPferdDeleted() {
        Toolkit.getDefaultToolkit().beep();
        Object[] options = {"Abbrechen",
                            "OK"};
        int answer = JOptionPane.showOptionDialog(
            SwingUtilities.getWindowAncestor(this),
            "Sind Sie sicher dass Sie das ausgewählte Pferd löschen wollen?",
            "Pferd löschen?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            options,
            options[1]);
         
        if (answer == 1) {
        	return true;
        }
        return false;
    }
    
    private void pferdDeleted() {
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(
            SwingUtilities.getWindowAncestor(this),
            "Das ausgewählte Pferd wurde gelöscht!",
            "Pferd wurde gelöscht",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            options,
            options[0]);
    }
    
	public void fehlerMeldung(String text) {
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(
            SwingUtilities.getWindowAncestor(this),
            text,
            "Fehler",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.ERROR_MESSAGE,
            null,
            options,
            options[0]);
	}
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	(new Guido()).setVisible(true);
            }
        });
    }
    
	private class DeletePferdActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(askPferdDeleted()) {
				try {
					int row = pferdTable.getSelectedRow();
					PferdTableModel m = (PferdTableModel)pferdTable.getModel();
					Object[] data = m.getRow(row);
					Pferd p = new Pferd(data);
					s.deletePferd(p);
					m.fetchData();
					m.fireTableDataChanged();
					pferdDeleted();
				} catch (IllegalArgumentException e1) {
					fehlerMeldung(e1.getMessage());
				} catch (ServiceException e1) {
					fehlerMeldung(e1.getMessage());
				}
			}
		}
	}
	
	private class CreatePferdActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Pferd p = new Pferd();
			try {
				s.createPferd(p);
				PferdTableModel m = (PferdTableModel)pferdTable.getModel();
				m.fetchData();
				m.fireTableDataChanged();
			} catch (IllegalArgumentException e1) {
				fehlerMeldung(e1.getMessage());
			} catch (ServiceException e1) {
				fehlerMeldung(e1.getMessage());
			}
		}
	}
	
	private class CreateRechnungActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				PferdTableModel m = (PferdTableModel)pferdTable.getModel();
				HashMap<Pferd, Therapieeinheit> einheiten = m.getEinheiten();
				
				Rechnung r = new Rechnung();
				r.setEinheiten(einheiten);
				r.setDat(new Date(System.currentTimeMillis()));
				s.createRechnung(r);
				
				m.setEinheiten(new HashMap<Pferd, Integer>());
				m.fetchData();
				m.fireTableDataChanged();
				
				RechnungsTableModel rm = (RechnungsTableModel)rechnungsTable.getModel();
				rm.fetchData();
				rm.fireTableDataChanged();
				
			} catch (IllegalArgumentException e1) {
				fehlerMeldung(e1.getMessage());
			} catch (ServiceException e1) {
				fehlerMeldung(e1.getMessage());
			}
		}
	}
	
	private class WucherActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				s.increaseTop3PferdsBy5Percent();
				PferdTableModel m = (PferdTableModel)pferdTable.getModel();
				m.fetchData();
				m.fireTableDataChanged();
			} catch (ServiceException e1) {
				fehlerMeldung(e1.getMessage());
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) { }

	private void applyFilter() {
		PferdTableModel m = (PferdTableModel)pferdTable.getModel();
		try {
			if(searchQueryField.getText().length() > 0)
				m.setQuery(this.searchQueryField.getText());
			else 
				m.setQuery(null);
			
			if(maxPreisField.getText().length() > 0)
				m.setMaxPreis(new Float(this.maxPreisField.getText()));
			else 
				m.setMaxPreis(0.0f);
			
			// Reset Color
			maxPreisField.setBackground(new Color(255, 255, 255));	
			
			String selection = typQueryField.getSelectedItem().toString();
			if(selection == "Alle") 
				m.setTypQuery(null);
			else
				m.setTypQuery(Therapieart.valueOf(selection));
			
			m.fetchData();
			m.fireTableDataChanged();
		} catch (ServiceException e1) {
			fehlerMeldung(e1.getMessage());
		} catch (NumberFormatException e1) { 
			maxPreisField.setBackground(new Color(255, 186, 186));	
			m.setMaxPreis(0.0f);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		applyFilter();
	}

	@Override
	public void keyTyped(KeyEvent arg0) { }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		applyFilter();
	}

	public JTable getRechnungsTable() {
		return rechnungsTable;
	}
}
