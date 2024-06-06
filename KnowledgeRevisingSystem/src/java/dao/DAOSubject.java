/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class DAOSubject extends DBConnect {

    public Vector<Subject> getAllSubject() {
        Vector<Subject> list = new Vector<>();
        String sql = "SELECT * FROM Subject";

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                list.add(new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5)));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Vector<Subject> getSubjectDetails(String sql) {
        Vector<Subject> list = new Vector<>();

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                list.add(new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5)));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getPageNumber(int n) {
        int pageNumber = 0;
        pageNumber = n / 3;
        if (n % 3 != 0) {
            pageNumber++;
        }
        return pageNumber;
    }

    public Vector<Subject> getSubjectForOnePage(int currentPage) {
        Vector<Subject> list = new Vector<>();
        String sql = "SELECT *"
                + "FROM Subject\n"
                + " ORDER BY subject_id LIMIT 3"
                + " OFFSET " + currentPage * 3;

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                list.add(new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5)));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void update(Subject s) {

        String sqlUpdate = "UPDATE `g5_krs_db`.`subject`\n"
                + "SET\n"
                + "`subject_name` = ?,\n"
                + "`subject_code` = ?,\n"
                + "`description` = ?,\n"
                + "`status` = ?\n"
                + "WHERE `subject_id` = ?;";
        try {
            PreparedStatement statement = con.prepareStatement(sqlUpdate);
            statement.setString(1, s.getSubject_name());
            statement.setString(2, s.getSubject_code());
            statement.setString(3, s.getDescription());
            statement.setBoolean(4, s.isStatus());
            statement.setInt(5, s.getSubject_id());

            statement.executeUpdate();
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DAOSubject o = new DAOSubject();
        Vector<Subject> list = o.getSubjectForOnePage(0);
        System.out.println(list.get(0));
    }

    public void insert(String name, String code, String description, boolean status) {
        String sql = "insert into subject (`subject_name`,`subject_code`,`description`,`status`)\n"
                + "values\n"
                + "(?,?,?,?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, code);
            statement.setString(3, description);
            statement.setBoolean(4, status);
            statement.executeUpdate();
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
        }
    }

    public int checkExisted(String name, String code) {
        String sql = "select * from Subject where subject_name = ? and subject_code =?";
        int cnt = 0;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, code);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                cnt++;
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cnt;
    }
    
    public int checkExistedForUpdate(String name, String code,int subject_id) {
        String sql = "select * from Subject where subject_name = ? and subject_code =? and subject_id!=?";
        int cnt = 0;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, code);
stm.setInt(3, subject_id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                cnt++;
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cnt;
    }

    public void insertManager(int subject_id, int manager_id, int speciality_id) {
        String sql = "insert into Subject_Manager (`subject_id`,`manager_id`,`speciality_id`)\n"
                + "values\n"
                + "(?,?,?)";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, subject_id);
            statement.setInt(2, manager_id);
            statement.setInt(3, speciality_id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
        }
    }

    public void updateManager(int subject_id, int manager_id, int speciality_id) {
        String sql = "UPDATE `g5_krs_db`.`subject_manager`\n"
                + "SET\n"
                + "`manager_id` = ?,\n"
                + "`speciality_id` = ?\n"
                + "where `subject_id` = ?;";
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(3, subject_id);
            statement.setInt(1, manager_id);
            statement.setInt(2, speciality_id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
        }
    }
}
