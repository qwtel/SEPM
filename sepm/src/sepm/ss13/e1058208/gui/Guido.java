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
import javax.swing.event.TableModelEvent;
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
	
	private JToolBar toolbar;
	private JScrollPane scrollPane;
	private JScrollPane rechnungsScrollPane;
	private JTable pferdTable;
	private JButton createButton;
	private JButton deleteButton;
	private JButton createRechnungButton;
	private JButton wucherButton;
	
	private JPanel searchBar = new JPanel();
	private JTextField searchQueryField = new JTextField(30);
	private JTextField maxPreisField = new JTextField(4);
	private JComboBox typQueryField = new JComboBox();
	
	public Guido() {
		log.debug("GUI DO!");
		try {
			s = new SimpleService();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initUI();
	}
	
	private void initUI() {
		this.setTitle("SEPM");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setPreferredSize(new Dimension(800, 600));
        this.setLocation(new Point(340, 120));
        
        this.setLayout(new BorderLayout());
        
        JMenuBar menubar = new JMenuBar();
        
        JMenu file = new JMenu("Ansicht");
        menubar.add(file);
        
        JMenuItem pferdeMenuItem = new JMenuItem("Pferde");
        JMenuItem rechnungsMenuItem = new JMenuItem("Rechnungen");
        pferdeMenuItem.addActionListener(new ShowPferdsActionListener());
        rechnungsMenuItem.addActionListener(new ShowRechnungsActionListener());
        
        file.add(pferdeMenuItem);
        file.add(rechnungsMenuItem);
        
        this.setJMenuBar(menubar);
        //ImageIcon icon = new ImageIcon(getClass().getResource("exit.png"));
        
        toolbar = new JToolBar();

        createButton = new JButton("Pferd hinzufügen");
        toolbar.add(createButton);
        deleteButton = new JButton("Pferd löschen");
        toolbar.add(deleteButton);
        createRechnungButton = new JButton("Rechnung erstellen");
        toolbar.add(createRechnungButton);
        wucherButton = new JButton("Wucher!");
        toolbar.add(wucherButton);
        
        createButton.addActionListener(new CreatePferdActionListener());
        deleteButton.addActionListener(new DeletePferdActionListener());
        createRechnungButton.addActionListener(new CreateRechnungActionListener());
        wucherButton.addActionListener(new WucherActionListener());

        this.add(toolbar, BorderLayout.NORTH);
        
      	typQueryField.addItem("Alle");
        for(Therapieart t : Therapieart.values()) {
        	typQueryField.addItem(t.toString());
        }
        
        searchBar.add(new JLabel("Filter:"));
        searchBar.add(new JLabel("Name"));
        searchBar.add(searchQueryField);
        searchBar.add(new JLabel("max. Preis"));
        searchBar.add(maxPreisField);
        searchBar.add(new JLabel("Therapieart"));
        searchBar.add(typQueryField);
        
        searchQueryField.addKeyListener(this);
        maxPreisField.addKeyListener(this);
        typQueryField.addActionListener(this);
        
        this.add(searchBar, BorderLayout.SOUTH);
        
        initPferdsUI();
        initRechnungsUI();
        this.pack();
	}
	
	private void initPferdsUI() {
		try {
			TableModel tableModel = new PferdTableModel(s);
	        pferdTable = new JTable(tableModel);
	        scrollPane = new JScrollPane(pferdTable);
	        pferdTable.setFillsViewportHeight(true);
	        TableColumn typ = pferdTable.getColumnModel().getColumn(2);
	        JComboBox comboBox = new JComboBox();
	        for(Therapieart t : Therapieart.values()) {
	        	comboBox.addItem(t.toString());
	        }
	        typ.setCellEditor(new DefaultCellEditor(comboBox));
	        
	        //TableColumn name = pferdTable.getColumnModel().getColumn(1);
	        //name.setDefaultEditor(Integer.class, new StringEditor(0, 100));
	        
	        pferdTable.getModel().addTableModelListener((TableModelListener)tableModel);
        
	        this.add(scrollPane);
	        
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initRechnungsUI() {
        /**
		try {
			TableModel tableModel = new PferdTableModel(s);
	        pferdTable = new JTable(tableModel);
	        scrollPane = new JScrollPane(pferdTable);
	        pferdTable.setFillsViewportHeight(true);
	        TableColumn typ = pferdTable.getColumnModel().getColumn(2);
	        JComboBox comboBox = new JComboBox();
	        for(Therapieart t : Therapieart.values()) {
	        	comboBox.addItem(t.toString());
	        }
	        typ.setCellEditor(new DefaultCellEditor(comboBox));
	        
	        //TableColumn name = pferdTable.getColumnModel().getColumn(1);
	        //name.setDefaultEditor(Integer.class, new StringEditor(0, 100));
	        
	        pferdTable.getModel().addTableModelListener((TableModelListener)tableModel);
        
	        this.add(scrollPane);
	        
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
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
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	(new Guido()).setVisible(true);
            }
        });
    }
    
	private class ShowPferdsActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			scrollPane.setVisible(true);
			toolbar.setVisible(true);
		}
	}
	
	private class ShowRechnungsActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			scrollPane.setVisible(false);
			toolbar.setVisible(false);
		}
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ServiceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private class CreateRechnungActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int rows[] = pferdTable.getSelectedRows();
				HashMap<Pferd, Therapieeinheit> einheiten = new HashMap<Pferd, Therapieeinheit>();
				PferdTableModel m = (PferdTableModel)pferdTable.getModel();
				
				for(Integer row : rows) {
					//log.info(row);
					Object[] data = m.getRow(row);
					//log.info(data[0] + " " + data[1]);
					Pferd p = new Pferd(data);
					Therapieeinheit t = new Therapieeinheit();
					t.setPreis(p.getPreis());
					einheiten.put(p, t);
				}
				
				Rechnung r = new Rechnung();
				r.setEinheiten(einheiten);
				r.setDat(new Date(System.currentTimeMillis()));
				s.createRechnung(r);
				
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
			
			// Rest Color
			maxPreisField.setBackground(new Color(255, 255, 255));	
			
			String selection = typQueryField.getSelectedItem().toString();
			if(selection == "Alle") 
				m.setTypQuery(null);
			else
				m.setTypQuery(Therapieart.valueOf(selection));
			
			m.fetchData();
			m.fireTableDataChanged();
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	
}
