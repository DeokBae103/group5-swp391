/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.StudentCourse;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class DAOStudentCourse extends DBConnect {

    public Vector<StudentCourse> getAllCourseOfStudent(int id) {
        Vector<StudentCourse> vector = new Vector<>();
        String sql = "SELECT ucs.user_id, u.full_name, c.class_name, s.subject_name,s.subject_code, t.full_name as teacher_name\n"
                + "FROM user_class_subject ucs \n"
                + "join user u on u.user_id = ucs.user_id \n"
                + "join class_subject cs on (cs.class_id = ucs.class_id and cs.subject_id = ucs.subject_id)\n"
                + "join subject s on s.subject_id= cs.subject_id\n"
                + "join class c on c.class_id = cs.class_id\n"
                + "join class_manager cm on (cm.class_id = cs.class_id and cm.subject_id = cs.subject_id)\n"
                + "join user t on cm.teacher_id = t.user_id\n"
                + "where u.user_id='" + id + "' and ucs.status = 0;";
        PreparedStatement stm;
        try {
            stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt(1);
                String fullname = rs.getString(2);
                String className = rs.getString(3);
                String subjectName = rs.getString(4);
                String subjectCode = rs.getString(5);
                String teacher = rs.getString(6);
                vector.add(new StudentCourse(userId, fullname, className, subjectName, subjectCode, teacher));
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DAOStudentCourse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        return vector;
    }
    public static void main(String[] args) {
        DAOStudentCourse dao = new DAOStudentCourse();
        Vector<StudentCourse> vector = dao.getAllCourseOfStudent(20);
        System.out.println(vector.get(0).toString());
    }
}
