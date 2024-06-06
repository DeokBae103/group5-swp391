<%-- 
    Document   : addUser
    Created on : May 17, 2024, 9:20:35 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>Knowledge Revising Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.ico.png">
        <!-- Bootstrap -->
        <link href="/krs/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- simplebar -->
        <link href="/krs/assets/css/simplebar.css" rel="stylesheet" type="text/css" />
        <!-- Select2 -->
        <link href="/krs/assets/css/select2.min.css" rel="stylesheet" />
        <!-- Icons -->
        <link href="/krs/assets/css/materialdesignicons.min.css" rel="stylesheet" type="text/css" />
        <link href="/krs/assets/css/remixicon.css" rel="stylesheet" type="text/css" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css"  rel="stylesheet">
        <!-- Css -->
        <link href="/krs/assets/css/style.min.css" rel="stylesheet" type="text/css" id="theme-opt" />

    </head>

    <body>
        <!-- Loader -->
        <div id="preloader">
            <div id="status">
                <div class="spinner">
                    <div class="double-bounce1"></div>
                    <div class="double-bounce2"></div>
                </div>
            </div>
        </div>
        <!-- Loader -->

        <div class="page-wrapper doctris-theme toggled">
            <!--Menu-->
            <jsp:include page="/admin/layout/menu.jsp"/>
            <!-- Start Page Content -->
            <main class="page-content bg-light" style="min-height: 99%">
                <!--Header-->
                <div class="top-header">
                <jsp:include page="/admin/layout/header.jsp"/></div>
                <div class="container-fluid">
                    <div class="layout-specing">
                        <div class="d-md-flex justify-content-between">
                            <h5 class="mb-0">Add New User</h5>

                            <nav aria-label="breadcrumb" class="d-inline-block mt-4 mt-sm-0">
                                <ul class="breadcrumb bg-transparent rounded mb-0 p-0">
                                    <li class="breadcrumb-item"><a href="index.html">User Management</a></li>
                                    <li class="breadcrumb-item"><a href="AdminUserServlet">List User</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">Add User</li>
                                </ul>
                            </nav>
                        </div>

                        <div class="row">
                            <div class="col-lg-12 mt-4">
                                <div class="card border-0 p-4 rounded shadow">                                   
                                    <h5 style="color: red">${requestScope.userNameError}</h5>
                                    <h5 style="color: red">${requestScope.emailError}</h5>
                                    <h5 style="color: red">${requestScope.phoneError}</h5>
                                    <form class="mt-4">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">User Id</label>
                                                    <input name="userId" id="name" type="text"  value ="${requestScope.newId}" class="form-control" readonly>
                                                </div>
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Full Name</label>
                                                    <input name="fullName" id="name2" type="text" class="form-control" placeholder="Full Name" pattern=".{1,20}" title="Please enter maximum 20 characters">
                                                </div>
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">User Name(<span style="color: red">*</span>)</label>
                                                    <input name="userName" id="userName" type="text" class="form-control" placeholder="User Name" required  pattern=".{1,20}" title="Please enter maximum 20 characters">
                                                </div> 
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Role(<span style="color: red">*</span>)</label>
                                                    <select name="role" class="form-control department-name select2input">
                                                        <c:forEach var="c" items="${requestScope.roles}">
                                                            <option value="${c}">${c}</option>    
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Email(<span style="color: red">*</span>)</label>
                                                    <input name="email" id="email" type="email" class="form-control" placeholder="Email" pattern=".{1,30}" title="Please enter maximum 30 characters"  required  >
                                                </div> 
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Phone no.(<span style="color: red">*</span>)</label>
                                                    <input name="phone" id="text" type="text" class="form-control" placeholder="Phone no"required maxlength="10" pattern="[0-9]{1,10}">
                                                </div>                                                                               
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Gender(<span style="color: red">*</span>)</label><br>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" name="gender" id="male" value="1" >
                                                        <label class="form-check-label" for="male">Male</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" name="gender" id="female" value="0">
                                                        <label class="form-check-label" for="female">Female</label>
                                                    </div>
                                                </div>                                                                               
                                            </div><!--end col-->

                                            <div style="display: flex; justify-content: space-around; margin-top: 3%;" > 
                                                <button type="submit" class="btn btn-primary">Add </button>     
                                                <button type="reset" class="btn btn-secondary">Reset</button>
                                            </div>
                                            <input type="hidden" name="action" value="addNewUser">
                                            </form>
                                        </div>
                                </div><!--end col-->
                            </div><!--end row-->
                        </div>
                    </div><!--end container-->

            </main>
            <!--End page-content" -->
        </div>
        <!-- page-wrapper -->
        <!-- Modal end -->

        <!-- javascript -->
        <script src="/krs/assets/js/jquery.min.js"></script>
        <script src="/krs/assets/js/bootstrap.bundle.min.js"></script>
        <!-- simplebar -->
        <script src="/krs/assets/js/simplebar.min.js"></script>
        <!-- Select2 -->
        <script src="/krs/assets/js/select2.min.js"></script>
        <script src="/krs/assets/js/select2.init.js"></script>
        <!-- Icons -->
        <script src="/krs/assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="/krs/assets/js/app.js"></script>
        <script>
            $(document).ready(function () {
                $('.select2input').select2({
                    minimumResultsForSearch: Infinity
                });
            });
        </script>
    </body>

</html>
