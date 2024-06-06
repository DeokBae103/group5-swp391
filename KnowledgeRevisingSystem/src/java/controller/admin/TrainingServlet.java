/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.DAOSetting;
import dao.DAOSubject;
import dao.DAOSubject;
import entity.Subject;
import entity.Subject;
import entity.Subject;
import entity.Subject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;

/**
 *
 * @author DELL
 */
public class TrainingServlet extends HttpServlet {

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
        String action = request.getParameter("action");
        DAOSubject daoSubject = new DAOSubject();
        DAOSetting daoSetting = new DAOSetting();
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("name");
        if (action != null && !action.isEmpty()) {
            if (action.equals("listAllSubject")) {
                String search = request.getParameter("search");
                String status = request.getParameter("status");
                if (search == null || search.isEmpty()) {
                    search = "";
                }
                if (status == null || status.isEmpty()) {
                    status = "All";
                }

                int currentPage = 0;
                if (request.getParameter("currentPage") != null && !request.getParameter("currentPage").isEmpty()) {
                    currentPage = Integer.parseInt(request.getParameter("currentPage")) - 1;
                }

                String sql = null;
                String sqlForPageNumber = null;
                if (status.equals("All")) {
                    sql = "select * from Subject where subject_name like '%" + search + "%' LIMIT 3 OFFSET " + currentPage * 3;
                    sqlForPageNumber = "select * from Subject where subject_name like '%" + search + "%'";
                } else {
                    sql = "select * from Subject where subject_name like '%" + search + "%' and status = " + status + " LIMIT 3 OFFSET " + currentPage * 3;
                    sqlForPageNumber = "select * from Subject where subject_name like '%" + search + "%' and status = " + status;
                }
                request.setAttribute("search", search);
                request.setAttribute("status", status);
                request.setAttribute("subjectList", daoSubject.getSubjectDetails(sql));
                request.setAttribute("currentPage", currentPage + 1);
                request.setAttribute("pageNumber", daoSubject.getPageNumber(daoSubject.getSubjectDetails(sqlForPageNumber).size()));
                request.setAttribute("size", daoSubject.getSubjectDetails(sqlForPageNumber).size());
                request.getRequestDispatcher("admin/subjectList.jsp").forward(request, response);
                return;
            }
            if (action.equals("edit")) {
                int subject_id = Integer.parseInt(request.getParameter("subject_id"));
                Subject s= daoSubject.getSubjectDetails("select * from Subject where subject_id=" + subject_id).get(0);
                request.setAttribute("subject_name", s.getSubject_name());
                    request.setAttribute("subject_code", s.getSubject_code());
                    request.setAttribute("description", s.getDescription());
                    request.setAttribute("status", s.isStatus());
                    request.setAttribute("subject_id", s.getSubject_id());
                request.getRequestDispatcher("admin/subjectDetails.jsp").forward(request, response);
                return;
            }
            if (action.equals("update")) {
                int subject_id = Integer.parseInt(request.getParameter("subject_id"));
                String subject_name = request.getParameter("subject_name");
                String subject_code = request.getParameter("subject_code");
                String description = request.getParameter("description");
                String status = request.getParameter("status");
                String editStatus = request.getParameter("editStatus");
                if (editStatus != null) {
                    Subject settingDetails = new Subject(subject_id, subject_name, subject_code, description, Boolean.valueOf(status));
                    daoSubject.update(settingDetails);
                    response.sendRedirect("TrainingServlet?action=listAllSubject");
                    return;
                }
                if (request.getParameter("reset") != null && !request.getParameter("reset").isEmpty()) {
                    Subject s = daoSubject.getSubjectDetails("select * from Subject where subject_id =" + subject_id).firstElement();
                    request.setAttribute("subject_name", s.getSubject_name());
                    request.setAttribute("subject_code", s.getSubject_code());
                    request.setAttribute("description", s.getDescription());
                    request.setAttribute("status", s.isStatus());
                    request.setAttribute("subject_id", s.getSubject_id());
                    request.getRequestDispatcher("admin/subjectDetails.jsp").forward(request, response);
                    return;
                }

                int flag = 0;
                if (request.getParameter("subject_name") == null || request.getParameter("subject_name").trim().isEmpty() || request.getParameter("subject_name").matches("$[0-9a-zA-Z]{1,50}")) {
                    request.setAttribute("warnName", "Subject name is at least 1 character and 50 character maximum");
                    flag++;
                }
                if (request.getParameter("subject_code") == null || request.getParameter("subject_code").trim().isEmpty() || request.getParameter("subject_code").matches("$[0-9a-zA-Z]{1,20}")) {
                    request.setAttribute("warnCode", "Subject code is at least 1 character and 10 character maximum");
                    flag++;
                }
                if (request.getParameter("description").length() > 255) {
                    request.setAttribute("warnDescription", "Description is 255 character maximum");
                    flag++;
                }

                if (request.getParameter("status") == null || request.getParameter("status").trim().isEmpty()) {
                    request.setAttribute("warnStatus", "Status must be checked");
                    flag++;
                }

                if (flag != 0) {
                    request.setAttribute("subject_name", subject_name);
                    request.setAttribute("subject_code", subject_code);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);
                    request.setAttribute("subject_id", subject_id);
                    request.getRequestDispatcher("admin/subjectDetails.jsp").forward(request, response);
                    return;
                }

                if (daoSubject.checkExistedForUpdate(subject_name, subject_code, subject_id) >= 1) {
                    request.setAttribute("subject_name", subject_name);
                    request.setAttribute("subject_code", subject_code);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);
                    request.setAttribute("subject_id", subject_id);
                    request.setAttribute("warn", 0);
                    request.getRequestDispatcher("admin/subjectDetails.jsp").forward(request, response);
                } else {
                    Subject subjectDetails = new Subject(subject_id, subject_name, subject_code, description, Boolean.valueOf(status));
                    request.setAttribute("subject_name", subject_name);
                    request.setAttribute("subject_code", subject_code);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);
                    request.setAttribute("subject_id", subject_id);
                    daoSubject.update(subjectDetails);
                    request.setAttribute("warn", 1);
                    request.getRequestDispatcher("admin/subjectDetails.jsp").forward(request, response);
                    return;
                }
            }
            if (action.equals("insert")) {
                String subject_name = request.getParameter("subject_name");
                String subject_code = request.getParameter("subject_code");
                String description = request.getParameter("description");
                String status = request.getParameter("status");

                int flag = 0;
                if (request.getParameter("subject_name") == null || request.getParameter("subject_name").trim().isEmpty() || request.getParameter("subject_name").matches("$[0-9a-zA-Z]{1,50}")) {
                    request.setAttribute("warnName", "Subject name is at least 1 character and 50 character maximum");
                    flag++;
                }
                if (request.getParameter("subject_code") == null || request.getParameter("subject_code").trim().isEmpty() || request.getParameter("subject_code").matches("$[0-9a-zA-Z]{1,20}")) {
                    request.setAttribute("warnCode", "Subject code is at least 1 character and 10 character maximum");
                    flag++;
                }
                if (request.getParameter("description").length() > 255) {
                    request.setAttribute("warnDescription", "Description is 255 character maximum");
                    flag++;
                }

                if (request.getParameter("status") == null || request.getParameter("status").trim().isEmpty()) {
                    request.setAttribute("warnStatus", "Status must be checked");
                    flag++;
                }

                if (flag != 0) {
                    request.setAttribute("subject_name", subject_name);
                    request.setAttribute("subject_code", subject_code);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("admin/addSubject.jsp").forward(request, response);
                    return;
                }
                if (daoSubject.checkExisted(subject_name, subject_code) >= 1) {
                    request.setAttribute("subject_name", subject_name);
                    request.setAttribute("subject_code", subject_code);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);
                    request.setAttribute("warn", 0);
                    request.getRequestDispatcher("admin/addSubject.jsp").forward(request, response);
                } else {
                    daoSubject.insert(subject_name, subject_code, description, Boolean.valueOf(status));
                    request.setAttribute("subject_name", subject_name);
                    request.setAttribute("subject_code", subject_code);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);
                    request.setAttribute("warn", 1);
                    request.getRequestDispatcher("admin/addSubject.jsp").forward(request, response);
                    return;
                }
            }
        } else {
            response.sendRedirect("TrainingServlet?action=listAllSubject");
            return;
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
