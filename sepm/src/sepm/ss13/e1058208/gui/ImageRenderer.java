package sepm.ss13.e1058208.gui;

import java.awt.Component;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;

/**
 * ImageREnderer für Image Tabellenzellen.
 * Skaliert ImageIcons und speichert sie in einer HashMap für merkliche Performanceverbesserung.
 * Wird zusammen mit TableFileChooseEditor verwendet.
 * 
 * @author Florian Klampfer
 */
class ImageRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ImageRenderer.class);
	private JLabel lbl = new JLabel();
	
	/**
	 * Store scaled ImageIcons for (huge) performance increase.
	 * Using Image as key because ImageIcons are constantly recreated by the JTable.
	 */
	private HashMap<Image, ImageIcon> scaled = new HashMap<Image, ImageIcon>();
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		ImageIcon imageIcon = (ImageIcon)value;
		Image image = imageIcon.getImage();
		
		// the image icons are not null but the have no width if no image is set.
		if(imageIcon.getIconWidth() > 0 && scaled.get(image) == null) {
			log.info("Scaling image...");
			ImageIcon sc = new ImageIcon(image.getScaledInstance(-1, 50, Image.SCALE_SMOOTH));
			scaled.put(image, sc);
		}
		
		lbl.setIcon(scaled.get(image));
		return lbl;
	}
}
