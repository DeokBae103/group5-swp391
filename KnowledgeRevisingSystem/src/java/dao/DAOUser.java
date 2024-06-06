/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.*;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.System.Logger;
import java.util.Vector;
import java.util.logging.Level;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author DELL
 */
public class DAOUser extends DBConnect {

    public Vector<User> getAll(String sql) {
        Vector<User> vector = new Vector<>();
        try {
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                int settingId = rs.getInt(2);
                String phone = rs.getString(3);
                String model = rs.getString(4);
                boolean gender = rs.getBoolean(5);
                String fullName = rs.getString(6);
                String userName = rs.getString(7);
                String password = rs.getString(8);
                int status = rs.getInt(9);
                String note = rs.getString(10);
                String thumbnailUrl = rs.getString(11);
                User user = new User(settingId, phone, model, gender, fullName, userName, password, status, note, thumbnailUrl);
                user.setUserId(id);
                vector.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public int insert(User user) {
        int n = 0;
        String sqlInsert = "INSERT INTO `g5_krs_db`.`user`\n"
                + "(`setting_id`,\n"
                + "`phone`,\n"
                + "`email`,\n"
                + "`gender`,\n"
                + "`full_name`,\n"
                + "`user_name`,\n"
                + "`password`,\n"
                + "`status`,\n"
                + "`note`,\n"
                + "`thumbnail_url`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?,?,?,?);";
        System.out.println(sqlInsert);
        try {
            PreparedStatement statement = con.prepareStatement(sqlInsert);
            statement.setInt(1, user.getSettingId());
            statement.setString(2, user.getPhone());
            statement.setString(3, user.getEmail());
            statement.setBoolean(4, user.getGender());
            statement.setString(5, user.getFullName());
            statement.setString(6, user.getUserName());
            statement.setString(7, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            statement.setInt(8, user.getStatus());
            statement.setString(9, user.getNote());
            statement.setString(10, user.getThumbnailUrl());
            n = statement.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int update(User user) {
        int n = 0;
        String sqlUpdate = "UPDATE `g5_krs_db`.`user`\n"
                + "SET\n"
                + "`setting_id` = ?,\n"
                + "`phone` = ?,\n"
                + "`email` = ?,\n"
                + "`gender` = ?,\n"
                + "`full_name` = ?,\n"
                + "`user_name` = ?,\n"
                + "`password` = ?,\n"
                + "`status` =?,\n"
                + "`note` = ?,\n"
                + "`thumbnail_url` = ?\n"
                + "WHERE `user_id` = ?;";
        try {
            PreparedStatement statement = con.prepareStatement(sqlUpdate);
            statement.setInt(1, user.getSettingId());
            statement.setString(2, user.getPhone());
            statement.setString(3, user.getEmail());
            statement.setBoolean(4, user.getGender());
            statement.setString(5, user.getFullName());
            statement.setString(6, user.getUserName());
            statement.setString(7, user.getPassword());
            statement.setInt(8, user.getStatus());
            statement.setString(9, user.getNote());
            statement.setString(10, user.getThumbnailUrl());
            statement.setInt(11, user.getUserId());
            n = statement.executeUpdate();
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
        }
        return n;
    }

    public int updateImage(String image, int id) {
        int n = 0;
        String sqlUpdate = "UPDATE `g5_krs_db`.`user`\n"
                + "SET\n"
                + "`thumbnail_url` = ?\n"
                + "WHERE `user_id` = ?;";
        try {
            PreparedStatement statement = con.prepareStatement(sqlUpdate);
            statement.setString(1, image);
            statement.setInt(2, id);
            n = statement.executeUpdate();
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
        }
        return n;
    }

    public int updatePassword(String password, String email) {
        int n = 0;
        String sqlUpdate = "UPDATE `g5_krs_db`.`user`\n"
                + "SET\n"
                + "`password` = ?\n"
                + "WHERE `email` = ?;";
        try {
            PreparedStatement statement = con.prepareStatement(sqlUpdate);
            statement.setString(1, BCrypt.hashpw(password, BCrypt.gensalt()));
            statement.setString(2, email);
            n = statement.executeUpdate();
        } catch (SQLException ex) {
            //log
            ex.printStackTrace();
        }
        return n;
    }

    public Vector<String> getStatus() {
        Vector<String> v = new Vector<>();
        String sql = "select distinct status from user";
        try {
            Statement state = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int s = rs.getInt(1);
                v.add(convertStatus(s));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }

    public int getLastUserId() {
        int count = 0;
        String sql = "SELECT *\n"
                + "FROM user\n"
                + "ORDER BY user_id DESC\n"
                + "LIMIT 1;";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                count = resultSet.getInt(1); // Lấy giá trị đếm từ cột đầu tiên
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public String convertStatus(int n) {
        if (n == 0) {
            return "Verified";
        } else if (n == 1) {
            return "Unverified";
        } else {
            return "Banned";
        }
    }

    public boolean login(String user, String pass) {
        String sql = "select * from user where user_name = ? or email = ?";
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, user);
            pre.setString(2, user);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                if ((user.equals(rs.getString("user_name")) || user.equals(rs.getString("email")))
                        && BCrypt.checkpw(pass, rs.getString("password"))) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean validateRegisterAccount(String username, String email, String phone, HttpServletRequest request) {
        boolean check = true;
        if (!this.getAll("select * from user where user_name = '" + username + "'").isEmpty()) {
            request.setAttribute("duplicateUsername", "Duplicate username");
            check = false;
        }
        if (!this.getAll("select * from user where email = '" + email + "'").isEmpty()) {
            request.setAttribute("duplicateEmail", "Duplicate email");
            check = false;
        }
        if (!this.getAll("select * from user where phone = '" + phone + "'").isEmpty()) {
            request.setAttribute("duplicatePhone", "Duplicate phone number");
            check = false;
        }

        return check;
    }

    private void autoKit() {
        Vector<User> vector = this.getAll("select * from user");

        String sqlUpdate = "update user set password = ? where user_id = ?";

        for (User user : vector) {
            try {
                PreparedStatement statement = con.prepareStatement(sqlUpdate);
                statement.setString(1, BCrypt.hashpw("1234567890", BCrypt.gensalt()));
                statement.setInt(2, user.getUserId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                //log
                ex.printStackTrace();
            }
        }
    }

    public boolean isEmailExist(String email) {
        boolean isEmailExist = false;
        String sql = "select * from user where email = ?";
        PreparedStatement pre;
        try {
            pre = con.prepareStatement(sql);
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            while(rs.next()) {
                isEmailExist = true;
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isEmailExist;
    }
    
    public boolean isPhoneExist(String phone) {
        boolean isPhoneExist = false;
        String sql = "select * from user where phone = ?";
        PreparedStatement pre;
        try {
            pre = con.prepareStatement(sql);
            pre.setString(1, phone);
            ResultSet rs = pre.executeQuery();
            while(rs.next()) {
                isPhoneExist = true;
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isPhoneExist;
    }
    public static void main(String[] args) {
        DAOUser daoUser = new DAOUser();
        daoUser.autoKit();
        
//        Vector<User> vUser = dao.getAll("select * from user");
//        String name = vUser.get(0).getFullName();
//        int count = dao.getLastUserId();
//        System.out.println(count);
//          int n = dao.updateImage("hello", 12);
//          System.out.println(n);
    }
}
