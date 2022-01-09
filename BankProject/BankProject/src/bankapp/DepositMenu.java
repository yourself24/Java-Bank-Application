package bankapp;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DepositMenu extends javax.swing.JDialog {

    private Customer customer;
    private Bank banca;

    public DepositMenu(java.awt.Frame parent, boolean modal,Bank banca, Customer customer) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        this.customer = customer;
        this.banca=banca;

    }


    private void initComponents() {
        /**
         * initialising components for deposit menu
         */
        amountLabel = new javax.swing.JLabel();
        amountField = new javax.swing.JTextField();
        depositButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Deposit Menu");
        getContentPane().setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        amountLabel.setText("Deposit Amount:");
        getContentPane().add(amountLabel);
        getContentPane().add(amountField);

        depositButton.setText("Deposit");
        depositButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositButtonActionPerformed(evt);
            }
        });
        getContentPane().add(depositButton);

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

    private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {
        /**
         * checing if the amount for deposit is a valid number
         */
        StringBuilder warnings = new StringBuilder();
        //Checks the deposit is not empty
        if (amountField.getText().isEmpty()) {
            warnings.append("Deposit amount is required.\n");
        } else {
            double amount = 0;
            //Checks if the deposit is a positive number
            try {
                amount = Bank.round(Double.parseDouble(amountField.getText()), 2);
                int result = JOptionPane.showConfirmDialog(this, "Deposit Lei " + String.format("%.2f", amount) + " to the account?\nInterest Earned: Lei " + String.format("%.2f", (banca.checkInterest(customer.getAccount().getBalance(),0)*amount)));
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        //Makes the deposit
                        banca.deposit(customer.getAccount().getAccountNumber(),amount);

                        this.dispose();
                    } catch (InvalidAmountException ex) {
                        warnings.append("Deposit amount is invalid.\n");
                    }
                    
                }
            } catch (NumberFormatException ex) {
                warnings.append("Deposit must be a number.\n");
            }
        }
        if (warnings.length() > 0) {
            JOptionPane.showMessageDialog(this, warnings.toString(), "Deposit Warnings", JOptionPane.WARNING_MESSAGE);
        }
    }



    private javax.swing.JTextField amountField;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton depositButton;

}
