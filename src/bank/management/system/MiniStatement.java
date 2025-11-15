package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MiniStatement extends JFrame {

    JLabel mini;

    MiniStatement(String pinnumber) {
        setTitle("Mini Statement");
        setLayout(null);

        mini = new JLabel();
        add(mini);

        JLabel bank = new JLabel("Indian Bank");
        bank.setBounds(150, 20, 100, 20);
        add(bank);

        JLabel card = new JLabel();
        card.setBounds(20, 80, 300, 20);
        add(card);

        JLabel balance = new JLabel();
        balance.setBounds(20, 400, 300, 20);
        add(balance);

        Conn conn = null;
        try {
            conn = new Conn();
            // Get Card Number
            ResultSet rs = conn.getStatement().executeQuery("SELECT * FROM login WHERE pin = '" + pinnumber + "'");
            if (rs.next()) {
                String cardNumber = rs.getString("cardnumber");
                card.setText("Card Number: " + cardNumber.substring(0, 4) + "xxxxxxxx" + cardNumber.substring(12));
            }

            // Get Transactions and calculate balance
            rs = conn.getStatement().executeQuery("SELECT * FROM bank WHERE pin = '" + pinnumber + "'");
            int bal = 0;
            StringBuilder miniText = new StringBuilder("<html>");
            while (rs.next()) {
                String date = rs.getString("date");
                String type = rs.getString("type");
                String amount = rs.getString("amount");

                miniText.append(date).append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                        .append(type).append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                        .append(amount).append("<br><br>");

                if (type.equalsIgnoreCase("Deposit")) {
                    bal += Integer.parseInt(amount);
                } else {
                    bal -= Integer.parseInt(amount);
                }
            }
            miniText.append("</html>");
            mini.setText(miniText.toString());

            balance.setText("Your current account balance is: " + bal);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (conn != null) conn.close();
        }

        mini.setBounds(20, 140, 400, 200);

        setSize(400, 600);
        setLocation(20, 20);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String args[]) {
        new MiniStatement("1234").setVisible(true); // Replace "1234" with actual PIN for testing
    }
}
