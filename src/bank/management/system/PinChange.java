package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PinChange extends JFrame implements ActionListener {

    JPasswordField pin, repin;
    JButton change, back;
    String pinnumber;

    PinChange(String pinnumber) {
        this.pinnumber = pinnumber;
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);

        JLabel text = new JLabel("CHANGE YOUR PIN");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(250, 280, 500, 35);
        image.add(text);

        JLabel pintext = new JLabel("NEW PIN");
        pintext.setForeground(Color.WHITE);
        pintext.setFont(new Font("System", Font.BOLD, 16));
        pintext.setBounds(165, 320, 180, 25);
        image.add(pintext);

        pin = new JPasswordField();
        pin.setFont(new Font("Raleway", Font.BOLD, 25));
        pin.setBounds(330, 320, 180, 25);
        image.add(pin);

        JLabel repintext = new JLabel("Re-Enter NEW PIN");
        repintext.setForeground(Color.WHITE);
        repintext.setFont(new Font("System", Font.BOLD, 16));
        repintext.setBounds(165, 360, 180, 25);
        image.add(repintext);

        repin = new JPasswordField();
        repin.setFont(new Font("Raleway", Font.BOLD, 25));
        repin.setBounds(330, 360, 180, 25);
        image.add(repin);

        change = new JButton("CHANGE");
        change.setBounds(355, 485, 150, 30);
        change.addActionListener(this);
        image.add(change);

        back = new JButton("BACK");
        back.setBounds(355, 520, 150, 30);
        back.addActionListener(this);
        image.add(back);

        setSize(900, 900);
        setLocation(300, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == change) {
            String npin = pin.getText();
            String rpin = repin.getText();

            if (npin.isEmpty() || rpin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter and confirm new PIN");
                return;
            }

            if (!npin.equals(rpin)) {
                JOptionPane.showMessageDialog(null, "Entered PINs do not match");
                return;
            }

            Conn conn = null;
            try {
                conn = new Conn();

                String query1 = "UPDATE bank SET pin=? WHERE pin=?";
                String query2 = "UPDATE login SET pin=? WHERE pin=?";
                String query3 = "UPDATE signupthree SET pin=? WHERE pin=?";

                PreparedStatement ps1 = conn.getConnection().prepareStatement(query1);
                PreparedStatement ps2 = conn.getConnection().prepareStatement(query2);
                PreparedStatement ps3 = conn.getConnection().prepareStatement(query3);

                ps1.setString(1, rpin);
                ps1.setString(2, pinnumber);
                ps2.setString(1, rpin);
                ps2.setString(2, pinnumber);
                ps3.setString(1, rpin);
                ps3.setString(2, pinnumber);

                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();

                conn.commit();
                JOptionPane.showMessageDialog(null, "PIN changed successfully");

                setVisible(false);
                new Transactions(rpin).setVisible(true);

            } catch (Exception e) {
                try {
                    if (conn != null) conn.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                if (conn != null) conn.close();
            }
        } else {
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        }
    }

    public static void main(String args[]) {
        new PinChange("").setVisible(true);
    }
}
