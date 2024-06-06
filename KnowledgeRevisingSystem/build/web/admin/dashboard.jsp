<%-- 
    Document   : settingList
    Created on : May 17, 2024, 9:11:33 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Knowledge Revising Page</title>
        
    </head>
    <body>
        <div id="preloader">
            <div id="status">
                <div class="spinner">
                    <div class="double-bounce1"></div>
                    <div class="double-bounce2"></div>
                </div>
            </div>
        </div>

        <div class="page-wrapper doctris-theme toggled">
            <jsp:include page="layout/menu.jsp"/>

            <main class="page-content bg-light">
                <div class="top-header">
                    <jsp:include page="layout/header.jsp"/>
                </div>

                
            </main>
        </div>
        <!-- javascript -->
        <script src="/krs/assets/js/bootstrap.bundle.min.js"></script>
        <!-- simplebar -->
        <script src="/krs/assets/js/simplebar.min.js"></script>
        <!-- Chart -->
        <script src="/krs/assets/js/apexcharts.min.js"></script>
        <script src="/krs/assets/js/columnchart.init.js"></script>
        <!-- Icons -->
        <script src="/krs/assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="/krs/assets/js/app.js"></script>    

    </body>
</html>
