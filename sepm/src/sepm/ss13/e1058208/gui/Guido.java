package sepm.ss13.e1058208.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import sepm.ss13.e1058208.service.SimpleService;

/**
 * Does the GUI. 
 * 
 * @author Florian Klampfer
 */
public class Guido extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Guido.class);
	
	private Service s;
	
	private JScrollPane scrollPane;
	private JTable pferdTable;
	private JButton createButton;
	private JButton deleteButton;
	private JButton createRechnungButton;
	private JButton wucherButton;
	
	public Guido() {
		log.debug("GUI DO!");
		s = new SimpleService();
		initUI();
	}
	
	private void initUI() {
		this.setTitle("Guido");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setPreferredSize(new Dimension(800, 600));
        this.setLocation(new Point(120, 60));
        
        this.setLayout(new BorderLayout());
        
        JMenuBar menubar = new JMenuBar();
        
        JMenu file = new JMenu("Ansicht");
        menubar.add(file);
        
        JMenuItem pferdeMenuItem = new JMenuItem("Pferde");
        JMenuItem rechnungenMenuItem = new JMenuItem("Rechnungen");
        pferdeMenuItem.addActionListener(new ShowPferdsActionListener());
        
        file.add(pferdeMenuItem);
        file.add(rechnungenMenuItem);
        
        this.setJMenuBar(menubar);
        //ImageIcon icon = new ImageIcon(getClass().getResource("exit.png"));

        showPferdsUI();
        this.pack();
	}
	
	private void showPferdsUI() {
        JToolBar toolbar = new JToolBar();

        createButton = new JButton("Pferd hinzufügen");
        toolbar.add(createButton);
        deleteButton = new JButton("Auswahl löschen");
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
        
        /**
         * TABLE
         */
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
         
        if (answer == 1) { //Revert!
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
		}
	}
	
	private class DeletePferdActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(askPferdDeleted()) {
				int row = pferdTable.getSelectedRow();
				PferdTableModel m = (PferdTableModel)pferdTable.getModel();
				Object[] data = m.getRow(row);
				Pferd p = new Pferd(data);
				s.deletePferd(p);
				m.fetchData();
				m.fireTableDataChanged();
				pferdDeleted();
			}
		}
	}
	
	private class CreatePferdActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Pferd p = new Pferd();
			s.createPferd(p);
			PferdTableModel m = (PferdTableModel)pferdTable.getModel();
			m.fetchData();
			m.fireTableDataChanged();
		}
	}
	
	private class CreateRechnungActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
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
		}
	}
	
	private class WucherActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			s.increaseTop3PferdsBy5Percent();
			PferdTableModel m = (PferdTableModel)pferdTable.getModel();
			m.fetchData();
			m.fireTableDataChanged();
		}
	}
	
}
