/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.TeacherCourse;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class DAOTeacherCourse extends DBConnect {

    public Vector<TeacherCourse> getAllCourseOfTeacher(int id) {
        Vector<TeacherCourse> vector = new Vector<>();
        String sql = "SELECT cm.teacher_id, c.class_name, s.subject_name, s.subject_code, s.subject_id, c.class_id FROM class_manager cm\n"
                + "join class_subject cs on (cm.class_id = cs.class_id and cm.subject_id = cs.subject_id)\n"
                + "join class c on cs.class_id = c.class_id\n"
                + "join subject s on s.subject_id = cs.subject_id\n"
                + "where teacher_id=?";
        PreparedStatement stm;
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int teacherId = rs.getInt(1);
                String className = rs.getString(2);
                String subjectName = rs.getString(3);
                String subjectCode = rs.getString(4);
                int subjectId = rs.getInt(5);
                int classId = rs.getInt(6);
                vector.add(new TeacherCourse(teacherId, className, subjectName, subjectCode, subjectId, classId, 0));
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DAOStudentCourse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        return vector;
    }

    public int getTotalStudentInClass(int subjectId, int classId, int teacherId) {
        int number = 0;
        String sql = "SELECT count(*) as total FROM class_manager cm\n"
                + "join class_subject cs on (cm.class_id = cs.class_id and cm.subject_id = cs.subject_id)\n"
                + "join class c on cs.class_id = c.class_id\n"
                + "join subject s on s.subject_id = cs.subject_id\n"
                + "join user_class_subject ucs on (ucs.class_id = cs.class_id and ucs.subject_id = cs.subject_id)\n"
                + "where cm.teacher_id = ? and cs.subject_id = ? and cs.class_id = ?";
        PreparedStatement stm;
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, teacherId);
            stm.setInt(2, subjectId);
            stm.setInt(3, classId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                number = rs.getInt("total");
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DAOStudentCourse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        return number;
    }
}
