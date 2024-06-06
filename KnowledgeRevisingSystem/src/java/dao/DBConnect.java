/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Contact;
import entity.StudentCourse;
import entity.Subject;
import entity.TeacherCourse;
import entity.User;
import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author DELL
 */
public class DBConnect {

    protected Connection con;

    public DBConnect() {
        String user = "root";
        String pass = "123456";
        String url = "jdbc:mysql://localhost:3306/G5_KRS_DB";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws SQLException, NoSuchMethodException, InstantiationException, InvocationTargetException, IllegalAccessException {
//        DAO dao = new DAO();
//        DAOSubject daoSubject = new DAOSubject();
//        Vector<Subject> list = dao.getAll("Subject", Subject.class);
//        System.out.println(list.get(0).toString());
//        DAOContact daoContact = new DAOContact();
//          DAOTeacherCourse dao = new DAOTeacherCourse();
//          int n = dao.getTotalStudentInClass(1, 1, 7);
//          System.out.println(n);
        DAOTeacherCourse dao = new DAOTeacherCourse();
        Vector<TeacherCourse> vector = dao.getAllCourseOfTeacher(7);
        System.out.println(vector.size());
//        daoContact.createContact(new Contact(0,"Duc", "dbm6@gail", "33342", "Hello ", 1, true, "MAE"));
//        Vector<Contact> vectorContact = daoContact.getAll("Contact", Contact.class);
//        Vector<User> vector = daoContact.getUserDetail("select * from user");
        //Subject subjectDetails = daoSubject.getDetails("Subject", Subject.class, 1).get(0);
        //dao.create("setting", new Subject(10, "unknown", "User", "No info", true));
    }

}
