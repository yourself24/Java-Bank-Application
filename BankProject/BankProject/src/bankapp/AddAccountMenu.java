package bankapp;


import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JOptionPane;


public class AddAccountMenu extends javax.swing.JDialog {

    private Bank bank;
    private Customer customer;


    public AddAccountMenu(java.awt.Frame parent, boolean modal, Bank bank) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.bank = bank;
        customer = null;
    }



    private void initComponents() {
        /**
         * Initialising components for the AddAccountMenu
         *
         */

        firstNameLabel = new javax.swing.JLabel();
        firstNameField = new javax.swing.JTextField();
        lastNameLabel = new javax.swing.JLabel();
        lastNameField = new javax.swing.JTextField();
        CNPLabel = new javax.swing.JLabel();
        CNPField = new javax.swing.JTextField();
        depositLabel = new javax.swing.JLabel();
        depositField = new javax.swing.JTextField();
        typeLabel = new javax.swing.JLabel();
        typeField = new javax.swing.JComboBox();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Account Menu");
        getContentPane().setLayout(new java.awt.GridLayout(6, 2, 5, 5));

        firstNameLabel.setText("First Name:");
        getContentPane().add(firstNameLabel);
        getContentPane().add(firstNameField);

        lastNameLabel.setText("Last Name:");
        getContentPane().add(lastNameLabel);
        getContentPane().add(lastNameField);

        CNPLabel.setText("Personal Numeric Code:");
        getContentPane().add(CNPLabel);
        getContentPane().add(CNPField);

        depositLabel.setText("Initial Deposit:");
        getContentPane().add(depositLabel);
        getContentPane().add(depositField);

        typeLabel.setText("Account Type:");
        getContentPane().add(typeLabel);

        typeField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Checking", "Savings" }));
        getContentPane().add(typeField);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        getContentPane().add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        getContentPane().add(cancelButton);

        pack();
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        StringBuilder warnings = new StringBuilder();
        String firstName = "", lastName = "", CNP = "", depositString = "";
        double amount = 0;
        //Verify first name field
        if (firstNameField.getText().isEmpty()) {
            warnings.append("First name must not be empty.\n");
        } else {
            firstName = firstNameField.getText();
        }
        //Verify last name field
        if (lastNameField.getText().isEmpty()) {
            warnings.append("Last name must not be empty.\n");
        } else {
            lastName = lastNameField.getText();
        }
        if (!CNPField.getText().matches("\\d{13}")) {
            warnings.append("CNP must be 13 digits\n");
        } else {
            CNP = CNPField.getText().replace("-", "");
        }
        //Verify initial deposit
        if (depositField.getText().isEmpty()) {
            warnings.append("Initial deposit must be entered.");
        } else {
            try {
                amount = Bank.round(Double.parseDouble(depositField.getText()), 2);
            } catch (NumberFormatException ex) {
                warnings.append("Initial deposit must be a number.");
            }
        }
        if (warnings.length() > 0) {
            JOptionPane.showMessageDialog(this, warnings.toString(), "Input Warnings", JOptionPane.WARNING_MESSAGE);
        } else {
            AccType accountType=AccType.Undef;
            if (typeField.getSelectedItem().toString() == "Checking") {
                if (amount >= 100) {
                    accountType=AccType.Check;
                } else {
                    warnings.append("Initial deposit must be at least 100 Lei for Checking accounts.");
                }
            } else if (typeField.getSelectedItem().toString() == "Savings") {
                if (amount >= 200) {
                    accountType=AccType.Saving;
                } else {
                    warnings.append("Initial deposit must be at least 200 Lei for Savings accounts.");
                }
            }
            if (accountType!=AccType.Undef ) {
                int accountId = bank.openAccount(firstName,lastName,CNP,accountType,amount);
                customer=bank.getCustomer(accountId);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, warnings.toString(), "Input Warnings", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    


    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField depositField;
    private javax.swing.JLabel depositLabel;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField CNPField;
    private javax.swing.JLabel CNPLabel;
    private javax.swing.JComboBox typeField;
    private javax.swing.JLabel typeLabel;


    Customer getCustomer() {
        return customer;
    }
}
