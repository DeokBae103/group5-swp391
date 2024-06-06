/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dao.DAOUser;
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
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.core.FileItemFactory;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;
import org.mindrot.jbcrypt.BCrypt;
import utils.CheckRegex;

/**
 *
 * @author ADMIN
 */
public class UserServlet extends HttpServlet {

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
        HttpSession session = request.getSession(true);

        //initialize DAO
        DAOUser daoUser = new DAOUser();

        //Get user name from session
        String name = (String) session.getAttribute("name");
        User user = new User();

        //if logged ( session has name )
        if (name != null) {
            Vector<User> vectorUser = daoUser.getAll("select * from user where user_name = '" + name + "' or email = '" + name + "'");
            user = vectorUser.get(0);
        }

        //execute list all users including null state action
        String action = request.getParameter("action");
        if (action == null) {
            action = "listAll";
        }

        if (action.equals("login")) {
            String signIn = request.getParameter("signin");

            if (signIn == null) {
                request.getRequestDispatcher("home/login.jsp").forward(request, response);
            } else {
                name = request.getParameter("name");
                String pass = request.getParameter("password");

                if (daoUser.login(name, pass)) {
                    Vector<User> vectorUser = daoUser.getAll("select * from user where user_name = '" + name + "' or email = '" + name + "'");
                    user = vectorUser.get(0);
                    session.setAttribute("User", user);
                    session.setAttribute("name", name);
                    response.sendRedirect("UserServlet?action=displayFollowByRole");
                    return;
                } else {
                    request.setAttribute("namevalue", name);
                    request.setAttribute("passwordvalue", pass);
                    request.setAttribute("error", "Username or password is incorrected!");
                    request.getRequestDispatcher("home/login.jsp").forward(request, response);
                }
            }
        } else if (action.equals("forgotpassword")) {
            String send = request.getParameter("send");

            if (send == null) {
                request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            } else {
                String email = request.getParameter("email");
                Vector<User> result = daoUser.getAll("select * from user where email = '" + email + "'");

                if (result.isEmpty()) {
                    request.setAttribute("emailvalue", email);
                    request.setAttribute("error", "Invalid email");
                    request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
                } else {
                    String password = generatePassword();
                    daoUser.updatePassword(password, email);
                    sendEmail(email, "[KRS]-IMPORTANT:Secure Your Account:Your password has been updated!!!",
                            "Here is you updated password:" + password + ". Please go to the website and change the password");
                    request.setAttribute("recentEmail", email);
                    request.getRequestDispatcher("home/changePassword.jsp").forward(request, response);
                }
            }
        } else if (action.equals("changepassword")) {
            String send = request.getParameter("send");

            if (send == null) {
                request.getRequestDispatcher("home/changePassword.jsp").forward(request, response);
            } else {
                String oldPassword = request.getParameter("oldpassword");
                String newPassword = request.getParameter("newpassword");
                String email = request.getParameter("email");

                if (email.isEmpty()) {
                    email = ((User) session.getAttribute("User")).getEmail();
                }
                Vector<User> vector = daoUser.getAll("select * from `user` where email = '" + email + "'");
                if (BCrypt.checkpw(oldPassword, vector.get(0).getPassword())) {
                    daoUser.updatePassword(newPassword, email);
                    request.getRequestDispatcher("home/login.jsp").forward(request, response);
                } else {
                    request.setAttribute("passwordvalue", newPassword);
                    request.setAttribute("oldpassword", oldPassword);
                    request.setAttribute("recentEmail", email);
                    request.setAttribute("error", "Invalid old password");
                    request.getRequestDispatcher("home/changePassword.jsp").forward(request, response);
                }
            }
        } else if (action.equals("register")) {
            String register = request.getParameter("register");

            if (register == null) {
                request.getRequestDispatcher("home/register.jsp").forward(request, response);
            } else {
                String fullname = request.getParameter("fullname");
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String password = request.getParameter("password");
                boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
                int role = Integer.parseInt(request.getParameter("role"));

                if (!daoUser.validateRegisterAccount(username, email, phone, request)) {
                    request.setAttribute("fullname", fullname);
                    request.setAttribute("username", username);
                    request.setAttribute("email", email);
                    request.setAttribute("phone", phone);
                    request.setAttribute("gender", gender);
                    request.setAttribute("password", password);
                    request.setAttribute("passwordConfirm", password);
                    request.getRequestDispatcher("home/register.jsp").forward(request, response);
                } else {
                    User registerAccount = new User(role, phone, email, gender, fullname, username, password, 1, "", "");
                    daoUser.insert(registerAccount);
                    request.getRequestDispatcher("home/login.jsp").forward(request, response);
                }
            }
        } else if (action.equals("updateProfile")) {
            //Update profile page
            String submit = request.getParameter("submit");
            //Submit != null
            if (submit != null && submit.equals("Save changes")) {
                String fullName = request.getParameter("fullName");
                boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String note = request.getParameter("note");
                String error = "";
                request.setAttribute("fullName", fullName);
                request.setAttribute("gender", gender);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("note", note);
                //Pattern check
                if (fullName.isBlank() || email.isBlank() || phone.isBlank()) {
                    error = "Please fill out all required fields";
                    request.setAttribute("error", error);
                    request.setAttribute("isSuccess", "false");
                    RequestDispatcher dis = request.getRequestDispatcher("/home/userProfile.jsp");
                    dis.forward(request, response);
                    //email existing
                } else if (daoUser.isEmailExist(email) && !email.equals(user.getEmail())) {
                    String errorEmail = "Email existing";
                    request.setAttribute("errorEmail", errorEmail);
                    request.setAttribute("isErrorEmail", "true");
                    RequestDispatcher dis = request.getRequestDispatcher("home/userProfile.jsp");
                    dis.forward(request, response);
                    //phone existing
                } else if (daoUser.isPhoneExist(phone) && !phone.equals(user.getPhone())) {
                    String errorPhone = "Phone existing";
                    request.setAttribute("errorPhone", errorPhone);
                    request.setAttribute("isErrorPhone", "true");
                    RequestDispatcher dis = request.getRequestDispatcher("home/userProfile.jsp");
                    dis.forward(request, response);
                    //pattern email
                } else if (!fullName.matches(CheckRegex.regexFullName)) {
                    String errorFullName = "Please type full name max 20 characters!";
                    request.setAttribute("errorFullName", errorFullName);
                    request.setAttribute("isErrorFullName", "true");
                    RequestDispatcher dis = request.getRequestDispatcher("home/userProfile.jsp");
                    dis.forward(request, response);
                    //pattern email
                } else if (!email.matches(CheckRegex.regexEmail) || email.length() >= 30) {
                    String errorEmail = "Please type email max 30 characters with format [example@...]";
                    request.setAttribute("errorEmail", errorEmail);
                    request.setAttribute("isErrorEmail", "true");
                    RequestDispatcher dis = request.getRequestDispatcher("home/userProfile.jsp");
                    dis.forward(request, response);
                    //pattern phone
                } else if (!phone.matches(CheckRegex.regexPhone)) {
                    String errorPhone = "Please type phone max 10 numbers!";
                    request.setAttribute("errorPhone", errorPhone);
                    request.setAttribute("isErrorPhone", "true");
                    RequestDispatcher dis = request.getRequestDispatcher("home/userProfile.jsp");
                    dis.forward(request, response);
                    //pattern description
                } else if (!note.matches(CheckRegex.regexDescription)) {
                    String errorNote = "Please type bio max 255 numbers!";
                    request.setAttribute("errorNote", errorNote);
                    request.setAttribute("isErrorNote", "true");
                    RequestDispatcher dis = request.getRequestDispatcher("home/userProfile.jsp");
                    dis.forward(request, response);
                    //Everything pattern OK
                } else {
                    user.setFullName(fullName);
                    user.setEmail(email);
                    user.setGender(gender);
                    user.setNote(note);
                    user.setPhone(phone);
                    int n = daoUser.update(user);
                    request.setAttribute("isSuccess", "true");
                    session.setAttribute("User", user);
                    RequestDispatcher dis = request.getRequestDispatcher("home/userProfile.jsp");
                    dis.forward(request, response);
                }
            } else {
             
                response.sendRedirect("home/userProfile.jsp");
            }
            //Action logout
        } else if (action.equals("logout")) {

            session.removeAttribute("name");
            response.sendRedirect("home/homepage.jsp");
        } else if (action.equals("listAll")) {
            response.sendRedirect("home/homepage.jsp");
            //Action update avatar
        } else if (action.equals("updateAvatar")) {
            String folder = getServletContext().getRealPath("/assets/images/imageUser");
            folder = folder.replace("build\\web", "web");
            String fileName = "";
            File file; // create file to upload
            int maxFileSize = 5000 * 1024;
            int maxMemSize = 5000 * 1024;
            //Context path: \krs
            //ContentType
            String contentType = request.getContentType();
            if (contentType.contains("multipart/form-data")) {
                // Set upload parameters
                DiskFileItemFactory factory = DiskFileItemFactory.builder()
                        .setBufferSize(maxMemSize).
                        setPath(folder)
                        .get();
                // Create a new file upload handler
                JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);
                upload.setFileSizeMax(maxFileSize);
                List<FileItem> files = upload.parseRequest(request);
                for (FileItem fileItem : files) {
                    System.out.println("fileItem:" + fileItem.getName());
                    if (!fileItem.isFormField()) {
                        fileName = fileItem.getName();

                        String path = folder + "\\" + fileName;
                        file = new File(path);
                        fileItem.write(file.toPath());

                    }
                }
                int n = daoUser.updateImage(fileName, user.getUserId());
                user.setThumbnailUrl(fileName);
                session.setAttribute("User", user);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                request.getRequestDispatcher("UserServlet?action=updateProfile").forward(request, response);

            }
        }

        if (action.equals("displayFollowByRole")) {
            request.setAttribute("user", user);
            //role admin, manager
            switch (user.getSettingId()) {
                case 1:
                case 2:
                    response.sendRedirect("/krs/admin/dashboard.jsp");
                    return;
                //role teacher
                case 3: {
                    RequestDispatcher dis = request.getRequestDispatcher("TeacherServlet?action=listCourse");
                    dis.forward(request, response);
                    return;

                }
                //role student
                case 4: {
                    RequestDispatcher dis = request.getRequestDispatcher("StudentServlet?action=listCourse");
                    dis.forward(request, response);
                    break;
                }

            }
        }
    }

    private String generatePassword() {
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        String password = "";
        for (int i = 0; i < 10; i++) {
            password += CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
        }
        return password;
    }

    private void sendEmail(String to, String title, String content) {
        final String from = "ducminhns2004@gmail.com";
        final String password = "";

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
