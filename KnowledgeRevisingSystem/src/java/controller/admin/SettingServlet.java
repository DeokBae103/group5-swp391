/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.DAOSetting;
import entity.Setting;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class SettingServlet extends HttpServlet {

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
        DAOSetting daoSetting = new DAOSetting();
        HttpSession session = request.getSession();
        String user_name = (String) session.getAttribute("name");

        if (action != null && !action.isEmpty()) {
            if (action.equals("listAllSetting")) {
                String search = request.getParameter("search");
                String type = request.getParameter("type");
                String status = request.getParameter("status");
                String order = request.getParameter("order");

                if (order != null && Character.isDigit(order.charAt(0))) {
                    order = " and `order` = " + request.getParameter("order");
                    request.setAttribute("order", request.getParameter("order"));
                } else if (order == null) {
                    order = " ORDER BY `order` ";
                    request.setAttribute("order", "ASC");
                } else {
                    order = " ORDER BY `order` " + order;
                    request.setAttribute("order", request.getParameter("order"));
                }
                if (search == null || search.isEmpty()) {
                    search = "";
                }
                if (status == null || status.isEmpty()) {
                    status = "All";
                }
                if (type == null || type.isEmpty()) {
                    type = "All";
                }
                int currentPage = 0;
                if (request.getParameter("currentPage") != null && !request.getParameter("currentPage").isEmpty()) {
                    currentPage = Integer.parseInt(request.getParameter("currentPage")) - 1;
                }

                String sql = null;
                String sqlForPageNumber = null;
                if (status.equals("All") && type.equals("All")) {
                    sql = "select * from Setting where name like '%" + search + "%'" + order + " LIMIT 3 OFFSET " + currentPage * 3;
                    sqlForPageNumber = "select * from Setting where name like '%" + search + "%'" + order;
                } else if (status.equals("All")) {
                    sql = "select * from Setting where name like '%" + search + "%' and type = '" + type + "'" + order + " LIMIT 3 OFFSET " + currentPage * 3;
                    sqlForPageNumber = "select * from Setting where name like '%" + search + "%' and type = '" + type + "'" + order;
                } else if (type.equals("All")) {
                    sql = "select * from Setting where name like '%" + search + "%' and status = " + status + order + " LIMIT 3 OFFSET " + currentPage * 3;
                    sqlForPageNumber = "select * from Setting where name like '%" + search + "%' and status = " + status + order;
                } else {
                    sql = "select * from Setting where name like '%" + search + "%' and status = " + status + " and type ='" + type + "'" + order + " LIMIT 3 OFFSET " + currentPage * 3;
                    sqlForPageNumber = "select * from Setting where name like '%" + search + "%' and status = " + status + " and type ='" + type + "'" + order;
                }
                request.setAttribute("search", search);

                request.setAttribute("type", type);
                request.setAttribute("status", status);
                request.setAttribute("orderList", daoSetting.getOneColumn("select distinct `order` from Setting"));
                request.setAttribute("typeList", daoSetting.getOneColumn("select distinct type from Setting"));
                request.setAttribute("settingList", daoSetting.getSettingDetails(sql));
                request.setAttribute("currentPage", currentPage + 1);
                request.setAttribute("pageNumber", daoSetting.getPageNumber(daoSetting.getSettingDetails(sqlForPageNumber).size()));
                request.setAttribute("size", daoSetting.getSettingDetails(sqlForPageNumber).size());
                request.getRequestDispatcher("admin/settingList.jsp").forward(request, response);
                return;

            }

            if (action.equals("edit")) {
                int setting_id = Integer.parseInt(request.getParameter("setting_id"));
                Setting s = daoSetting.getSettingDetails("select * from Setting where setting_id=" + setting_id).get(0);
                request.setAttribute("type", s.getType());
                    request.setAttribute("name", s.getName());
                    request.setAttribute("order", s.getOrder());
                    request.setAttribute("description", s.getDescription());
                    request.setAttribute("status", s.isStatus());
                    request.setAttribute("setting_id", setting_id);
                
                request.getRequestDispatcher("admin/settingDetails.jsp").forward(request, response);
                return;
            }

            if (action.equals("update")) {
                if(request.getParameter("reset")!=null&&!request.getParameter("reset").isEmpty()){
                    int setting_id = Integer.parseInt(request.getParameter("setting_id"));
                    Setting s= daoSetting.getSettingDetails("select * from Setting where setting_id =" + setting_id).firstElement();
                    request.setAttribute("type", s.getType());
                    request.setAttribute("name", s.getName());
                    request.setAttribute("order", s.getOrder());
                    request.setAttribute("description", s.getDescription());
                    request.setAttribute("status", s.isStatus());
                    request.setAttribute("setting_id", setting_id);
                    request.getRequestDispatcher("admin/settingDetails.jsp").forward(request, response);
                    return;
                }
                String editStatus = request.getParameter("editStatus");
                if (editStatus != null) {
                    int setting_id = Integer.parseInt(request.getParameter("setting_id"));
                    String type = request.getParameter("type");
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    boolean status = Boolean.valueOf(request.getParameter("status"));
                    int order = Integer.parseInt(request.getParameter("order"));
                    Setting settingDetails = new Setting(setting_id, name, type, description, status, order);
                    daoSetting.update(settingDetails);
                    response.sendRedirect("SettingServlet?action=listAllSetting");
                    return;
                }
                String type = request.getParameter("type");
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String status = request.getParameter("status");
                String order = request.getParameter("order");
                int setting_id = Integer.parseInt(request.getParameter("setting_id"));
                int flag = 0;
                if (request.getParameter("type") == null || request.getParameter("type").trim().isEmpty() || request.getParameter("type").matches("$[0-9a-zA-Z]{1,20}")) {
                    request.setAttribute("warnType", "Type is at least 1 character and 20 character maximum");
                    flag++;
                } 
                if (request.getParameter("name") == null || request.getParameter("name").trim().isEmpty() || request.getParameter("name").matches("$[0-9a-zA-Z]{1,20}")) {
                    request.setAttribute("warnName", "Name is at least 1 character and 20 character maximum");
                    flag++;
                } 
                if (request.getParameter("description").length()>255) {
                    request.setAttribute("warnDescription", "Description is 255 character maximum");
                    flag++;
                } 
                if (request.getParameter("order") == null || request.getParameter("order").trim().isEmpty() || request.getParameter("order").matches("$[0-9]{1,2}")) {
                    request.setAttribute("warnOrder", "Order is at least 1 and 2 digit maximum");
                    flag++;
                }
                if (request.getParameter("status") == null || request.getParameter("status").trim().isEmpty()) {
                    request.setAttribute("warnStatus", "Status must be checked");
                    flag++;
                }

                if (flag != 0) {                    
                    request.setAttribute("type", type);
                    request.setAttribute("name", name);
                    request.setAttribute("order", order);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);
                    request.setAttribute("setting_id", setting_id);
                    request.getRequestDispatcher("admin/settingDetails.jsp").forward(request, response);
                    return;
                }

                if (daoSetting.checkExisted(type.trim(), name.trim(), setting_id) >= 1) {
                    request.setAttribute("type", type);
                    request.setAttribute("name", name);
                    request.setAttribute("order", order);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);
                    request.setAttribute("setting_id", setting_id);
                    request.setAttribute("warn", 0);
                    request.getRequestDispatcher("admin/settingDetails.jsp").forward(request, response);
                    return;
                } else {
                    Setting settingDetails = new Setting(setting_id, name, type, description, Boolean.valueOf(status), Integer.valueOf(order));
                    daoSetting.update(settingDetails);
                    request.setAttribute("type", type);
                    request.setAttribute("name", name);
                    request.setAttribute("order", order);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);
                    request.setAttribute("setting_id", setting_id);
                    request.setAttribute("warn", 1);
                    request.getRequestDispatcher("admin/settingDetails.jsp").forward(request, response);
                    return;
                }

            }

            if (action.equals("insert")) {
                String type = request.getParameter("type");
                String name = request.getParameter("settingName");
                String description = request.getParameter("description");
                String status = request.getParameter("status");
                String order = request.getParameter("order");
               
                int flag = 0;
                if (request.getParameter("type") == null || request.getParameter("type").trim().isEmpty() || request.getParameter("type").matches("$[0-9a-zA-Z]{1,20}")) {
                    request.setAttribute("warnType", "Type is at least 1 character and 20 character maximum");
                    flag++;
                } 
                if (request.getParameter("settingName") == null || request.getParameter("settingName").trim().isEmpty() || request.getParameter("settingName").matches("$[0-9a-zA-Z]{1,20}")) {
                    request.setAttribute("warnName", "Name is at least 1 character and 20 character maximum");
                    flag++;
                } 
                if (request.getParameter("description").length()>255) {
                    request.setAttribute("warnDescription", "Description is 255 character maximum");
                    flag++;
                } 
                if (request.getParameter("order") == null || request.getParameter("order").trim().isEmpty() || !request.getParameter("order").matches("[0-9]{1,2}") || Integer.valueOf(order)<=0) {
                    request.setAttribute("warnOrder", "Order must be between 0 and 99");
                    flag++;
                }
                if (request.getParameter("status") == null || request.getParameter("status").trim().isEmpty()) {
                    request.setAttribute("warnStatus", "Status must be checked");
                    flag++;
                }

                if (flag != 0) {                    
                    request.setAttribute("type", type);
                    request.setAttribute("settingName", name);
                    request.setAttribute("order", order);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);
                    request.getRequestDispatcher("admin/addSetting.jsp").forward(request, response);
                    return;
                }

                if (daoSetting.checkExistedForInsert(type.trim(), name.trim()) >= 1) {
                    request.setAttribute("warn", 0);
                     request.setAttribute("type", type);
                    request.setAttribute("settingName", name);
                    request.setAttribute("order", order);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);  
                    request.getRequestDispatcher("admin/addSetting.jsp").forward(request, response);
                    return;
                } else if (daoSetting.checkExistedForInsert(type, name) == 0) {
                    daoSetting.insert(name, type, description, Boolean.valueOf(status), Integer.valueOf(order));
                    request.setAttribute("type", type);
                    request.setAttribute("settingName", name);
                    request.setAttribute("order", order);
                    request.setAttribute("description", description);
                    request.setAttribute("status", status);  
                    request.setAttribute("warn", 1);
                    request.getRequestDispatcher("admin/addSetting.jsp").forward(request, response);
                    return;
                }
            }
        } else {
            response.sendRedirect("SettingServlet?action=listAllSetting");
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
