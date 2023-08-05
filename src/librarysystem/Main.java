package librarysystem;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import librarysystem.AppScreens.AppPanel;
import librarysystem.loginScreen.LoginPanel;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Main mainApp = new Main();
	private JPanel mainPanel;
	private LoginPanel loginPanel;
	private AppPanel appPanel;
	private String[] listPanel = { "login", "app" };

	private Main() {
		initComponents();
		setVisible(true);
		centerFrameOnDesktop(this);
	}

	private void initComponents() {
		initLayouts();
		initAppInfo();
	}

	private void initAppInfo() {
		setTitle("Library Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(800, 500);
	}

	private void initLayouts() {

		mainPanel = new JPanel(new CardLayout());
		loginPanel = LoginPanel.INSTANCE;
		appPanel = AppPanel.INSTANCE;
		appPanel.setBackground(Color.blue);

		mainPanel.add(listPanel[0], loginPanel);
		mainPanel.add(listPanel[1], appPanel);
		

		add(mainPanel);

	}

	public void navigateToLogin() {
		((CardLayout) (mainPanel.getLayout())).show(mainPanel, listPanel[0]);
	}

	public void navigateToApp() {
		((CardLayout) (mainPanel.getLayout())).show(mainPanel, listPanel[1]);
	}

	public static Main getInstance() {
		return mainApp;
	}

	public static void centerFrameOnDesktop(Component f) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		int frameHeight = f.getSize().height;
		int frameWidth = f.getSize().width;
		f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Main.getInstance();
			}
		});
	}

}
