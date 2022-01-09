package bankapp;


public class AccountDetailsPage extends javax.swing.JDialog {


    public AccountDetailsPage(java.awt.Frame parent, boolean modal,Bank banca, Customer customer) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        
        setTitle(String.format("Account Details Page - %s %s", customer.getFirstName(), customer.getLastName()));
        
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        CNPField.setText(customer.getCNP());
        typeField.setText(customer.getAccount().getAccountType().name());
        accountNumberField.setText(String.valueOf(customer.getAccount().getAccountNumber()));
        balanceField.setText(String.format("$%.2f", customer.getAccount().getBalance()));
        interestField.setText(String.valueOf(banca.checkInterest(customer.getAccount().getBalance(),0) * 100) + "%");
        feeField.setText(String.format("$%.2f",banca.getTransactionFee(customer.getAccount().getAccountType())));
    }



    private void initComponents() {

        firstNameLabel = new javax.swing.JLabel();
        firstNameField = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        lastNameField = new javax.swing.JLabel();
        CNPLabel = new javax.swing.JLabel();
        CNPField = new javax.swing.JLabel();
        typeLabel = new javax.swing.JLabel();
        typeField = new javax.swing.JLabel();
        accountNumberLabel = new javax.swing.JLabel();
        accountNumberField = new javax.swing.JLabel();
        balanceLabel = new javax.swing.JLabel();
        balanceField = new javax.swing.JLabel();
        interestLabel = new javax.swing.JLabel();
        interestField = new javax.swing.JLabel();
        feeLabel = new javax.swing.JLabel();
        feeField = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(9, 2, 5, 5));

        firstNameLabel.setText("First Name:");
        getContentPane().add(firstNameLabel);
        getContentPane().add(firstNameField);

        lastNameLabel.setText("Last Name:");
        getContentPane().add(lastNameLabel);
        getContentPane().add(lastNameField);

        CNPLabel.setText("Personal Numeric Code:");
        getContentPane().add(CNPLabel);
        getContentPane().add(CNPField);

        typeLabel.setText("Account Type:");
        getContentPane().add(typeLabel);
        getContentPane().add(typeField);

        accountNumberLabel.setText("Account Number:");
        getContentPane().add(accountNumberLabel);
        getContentPane().add(accountNumberField);

        balanceLabel.setText("Balance:");
        getContentPane().add(balanceLabel);
        getContentPane().add(balanceField);

        interestLabel.setText("Interest Rate:");
        getContentPane().add(interestLabel);
        getContentPane().add(interestField);

        feeLabel.setText("Transaction Fee:");
        getContentPane().add(feeLabel);
        getContentPane().add(feeField);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        getContentPane().add(okButton);

        pack();
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }



    private javax.swing.JLabel accountNumberField;
    private javax.swing.JLabel accountNumberLabel;
    private javax.swing.JLabel balanceField;
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JLabel feeField;
    private javax.swing.JLabel feeLabel;
    private javax.swing.JLabel firstNameField;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JLabel interestField;
    private javax.swing.JLabel interestLabel;
    private javax.swing.JLabel lastNameField;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel CNPField;
    private javax.swing.JLabel CNPLabel;
    private javax.swing.JLabel typeField;
    private javax.swing.JLabel typeLabel;
}
