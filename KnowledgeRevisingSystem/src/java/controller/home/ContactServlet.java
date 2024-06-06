/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dao.DAOContact;
import dao.DAOUser;
import entity.Contact;
import entity.PatternRegex;
import entity.User;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Properties;
import java.util.Vector;
import utils.CheckRegex;

/**
 *
 * @author ADMIN
 */
public class ContactServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DAOContact daoContact = new DAOContact();
        DAOUser daoUser = new DAOUser();
        Vector<User> vectorAdmin = daoUser.getAll("select * from user where setting_id = 1");
        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);
        request.setAttribute("vectorAdmin", vectorAdmin);
        if (action == null) {
            action = "createContactMessage";
        }

        if (action.equals("createContactMessage")) {
            String submit = request.getParameter("submit");

            //List form
            if (submit == null) {
                String isSuccess = request.getParameter("isSuccess");
                request.setAttribute("isSuccess", isSuccess);

                RequestDispatcher dis = request.getRequestDispatcher("home/contactAdmin.jsp");
                dis.forward(request, response);
                //If is user
                //submit form
            } else {
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String subject = request.getParameter("subject");
                String message = request.getParameter("message");
                String error = "";
                String errorFullName = "";
                String errorEmail = "";
                String errorPhone = "";
                String errorSubject = "";
                String errorMessage = "";
                String isSuccess = "true";
                boolean isError = false;
                String adminId = request.getParameter("adminId");
                request.setAttribute("fullName", fullName);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("subject", subject);
                request.setAttribute("message", message);
                request.setAttribute("adminId", adminId);

                //Pattern check
                if (fullName.isBlank() || email.isBlank() || subject.isBlank() || message.isBlank() || !fullName.matches(CheckRegex.regexFullName)
                        || (!email.matches(CheckRegex.regexEmail) || email.length() >= 30) || !phone.matches(CheckRegex.regexPhone)
                        || !subject.matches(CheckRegex.regexSubject) || !message.matches(CheckRegex.regexMessage)) {
                    isError = true;
                    isSuccess = "false";
                }
                if (fullName.isBlank()) {
                    errorFullName = "Please fill out Full name fields";
                }
                if (email.isBlank()) {
                    errorEmail = "Please fill out email fields";
                }
                if (subject.isBlank()) {
                    errorSubject = "Please fill out subject fields";
                }
                if (message.isBlank()) {
                    errorMessage = "Please fill out message fields";
                }
                if (!fullName.matches(CheckRegex.regexFullName)) {
                    errorFullName = "Please type full name max 20 characters!";
                }
                if (!email.matches(CheckRegex.regexEmail) || email.length() >= 30) {
                    errorEmail = "Please type email max 30 characters with format [example@...]";
                }
                if (!phone.matches(CheckRegex.regexPhone)) {
                    errorPhone = "Please type phone max 10 numbers!";
                }
                if (!subject.matches(CheckRegex.regexSubject)) {
                    errorSubject = "Please type subject max 30 characters!";                   
                }
                if (!message.matches(CheckRegex.regexMessage)) {
                    errorMessage = "Please type message max 255 characters!";                
                }
                
                request.setAttribute("isSuccess", isSuccess);
                //auto incremental ID
                int n = daoContact.createContact(new Contact(0, fullName, email, phone, message, 8, false, subject, Integer.parseInt(adminId)));
//                    String content = String.format("FullN%s", fullName);
                response.sendRedirect("ContactServlet?isSuccess=true");

            }
        }

    }

    private void sendEmailContact(String to, String title, String content) {
        final String from = "d025904c39d8bb";
        final String password = "9d36bdcd0ded1d";

        Properties pros = new Properties();
        pros.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        pros.put("mail.smtp.port", "2525");
        pros.put("mail.smtp.auth", true);
        pros.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }

        };

        //Phien lam viec
        Session session = Session.getInstance(pros, auth);

        // Gui email
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.addHeader("Context-type", "text/HTML;charset=UTF-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            msg.setSubject(title);
            msg.setText(content);

            Transport.send(msg);
        } catch (MessagingException ex) {

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
