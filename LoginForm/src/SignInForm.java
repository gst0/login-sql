import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class SignInForm extends JFrame{
    final private Font mainFont = new Font("Helvetica", Font.BOLD, 18);

    JTextField tfName;
    JTextField tfEmail;
    JTextField tfPhone;
    JTextField tfAddress;
    JPasswordField pfPassword;

    public void initialize() {
        // Sign In Form
        JLabel lbSignInForm = new JLabel("Sign In", SwingConstants.CENTER);
        lbSignInForm.setFont(mainFont);

        JLabel lbName = new JLabel("Name:");
        lbName.setFont(mainFont);

        tfName = new JTextField();
        tfName.setFont(mainFont);

        JLabel lbEmail = new JLabel("Email:");
        lbEmail.setFont(mainFont);

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbPhone = new JLabel("Phone:");
        lbPhone.setFont(mainFont);

        tfPhone = new JTextField();
        tfPhone.setFont(mainFont);

        JLabel lbAddress = new JLabel("Address:");
        lbAddress.setFont(mainFont);

        tfAddress = new JTextField();
        tfAddress.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password:");
        lbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel signInPanel = new JPanel();
        signInPanel.setLayout(new GridLayout(0, 1, 10, 10));
        signInPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        signInPanel.add(lbSignInForm);
        signInPanel.add(lbName);
        signInPanel.add(tfName);
        signInPanel.add(lbEmail);
        signInPanel.add(tfEmail);
        signInPanel.add(lbPhone);
        signInPanel.add(tfPhone);
        signInPanel.add(lbAddress);
        signInPanel.add(tfAddress);
        signInPanel.add(lbPassword);
        signInPanel.add(pfPassword);

        // Buttons Panel
        JButton btnSignIn = new JButton("Sign In");
        btnSignIn.setFont(mainFont);
        btnSignIn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                String name = tfName.getText();
                String email = tfEmail.getText();
                String phone = tfPhone.getText();
                String address = tfAddress.getText();
                String password = String.valueOf(pfPassword.getPassword());

                boolean dbConnection = signInUser(name, email, phone, address, password);

                if (dbConnection) {
                    LoginForm loginForm = new LoginForm();
                    loginForm.initialize();
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(SignInForm.this, "There are invalid camps", "Try Again", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnClear = new JButton("Clear");
        btnClear.setFont(mainFont);
        btnClear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfName.setText("");                
                tfEmail.setText("");                
                tfPhone.setText("");                
                tfAddress.setText("");                
                pfPassword.setText("");                
            }
        });

        JButton btnLogin = new JButton("Already Sign In");
        btnLogin.setFont(mainFont);
        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm loginForm = new LoginForm();
                loginForm.initialize();
                dispose();            
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        gbc.gridx = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        buttonsPanel.add(btnSignIn, gbc);
        
        gbc.gridx = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        buttonsPanel.add(btnLogin, gbc);
        
        gbc.gridx = 1;
        gbc.gridx = 1;
        buttonsPanel.add(btnClear, gbc);


        // Initialize the Frame
        add(signInPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Sign In");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 690);
        // setMinimumSize(new Dimension(350, 690));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean signInUser(String name, String email, String phone, String address, String password) {
        boolean dbConnection = false;

        String DB_URL = "jdbc:mysql://localhost/myStore";
        String USERNAME = "root";
        String PASSWORD = "1234";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connect to database successfully

            String sql = "INSERT INTO users (name, email, phone, address, password) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            dbConnection = true;

        }catch(Exception e){
            System.out.println(e);
            System.out.println("Database connection failed");
        }
        return dbConnection;
    }
}
