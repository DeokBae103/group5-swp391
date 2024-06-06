package dao;

import entity.*;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOPost extends DBConnect {

    public Vector<Post> getAllPosts() {
        Vector<Post> vector = new Vector<>();
        String sql = "select p.*, s.name as setting_name from Post p\n"
                + "join post_category pc on p.post_id = pc.post_id\n"
                + "join setting s on pc.setting_id = s.setting_id";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                vector.add(new Post(rs.getInt("post_id"), rs.getString("title"), rs.getString("summary"), rs.getString("thumbnail_url"), rs.getString("content"), rs.getBoolean("status"), rs.getString("setting_name")));
            }
            return vector;
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Vector<Post> getPostsByKeyword(String query, String settingName) {
        Vector<Post> vector = new Vector<>();
        String sql = "select p.*, s.name as setting_name from Post p\n"
                + "join post_category pc on p.post_id = pc.post_id\n"
                + "join setting s on pc.setting_id = s.setting_id where\n"
                + "p.title like ? and s.name like ?";
        settingName = (settingName.equals("all") ? "" : settingName);
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, "%" + query + "%");
            stm.setString(2, "%" + settingName + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                vector.add(new Post(rs.getInt("post_id"), rs.getString("title"), rs.getString("summary"), rs.getString("thumbnail_url"), rs.getString("content"), rs.getBoolean("status"), rs.getString("setting_name")));
            }
            return vector;
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Post getPostByPostId(int postId) {
        String sql = "select p.*, s.name as setting_name from Post p\n"
                + "join post_category pc on p.post_id = pc.post_id\n"
                + "join setting s on pc.setting_id = s.setting_id\n"
                + "where p.post_id = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, postId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return new Post(rs.getInt("post_id"), rs.getString("title"), rs.getString("summary"), rs.getString("thumbnail_url"), rs.getString("content"), rs.getBoolean("status"),rs.getString("setting_name"));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updatePosts(Post p) {
        String sql = "UPDATE Post SET title = ?, summary = ?, thumbnail_url = ?, content = ?, status = ? where post_id = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, p.getTitle());
            stm.setString(2, p.getSummary());
            stm.setString(3, p.getThumbnail_url());
            stm.setString(4, p.getContent());
            stm.setBoolean(5, p.getStatus());
            stm.setInt(6, p.getPost_id());
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addPost(Post p) {
        String sql = "INSERT INTO post (`title`,`summary`,`thumbnail_url`,`content`,`status`) VALUES (?,?,?,?,?)";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, p.getTitle());
            stm.setString(2, p.getSummary());
            stm.setString(3, p.getThumbnail_url());
            stm.setString(4, p.getContent());
            stm.setBoolean(5, p.getStatus());
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletePosts(int postId) {
        DAOPost_Category pcdao = new DAOPost_Category();
        pcdao.deletePostCategory(postId);
        String sql = "DELETE FROM Post WHERE post_id = ?";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, postId);
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getLastPostID() {
        String sql = "SELECT post_id FROM post ORDER BY post_id DESC LIMIT 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
