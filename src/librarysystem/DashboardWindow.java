package librarysystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import business.SystemController;

public class DashboardWindow extends JPanel {
	public static final DashboardWindow INSTANCE = new DashboardWindow();
	SystemController ci = new SystemController();

	private JPanel mainPanel, statitsiquePanel, booksCounterPanel, memberCounterPanel, recordCounterPanel,
			profilePicturePanel;
	private JLabel booksCounterLabel, memberCounterLabel, recordCounterLabel;

	private DashboardWindow() {
		super(new CardLayout());
		init();
	}

	public void init() {
		setProfilePicture();

	}

	private void setProfilePicture() {
		String currDirectory = System.getProperty("user.dir");
		String pathToImage = currDirectory + "/src/librarysystem/home.jpg";
		ImageIcon image = new ImageIcon(pathToImage);

		JLabel icon = new JLabel(image);
		add(icon, BorderLayout.CENTER);

	}

}
