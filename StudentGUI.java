import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentGUI extends JFrame {

    JTextField rollField, nameField, marksField;
    JTextArea displayArea;

    public StudentGUI() {
        setTitle("Student Management System");
        setSize(450, 450);
        setLayout(new FlowLayout());

        add(new JLabel("Roll:"));
        rollField = new JTextField(10);
        add(rollField);

        add(new JLabel("Name:"));
        nameField = new JTextField(10);
        add(nameField);

        add(new JLabel("Marks:"));
        marksField = new JTextField(10);
        add(marksField);

        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View");
        JButton deleteBtn = new JButton("Delete");
        JButton updateBtn = new JButton("Update");
        JButton searchBtn = new JButton("Search");

        add(addBtn);
        add(viewBtn);
        add(deleteBtn);
        add(updateBtn);
        add(searchBtn);

        displayArea = new JTextArea(12, 35);
        add(displayArea);

        addBtn.addActionListener(e -> addStudent());
        viewBtn.addActionListener(e -> viewStudents());
        deleteBtn.addActionListener(e -> deleteStudent());
        updateBtn.addActionListener(e -> updateStudent());
        searchBtn.addActionListener(e -> searchStudent());

        setVisible(true);
    }

    void addStudent() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO students VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, Integer.parseInt(rollField.getText()));
            ps.setString(2, nameField.getText());
            ps.setDouble(3, Double.parseDouble(marksField.getText()));

            ps.executeUpdate();
            displayArea.setText("Student Added!");

        } catch (Exception e) {
            displayArea.setText("Error adding student");
        }
    }

    void viewStudents() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            displayArea.setText("");

            while (rs.next()) {
                displayArea.append(
                    rs.getInt("roll") + " " +
                    rs.getString("name") + " " +
                    rs.getDouble("marks") + "\n"
                );
            }

        } catch (Exception e) {
            displayArea.setText("Error fetching data");
        }
    }

    void deleteStudent() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "DELETE FROM students WHERE roll=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, Integer.parseInt(rollField.getText()));
            int rows = ps.executeUpdate();

            if (rows > 0)
                displayArea.setText("Student Deleted!");
            else
                displayArea.setText("Student Not Found");

        } catch (Exception e) {
            displayArea.setText("Error deleting student");
        }
    }

    void updateStudent() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE students SET name=?, marks=? WHERE roll=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, nameField.getText());
            ps.setDouble(2, Double.parseDouble(marksField.getText()));
            ps.setInt(3, Integer.parseInt(rollField.getText()));

            int rows = ps.executeUpdate();

            if (rows > 0)
                displayArea.setText("Student Updated!");
            else
                displayArea.setText("Student Not Found");

        } catch (Exception e) {
            displayArea.setText("Error updating student");
        }
    }

    void searchStudent() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM students WHERE roll=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, Integer.parseInt(rollField.getText()));
            ResultSet rs = ps.executeQuery();

            displayArea.setText("");

            if (rs.next()) {
                displayArea.append(
                    rs.getInt("roll") + " " +
                    rs.getString("name") + " " +
                    rs.getDouble("marks")
                );
            } else {
                displayArea.setText("Student Not Found");
            }

        } catch (Exception e) {
            displayArea.setText("Error searching student");
        }
    }

    public static void main(String[] args) {
        new StudentGUI();
    }
}
