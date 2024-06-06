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
public class DAOClass extends DBConnect{
    public Vector<Class_Manager> getAllClass() {
        Vector<Class_Manager> list = new Vector<>();
        String sql = "SELECT * FROM Class";

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
//                list.add(new Class_Manager(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6)));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Vector<Class_Manager> getClass_ManagerDetails(String sql) {
        Vector<Class_Manager> list = new Vector<>();

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                //list.add(new Class_Manager(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6)));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Vector<String> getOneColumn(String sql) {
        Vector<String> list = new Vector<>();

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOClass.class.getName()).log(Level.SEVERE, null, ex);
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

    public Vector<Class_Manager> getClass_ManagerForOnePage(int currentPage) {
        Vector<Class_Manager> list = new Vector<>();
        String sql = "SELECT *"
                + "FROM Class_Manager\n"
                + " ORDER BY setting_id LIMIT 3"
                + " OFFSET " + currentPage * 3;

        try (PreparedStatement stm = con.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                //list.add(new Class_Manager(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getInt(6)));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

//    public void update(Class_Manager s) {
//
//        String sqlUpdate = "UPDATE `g5_krs_db`.`setting`\n"
//                + "SET\n"
//                + "`name` = ?,\n"
//                + "`type` = ?,\n"
//                + "`description` = ?,\n"
//                + "`status` = ?,\n"
//                + "`order` = ?\n"
//                + "WHERE `setting_id` = ?;";
//        try {
//            PreparedStatement statement = con.prepareStatement(sqlUpdate);
//            statement.setString(1, s.getName());
//            statement.setString(2, s.getType());
//            statement.setString(3, s.getDescription());
//            statement.setBoolean(4, s.isStatus());
//            statement.setInt(5, s.getOrder());
//            statement.setInt(6, s.getClass_Manager_id());
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            //log
//            ex.printStackTrace();
//        }
//    }

    

    public void insert(String name, String type, String description, boolean status, int order) {
        String sql = "INSERT INTO `g5_krs_db`.`setting`\n"
                + "(`name`,`type`,`description`,`status`,`order`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?)";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, type);
            stm.setString(3, description);
            stm.setBoolean(4, status);
            stm.setInt(5, order);
            stm.executeUpdate();
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
        }
    }

    public int checkExisted(String type, String name,int setting_id) {
        String sql = "select * from Class_Manager where type = ? and name = ? and setting_id != ?";
        int cnt=0;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            
            stm.setString(1, type);  
            
            stm.setString(2, name);
            stm.setInt(3, setting_id);
            ResultSet rs = stm.executeQuery();            
            while (rs.next()) {
                cnt++;
            }            
        } catch (Exception ex) {
            Logger.getLogger(DAOClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cnt;
    }
    public int checkExistedForInsert(String type, String name) {
        String sql = "select * from Class_Manager where type = ? and name = ?";
        int cnt=0;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            
            stm.setString(2, name);
            stm.setString(1, type);          
            ResultSet rs = stm.executeQuery();            
            while (rs.next()) {
                cnt++;
            }            
        } catch (Exception ex) {
            Logger.getLogger(DAOClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cnt;
    }
}
