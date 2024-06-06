<%-- Document : menu Created on : May 20, 2024, 6:14:21 AM Author : dell --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
            <style>
                .sidebar-menu li {
                    font-weight: bolder;
                    font-size: large;
                    display: block;
                    margin-top: 8%;
                }

                .sidebar-submenu li a {
                    font-weight: bolder;
                    font-size: medium;
                }
            </style>
        </head>
        <nav id="sidebar" class="sidebar-wrapper">
            <div class="sidebar-content" data-simplebar style="height: calc(100% - 60px);">
                <div class="sidebar-brand">
                    <a href="/krs/homepage.jsp">
                        <img src="/krs/assets/images/logo-krs-rvBG.png" width="50" height="50" class="d-inline-block align-top" alt="">
                        <img src="/krs/assets/images/logo-krs-text-rmBG.png" width="50" height="50" class="d-inline-block align-top" alt="">
                    </a>
                </div>

                <ul class="sidebar-menu" style="display: flex; flex-direction: column;">
                    <li><a href="/krs/admin/dashboard.jsp">Dashboard</a></li>
                    <li class="sidebar-dropdown">
                        <a href="javascript:void(0)">Administration</a>
                        <div class="sidebar-submenu">
                            <ul>
                                <li><a href="/krs/SettingServlet?action=listAllSetting">System Setting</a></li>
                                <li><a href="/krs/admin/addSetting.jsp">New Setting</a></li>
                                <li><a href="/krs/PostServlet">Post List</a></li>
                                 <li><a href="/krs/PostServlet?mode=Addnew">New Post</a></li>
                            </ul>
                        </div>
                    </li>

                    <li class="sidebar-dropdown">
                        <a href="javascript:void(0)">Training</a>
                        <div class="sidebar-submenu">
                            <ul>
                                <li><a href="/krs/TrainingServlet?action=listAllSubject">Subject List</a></li>
                           <li><a href="/krs/admin/addSubject.jsp">New Subject</a></li>
                            </ul>
                        </div>
                    </li>

                    <li class="sidebar-dropdown">
                        <a href="javascript:void(0)">User Accounts</a>
                        <div class="sidebar-submenu">
                            <ul>
                                <li><a href="/krs/AdminUserServlet">User List</a></li>
                                <li><a href="/krs/AdminUserServlet?action=newUser">Add User</a></li>
                                
                            </ul>
                        </div>
                    </li>
                  
                </ul>
                <!-- sidebar-menu  -->
            </div>
        </nav>

        </html>