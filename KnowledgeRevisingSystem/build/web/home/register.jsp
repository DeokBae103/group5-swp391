<%-- 
    Document   : register
    Created on : May 17, 2024, 8:52:42 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Setting, dao.DAOSetting, java.util.Vector" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Register</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Premium Bootstrap 4 Landing Page Template" />
        <meta name="keywords" content="Appointment, Booking, System, Dashboard, Health" />
        <meta name="author" content="Shreethemes" />
        <meta name="email" content="support@shreethemes.in" />
        <meta name="website" content="https://shreethemes.in" />
        <meta name="Version" content="v1.2.0" />
        <!-- favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.ico.png">
        <!-- Bootstrap -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- Icons -->
        <link href="assets/css/materialdesignicons.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/remixicon.css" rel="stylesheet" type="text/css" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css"  rel="stylesheet">
        <!-- Css -->
        <link href="assets/css/style.min.css" rel="stylesheet" type="text/css" id="theme-opt" />
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
        <!-- Loader -->

        <div class="back-to-home rounded d-none d-sm-block">
            <a href="homepage.jsp" class="btn btn-icon btn-primary"><i data-feather="home" class="icons"></i></a>
        </div>

        <!-- Hero Start -->
        <section class="bg-half-150 d-table w-100 bg-light" style="background: url('assets/images/bg/bg-lines-one.png') center;">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 col-md-8">
                        <div style="display: flex; justify-content: center">
                            <img src="assets/images/logo-krs-rvBG.png" width="65" height="65"   alt="">
                            <img src="assets/images/logo-krs-text-rmBG.png" width="65" height="65"  alt="">
                        </div>
                        <div class="card login-page bg-white shadow mt-4 rounded border-0">
                            <div class="card-body">
                                <h4 class="text-center">Sign Up</h4>  
                                <form action="UserServlet?action=register" method="post" class="login-form mt-4" onsubmit="return validateForm()">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="mb-3">                                                
                                                <label class="form-label">Fullname <span class="text-danger">*</span></label>
                                                <input pattern=".{1,20}" title="Please enter maximum 20 characters" type="text" class="form-control" placeholder="Fullname" name="fullname" value="${requestScope.fullname}" required="">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="mb-3">                                                
                                                <label class="form-label">Username <span class="text-danger">*</span></label>
                                                <input pattern=".{1,20}" title="Please enter maximum 20 characters" type="text" class="form-control" placeholder="Username" name="username" value="${requestScope.username}" required="">
                                            </div>
                                        </div>
                                        <p style="color: red;">${requestScope.duplicateUsername}</p>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Your Email <span class="text-danger">*</span></label>
                                                <input type="email" class="form-control" placeholder="Email" name="email" value="${requestScope.email}" required="">
                                            </div>
                                        </div>
                                        <p style="color: red;">${requestScope.duplicateEmail}</p>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <div class="row">
                                                    <label class="form-label">Gender</label>
                                                </div>
                                                <div class="d-flex">
                                                    <div class="form-check me-3">
                                                        <input class="form-check-input" type="radio" name="gender" ${(requestScope.gender)==true?"checked":""} checked id="genderMale" value=true>
                                                        <label class="form-check-label" for="genderMale">Male</label>
                                                    </div>
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="radio" name="gender" ${(requestScope.gender)==false?"checked":""} id="genderFemale" value=false>
                                                        <label class="form-check-label" for="genderFemale">Female</label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>      
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Your Phone Number <span class="text-danger">*</span></label>
                                                <input pattern=".{10,10}" title="Please enter maximum 10 digits" type="text" class="form-control" placeholder="Phone Number" name="phone" value="${requestScope.phone}" required="">
                                            </div>
                                        </div>
                                        <p style="color: red;">${requestScope.duplicatePhone}</p>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Password <span class="text-danger">*</span></label>
                                                <input pattern=".{10,20}" title="Password requires at least 10 characters and max 20 characters" type="password" class="form-control" placeholder="Password" id="password" name="password" value="${requestScope.password}" required="">
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Password Confirm <span class="text-danger">*</span></label>
                                                <input type="password" class="form-control" placeholder="Password Confirm" id="passwordconfirm" name="passwordconfirm" value="${requestScope.passwordConfirm}" required="">
                                            </div>
                                        </div>

                                        <p style="color: red;" id="errorconfirmpassword"></p>
                                        <div class="form-group">
                                            <label for="exampleSelect">Role</label>
                                            <select name="role" class="form-control" id="roleselect">
                                                <% DAOSetting daoSetting = new DAOSetting(); 
                                                Vector<Setting> vSetting = daoSetting.getSettingDetails("select * from setting where type = 'User' and setting_id != 1 and setting_id != 2");%>
                                                <c:forEach items="<%=vSetting%>" var="setting">
                                                    <option value="${setting.getSetting_id()}">${setting.getName()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <div class="form-check">
                                                    <input class="form-check-input align-middle" type="checkbox" value="" id="accept-tnc-check">
                                                    <label class="form-check-label" for="accept-tnc-check">I Accept <a href="#" class="text-primary">Terms And Condition</a></label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="d-grid">
                                                <button name="register" value="register" class="btn btn-primary">Register</button>
                                            </div>
                                        </div>

                                        <!--                                        <div class="col-lg-12 mt-3 text-center">
                                                                                    <h6 class="text-muted">Or</h6>
                                                                                </div>end col
                                        
                                                                                end col
                                        
                                                                                <div class="col-12 mt-3">
                                                                                    <div class="d-grid">
                                                                                        <a href="javascript:void(0)" class="btn btn-soft-primary"><i class="uil uil-google"></i> Google</a>
                                                                                    </div>
                                                                                </div>end col-->

                                        <div class="mx-auto">
                                            <p class="mb-0 mt-3"><small class="text-dark me-2">Already have an account ?</small> <a href="UserServlet?action=login" class="text-dark fw-bold">Sign in</a></p>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div><!---->
                    </div> <!--end col-->
                </div><!--end row-->
            </div> <!--end container-->
        </section><!--end section-->
        <!-- Hero End -->

        <!-- javascript -->
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <!-- Icons -->
        <script src="assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="assets/js/app.js"></script>

        <script>
                                    function validateForm() {
                                        var password = document.getElementById("password").value;
                                        var confirmPassword = document.getElementById("passwordconfirm").value;
                                        if (password !== confirmPassword) {
                                            document.getElementById("errorconfirmpassword").innerHTML = "Confirm password does not match.";
                                            return false;
                                        }
                                        return true;
                                    }
        </script>

    </body>
</html>