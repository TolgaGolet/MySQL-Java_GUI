package javaapplication23;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class JavaApplication23 extends JFrame {
    //Database connection
        static Connection connection=null;
        static Statement statement=null;
        static String query="";

        static String JDBCDriver="com.mysql.cj.jdbc.Driver";
        static String databaseURL=""; //Database URL
        static String username=""; //Username
        static String password=""; //Password

        JTabbedPane tabbedPane=new JTabbedPane();

        JPanel insert=new JPanel();
        JPanel read=new JPanel();
        JPanel update=new JPanel();
        JPanel delete=new JPanel();

        String[] comboBoxItems={"Choose a Table", "Courses", "Student Courses", "Student"};
        String[] studentColumns={"Choose a column to update", "firstName", "lastName", "gender", "address", "phoneNumber", "emergencyContact"};
        String[] studentCoursesColumns={"Choose a column to update", "courseID", "finalGrade"};
        String[] coursesColumns={"Choose a column to update", "courseName", "professorName", "class", "courseTime"};
        JComboBox comboBoxInsert=new JComboBox(comboBoxItems);
        JComboBox comboBoxRead=new JComboBox(comboBoxItems);
        JComboBox comboBoxUpdate=new JComboBox(comboBoxItems);
        JComboBox comboBoxDelete=new JComboBox(comboBoxItems);
        JComboBox comboBoxStudent=new JComboBox(studentColumns);
        JComboBox comboBoxStudentCourses=new JComboBox(studentCoursesColumns);
        JComboBox comboBoxCourses=new JComboBox(coursesColumns);

        JLabel readLabel=new JLabel();

        JLabel insertLabel1=new JLabel();
        JLabel insertLabel2=new JLabel();
        JLabel insertLabel3=new JLabel();
        JLabel insertLabel4=new JLabel();
        JLabel insertLabel5=new JLabel();
        JLabel insertLabel6=new JLabel();
        JLabel insertLabel7=new JLabel();
        JLabel updateLabel1=new JLabel();
        JLabel updateLabel2=new JLabel();
        JLabel deleteLabel=new JLabel();

        JTextField insertTextField1=new JTextField();
        JTextField insertTextField2=new JTextField();
        JTextField insertTextField3=new JTextField();
        JTextField insertTextField4=new JTextField();
        JTextField insertTextField5=new JTextField();
        JTextField insertTextField6=new JTextField();
        JTextField insertTextField7=new JTextField();
        JTextField updateTextField1=new JTextField();
        JTextField updateTextField2=new JTextField();
        JTextField deleteTextField=new JTextField();

        JButton insertButton=new JButton();
        JButton readButton=new JButton();
        JButton updateButton=new JButton();
        JButton deleteButton=new JButton();

        ArrayList<String> insertList=new ArrayList<String>();

    //Creating GUI in constructor.
    public JavaApplication23() {
        tabbedPane.add("Insert", insert);
        tabbedPane.add("Read", read);
        tabbedPane.add("Update", update);
        tabbedPane.add("Delete", delete);

        Dimension dimen=new Dimension(300, 35);
        Dimension bDimen=new Dimension(100, 40); //buttondimension
        Dimension lDimen=new Dimension(1000, 1000); //labeldimension
        comboBoxInsert.setMaximumSize(dimen);
        comboBoxInsert.setMinimumSize(dimen);
        comboBoxUpdate.setMaximumSize(dimen);
        comboBoxUpdate.setMinimumSize(dimen);
        comboBoxDelete.setMaximumSize(dimen);
        comboBoxDelete.setMinimumSize(dimen);
        comboBoxStudent.setMaximumSize(dimen);
        comboBoxStudent.setMinimumSize(dimen);
        comboBoxStudentCourses.setMaximumSize(dimen);
        comboBoxStudentCourses.setMinimumSize(dimen);
        comboBoxCourses.setMaximumSize(dimen);
        comboBoxCourses.setMinimumSize(dimen);
        insertTextField1.setMaximumSize(dimen);
        insertTextField1.setMinimumSize(dimen);
        insertTextField2.setMaximumSize(dimen);
        insertTextField2.setMinimumSize(dimen);
        insertTextField3.setMaximumSize(dimen);
        insertTextField3.setMinimumSize(dimen);
        insertTextField4.setMaximumSize(dimen);
        insertTextField4.setMinimumSize(dimen);
        insertTextField5.setMaximumSize(dimen);
        insertTextField5.setMinimumSize(dimen);
        insertTextField6.setMaximumSize(dimen);
        insertTextField6.setMinimumSize(dimen);
        insertTextField7.setMaximumSize(dimen);
        insertTextField7.setMinimumSize(dimen);
        updateTextField1.setMaximumSize(dimen);
        updateTextField1.setMinimumSize(dimen);
        updateTextField2.setMaximumSize(dimen);
        updateTextField2.setMinimumSize(dimen);
        deleteTextField.setMaximumSize(dimen);
        deleteTextField.setMinimumSize(dimen);
        insertButton.setMaximumSize(bDimen);
        insertButton.setMinimumSize(bDimen);
        readButton.setMaximumSize(bDimen);
        readButton.setMinimumSize(bDimen);
        updateButton.setMaximumSize(bDimen);
        updateButton.setMinimumSize(bDimen);
        deleteButton.setMaximumSize(bDimen);
        deleteButton.setMinimumSize(bDimen);

        read.setLayout(null);
        comboBoxRead.setBounds(new Rectangle(new Point(138, 0), dimen));
        readButton.setBounds(new Rectangle(new Point(450, 0), bDimen));
        readButton.setText("Refresh");
        readLabel.setBounds(new Rectangle(new Point(0, -400), lDimen));
        insert.setLayout(new BoxLayout(insert, BoxLayout.PAGE_AXIS));
        update.setLayout(new BoxLayout(update, BoxLayout.PAGE_AXIS));
        delete.setLayout(new BoxLayout(delete, BoxLayout.PAGE_AXIS));

        insert.add(comboBoxInsert);
        read.add(comboBoxRead);
        read.add(readButton);
        read.add(readLabel);
        update.add(comboBoxUpdate);
        delete.add(comboBoxDelete);

        add(tabbedPane);

        setLocation(600, 200);
        setTitle("School Database Management System");
        setSize(600,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        comboBoxInsert.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange()==ItemEvent.SELECTED) {
                    if(comboBoxInsert.getSelectedItem().equals("Student")) {
                        insert.removeAll();
                        insert.add(comboBoxInsert);
                        insert.add(insertLabel1);
                        insertLabel1.setText("Student ID");
                        insert.add(insertTextField1);
                        insertTextField1.setText("");
                        insert.add(insertLabel2);
                        insertLabel2.setText("First Name");
                        insert.add(insertTextField2);
                        insertTextField2.setText("");
                        insert.add(insertLabel3);
                        insertLabel3.setText("Last Name");
                        insert.add(insertTextField3);
                        insertTextField3.setText("");
                        insert.add(insertLabel4);
                        insertLabel4.setText("Gender(Ex: M)");
                        insert.add(insertTextField4);
                        insertTextField4.setText("");
                        insert.add(insertLabel5);
                        insertLabel5.setText("Address");
                        insert.add(insertTextField5);
                        insertTextField5.setText("");
                        insert.add(insertLabel6);
                        insertLabel6.setText("Phone Number");
                        insert.add(insertTextField6);
                        insertTextField6.setText("");
                        insert.add(insertLabel7);
                        insertLabel7.setText("Emergency Contact");
                        insert.add(insertTextField7);
                        insertTextField7.setText("");
                        insert.add(insertButton);
                        insertButton.setText("Insert");
                    }
                    else if(comboBoxInsert.getSelectedItem().equals("Student Courses")) {
                        insert.removeAll();
                        insert.add(comboBoxInsert);
                        insert.add(insertLabel1);
                        insertLabel1.setText("Student ID");
                        insert.add(insertTextField1);
                        insertTextField1.setText("");
                        insert.add(insertLabel2);
                        insertLabel2.setText("Course ID");
                        insert.add(insertTextField2);
                        insertTextField2.setText("");
                        insert.add(insertLabel3);
                        insertLabel3.setText("Final Grade");
                        insert.add(insertTextField3);
                        insertTextField3.setText("");
                        insert.add(insertButton);
                        insertButton.setText("Insert");
                    }
                    else if(comboBoxInsert.getSelectedItem().equals("Courses")) {
                        insert.removeAll();
                        insert.add(comboBoxInsert);
                        insert.add(insertLabel1);
                        insertLabel1.setText("Course ID");
                        insert.add(insertTextField1);
                        insertTextField1.setText("");
                        insert.add(insertLabel2);
                        insertLabel2.setText("Course Name");
                        insert.add(insertTextField2);
                        insertTextField2.setText("");
                        insert.add(insertLabel3);
                        insertLabel3.setText("Professor Name");
                        insert.add(insertTextField3);
                        insertTextField3.setText("");
                        insert.add(insertLabel4);
                        insertLabel4.setText("Class");
                        insert.add(insertTextField4);
                        insertTextField4.setText("");
                        insert.add(insertLabel5);
                        insertLabel5.setText("Course Time(Ex: 10:00:00)");
                        insert.add(insertTextField5);
                        insertTextField5.setText("");
                        insert.add(insertButton);
                        insertButton.setText("Insert");
                    }
                }
            }
        });

        comboBoxRead.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange()==ItemEvent.SELECTED) {
                    if(comboBoxRead.getSelectedItem().equals("Student")) {
                        try {
                            readLabel.setText(readQuery("Student"));
                        } catch (SQLException ex) {
                            Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else if(comboBoxRead.getSelectedItem().equals("Student Courses")) {
                        try {
                            readLabel.setText(readQuery("StudentCourses"));
                        } catch (SQLException ex) {
                            Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else if(comboBoxRead.getSelectedItem().equals("Courses")) {
                        try {
                            readLabel.setText(readQuery("Courses"));
                        } catch (SQLException ex) {
                            Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });

        comboBoxUpdate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange()==ItemEvent.SELECTED) {
                    if(comboBoxUpdate.getSelectedItem().equals("Student")) {
                        update.removeAll();
                        update.add(comboBoxUpdate);
                        update.add(updateLabel1);
                        updateLabel1.setText("Student ID to update");
                        update.add(updateTextField1);
                        updateTextField1.setText("");
                        update.add(comboBoxStudent);
                        update.add(updateLabel2);
                        updateLabel2.setText("<html>Enter the new value<br/>If it's gender enter M or F<html>");
                        update.add(updateTextField2);
                        updateTextField2.setText("");
                        update.add(updateButton);
                        updateButton.setText("Update");
                    }
                    else if(comboBoxUpdate.getSelectedItem().equals("Student Courses")) {
                        update.removeAll();
                        update.add(comboBoxUpdate);
                        update.add(updateLabel1);
                        updateLabel1.setText("Student ID to update");
                        update.add(updateTextField1);
                        updateTextField1.setText("");
                        update.add(comboBoxStudentCourses);
                        update.add(updateLabel2);
                        updateLabel2.setText("Enter the new value");
                        update.add(updateTextField2);
                        updateTextField2.setText("");
                        update.add(updateButton);
                        updateButton.setText("Update");
                    }
                    else if(comboBoxUpdate.getSelectedItem().equals("Courses")) {
                        update.removeAll();
                        update.add(comboBoxUpdate);
                        update.add(updateLabel1);
                        updateLabel1.setText("Course ID to update");
                        update.add(updateTextField1);
                        updateTextField1.setText("");
                        update.add(comboBoxCourses);
                        update.add(updateLabel2);
                        updateLabel2.setText("<html>Enter the new value<br/>If it's Time enter in this format: 10:00:00<html>");
                        update.add(updateTextField2);
                        updateTextField2.setText("");
                        update.add(updateButton);
                        updateButton.setText("Update");
                    }
                }
            }
        });

        comboBoxDelete.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange()==ItemEvent.SELECTED) {
                    if(comboBoxDelete.getSelectedItem().equals("Student")) {
                        delete.removeAll();
                        delete.add(comboBoxDelete);
                        delete.add(deleteLabel);
                        deleteLabel.setText("Student ID to delete");
                        delete.add(deleteTextField);
                        deleteTextField.setText("");
                        delete.add(deleteButton);
                        deleteButton.setText("Delete");
                    }
                    else if(comboBoxDelete.getSelectedItem().equals("Student Courses")) {
                        delete.removeAll();
                        delete.add(comboBoxDelete);
                        delete.add(deleteLabel);
                        deleteLabel.setText("Student ID to delete");
                        delete.add(deleteTextField);
                        deleteTextField.setText("");
                        delete.add(deleteButton);
                        deleteButton.setText("Delete");
                    }
                    else if(comboBoxDelete.getSelectedItem().equals("Courses")) {
                        delete.removeAll();
                        delete.add(comboBoxDelete);
                        delete.add(deleteLabel);
                        deleteLabel.setText("Course ID to delete");
                        delete.add(deleteTextField);
                        deleteTextField.setText("");
                        delete.add(deleteButton);
                        deleteButton.setText("Delete");
                    }
                }
            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(comboBoxInsert.getSelectedItem()=="Student") {
                    if(insertTextField1.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Studant ID can't be empty");
                    else if(insertTextField2.getText().isEmpty()) JOptionPane.showMessageDialog(null, "First Name can't be empty");
                    else if(insertTextField3.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Last Name can't be empty");
                    else if(insertTextField4.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Gender can't be empty");
                    else if(insertTextField5.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Address can't be empty");
                    else if(insertTextField6.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Phone Number can't be empty");
                    else if(insertTextField7.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Emergency Contact can't be empty");
                    else {
                    insertList.removeAll(insertList);
                    insertList.add(insertTextField1.getText());
                    insertList.add(insertTextField2.getText());
                    insertList.add(insertTextField3.getText());
                    insertList.add(insertTextField4.getText());
                    insertList.add(insertTextField5.getText());
                    insertList.add(insertTextField6.getText());
                    insertList.add(insertTextField7.getText());
                        try {
                            insertQuery("Student");
                            JOptionPane.showMessageDialog(null, "Query is successful!");
                        } catch (SQLException ex) {
                            Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "ERROR!\nLook at the console for details.");
                        }
                    }
                }
                else if(comboBoxInsert.getSelectedItem()=="Student Courses") {
                    if(insertTextField1.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Studant ID can't be empty");
                    else if(insertTextField2.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Course ID can't be empty");
                    else if(insertTextField3.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Final Grade can't be empty");
                    else {
                        insertList.removeAll(insertList);
                        insertList.add(insertTextField1.getText());
                        insertList.add(insertTextField2.getText());
                        insertList.add(insertTextField3.getText());
                        try {
                            insertQuery("Student Courses");
                            JOptionPane.showMessageDialog(null, "Query is successful!");
                        } catch (SQLException ex) {
                            Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "ERROR!\nLook at the console for details.");
                        }
                    }
                }
                else if(comboBoxInsert.getSelectedItem()=="Courses") {
                    if(insertTextField1.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Course ID can't be empty");
                    else if(insertTextField2.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Course Name can't be empty");
                    else if(insertTextField3.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Professor Name can't be empty");
                    else if(insertTextField4.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Class can't be empty");
                    else if(insertTextField5.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Course Time can't be empty");
                    else {
                        insertList.removeAll(insertList);
                        insertList.add(insertTextField1.getText());
                        insertList.add(insertTextField2.getText());
                        insertList.add(insertTextField3.getText());
                        insertList.add(insertTextField4.getText());
                        insertList.add(insertTextField5.getText());
                        try {
                            insertQuery("Courses");
                            JOptionPane.showMessageDialog(null, "Query is successful!");
                        } catch (SQLException ex) {
                            Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "ERROR!\nLook at the console for details.");
                        }
                    }
                }
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(comboBoxRead.getSelectedItem().equals("Student")) {
                        try {
                            readLabel.setText(readQuery("Student"));
                        } catch (SQLException ex) {
                            Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else if(comboBoxRead.getSelectedItem().equals("Student Courses")) {
                        try {
                            readLabel.setText(readQuery("StudentCourses"));
                        } catch (SQLException ex) {
                            Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else if(comboBoxRead.getSelectedItem().equals("Courses")) {
                        try {
                            readLabel.setText(readQuery("Courses"));
                        } catch (SQLException ex) {
                            Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            }
        }); //Refresh button

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(updateTextField1.getText().isEmpty()||updateTextField2.getText().isEmpty()) JOptionPane.showMessageDialog(null, "You should fill the fields.");
                else if(comboBoxUpdate.getSelectedItem()=="Student") {
                    if(comboBoxStudent.getSelectedItem()=="Choose a column to update") JOptionPane.showMessageDialog(null, "You should select a column");
                    else {
                        try {
                        updateQuery("Student", (String)comboBoxStudent.getSelectedItem(), updateTextField2.getText(), updateTextField1.getText());
                        JOptionPane.showMessageDialog(null, "Query is successful!");
                    } catch (SQLException ex) {
                        Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "ERROR!\nLook at the console for details.");
                    }
                    }
                }
                else if(comboBoxUpdate.getSelectedItem()=="Student Courses") {
                    if(comboBoxStudentCourses.getSelectedItem()=="Choose a column to update") JOptionPane.showMessageDialog(null, "You should select a column");
                    else {
                        try {
                        updateQuery("Student Courses", (String)comboBoxStudentCourses.getSelectedItem(), updateTextField2.getText(), updateTextField1.getText());
                        JOptionPane.showMessageDialog(null, "Query is successful!");
                    } catch (SQLException ex) {
                        Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "ERROR!\nLook at the console for details.");
                    }
                    }
                }
                else if(comboBoxUpdate.getSelectedItem()=="Courses") {
                    if(comboBoxCourses.getSelectedItem()=="Choose a column to update") JOptionPane.showMessageDialog(null, "You should select a column");
                    else {
                        try {
                        updateQuery("Courses", (String)comboBoxCourses.getSelectedItem(), updateTextField2.getText(), updateTextField1.getText());
                        JOptionPane.showMessageDialog(null, "Query is successful!");
                    } catch (SQLException ex) {
                        Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "ERROR!\nLook at the console for details.");
                    }
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(comboBoxDelete.getSelectedItem()=="Student") {
                    if(deleteTextField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Enter a key in order to delete");
                    try {
                        deleteQuery("Student", deleteTextField.getText());
                        JOptionPane.showMessageDialog(null, "Query is successful!");
                    } catch (SQLException ex) {
                        Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "ERROR!\nLook at the console for details.");
                    }
                }
                else if(comboBoxDelete.getSelectedItem()=="Student Courses") {
                    if(deleteTextField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Enter a key in order to delete");
                    try {
                        deleteQuery("StudentCourses", deleteTextField.getText());
                        JOptionPane.showMessageDialog(null, "Query is successful!");
                    } catch (SQLException ex) {
                        Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "ERROR!\nLook at the console for details.");
                    }
                }
                else if(comboBoxDelete.getSelectedItem()=="Courses") {
                    if(deleteTextField.getText().isEmpty()) JOptionPane.showMessageDialog(null, "Enter a key in order to delete");
                    try {
                        deleteQuery("Courses", deleteTextField.getText());
                        JOptionPane.showMessageDialog(null, "Query is successful!");
                    } catch (SQLException ex) {
                        Logger.getLogger(JavaApplication23.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "ERROR!\nLook at the console for details.");
                    }
                }
            }
        });

    }

    public void insertQuery(String tableName) throws SQLException {
        query="";
        switch (tableName) {
            case "Student":
                query="insert into Student(studentID, firstName, lastName, gender, address, phoneNumber, emergencyContact) values(";
                query+=insertList.get(0)+", ";
                for (int i=1;i<insertList.size()-1;i++) {
                    query+="'"+insertList.get(i)+"', ";
                }
                query+="'"+insertList.get(insertList.size()-1)+"');";
                System.out.println(query);
                statement=connection.createStatement();
                statement.execute(query);
                break;
            case "Student Courses":
                query="insert into StudentCourses(studentID, courseID, finalGrade) values(";
                for(int i=0;i<insertList.size()-1;i++) query+=insertList.get(i)+", ";
                query+=insertList.get(insertList.size()-1)+");";
                System.out.println(query);
                statement=connection.createStatement();
                statement.execute(query);
                break;
            case "Courses":
                query="insert into Courses(courseID, courseName, professorName, class, courseTime) values(";
                query+=insertList.get(0)+", ";
                for(int i=1;i<insertList.size()-1;i++) {
                    query+="'"+insertList.get(i)+"', ";
                }
                query+="'"+insertList.get(insertList.size()-1)+"');";
                System.out.println(query);
                statement=connection.createStatement();
                statement.execute(query);
                break;
            default:
                JOptionPane.showMessageDialog(null, "ERROR!");
                break;
            }
    }

    public String readQuery(String tableName) throws SQLException {
        query="select * from "+tableName;
        String data="<html>";

        statement=connection.createStatement();
        ResultSet result=statement.executeQuery(query);
        ResultSetMetaData metaData = result.getMetaData();
        int number = metaData.getColumnCount();
        for(int i=1; i<=number; i++) data+=metaData.getColumnName(i)+"&emsp;";
        data+="<br/>";
        while(result.next()) {
            for(int i=1; i<=number; i++) data+=result.getObject(i)+"&emsp;&emsp;&emsp;";
            data+="<br/>";
        }
        data+="<html>";
        return data;
    }

    public void updateQuery(String tableName, String column, String newValue, String condition) throws SQLException {
        query="";
        switch (tableName) {
            case "Student":
                query="update Student set "+column+"='"+newValue+"' where studentID="+condition+";";
                System.out.println(query);
                statement=connection.createStatement();
                statement.execute(query);
                break;
            case "Student Courses":
                query="update StudentCourses set "+column+"="+newValue+" where studentID="+condition+";";
                System.out.println(query);
                statement=connection.createStatement();
                statement.execute(query);
                break;
            case "Courses":
                query="update Courses set "+column+"='"+newValue+"' where courseID="+condition+";";
                System.out.println(query);
                statement=connection.createStatement();
                statement.execute(query);
                break;
            default:
                JOptionPane.showMessageDialog(null, "ERROR!");
                break;
        }
    }

    public void deleteQuery(String tableName, String key) throws SQLException {
        query="delete from ";
        switch (tableName) {
            case "Student":
                query+="Student where studentID="+key+";";
                System.out.println(query);
                statement=connection.createStatement();
                statement.execute(query);
                break;
            case "StudentCourses":
                query+="StudentCourses where studentID="+key+";";
                System.out.println(query);
                statement=connection.createStatement();
                statement.execute(query);
                break;
            case "Courses":
                query+="Courses where courseID="+key+";";
                System.out.println(query);
                statement=connection.createStatement();
                statement.execute(query);
                break;
            default:
                JOptionPane.showMessageDialog(null, "ERROR!");
                break;
            }
    }

    public static void connect() throws ClassNotFoundException, SQLException {
            Class.forName(JDBCDriver);
            connection=DriverManager.getConnection(databaseURL, username, password);
    }

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        connect();
        new JavaApplication23();
    }
}
