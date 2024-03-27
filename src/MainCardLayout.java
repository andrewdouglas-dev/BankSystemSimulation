import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainCardLayout extends JFrame{
    private JPanel MainPanel;
    private JTextField HomeCustomerSSN;
    private JButton HomeLoginButton;
    private JLabel InvalidCustomer;
    private JPanel HomePanel;
    private JTextField AccountTextField;
    private JLabel InvalidAccount;
    private JButton AccountSelectButton;
    private JPanel AccountPanel;
    private JPanel AccInfoPanel;
    private JLabel AccountInfo;
    private JButton DepositButton;
    private JButton WithdrawalButton;
    private JButton TransferButton;
    private JPanel DepositPanel;
    private JTextField DepositAmount;
    private JButton DepositPanelButton;
    private JLabel InvalidDeposit;
    private JButton DepositDoneButton;
    private JLabel DepositPanelLabel;
    private JLabel SuccessDepositLabel;
    private JPanel WithdrawalPanel;
    private JTextField WithdrawalAmount;
    private JButton WithdrawalPanelButton;
    private JButton WithdrawalDoneButton;
    private JLabel WithdrawalPanelLabel;
    private JLabel InvalidWithdrawal;
    private JLabel SuccessWithdrawalLabel;
    private JTextField TransferToAccount;
    private JLabel TransferToAccountLabel;
    private JPanel TransferPanel;
    private JTextField TransferAmount;
    private JLabel TransferAmountLabel;
    private JButton TransferPanelButton;
    private JButton TransferDoneButton;
    private JLabel InvalidTransferAccount;
    private JLabel InvalidTransferAmount;
    private JLabel SuccessfulTransfer;
    private JLabel InvalidTransfer;

    Customer customer;
    Account account;
    Transaction transaction;

    public MainCardLayout() {
        setUpWindow();

        customer = new Customer();
        account = new Account();

        HomeLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customer.setCustomer(HomeCustomerSSN.getText())) {
                    MainPanel.removeAll();
                    MainPanel.add(AccountPanel);
                    MainPanel.repaint();
                    MainPanel.revalidate();
                } else {
                    InvalidCustomer.setVisible(true);
                }
            }
        });

        AccountSelectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (account.setAccount(customer, AccountTextField.getText())) {
                    InvalidAccount.setVisible(false);
                    toAccInfo();
                } else {
                    InvalidAccount.setVisible(true);
                }
            }
        });

        DepositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.removeAll();
                MainPanel.add(DepositPanel);
                MainPanel.repaint();
                MainPanel.revalidate();
            }
        });
        WithdrawalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.removeAll();
                MainPanel.add(WithdrawalPanel);
                MainPanel.repaint();
                MainPanel.revalidate();
            }
        });

        TransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.removeAll();
                MainPanel.add(TransferPanel);
                MainPanel.repaint();
                MainPanel.revalidate();
            }
        });

        DepositPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaction = new Transaction(account);
                if (transaction.Deposit(Float.parseFloat(DepositAmount.getText()))) {
                    InvalidDeposit.setVisible(false);
                    DepositPanelButton.setVisible(false);
                    DepositPanelLabel.setVisible(false);
                    DepositAmount.setVisible(false);
                    SuccessDepositLabel.setVisible(true);
                    DepositDoneButton.setVisible(true);
                } else {
                    InvalidDeposit.setVisible(true);
                }
            }
        });

        DepositDoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toAccInfo();
                InvalidDeposit.setVisible(false);
                SuccessDepositLabel.setVisible(false);
                DepositDoneButton.setVisible(false);

                DepositPanelButton.setVisible(true);
                DepositPanelLabel.setVisible(true);
                DepositAmount.setVisible(true);
                DepositAmount.setText("");
            }
        });

        WithdrawalPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaction = new Transaction(account);
                int outcome = transaction.Withdrawal(Float.parseFloat(WithdrawalAmount.getText()));

                if (outcome == 1) {
                    InvalidWithdrawal.setText("Insufficient Account Balance to withdrawal. Try again with different balance.");
                    InvalidWithdrawal.setVisible(true);
                } else if (outcome == 2) {
                    InvalidWithdrawal.setText("There was an error with this transaction. Please try again.");
                    InvalidWithdrawal.setVisible(true);
                } else {
                    SuccessWithdrawalLabel.setVisible(true);
                    WithdrawalDoneButton.setVisible(true);
                    WithdrawalAmount.setVisible(false);
                    WithdrawalPanelButton.setVisible(false);
                    WithdrawalPanelLabel.setVisible(false);
                    InvalidWithdrawal.setVisible(false);
                }
            }
        });

        WithdrawalDoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toAccInfo();
                SuccessWithdrawalLabel.setVisible(false);
                WithdrawalDoneButton.setVisible(false);
                InvalidWithdrawal.setVisible(false);

                WithdrawalAmount.setVisible(true);
                WithdrawalPanelButton.setVisible(true);
                WithdrawalPanelLabel.setVisible(true);
                WithdrawalAmount.setText("");
            }
        });


        TransferPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account account1 = new Account();
                account1.setAccount(customer, TransferToAccount.getText());

                if (TransferToAccount.getText().equals(account.accountID)) {
                    InvalidTransferAccount.setText("Please enter different Account ID than the one currently being used.");
                    InvalidTransferAccount.setVisible(true);
                } else if (!account1.setAccount(customer, TransferToAccount.getText())) {
                    InvalidTransferAccount.setText("Account number not found for customer.");
                    InvalidTransferAccount.setVisible(true);
                } else {
                    InvalidTransferAccount.setVisible(false);
                    Transaction transaction = new Transaction(account);
                    int code = transaction.Transfer(account1, Float.parseFloat(TransferAmount.getText()));

                    if (code == 1) {
                        InvalidTransferAmount.setVisible(true);
                    } else if (code == 2) {
                        InvalidTransfer.setText("Problem with this function. Please try again.");
                        InvalidTransfer.setVisible(true);
                    } else {
                        InvalidTransfer.setVisible(false);
                        InvalidTransferAmount.setVisible(false);
                        InvalidTransferAccount.setVisible(false);
                        TransferAmountLabel.setVisible(false);
                        TransferToAccountLabel.setVisible(false);
                        TransferAmount.setVisible(false);
                        TransferToAccount.setVisible(false);
                        TransferPanelButton.setVisible(false);

                        SuccessfulTransfer.setVisible(true);
                        TransferDoneButton.setVisible(true);
                    }
                }
            }
        });

        TransferDoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toAccInfo();
                InvalidTransfer.setVisible(false);
                InvalidTransferAmount.setVisible(false);
                InvalidTransferAccount.setVisible(false);
                SuccessfulTransfer.setVisible(false);
                TransferDoneButton.setVisible(false);

                TransferAmountLabel.setVisible(true);
                TransferToAccountLabel.setVisible(true);
                TransferAmount.setVisible(true);
                TransferToAccount.setVisible(true);
                TransferPanelButton.setVisible(true);
                TransferAmount.setText("");
                TransferToAccount.setText("");
            }
        });
    }

    public void toAccInfo() {
        MainPanel.removeAll();
        MainPanel.add(AccInfoPanel);
        AccountInfo.setText(account.getAccountDetails());
        MainPanel.repaint();
        MainPanel.revalidate();
    }

    public void setUpWindow() {
        this.setTitle("Bank System v0.1");
        ImageIcon img = new ImageIcon("bank logo.png");
        this.setIconImage(img.getImage());
        this.setVisible(true);
        //this.setContentPane(MainPanel);
        this.setSize(600,400);
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(MainPanel, BorderLayout.CENTER);
    }
}
