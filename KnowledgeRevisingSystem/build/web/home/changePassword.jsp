<%-- 
    Document   : forgotPassword
    Created on : May 19, 2024, 9:07:55 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Change Password</title>
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
            <a href="${sessionScope.name!=null?'UserServlet?action=displayFollowByRole':'homepage.jsp'}" class="btn btn-icon btn-primary"><i data-feather="home" class="icons"></i></a>
        </div>

        <!-- Hero Start -->
        <section class="bg-home d-flex bg-light align-items-center" style="background: url('assets/images/bg/bg-lines-one.png') center;">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 col-md-8">
                        <div style="display: flex; justify-content: center">
                            <img src="assets/images/logo-krs-rvBG.png" width="65" height="65"   alt="">
                            <img src="assets/images/logo-krs-text-rmBG.png" width="65" height="65"  alt="">
                        </div>
                        <div class="card login-page bg-white shadow mt-4 rounded border-0">
                            <div class="card-body">
                                <h4 class="text-center">Change Password</h4>  
                                <form  class="login-form mt-4" method="post" action="UserServlet?action=changepassword" onsubmit="return validateForm()" >
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <p class="text-muted">Please enter your password you receive from email or your old one.</p>
                                            <div class="mb-3">
                                                <label class="form-label">Old Password <span class="text-danger">*</span></label>
                                                <input type="password" class="form-control" placeholder="Enter Your old password" name="oldpassword" value="${requestScope.oldpassword}" required="">
                                            </div>
                                            <p style="color: red;">${requestScope.error}</p>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="mb-3">
                                                <label class="form-label">New Password <span class="text-danger">*</span></label>
                                                <input value="${requestScope.passwordvalue}" pattern=".{10,20}" title="Password requires at least 10 characters and max 20 characters" type="password" class="form-control" placeholder="Enter Your new password" name="newpassword" id="newpassword" required="">
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="mb-3">
                                                <label class="form-label">Confirm new password <span class="text-danger">*</span></label>
                                                <input value="${requestScope.passwordvalue}" type="password" class="form-control" placeholder="Confirm Your new password" name="confirmpassword" id="confirmpassword" required="">
                                            </div>
                                        </div>
                                        <p style="color: red;" id="errorconfirmpassword"></p>
                                        <div class="col-lg-12">
                                            <div class="d-grid">
                                                <button name="send" value="send" class="btn btn-primary">Send</button>
                                            </div>
                                        </div>
                                        <div class="mx-auto">
                                            <p class="mb-0 mt-3"><small class="text-dark me-2">Remember your password ?</small> <a href="login.jsp" class="text-dark h6 mb-0">Sign in</a></p>
                                        </div>
                                    </div>
                                    <input type="hidden" name="email" value="${requestScope.recentEmail}">
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
                                        var newPassword = document.getElementById("newpassword").value;
                                        var confirmPassword = document.getElementById("confirmpassword").value;
                                        if (newPassword !== confirmPassword) {
                                            document.getElementById("errorconfirmpassword").innerHTML = "Confirm password does not match.";
                                            return false;
                                        }
                                        return true;
                                    }
        </script>

    </body>
</html>