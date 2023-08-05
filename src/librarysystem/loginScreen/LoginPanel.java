package librarysystem.loginScreen;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import librarysystem.LoginWindow;

public class LoginPanel extends JPanel {
	public static LoginPanel INSTANCE = new LoginPanel();
	// ITEN
	private JSplitPane splitPane;

	private JPanel leftLoginSide;
	private JPanel rightLoginSide;

	private LoginPanel() {
		super(new CardLayout());
		initComponents();
	}

	private void setLeftLoginSidePanel() {
		leftLoginSide = new JPanel(new BorderLayout());
		leftLoginSide.setBackground(Color.LIGHT_GRAY);
		String currDirectory = System.getProperty("user.dir");
    	String pathToImage = currDirectory+"/src/librarysystem/home.jpeg";
        ImageIcon image = new ImageIcon(pathToImage);

		JLabel icon = new JLabel(image);
		leftLoginSide.add(icon,BorderLayout.CENTER);

	}

	private void setRightLoginSidePanel() {
		rightLoginSide = new JPanel();
		rightLoginSide.setBackground(Color.LIGHT_GRAY);
		rightLoginSide.add(LoginWindow.INSTANCE);
	}

	private void initComponents() {

		setLeftLoginSidePanel();
		setRightLoginSidePanel();

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftLoginSide, rightLoginSide);
		splitPane.setDividerLocation(500);
		// Add the SplitPane to the Pane
		add(splitPane, BorderLayout.CENTER);

	}

}
