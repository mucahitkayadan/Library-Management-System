package librarysystem;


import dataaccess.Auth;

import javax.swing.*;

public class MenuWindow extends JPanel implements LibWindow {
    public static final MenuWindow INSTANCE = new MenuWindow();

    private boolean isInitialized = false;
    private Auth role;

    public void init() {
        clearContent();

        if (role == Auth.ADMIN) {
            add(getListBooksButton());
            add(getUserListButton());
        } else if (role == Auth.LIBRARIAN) {
            add(getCheckoutButton());
            add(getSearchMemberButton());
            add(getSearchBookButton());

        } else if (role == Auth.BOTH) {
            add(getListBooksButton());
            add(getUserListButton());
            add(getCheckoutButton());
            add(getSearchBookButton());
            add(getSearchMemberButton());
        }

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        add(separator);

        add(getLogoutButton());
        isInitialized(true);
    }

    private static JButton getLogoutButton() {
        JButton logoutButton = new JButton("Sign Out");
        logoutButton.addActionListener(e -> {
            LibrarySystem.INSTANCE.setLoggedInUser(null);
            LibrarySystem.INSTANCE.init();
        });
        return logoutButton;
    }

    private static JButton getUserListButton() {
        JButton usersButton = new JButton("Members");
        usersButton.addActionListener(e -> LibrarySystem.INSTANCE.openListLibraryMemberWindow());
        return usersButton;
    }

    private static JButton getCheckoutButton() {
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> LibrarySystem.INSTANCE.openBookCheckoutWindow());
        return checkoutButton;
    }

    private static JButton getListBooksButton() {
        JButton booksButton = new JButton("Books");
        booksButton.addActionListener(e -> LibrarySystem.INSTANCE.openListLibraryBookWindow());
        return booksButton;
    }

    private static JButton getSearchMemberButton() {
        JButton booksButton = new JButton("Search Member");
        booksButton.addActionListener(e -> LibrarySystem.INSTANCE.openSearchMemberWindow());
        return booksButton;
    }

    private static JButton getSearchBookButton() {
        JButton booksButton = new JButton("Search Book");
        booksButton.addActionListener(e -> LibrarySystem.INSTANCE.openSearchBookWindow());
        return booksButton;
    }

    public void clearContent() {
        removeAll();
        revalidate();
        repaint();
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    public void setAuth(Auth role) {
        this.role = role;
        init();
    }

    private MenuWindow() {
    }
}

