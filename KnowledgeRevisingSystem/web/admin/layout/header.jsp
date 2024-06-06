<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Knowledge Revising Page</title>
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
        <!-- SLIDER -->
        <link href="/krs/assets/css/tiny-slider.css" rel="stylesheet" />
        <!-- css -->
        <link href="/krs/assets/css/style.min.css" rel="stylesheet" type="text/css" id="theme-opt" />
        <!-- this link make button dropdown menu right -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- Bootstrap CSS CDN -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>


        <style>
            .no-border-radius{
                border-radius: 0% !important;
            }
            .avatar {
                vertical-align: middle;
                width: 40px;
                height: 40px;
                border-radius: 50%;
            }
            .form-control-header {
                width: 400px!important; /* Extended width for the search input */
            }
            .navbar-custom {
                background-color: #fff; /* White background */
                    border-bottom: solid 1px #e9ecef;
                padding: 10px 20px;
               
                height: 71px;

            }
            .navbar-brand,
            .form-control,
            .btn {
                border-radius: 10px; /* Rounded corners for all elements */
                border: 1px solid #000; /* Bolder black border */
            }
            .navbar-nav .nav-link {
                margin-right: 10px;
                border: none; /* No border for the nav links */
            }

            .btn-signup {
                background-color: #ffeb3b; /* Custom yellow background for Sign up */
                border-color: #ffeb3b;
                color:#000
            }
            .nav-link-underline{
                text-decoration: none; /* Remove underline from link */
                color: inherit; /* Inherit the color from the button */
                position: relative;
            }

            .nav-link-underline::after{
                content: '';
                position: absolute;
                left: 0;
                bottom: 0;
                width: 0;
                height: 2px; /* Adjust thickness as needed */
                background-color: currentColor; /* Use current text color */
                transition: width 0.3s ease;
            }

            .nav-link-underline:hover::after{
                width:100%;
            }
            .dropdown-menu{
                top:100% !important;
                
            }
            .dropdown-item{
                font-size: 16px!important;
                text-transform: none!important;
                letter-spacing: 0px!important;
                font-weight: 400 !important;
            }

        </style>
      
    </head>
    <body>
        <!-- Navbar -->
        <nav class="navbar no-border-radius navbar-expand-lg navbar-custom">


            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-black" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Study tools
                        </a>
                        <div class="dropdown-menu shadow bg-white rounded d-none" aria-labelledby="navbarDropdown"></div>
                    </li> 
                    <li class="nav-item">
                        <a class="nav-link nav-link-underline" href="/krs/BlogServlet">Blog</a>

                    </li>   
                </ul>

                <ul class="navbar-nav ml-auto ">
                    <c:if test="${sessionScope.User.getSettingId()!=1}">             
                    <li class="nav-item align-content-center">
                        <a class="nav-link nav-link-underline" href="/krs/ContactServlet">Contact</a>

                    </li>
                    </c:if>
                    <li class="nav-item align-content-center dropdown">

                        <a class="nav-link" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img src="/krs/assets/images/doctors/01.jpg" alt="Avatar" class="avatar">
                        </a>
                        <div class=" dropdown-menu dropdown-menu-end shadow bg-white rounded" aria-labelledby="navbarDropdown">

                            <a class="d-flex dropdown-item align-items-center p-2" href="/krs/admin/dashboard.jsp">
                                <img src="/krs/assets/images/header/dashboard-icon.png" class="mr-4 ml-2" width="15" height="15" alt="alt"/>
                                <span>Dashboard</span>
                            </a>

                            <a class="d-flex dropdown-item align-items-center p-2" href="/krs/UserServlet?action=updateProfile">
                                <img src="/krs/assets/images/header/user-icon.png" class="mr-4 ml-2" width="15" height="15" alt="alt"/>
                                <span>Profile</span>
                            </a>
                                <a class="d-flex dropdown-item align-items-center p-2 mb-1 mr-2" href="/krs/changePassword.jsp">
                                    <img src="/krs/assets/images/header/padlock.png" class="mr-4 ml-2" width="15" height="15" alt="alt"/>
                                    <span>Change password</span>
                                </a>

                                <a class="d-flex dropdown-item align-items-center p-2 border-top" href="/krs/UserServlet?action=logout">
                                    <img src="/krs/assets/images/header/logout-icon.png" class="mr-4 ml-2" width="15" height="15" alt="alt"/>
                                    <span>Log Out</span>
                                </a>


                            </div>

                        </li>
                </ul>
            </div>
        </nav>


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
