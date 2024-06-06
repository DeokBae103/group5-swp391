/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.*;
import java.util.Vector;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Duc Bui
 */
public class DAOContact extends DBConnect {

    public Vector<User> getUserDetail(String sql) {
        Vector<User> vector = new Vector<>();
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                vector.add(new User(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10), rs.getString(11)
                ));
                break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOContact.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public int createContact(Contact contact) {
        int n = 0;
        String sql = "INSERT INTO `g5_krs_db`.`contact`\n"
                + "(\n"
                + "`full_name`,\n"
                + "`email`,\n"
                + "`phone`,\n"
                + "`message`,\n"
                + "`setting_id`,\n"
                + "`status`,\n"
                + "`subject`,\n"
                + "`admin_id`)\n"
                + "VALUES(?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement pre = con.prepareStatement(sql);

            pre.setString(1, contact.getFull_name());
            pre.setString(2, contact.getEmail());
            pre.setString(3, contact.getPhone());
            pre.setString(4, contact.getMessage());
            pre.setInt(5, contact.getSetting_id());
            pre.setBoolean(6, contact.isStatus());
            pre.setString(7, contact.getSubject());
            pre.setInt(8, contact.getAdmin_id());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOContact.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public static void main(String[] args) {
        DAOContact dao = new DAOContact();
        int n = dao.createContact(new Contact(0, "dasd", "asdas@fgm.com", "32432", "asd", 1, true, "33scsdc", 1));
        System.out.println(n);
    }
}
