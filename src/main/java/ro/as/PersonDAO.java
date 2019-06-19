package ro.as;

import java.sql.*;
import java.util.ArrayList;

public class PersonDAO {
    private ArrayList personsList;
    private String userid = "sa";
    private String password = "";
    static String url = "jdbc:h2:D:/work/address-book;DB_CLOSE_ON_EXIT=FALSE\n";
    private Connection con;
    // constructor
    public PersonDAO() {
        personsList = new ArrayList();
        getConnection(); //Create Connection to the Oracle Database
    }
    public Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }
        try {
            con = DriverManager.getConnection(url, userid, password);
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        return con;
    }
    public ArrayList searchPerson(String name) {
        try {
            String sql = "SELECT * FROM Person WHERE name like '%" + name
                    + "%'";
            // Create a prepared statement
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            String pname = "";
            String address = "";
            String email = "";
            int id, phone;
            while (rs.next()) {
                id = rs.getInt("id");
                pname = rs.getString("name");
                address = rs.getString("address");
                phone = rs.getInt("phone");
                email = rs.getString("email");
                //Create a ro.as.PersonInfo object
                PersonInfo person = new PersonInfo(id, pname, address,
                        phone, email);
                //Add the person object to array list
                personsList.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return personsList;
    }
    public void savePerson(PersonInfo person) {
        try {
            String sql = "INSERT INTO Person(name, address, "
                    + "phone, email) VALUES (?,?,?,?) ";
            // Create a Preparedstatement
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, person.getName());
            ps.setString(2, person.getAddress());
            ps.setInt(3, person.getPhone());
            ps.setString(4, person.getEmail());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void updatePerson(PersonInfo person) {
        try {
            String sql = "UPDATE Person SET name = ?, address=? , "
                    + "phone=? , email=? where id=?";
            // Create a Prepared statement
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, person.getName());
            ps.setString(2, person.getAddress());
            ps.setInt(3, person.getPhone());
            ps.setString(4, person.getEmail());
            ps.setInt(5, person.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public int removePerson(String name) {
        int no = 0;
        try {
            String sql = "DELETE FROM Person WHERE name = ?";
            // Create a Prepared statement
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            no = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return no;
    }
}
