import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainFrame extends JFrame {
    Font mainFont = new Font("Helvetica", Font.BOLD, 18);

    public void initialize(User user) {
        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        infoPanel.add(new JLabel("Name:"));
        infoPanel.add(new JLabel(user.name));
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(new JLabel(user.email));
        infoPanel.add(new JLabel("Phone:"));
        infoPanel.add(new JLabel(user.phone));
        infoPanel.add(new JLabel("Address:"));
        infoPanel.add(new JLabel(user.address));

        Component[] labels = infoPanel.getComponents();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(mainFont);
        }

        // Buttons Panel
        JButton btnSignOut = new JButton("Sign Out");
        btnSignOut.setFont(mainFont);
        btnSignOut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm loginForm = new LoginForm();
                loginForm.initialize();
                dispose();                
            }
        });

        JButton btnDelete = new JButton("Delete acount");
        btnDelete.setFont(mainFont);
        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean dbConnection = deleteUser(user.email);

                if (dbConnection) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Conta excluída com sucesso!", "Conta - Situação", JOptionPane.DEFAULT_OPTION);
                    LoginForm loginForm = new LoginForm();
                    loginForm.initialize();
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Erro ao excluir a conta", "Conta - Situação", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnSignOut);
        buttonsPanel.add(btnDelete);

        // Initialize 
        add(infoPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Dashboard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(350, 300);
        setMinimumSize(new Dimension(500, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean deleteUser(String email) {
        boolean dbConnection = false;

        String DB_URL = "jdbc:mysql://localhost/myStore";
        String USERNAME = "root";
        String PASSWORD = "1234";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connect to database successfully

            String sql = "DELETE FROM users WHERE email = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
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
