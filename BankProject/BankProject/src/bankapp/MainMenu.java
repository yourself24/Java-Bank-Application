package bankapp;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class MainMenu extends javax.swing.JFrame {

    private Bank bank;
    private String saveLocation = null;
    private final DefaultTableModel model;

    public MainMenu() {
        initComponents();
        setLocationRelativeTo(null);
        bank = new Bank();
        model = (DefaultTableModel) accountTable.getModel();
        reloadTable();
    }


    private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        addAccountButton = new javax.swing.JButton();
        removeAccountButton = new javax.swing.JButton();
        depositButton = new javax.swing.JButton();
        withdrawButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        accountTable = new javax.swing.JTable();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bank Application");
        getContentPane().setBackground(Color.YELLOW);

        addAccountButton.setText("Add Account");
        addAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAccountButtonActionPerformed(evt);
            }
        });

        removeAccountButton.setText("Remove Account");
        removeAccountButton.setEnabled(false);
        removeAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAccountButtonActionPerformed(evt);
            }
        });

        depositButton.setText("Deposit");
        depositButton.setEnabled(false);
        depositButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositButtonActionPerformed(evt);
            }
        });

        withdrawButton.setText("Withdrawal");
        withdrawButton.setEnabled(false);
        withdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawButtonActionPerformed(evt);
            }
        });

        accountTable.setAutoCreateRowSorter(true);
        accountTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "First Name", "Last Name", "Account Number", "Balance"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        accountTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        accountTable.getTableHeader().setReorderingAllowed(false);
        Color ivory=new Color(255,255,208);
        accountTable.setBackground(ivory);

        accountTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accountTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(accountTable);
        if (accountTable.getColumnModel().getColumnCount() > 0) {
            accountTable.getColumnModel().getColumn(0).setResizable(false);
            accountTable.getColumnModel().getColumn(1).setResizable(false);
            accountTable.getColumnModel().getColumn(2).setResizable(false);
            accountTable.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
                contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                                .addComponent(addAccountButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(removeAccountButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(depositButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(withdrawButton)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        contentPanelLayout.setVerticalGroup(
                contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addAccountButton)
                                        .addComponent(removeAccountButton)
                                        .addComponent(depositButton)
                                        .addComponent(withdrawButton))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                .addContainerGap())
        );


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }



    private void addAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {
        AddAccountMenu menu = new AddAccountMenu(this, true, bank);
        menu.setVisible(true);
        if (menu.getCustomer() != null) {
            addCustomerToTable(menu.getCustomer());
        }
    }

    private void removeAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure?", "Select an Option" , JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int selectedRow = accountTable.getSelectedRow();
            if (selectedRow >= 0) {
                Customer customer = getSelectedCustomer(selectedRow);
                if (customer != null) {
                    bank.closingAccount(customer.getAccount().getAccountNumber());
                    removeCustomerFromTable(selectedRow);
                }
            }
        }
    }

    private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {
        depositOrWithdraw("deposit");
    }

    private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {
        depositOrWithdraw("withdraw");
    }

    private void depositOrWithdraw(String action) {
        int selectedRow = accountTable.getSelectedRow();
        Customer customer = getSelectedCustomer(selectedRow);
        if (customer != null) {
            javax.swing.JDialog window = null;
            if (action.equals("deposit")) {
                window = new DepositMenu(this, true,bank, customer);
            }
            else if (action.equals("withdraw")) {
                window = new WithdrawalMenu(this, true,bank,customer);
            }
            if (window != null) {
                window.setVisible(true);
            }
            customer=bank.getCustomer(customer.getAccount().getAccountNumber());
            reloadCustomerRowData(selectedRow, customer);
        }
    }

    private void accountTableMouseClicked(java.awt.event.MouseEvent evt) {
        setAccountButtonsActive(true);

        if (evt.getClickCount() == 2) {
            int selectedRow = accountTable.getSelectedRow();
            Customer customer = getSelectedCustomer(selectedRow);
            if (customer != null) {
                AccountDetailsPage page = new AccountDetailsPage(this, true,bank, customer);
                page.setVisible(true);
            }
        }
    }









    private Customer getSelectedCustomer(int selectedRow) {
        Customer customer = null;
        if (selectedRow >= 0) {
            int accountNumber = (int) accountTable.getValueAt(selectedRow, 2);
            customer = bank.getCustomer(accountNumber);
        }
        return customer;
    }

    private void addCustomerToTable(Customer customer) {
        model.addRow(new Object[]{});
        reloadCustomerRowData(model.getRowCount() - 1, customer);
    }

    private void removeCustomerFromTable(int row) {
        model.removeRow(row);
    }

    private void reloadCustomerRowData(int selectedRow, Customer customer) {
        model.setValueAt(customer.getFirstName(), selectedRow, 0);
        model.setValueAt(customer.getLastName(), selectedRow, 1);
        model.setValueAt(customer.getAccount().getAccountNumber(), selectedRow, 2);
        model.setValueAt(String.format("%.2f", customer.getAccount().getBalance()), selectedRow, 3);
    }

    private void reloadTable() {
        DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
        // Deletes the rows from the highest index downwards, since deleting
        // from index 0 would shift all remaining rows to a lower index
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        for (Customer c : bank.getCustomers()) {
            addCustomerToTable(c);
        }
    }

    private void setAccountButtonsActive(boolean active) {
        depositButton.setEnabled(active);
        withdrawButton.setEnabled(active);
        removeAccountButton.setEnabled(active);
    }


    public static void main(String args[]) {


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }


    private javax.swing.JTable accountTable;
    private javax.swing.JButton addAccountButton;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton depositButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeAccountButton;
    private javax.swing.JButton withdrawButton;


}
