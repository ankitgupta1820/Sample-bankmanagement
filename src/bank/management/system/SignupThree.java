package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SignupThree extends JFrame implements ActionListener{
    
    JRadioButton r1,r2,r3,r4;
    JCheckBox c1,c2,c3,c4,c5,c6,c7;
    JButton submit,cancel;
    String formno;
    
    SignupThree(String formno){
        this.formno=formno;
        setLayout(null);
        
        JLabel l1= new JLabel("Page 3:Account Details ");
        l1.setFont(new Font("Raleway",Font.BOLD,22));
        l1.setBounds(280,20,400,40);
        add(l1);
        
        JLabel type = new JLabel("Account Type");
        type.setFont(new Font("Raleway",Font.BOLD,22));
        type.setBounds(100,80,200,30);
        add(type);

         r1= new JRadioButton("Savings Account");
         r1.setFont(new Font("Raleway",Font.BOLD,16));
         r1.setBackground(Color.WHITE);
         r1.setBounds(100,120,250,20);
         add(r1);
         
         r2= new JRadioButton("Fixed Deposit Account");
         r2.setFont(new Font("Raleway",Font.BOLD,16));
         r2.setBackground(Color.WHITE);
         r2.setBounds(350,120,250,20);
         add(r2);
         
         r3= new JRadioButton("Current Account");
         r3.setFont(new Font("Raleway",Font.BOLD,16));
         r3.setBackground(Color.WHITE);
         r3.setBounds(100,160,250,20);
         add(r3);
         
         r4= new JRadioButton("Reccuring Deposit Account");
         r4.setFont(new Font("Raleway",Font.BOLD,16));
         r4.setBackground(Color.WHITE);
         r4.setBounds(350,160,250,20);
         add(r4);
         
         ButtonGroup groupaccount= new ButtonGroup();
         groupaccount.add(r1);
         groupaccount.add(r2);
         groupaccount.add(r3);
         groupaccount.add(r4);

        JLabel card = new JLabel("Card Number");
        card.setFont(new Font("Raleway",Font.BOLD,22));
        card.setBounds(100,240,200,30);
        add(card);
        
        JLabel number = new JLabel("xxxx-xxxx-xxxx-4576");
        number.setFont(new Font("Raleway",Font.BOLD,22));
        number.setBounds(330,240,300,30);
        add(number);
        
        JLabel carddetail = new JLabel("Your 16 digit Card Number");
        carddetail.setFont(new Font("Raleway",Font.BOLD,12));
        carddetail.setBounds(100,270,300,20);
        add(carddetail);


        JLabel pin = new JLabel("PIN:");
        pin.setFont(new Font("Raleway",Font.BOLD,22));
        pin.setBounds(100,310,200,30);
        add(pin);
        
        JLabel pnumber = new JLabel("xxxx");
        pnumber.setFont(new Font("Raleway",Font.BOLD,22));
        pnumber.setBounds(330,310,300,30);
        add(pnumber);

        JLabel pindetail = new JLabel("Your 4 digit Password");
        pindetail.setFont(new Font("Raleway",Font.BOLD,12));
        pindetail.setBounds(100,340,300,20);
        add(pindetail);

         JLabel services = new JLabel("Services Required:");
        services.setFont(new Font("Raleway",Font.BOLD,22));
        services.setBounds(100,390,400,30);
        add(services);
        
        c1 = new JCheckBox("ATM CARD");
        c1.setBackground(Color.WHITE);
        c1.setFont(new Font("Raleway",Font.BOLD,16));
        c1.setBounds(100,440,200,30);
        add(c1);
        
        c2 = new JCheckBox("INTERNET BANKING");
        c2.setBackground(Color.WHITE);
        c2.setFont(new Font("Raleway",Font.BOLD,16));
        c2.setBounds(350,440,200,30);
        add(c2);
        
        c3 = new JCheckBox("MOBILE BANKING");
        c3.setBackground(Color.WHITE);
        c3.setFont(new Font("Raleway",Font.BOLD,16));
        c3.setBounds(100,490,200,30);
        add(c3);
        
        c4 = new JCheckBox("e-MAIL and SMS ALERTS");
        c4.setBackground(Color.WHITE);
        c4.setFont(new Font("Raleway",Font.BOLD,16));
        c4.setBounds(350,490,230,30);
        add(c4);
        
        c5 = new JCheckBox("CHEQUE BOOK");
        c5.setBackground(Color.WHITE);
        c5.setFont(new Font("Raleway",Font.BOLD,16));
        c5.setBounds(100,540,200,30);
        add(c5);
        
        c6 = new JCheckBox("E-STATEMENT");
        c6.setBackground(Color.WHITE);
        c6.setFont(new Font("Raleway",Font.BOLD,16));
        c6.setBounds(350,540,200,30);
        add(c6);
        
        c7 = new JCheckBox("I hereby declare that the above entered details are correct to the best of my knowledge");
        c7.setBackground(Color.WHITE);
        c7.setFont(new Font("Raleway",Font.BOLD,12));
        c7.setBounds(100,580,600,30);
        add(c7);
        
        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Raleway",Font.BOLD,14));
        submit.setBounds(250,620,100,30);
        submit.addActionListener(this);
        add(submit);
        
        cancel = new JButton("Cancel");
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Raleway",Font.BOLD,14));
        cancel.setBounds(420,620,100,30);
        cancel.addActionListener(this);
        add(cancel);
        
        getContentPane().setBackground(Color.WHITE);
        
        setSize(850,820);
        setLocation(350,0);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == submit) {
        String accountType = null;
        if (r1.isSelected()) accountType = "Savings Account";
        else if (r2.isSelected()) accountType = "Fixed Deposit Account";
        else if (r3.isSelected()) accountType = "Current Account";
        else if (r4.isSelected()) accountType = "Recurring Deposit Account";

        Random random = new Random();
        String cardnumber = "" + Math.abs((random.nextLong() % 90000000L) + 5040936000000000L);
        String pinnumber = "" + Math.abs((random.nextLong() % 9000L) + 1000L);

        String facility = "";
        if (c1.isSelected()) facility += " ATM Card";
        if (c2.isSelected()) facility += " Internet Banking";
        if (c3.isSelected()) facility += " Mobile Banking";
        if (c4.isSelected()) facility += " Email+SMS Alerts";
        if (c5.isSelected()) facility += " Cheque Book";
        if (c6.isSelected()) facility += " E-statement";

        if (accountType == null || accountType.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account Type is required");
            return;
        }

        Conn conn = null;
        try {
            conn = new Conn(); // New connection with autoCommit=false

            String query1 = "INSERT INTO signupThree VALUES('" + formno + "','" + accountType + "','" + cardnumber + "','" + pinnumber + "','" + facility + "')";
            String query2 = "INSERT INTO login VALUES('" + formno + "','" + cardnumber + "','" + pinnumber + "')";

            conn.getStatement().executeUpdate(query1);
            conn.getStatement().executeUpdate(query2);

            conn.commit(); // ✅ commit both inserts together

            JOptionPane.showMessageDialog(null, "Card Number: " + cardnumber + " \nPin: " + pinnumber);

            setVisible(false);
            new Deposit(pinnumber).setVisible(true);

        } catch (Exception e) {
            System.out.println(e);
            if (conn != null) {
                conn.rollback(); // ✅ rollback if something fails
            }
        } finally {
            if (conn != null) conn.close(); // ✅ close connection
        }

    } else if (ae.getSource() == cancel) {
        setVisible(false);
        new Login().setVisible(true);
    }
}

  
    
    public static void main(String args[]){
        new SignupThree("");
    }
}
