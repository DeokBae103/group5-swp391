/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.DAOSetting;
import dao.DAOUser;
import entity.Setting;
import entity.User;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;

/**
 *
 * @author DELL
 */
public class AdminUserServlet extends HttpServlet {

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
        DAOSetting daoSetting = new DAOSetting();
        String user_name = (String)session.getAttribute("name");
        
            
        
        //execute list all users including null state action
        String action = request.getParameter("action");
        System.out.println("action:" + action);
        if (action == null) {
            action = "listAll";
        }
        //============================List All=================================
        if (action.equals("listAll")) {
            //Initialize vector contain information class
            Vector<User> vUser = new Vector<>();
            Vector<Setting> vSetting = daoSetting.getAllSetting();
            //get request
            String changeFlag = request.getParameter("change");
            String sort = request.getParameter("sort");
            String status = request.getParameter("status");
            String role = (request.getParameter("role") == null) ? "" : request.getParameter("role");
            String search = request.getParameter("search");
            //sql plugin
            String sortSQL = "";
            String roleSQL = "";
            String statusSQL = "";
            //solve sortSQL         
            int change = (sort == null || sort.isEmpty()) ? 0 : Integer.parseInt(sort); // The change variable is used to change the ascending or descending sort
            if (changeFlag != null) {
                change++;
            }
            if (change == 1) { //In case the user wants to sort in descending order
                sortSQL = " order by  user_id desc";
            } else if (change == 2) {
                change = 0;
            }
            System.out.println(change);
            //solve statusSQL
            if (status != null && !status.isEmpty()) {
                statusSQL = " and status = " + revertStatus(status);
            }
            //solve statusSQL
            int setting_id = 0;
            for (Setting s : vSetting) {
                if (role.equals(s.getName())) {
                    setting_id = s.getSetting_id();
                }
            }
            if (role != null && !role.isEmpty()) {
                roleSQL = " and setting_id = " + setting_id;
            }
            //handles all the searching, filtering, and sorting
            System.out.println(roleSQL + statusSQL + sortSQL);
            if (search != null && !search.isEmpty() && !search.isBlank()) {
                vUser = daoUser.getAll("select * from user where full_name  like '%"+search+"%' or phone like '%"+search+"%' or email like '%"+search+"%' or user_name like '%"+search+"%'" + roleSQL + statusSQL + sortSQL);
            } else {
                if (roleSQL.isBlank() && statusSQL.isBlank() && sortSQL.isBlank()) {
                    vUser = daoUser.getAll("select * from user");
                } else {
                    if (!roleSQL.isBlank()) {
                        roleSQL = " where setting_id = " + setting_id;
                    }
                    if (!statusSQL.isBlank() && roleSQL.isBlank()) {
                        statusSQL = " where status = " + revertStatus(status);
                    }
                    vUser = daoUser.getAll("select * from user" + roleSQL + statusSQL + sortSQL);
                }
            }
            System.out.println(roleSQL + statusSQL + sortSQL);

            //get list by page
            int page, numberInpage = 6;
            int size = vUser.size();
            int num = (size % numberInpage == 0) ? (size / numberInpage) : ((size / numberInpage) + 1);
            String xpage = request.getParameter("page");
            if (xpage == null) {
                page = 1;
            } else {
                page = Integer.parseInt(xpage);
            }
            int start, end;
            start = (page - 1) * numberInpage;
            end = Math.min(page * numberInpage, size);
            Vector<User> v = getListByPage(vUser, start, end);

            request.setAttribute("size", size);
            request.setAttribute("userList", v);
            request.setAttribute("page", page);
            request.setAttribute("numPage", num);
            // get distinct role list
            Vector<String> roles = daoSetting.getOneColumn("select  distinct name from setting where type = \"User\";");

            // get status list
            Vector<String> statusList = daoUser.getStatus();

            //set request
            request.setAttribute("sort", change);
            request.setAttribute("search", search);
            request.setAttribute("role", role);
            request.setAttribute("status", status);
            request.setAttribute("setting", vSetting);
            request.setAttribute("roles", roles);
            request.setAttribute("statusList", statusList);
            //forward
            request.getRequestDispatcher("admin/userList.jsp").forward(request, response);
        }

        //==========================Page New User==============================
        if (action.equals("newUser")) {
            //get last user id
            int lastId = daoUser.getLastUserId();
            // get distinct role list
            Vector<String> roles = daoSetting.getOneColumn("select  distinct name from setting where type = \"User\";");
            //set request
            request.setAttribute("newId", ++lastId);
            request.setAttribute("roles", roles);
            //forward
            request.getRequestDispatcher("admin/addUser.jsp").forward(request, response);
        }
        //==========================Add New User===============================
        if (action.equals("addNewUser")) {
            Vector<Setting> vSetting = daoSetting.getAllSetting();
            //get request
            int userId = Integer.parseInt(request.getParameter("userId"));
            String fullName = request.getParameter("fullName");
            String userName = request.getParameter("userName");
            String role = request.getParameter("role");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String password = generatePassword();
            int setting_id = 0;
            for (Setting s : vSetting) {
                if (role.equals(s.getName())) {
                    setting_id = s.getSetting_id();
                }
            }
            boolean flag = true;//Create a flag to mark whether there is an error or not
            Vector<User> vUser = daoUser.getAll("select * from user");
            for (User u : vUser) {
                //In case of user name error
                if (u.getUserName().equals(userName)) {
                    request.setAttribute("userNameError", "User name was existed!");
                    flag = false;
                }
                //In case of email error
                if (u.getEmail().equals(email)) {
                    request.setAttribute("emailError", "Email was existed!");
                    flag = false;
                }
                //incase of phone erro
                if (u.getPhone() != null) {
                    if (u.getPhone().equals(phone)) {
                        request.setAttribute("phoneError", "Phone was existed!");
                        flag = false;
                    }
                }
            }
            //In case flag = false, it will return to the website for the user to re-do
            if (flag == false) {
                request.getRequestDispatcher("AdminUserServlet?action=newUser").forward(request, response);
            } //In case flag = true, it will return to the user list page
            else {
                User user = new User(setting_id, phone, email, gender.equals("1"), fullName, userName, password, 1, null, "avatar-trang-4.jpg");
                int n = daoUser.insert(user);
                if (n > 0) {
                    //send password for user
                    boolean bool = sendEmail(email, "[KRS]-IMPORTANT:Secure Your Account:Your password has been updated!!!",
                            "Here is you updated password:" + password + ". Please go to the website and change the password");
                    System.out.println(bool);
                    response.sendRedirect("AdminUserServlet");
                } else {
                    response.sendRedirect("AdminUserServlet?action=newUser");
                }
            }
        }
        //============================Update ==================================
        if (action.equals("Update")) {
            Vector<Setting> vSetting = daoSetting.getAllSetting();
            String submit = request.getParameter("submit");
            //before submit
            if (submit == null) {
                //get infomation about the selected user
                int userId = Integer.parseInt(request.getParameter("userId"));
                User user = daoUser.getAll("select * from user where user_id=" + userId).get(0);
                for (Setting s : vSetting) {
                    if (s.getSetting_id() == user.getSettingId()) {
                        request.setAttribute("userRole", s.getName());
                    }
                }
                // get distinct role list             
                Vector<String> roles = daoSetting.getOneColumn("select  distinct name from setting where type = \"User\";");
                // get status list
                Vector<String> statusList = daoUser.getStatus();
                //set request
                request.setAttribute("statusUser", daoUser.convertStatus(user.getStatus()));
                request.setAttribute("userInfo", user);
                request.setAttribute("roles", roles);
                request.setAttribute("img", user.getThumbnailUrl());
                request.setAttribute("statusList", statusList);
                //forward
                request.getRequestDispatcher("admin/userDetails.jsp").forward(request, response);
            } //after submit update
            else {
                //get request
                int userId = Integer.parseInt(request.getParameter("userId"));
                String fullName = request.getParameter("fullName");
                String userName = request.getParameter("userName");
                String role = request.getParameter("role");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                int gender = Integer.parseInt(request.getParameter("gender"));
                String note = request.getParameter("note");
                String status = request.getParameter("status");
                int setting_id = 0;
                for (Setting s : vSetting) {
                    if (role.equals(s.getName())) {
                        setting_id = s.getSetting_id();
                    }
                }
                boolean flag = true;//Create a flag to mark whether there is an error or not
                Vector<User> vUser = daoUser.getAll("select * from user where user_id !="+userId+" and phone='"+phone+"'");
                if(!vUser.isEmpty()){
                    flag = false;
                }               
                //In case flag = false, it will return to the website for the user to re-do
                if (flag == false) {
                    response.sendRedirect("/krs/AdminUserServlet?action=Update&userId=" + userId+"&err=phone");
                } //In case flag = true, it will return to the user list page
                else {
                    User old = daoUser.getAll("select * from user where user_id=" + userId).get(0);
                    System.out.println("user_id dadooo:"+userId);
                    // update
                    User UpdateUser = new User(setting_id, phone, email, gender == 1, fullName, userName, old.getPassword(), revertStatus(status), note, old.getThumbnailUrl());
                    UpdateUser.setUserId(userId);
                    daoUser.update(UpdateUser);
                    //forward
                     response.sendRedirect("/krs/AdminUserServlet");
                }
            }
        }
        //=========Update image===============
        if (action.equals("updateImage")) {
            System.out.println("Update image");
            int userId = Integer.parseInt(request.getParameter("userId"));
            try {
                String fileName = "";
                String folder = getServletContext().getRealPath("assets/images/imageUser");
                folder = folder.replace("build\\web", "web");// folder store images
                System.out.println("1233");
                System.out.println(folder);
                System.out.println(request.getContextPath());
                File file; // create file to upload
                int maxFileSize = 5000 * 1024;
                int maxMemSize = 5000 * 1024;
                String contentType = request.getContentType(); // get contentType
                System.out.println("contentType: " + contentType.indexOf(contentType));
                if (contentType.indexOf(contentType) >= 0) {
                    DiskFileItemFactory factory = DiskFileItemFactory.builder()
                            .setBufferSize(maxMemSize).
                            setPath(folder)
                            .get();

                    JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);
                    upload.setFileSizeMax(maxFileSize);

                    List<FileItem> files = upload.parseRequest(request);

                    for (FileItem fileItem : files) {
                        System.out.println("fileItem:" + fileItem.getName());
                        if (!fileItem.isFormField()) {
                            fileName = fileItem.getName();
                            System.out.println(fileName);
                            String path = folder + "\\" + fileName;
                            file = new File(path);
                            fileItem.write(file.toPath());
                            System.out.println(path);
                        }
                    }
                    int n = daoUser.updateImage(fileName, userId);
                    if (n > 0) {
                        System.out.println("Successfully");
                    } else {
                        System.out.println("Wrong");
                    }
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AdminUserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    request.getRequestDispatcher("AdminUserServlet?action=Update&userId=" + userId).forward(request, response);
                }
            } catch (IOException e) {
                request.getRequestDispatcher("AdminUserServlet?action=Update&userId=" + userId).forward(request, response);
            }
        }
        //=========Ban user===============
        if(action.equals("Ban")){
            String userId = request.getParameter("userId");
            User user = daoUser.getAll("select * from user where user_id = "+ userId).get(0);
            user.setStatus(2);
            daoUser.update(user);
            response.sendRedirect("AdminUserServlet");
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

    //convert text status to integer status
    private int revertStatus(String s) {
        if (s.equals("Verified")) {
            return 0;
        } else if (s.equals("Unverified")) {
            return 1;
        } else if (s.equals("Banned")) {
            return 2;
        } else {
            return -1;
        }
    }

    //generate random password
    private String generatePassword() {
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        String password = "";
        for (int i = 0; i < 10; i++) {
            password += CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
        }
        return password;
    }

    //send email
    private boolean sendEmail(String to, String title, String content) {
        final String from = "5b7bc0110d7864";
        final String password = "84a956a842cd0c";

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
            Logger.getLogger(AdminUserServlet.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    private Vector<User> getListByPage(Vector<User> vector, int start, int end) {
        Vector<User> vt = new Vector<>();
        for (int i = start; i < end; i++) {
            vt.add(vector.get(i));
        }
        return vt;
    }
}
