package dao;

import entity.*;
import entity.Post_Category;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VKHOANG
 */
public class DAOPost_Category extends DBConnect{
    public void insertPostCategory(int postId, int settingId){
        String sql = "INSERT INTO post_category VALUES (?,?,null)";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, postId);
            ps.setInt(2, settingId);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPost_Category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updatePostCategory(int postId, int settingId){
        String sql = "UPDATE post_category SET setting_id = ? WHERE post_id = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, settingId);
            ps.setInt(2,postId);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPost_Category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deletePostCategory(int post_id){
        String sql = "DELETE FROM Post_Category WHERE post_id = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, post_id);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPost_Category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
