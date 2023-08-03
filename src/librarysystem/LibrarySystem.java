package librarysystem;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LibrarySystem extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE = new LibrarySystem();
	JPanel mainPanel;
	JPanel menuPanel;
	JPanel contentPanel;
	JSeparator separator;
	String pathToImage;
	JSplitPane splitPane;

	private User loggedInUser;

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	private boolean isInitialized = false;

	private static LibWindow[] allWindows = {
			LibrarySystem.INSTANCE,
			LoginWindow.INSTANCE,
			MenuWindow.INSTANCE,
	};

	public static void hideAllWindows() {
		for (LibWindow frame : allWindows) {
			frame.setVisible(false);
		}
	}

	private LibrarySystem() {
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(15, 1));
		menuPanel.setBackground(Color.LIGHT_GRAY);
		menuPanel.setBorder(new EmptyBorder(15, 30, 10, 30));

		contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(1, 1));
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPanel, contentPanel);
		splitPane.setDividerLocation(200);
		// splitPane.setEnabled(false);
		// splitPane.setDividerSize(0);
		getContentPane().add(splitPane);
	}

	public void init() {
		formatContentPane();
		setPathToImage();
		insertSplashImage();
		setSize(810, 700);
		isInitialized(true);
	}

	private void formatContentPane() {
		if (loggedInUser == null) {
			menuPanel = LoginWindow.INSTANCE;
		} else {
			MenuWindow.INSTANCE.setAuth(loggedInUser.getAuthorization());
			menuPanel = MenuWindow.INSTANCE;
		}
		menuPanel.setLayout(new GridLayout(15, 1));
		menuPanel.setBackground(Color.LIGHT_GRAY);
		splitPane.setLeftComponent(menuPanel);

	}

	private void setPathToImage() {
		String currDirectory = System.getProperty("user.dir");
		pathToImage = currDirectory + "/src/librarysystem/library.jpg";
	}

	private void insertSplashImage() {
		contentPanel.removeAll();
		contentPanel.revalidate();
		contentPanel.repaint();
		ImageIcon image = new ImageIcon(pathToImage);
		contentPanel.add(new JLabel(image));
		contentPanel.setBackground(Color.WHITE);
	}

	public void openBookCheckoutWindow() {
		if (!(contentPanel instanceof BookCheckoutWindow)) {
			contentPanel = new BookCheckoutWindow();
			splitPane.setRightComponent(contentPanel);
		}
	}

	public void openListLibraryBookWindow() {
		if (!(contentPanel instanceof ListLibraryBookWindow)) {
			contentPanel = new ListLibraryBookWindow();
			splitPane.setRightComponent(contentPanel);
		}
	}

	public void openSearchMemberWindow() {
		if (!(contentPanel instanceof SearchMemberWindow)) {
			contentPanel = new SearchMemberWindow();
			splitPane.setRightComponent(contentPanel);
		}
	}

	public void openSearchBookWindow() {
		if (!(contentPanel instanceof SearchBookWindow)) {
			contentPanel = new SearchBookWindow();
			splitPane.setRightComponent(contentPanel);
		}
	}

	public void openListLibraryMemberWindow() {
		if (!(contentPanel instanceof ListLibraryMemberWindow)) {
			contentPanel = new ListLibraryMemberWindow();
			splitPane.setRightComponent(contentPanel);
		}
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
}
