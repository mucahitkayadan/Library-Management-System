package librarysystem;

import business.Book;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListLibraryBookWindow extends JPanel implements LibWindow {
    private JTextField txtIsbn;
    private JTextField txtAvailability;
    private JTextField txtTitle;
    private JTable table;
    private final ControllerInterface ci = new SystemController();
    private final List<String> defaultList = new ArrayList<>();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private JTextField searchField;
    DefaultTableModel model;

    private int selectedRow = -1;

    public ListLibraryBookWindow() {
        init();
    }

    private JList<String> createJList() {
        JList<String> ret = new JList<>(listModel);
        ret.setVisibleRowCount(4);
        return ret;
    }

    private void initializeDefaultList() {
        defaultList.add("Red");
        defaultList.add("Blue");
        defaultList.add("Yellow");
    }

    private void initialize() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblNewLabel = new JLabel("Book shelf");
        panel.add(lblNewLabel);
        Object[] columnsObjects = {"ISBN", "Title", "Maximum checkout", "Available copies", "Total copies",
                "Authors"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsObjects);
        updateJtable();

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);

        JPanel panel_2 = new JPanel();
        add(panel_2, BorderLayout.CENTER);

        JPanel panel_3 = new JPanel();
        panel_3.setLayout(new GridLayout(0, 6, 0, 0));
        panel_3.setBounds(5, 231, 700, 39);

        searchField = new JTextField();
        searchField.setSize(200, 24);
        panel_3.add(searchField);
        JButton btnSearch = new JButton("ISBN SEARCH");
        panel_3.add(btnSearch);

        JButton btnClearSearch = new JButton("CLEAR SEARCH");
        panel_3.add(btnClearSearch);

        JButton btnCopy = new JButton("COPY");
        panel_3.add(btnCopy);

        JPanel middlePanel = new JPanel();
        middlePanel.setBounds(5, 5, 460, 219);
        middlePanel.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblIsbn = new JLabel("ISBN:");
        middlePanel.add(lblIsbn);

        txtIsbn = new JTextField();
        middlePanel.add(txtIsbn);
        txtIsbn.setColumns(10);

        JLabel lblTitle = new JLabel("Title:");
        middlePanel.add(lblTitle);

        txtTitle = new JTextField();
        middlePanel.add(txtTitle);
        txtTitle.setColumns(10);

        JLabel lblAvailability = new JLabel("Availability:");
        middlePanel.add(lblAvailability);

        txtAvailability = new JTextField("");
        middlePanel.add(txtAvailability);
        txtAvailability.setColumns(10);

        JList<String> mainList = createJList();
        mainList.setFixedCellWidth(70);
        JScrollPane mainScroll = new JScrollPane(mainList);

        initializeDefaultList();
        panel_2.add(mainScroll);

        panel_2.setLayout(null);
        panel_2.add(panel_3);
        panel_2.add(middlePanel);

        JPanel panel_4 = new JPanel();
        panel_4.setBounds(5, 282, 580, 275);
        panel_2.add(panel_4);
        panel_4.setLayout(new BorderLayout(0, 0));
        table = new JTable() {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(model);
        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(5).setPreferredWidth(200);
        colModel.getColumn(4).setPreferredWidth(50);
        colModel.getColumn(3).setPreferredWidth(50);
        colModel.getColumn(2).setPreferredWidth(50);
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(0).setPreferredWidth(75);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);
        panel_4.add(jScrollPane);

        btnCopy.addActionListener(e -> {
            int count = table.getSelectedRowCount();
            if (count == 1) {
                selectedRow = table.getSelectedRow();
                String isbn = (String) table.getValueAt(selectedRow, 0);
                Book book = ci.getBookByISBN(isbn);
                book.addCopy();
                ci.saveBook(book);

                model.setValueAt(book.getAvailableBooksLength(), selectedRow, 3);
                model.setValueAt(book.getCopies().length, selectedRow, 4);

                clearText();
                JOptionPane.showMessageDialog(this, "Copy a book successfully.", "",
                        JOptionPane.INFORMATION_MESSAGE);
                table.clearSelection();

            } else if (count > 1) {
                JOptionPane.showMessageDialog(this, "Please select single a book.", "", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "There is no book to copy", "", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSearch.addActionListener(e -> {
            String isbn = searchField.getText();
            if (isbn.isEmpty()) {
                updateJtable();
            } else {
                updateJtableByIsbn(isbn);
                if (table.getRowCount() > 0) {
                    table.setRowSelectionInterval(0, 0);
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int count = table.getSelectedRowCount();
                if (count == 1) {
                    selectedRow = table.getSelectedRow();
                    Book book = ci.getBookByISBN((String) model.getValueAt(selectedRow, 0));
                    txtTitle.setText(book.getTitle());
                    txtIsbn.setText(book.getIsbn());
                    txtAvailability.setText(String.valueOf(book.getNumCopies()));
                } else {
                    clearText();
                }
                super.mouseClicked(e);
            }
        });

        btnClearSearch.addActionListener((evt) -> {
            searchField.setText("");
            updateJtable();
        });
    }

    void clearText() {
        txtTitle.setText("");
        txtIsbn.setText("");
        txtAvailability.setText("");
    }

    void updateJtable() {
        model.setRowCount(0);
        Collection<Book> books = ci.allBooks();
        for (Book book : books) {
            Object[] objects = {book.getIsbn(), book.getTitle(), book.getMaxCheckoutLength(),
                    book.getAvailableBooksLength(), book.getCopies().length, book.getAuthors().toString()};
            model.addRow(objects);
        }
    }

    void updateJtableByIsbn(String isbn) {
        model.setRowCount(0);
        Collection<Book> books = ci.allBooks();
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                Object[] objects = {book.getIsbn(), book.getTitle(), book.getMaxCheckoutLength(),
                        book.getAvailableBooksLength(), book.getCopies().length, book.getAuthors().toString()};
                model.addRow(objects);
                break;
            }
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