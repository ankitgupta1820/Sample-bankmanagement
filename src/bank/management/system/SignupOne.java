package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;

public class SignupOne extends JFrame implements ActionListener {

    long random;
    JTextField nameTextField, fnameTextField, emailTextField, addressTextField, cityTextField, stateTextField, pinTextField;
    JRadioButton male, female, married, unmarried, other;
    JButton next;
    JDateChooser dateChooser;
    String formno;

    SignupOne() {
        // Generate random form number
        Random ran = new Random();
        random = Math.abs((ran.nextLong() % 9000L) + 1000L);
        formno = String.valueOf(random);

        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 1");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(850, 800));
        panel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 0, 850, 600);
        add(scrollPane);

        JLabel formNoLabel = new JLabel("APPLICATION FORM NO: " + formno);
        formNoLabel.setFont(new Font("Raleway", Font.BOLD, 28));
        formNoLabel.setBounds(140, 20, 600, 40);
        panel.add(formNoLabel);

        JLabel personalDetails = new JLabel("Page 1: Personal Details");
        personalDetails.setFont(new Font("Raleway", Font.BOLD, 22));
        personalDetails.setBounds(290, 80, 400, 30);
        panel.add(personalDetails);

        // Name
        JLabel name = new JLabel("Name:");
        name.setFont(new Font("Raleway", Font.BOLD, 20));
        name.setBounds(100, 140, 100, 30);
        panel.add(name);

        nameTextField = new JTextField();
        nameTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        nameTextField.setBounds(300, 140, 400, 30);
        panel.add(nameTextField);

        // Father's Name
        JLabel fname = new JLabel("Father's Name:");
        fname.setFont(new Font("Raleway", Font.BOLD, 20));
        fname.setBounds(100, 190, 200, 30);
        panel.add(fname);

        fnameTextField = new JTextField();
        fnameTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        fnameTextField.setBounds(300, 190, 400, 30);
        panel.add(fnameTextField);

        // Date of Birth
        JLabel dob = new JLabel("Date of Birth:");
        dob.setFont(new Font("Raleway", Font.BOLD, 20));
        dob.setBounds(100, 240, 200, 30);
        panel.add(dob);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(300, 240, 400, 30);
        panel.add(dateChooser);

        // Gender
        JLabel gender = new JLabel("Gender:");
        gender.setFont(new Font("Raleway", Font.BOLD, 20));
        gender.setBounds(100, 290, 200, 30);
        panel.add(gender);

        male = new JRadioButton("Male");
        male.setBounds(300, 290, 80, 30);
        female = new JRadioButton("Female");
        female.setBounds(400, 290, 100, 30);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        panel.add(male);
        panel.add(female);

        // Email
        JLabel email = new JLabel("Email:");
        email.setFont(new Font("Raleway", Font.BOLD, 20));
        email.setBounds(100, 340, 200, 30);
        panel.add(email);

        emailTextField = new JTextField();
        emailTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        emailTextField.setBounds(300, 340, 400, 30);
        panel.add(emailTextField);

        // Marital Status
        JLabel marital = new JLabel("Marital Status:");
        marital.setFont(new Font("Raleway", Font.BOLD, 20));
        marital.setBounds(100, 390, 200, 30);
        panel.add(marital);

        married = new JRadioButton("Married");
        married.setBounds(300, 390, 100, 30);
        unmarried = new JRadioButton("Unmarried");
        unmarried.setBounds(400, 390, 100, 30);
        other = new JRadioButton("Other");
        other.setBounds(500, 390, 100, 30);

        ButtonGroup maritalGroup = new ButtonGroup();
        maritalGroup.add(married);
        maritalGroup.add(unmarried);
        maritalGroup.add(other);

        panel.add(married);
        panel.add(unmarried);
        panel.add(other);

        // Address
        JLabel address = new JLabel("Address:");
        address.setFont(new Font("Raleway", Font.BOLD, 20));
        address.setBounds(100, 440, 200, 30);
        panel.add(address);

        addressTextField = new JTextField();
        addressTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        addressTextField.setBounds(300, 440, 400, 30);
        panel.add(addressTextField);

        // City
        JLabel city = new JLabel("City:");
        city.setFont(new Font("Raleway", Font.BOLD, 20));
        city.setBounds(100, 490, 200, 30);
        panel.add(city);

        cityTextField = new JTextField();
        cityTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        cityTextField.setBounds(300, 490, 400, 30);
        panel.add(cityTextField);

        // State
        JLabel state = new JLabel("State:");
        state.setFont(new Font("Raleway", Font.BOLD, 20));
        state.setBounds(100, 540, 200, 30);
        panel.add(state);

        stateTextField = new JTextField();
        stateTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        stateTextField.setBounds(300, 540, 400, 30);
        panel.add(stateTextField);

        // Pin Code
        JLabel pin = new JLabel("Pin Code:");
        pin.setFont(new Font("Raleway", Font.BOLD, 20));
        pin.setBounds(100, 590, 200, 30);
        panel.add(pin);

        pinTextField = new JTextField();
        pinTextField.setFont(new Font("Raleway", Font.BOLD, 14));
        pinTextField.setBounds(300, 590, 400, 30);
        panel.add(pinTextField);

        // Next Button
        next = new JButton("Next");
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.setFont(new Font("Raleway", Font.BOLD, 14));
        next.setBounds(620, 660, 80, 30);
        next.addActionListener(this);
        panel.add(next);

        setSize(850, 600);  // Window size smaller, scrollable
        setLocation(350, 10);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        // Validate fields
        if (nameTextField.getText().equals("") || fnameTextField.getText().equals("") ||
                ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill required fields");
            return;
        }

        // Forward data to SignupTwo
        setVisible(false);
        new SignupTwo(formno).setVisible(true);
    }

    public static void main(String[] args) {
        new SignupOne();
    }
}

