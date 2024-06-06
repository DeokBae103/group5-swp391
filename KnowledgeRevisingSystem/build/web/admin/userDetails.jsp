<%-- 
    Document   : userDetails
    Created on : May 17, 2024, 9:20:29 PM
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
        <link rel="shortcut icon" href="/krs/assets/images/favicon.ico.png">
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

            <!-- sidebar-wrapper  -->
            <jsp:include page="layout/menu.jsp"/>
            <!-- Start Page Content -->
            <main class="page-content bg-light">           
                <jsp:include page="layout/header.jsp"/>
                <div class="container-fluid">
                    <div class="layout-specing">
                        <div class="d-md-flex justify-content-between">
                            <h5 class="mb-0">User Detail</h5>

                            <nav aria-label="breadcrumb" class="d-inline-block mt-4 mt-sm-0">
                                <ul class="breadcrumb bg-transparent rounded mb-0 p-0">
                                    <li class="breadcrumb-item"><a href="javascript:void(0)"> User Management</a></li>
                                    <li class="breadcrumb-item"><a href="/krs/AdminUserServlet">List User</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">User Detail</li>
                                </ul>
                            </nav>
                        </div>

                        <div class="row">                           
                            <div class="col-lg-12 mt-4">                                 
                                <div class="card border-0 p-4 rounded shadow">
                                    <div class="row align-items-center">
                                        <form action="AdminUserServlet?action=updateImage&userId=${param.userId}" method="post" enctype="multipart/form-data" style="display: flex;" >
                                            <div class="col-lg-2 col-md-4">
                                                <img src="/krs/assets/images/imageUser/${requestScope.img}" class="avatar avatar-md-md rounded-pill shadow mx-auto d-block" alt="">
                                            </div><!--end col-->
                                            <div class="col-lg-5 col-md-8 text-left text-md-start mt-4 mt-sm-0">
                                                <h5 class="">Upload your picture</h5>
                                                <p class="text-muted mb-0">For best results, use an image at least 600px by 600px in either .jpg or .png format</p>                                            
                                            </div><!--end col-->

                                            <div class="col-lg-5 col-md-12 text-lg-end text-center mt-4 mt-lg-0">
                                                <input type="file" name="file" id="actual-btn" accept="image/*" hidden  onchange="this.form.submit()">
                                                <label for="actual-btn"  class="label btn btn-primary">Upload image</label>
                                            </div><!--end col-->
                                        </form>
                                    </div><!--end row-->

                                    <form class="mt-4" method="post" action="AdminUserServlet">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Full Name</label>
                                                    <input name="fullName" id="name" type="text" class="form-control" value="${requestScope.userInfo.fullName}" required maxlength="20">
                                                </div>
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Phone</label>
                                                    <input name="phone" id="text" type="text" class="form-control" value="${requestScope.userInfo.phone}" maxlength="10" pattern="[0-9]{1,10}">
                                                    <c:if test="${param.err != null}">
                                                        <p style="color: red">Phone was existed!</p>
                                                    </c:if>
                                                </div>                                                                               
                                            </div>
                                            
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">User Name</label>
                                                    <input name="userName" id="name2" type="text" class="form-control" value="${requestScope.userInfo.userName}" readonly  maxlength="20">                                                    
                                                </div>
                                            </div><!--end col-->

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Email(<span style="color: red">*</span>)</label>
                                                    <input name="email" id="email" type="email" class="form-control" value="${requestScope.userInfo.email}" readonly maxlength="30">
                                                </div> 
                                            </div>
                       

                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label class="form-label">Gender</label><br>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" name="gender" <c:if test="${requestScope.userInfo.gender == true }">checked</c:if> value="1" >
                                                            <label class="form-check-label" for="male">Male</label>
                                                        </div>
                                                        <div class="form-check form-check-inline">
                                                            <input class="form-check-input" type="radio" name="gender" <c:if test="${requestScope.userInfo.gender == false }">checked</c:if> value="0">
                                                            <label class="form-check-label" for="female">Female</label>
                                                        </div>
                                                    </div>
                                                </div><!--end col-->    

                                                <div class="col-md-6">
                                                    <div class = row>
                                                        <div class="col-md-6">
                                                            <label class="form-label">Role</label>
                                                            <select name="role" class="form-control gender-name select2input" required>
                                                            <c:forEach var="c" items="${requestScope.roles}">
                                                                <option value="${c}" <c:if test="${requestScope.userRole eq c}">selected</c:if>>${c}</option>    
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label class="form-label">Status</label>
                                                        <select name="status" class="form-control gender-name select2input" required>
                                                            <c:forEach var="c" items="${requestScope.statusList}">
                                                                <option value="${c}" <c:if test="${requestScope.statusUser eq c}">selected</c:if>  >${c}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div><!--end col-->

                                            <div class="col-md-12">
                                                <div class="mb-3">
                                                    <label class="form-label">Note</label>
                                                    <textarea name="note" id="comments" rows="3" class="form-control" maxlength="255" >${requestScope.userInfo.note}</textarea>
                                                </div>
                                            </div>
                                        </div><!--end row-->

                                        <div style="display: flex; justify-content: space-around; margin-top: 3%;" > 
                                            <button type="submit" name="submit" value="update" class="btn btn-primary">Update</button>     
                                            <button type="reset" class="btn btn-secondary">Reset</button>
                                        </div>
                                        <input type="hidden" name="userId" value="${requestScope.userInfo.userId}">
                                        <input type="hidden" name="action" value="Update">
                                    </form>
                                </div>
                            </div><!--end col-->
                        </div><!--end row-->
                    </div>
                </div><!--end container-->


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
