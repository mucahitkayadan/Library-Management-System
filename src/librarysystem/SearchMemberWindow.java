package librarysystem;

import business.Book;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class SearchMemberWindow extends JPanel implements LibWindow {
    private JTextField txtFieldFirstName;
    private JTextField txtState;
    private JTextField txtZip;
    private JTextField txtFieldLastName;
    private JTextField txtCity;
    private JTextField txtFieldStreet;
    private JTextField txtFieldId;
    private JTextField txtTelephone;

    ControllerInterface ci = new SystemController();

    private boolean isInitialized = false;

    public SearchMemberWindow() {
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

        JLabel lblNewLabel = new JLabel("Member Search");
        panel.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);

        JPanel panel_2 = new JPanel();
        add(panel_2, BorderLayout.CENTER);

        JPanel panel_3 = new JPanel();
        panel_3.setBounds(154, 231, 430, 39);

        JButton searchMemberButton = new JButton("SEARCH");
        panel_3.add(searchMemberButton);
        searchMemberButton.setHorizontalAlignment(SwingConstants.RIGHT);

        JButton printRecordButton = new JButton("PRINT RECORD");
        panel_3.add(printRecordButton);

        JButton clearFieldsButton = new JButton("CLEAR FIELDS");
        panel_3.add(clearFieldsButton);

        JPanel middlePanel = new JPanel();
        middlePanel.setBounds(5, 5, 460, 219);
        middlePanel.setLayout(new GridLayout(0, 2, 0, 0));
        JLabel lblMemberId = new JLabel("ID:");
        middlePanel.add(lblMemberId);

        txtFieldId = new JTextField();
        middlePanel.add(txtFieldId);
        txtFieldId.setColumns(10);

        JLabel lblFirstName = new JLabel("First Name:");
        middlePanel.add(lblFirstName);

        txtFieldFirstName = new JTextField();
        middlePanel.add(txtFieldFirstName);
        txtFieldFirstName.setColumns(10);

        JLabel lblLastName = new JLabel("Last Name:");
        middlePanel.add(lblLastName);

        txtFieldLastName = new JTextField();
        middlePanel.add(txtFieldLastName);
        txtFieldLastName.setColumns(10);

        JLabel lblStreet = new JLabel("Street:");
        middlePanel.add(lblStreet);

        txtFieldStreet = new JTextField();
        middlePanel.add(txtFieldStreet);
        txtFieldStreet.setColumns(10);

        JLabel lblCity = new JLabel("City:");
        middlePanel.add(lblCity);

        txtCity = new JTextField();
        middlePanel.add(txtCity);
        txtCity.setColumns(10);

        JLabel lblState = new JLabel("State:");
        middlePanel.add(lblState);

        txtState = new JTextField();
        middlePanel.add(txtState);
        txtState.setColumns(10);

        JLabel lblZip = new JLabel("Zip:");
        middlePanel.add(lblZip);

        txtZip = new JTextField();
        middlePanel.add(txtZip);
        txtZip.setColumns(10);

        JLabel lblTelephone = new JLabel("Telephone:");
        middlePanel.add(lblTelephone);

        txtTelephone = new JTextField();
        middlePanel.add(txtTelephone);
        txtTelephone.setColumns(10);

        panel_2.setLayout(null);
        panel_2.add(panel_3);
        panel_2.add(middlePanel);

        searchMemberButton.addActionListener((evt) -> {

            String memberId = txtFieldId.getText();
            if (memberId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Enter Member ID to search", "", ERROR_MESSAGE);
                return;
            }

            LibraryMember member = ci.findMemberById(memberId);

            if (member == null) {
                JOptionPane.showMessageDialog(this, "Member with ID " + memberId + " not found", "", ERROR_MESSAGE);
                return;
            }

            txtFieldId.setEnabled(false);

            txtFieldFirstName.setText(member.getFirstName());
            txtFieldLastName.setText(member.getLastName());
            txtTelephone.setText(member.getTelephone());
            txtFieldStreet.setText(member.getAddress().getStreet());
            txtZip.setText(member.getAddress().getZip());
            txtCity.setText(member.getAddress().getCity());
            txtState.setText(member.getAddress().getState());
        });

        printRecordButton.addActionListener(e -> {

            String memberId = txtFieldId.getText();
            if (memberId.isEmpty() || txtFieldId.isEnabled()) {
                JOptionPane.showMessageDialog(this, "Please Enter Member ID to search", "",
                        ERROR_MESSAGE);
                return;
            }

            LibraryMember member = ci.findMemberById(memberId);

            if (member == null) {
                JOptionPane.showMessageDialog(this, "Member with ID " + memberId + " not found", "",
                        ERROR_MESSAGE);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            System.out.printf("%-10s\t%-10s\t%-8s\t%-6s\t%-20s\t%s\n", "CHECKOUT", "DUE", "ISBN", "COPIES", "TITLE", "MEMBER");
            member.getCheckoutRecords().forEach(record -> {
                record.getEntries().forEach(entry -> {
                    Book b = entry.getCopy().getBook();
                    LibraryMember m = record.getMember();
                    String checkoutDate = entry.getCheckoutDate().format(formatter);
                    String dueDate = entry.getDueDate().format(formatter);
                    System.out.printf("%-10s\t%-10s\t%-8s\t%-6s\t%-20s\t%s\n", checkoutDate, dueDate, b.getIsbn(), b.getNumCopies(), b.getTitle(), m.getFullName());
                });
            });
        });

        clearFieldsButton.addActionListener((evt) -> clearText());
    }

    void clearText() {
        txtCity.setText("");
        txtFieldId.setEnabled(true);
        txtFieldFirstName.setText("");
        txtFieldId.setText("");
        txtFieldLastName.setText("");
        txtFieldStreet.setText("");
        txtState.setText("");
        txtTelephone.setText("");
        txtZip.setText("");
    }

    @Override
    public void init() {
        initialize();
        this.isInitialized(true);
    }

    @Override
    public boolean isInitialized() {
        return this.isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        this.isInitialized = val;
    }
}
