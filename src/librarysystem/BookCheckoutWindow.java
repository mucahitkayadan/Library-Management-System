package librarysystem;

import business.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class BookCheckoutWindow extends JPanel implements LibWindow {

    public static final BookCheckoutWindow INSTANCE = new BookCheckoutWindow();

    private JComboBox<String> bookIsbnTextField;
    private JComboBox<String> memberIdTextField;
    DefaultTableModel model = new DefaultTableModel();
    ControllerInterface ci = new SystemController();

    /**
     * Create the application.
     */
    public BookCheckoutWindow() {
        init();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblNewLabel = new JLabel("Book Checkout History");
        panel.add(lblNewLabel);

        JLabel errorLabel = new JLabel();
        errorLabel.setText("Hello World");
        panel.add(lblNewLabel);

        Object[] columnsObjects = { "CHECKOUT DATE", "DUE DATE", "ISBN", "BOOK TITLE", "BORROWER", "TEL" };

        model.setColumnIdentifiers(columnsObjects);
        getCheckoutHistoryList();

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);

        JPanel panel_2 = new JPanel();
        add(panel_2, BorderLayout.CENTER);

        JPanel panel_3 = new JPanel();
        panel_3.setBounds(154, 231, 430, 39);

        JButton checkoutBookButton = new JButton("CHECKOUT BOOK");
        panel_3.add(checkoutBookButton);
        checkoutBookButton.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel middlePanel = new JPanel();
        middlePanel.setBounds(5, 5, 460, 219);
        middlePanel.setLayout(new GridLayout(0, 2, 0, 0));
        JLabel memberIdLabel = new JLabel("Member ID:");
        middlePanel.add(memberIdLabel);

        Collection<LibraryMember> members = ci.alLibraryMembers();

        String[] membersStrings = new String[members.size()];
        int counter = 0;
        for (LibraryMember member : members) {
            membersStrings[counter] = member.getMemberId() + " - " + member.getFullName();
            counter++;
        }

        memberIdTextField = new JComboBox<>(membersStrings);
        middlePanel.add(memberIdTextField);

        JLabel bookIsbnLabel = new JLabel("ISBN:");
        middlePanel.add(bookIsbnLabel);

        Collection<Book> books = ci.allBooks();
        String[] bookStrings = new String[books.size()];
        int index = 0;
        for (Book book : books) {
            bookStrings[index] = book.getIsbn() + " - " + book.getTitle();
            index++;
        }

        bookIsbnTextField = new JComboBox<>(bookStrings);
        middlePanel.add(bookIsbnTextField);

        panel_2.setLayout(null);
        panel_2.add(panel_3);
        panel_2.add(middlePanel);

        JPanel panel_4 = new JPanel();
        panel_4.setBounds(5, 282, 650, 275);
        panel_2.add(panel_4);
        panel_4.setLayout(new BorderLayout(0, 0));

        JTable table = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(model);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);
        panel_4.add(jScrollPane);

        checkoutBookButton.addActionListener(event -> {
            String memberId = (String) memberIdTextField.getSelectedItem();
            String bookIsbn = (String) bookIsbnTextField.getSelectedItem();
            if (memberId == null || memberId.isEmpty() || bookIsbn == null || bookIsbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required", "", JOptionPane.ERROR_MESSAGE);
                return;
            }

            memberId = memberId.substring(0, memberId.indexOf(" - ")).trim();
            bookIsbn = bookIsbn.substring(0, bookIsbn.indexOf(" - ")).trim();

            try {
                clearText();
                ci.checkBook(memberId, bookIsbn);
                JOptionPane.showMessageDialog(this, "Book successfully checked out", "", INFORMATION_MESSAGE,
                        new ImageIcon(System.getProperty("user.dir") + "/src/librarysystem/success.png"));
                getCheckoutHistoryList();
            } catch (LibrarySystemException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    void clearText() {
        memberIdTextField.setSelectedItem(null);
        bookIsbnTextField.setSelectedItem(null);
    }

    void getCheckoutHistoryList() {
        Collection<CheckoutHistory> checkouts = ci.getCheckoutHistory();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        model.setRowCount(0);
        for (CheckoutHistory checkout : checkouts) {
            model.addRow(new Object[] {
                    checkout.getCheckoutDate().format(formatter),
                    checkout.getDueDate().format(formatter),
                    checkout.getCopy().getBook().getIsbn(),
                    checkout.getCopy().getBook().getTitle(),
                    checkout.getMember().getFullName(),
                    checkout.getMember().getTelephone(),
            });
        }
    }

    @Override
    public void init() {
        initialize();
    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public void isInitialized(boolean val) {
    }
}
