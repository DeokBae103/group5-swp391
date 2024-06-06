<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Knowledge Revising Page</title>

        <!-- this link make button dropdown menu right -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- Bootstrap CSS CDN -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>
        <style>

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
                border-radius: 15px; /* Rounded corners for the navbar */
                padding: 10px 20px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Box shadow for the navbar */
                height: 70px;

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
        </style>
    </head>
    <body>

        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-custom">

            <nav class="navbar navbar-light">
                <a class="navbar-brand" href="${sessionScope.name!=null?'UserServlet?action=displayFollowByRole':'homepage.jsp'}">
                    <img src="/krs/assets/images/logo-krs-rvBG.png" width="30" height="30" class="d-inline-block align-top" alt="">
                    <img src="/krs/assets/images/logo-krs-text-rmBG.png" width="30" height="32" class="d-inline-block align-top" alt="">

                </a>
            </nav>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-black" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Study tools
                        </a>
                        <div class="dropdown-menu shadow bg-white rounded d-none" aria-labelledby="navbarDropdown">
                        </div>
                    </li> 
                    <li class="nav-item">
                        <a class="nav-link nav-link-underline" href="BlogServlet">Blogs</a>
                    </li>   
                </ul>
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control form-control-header mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">
                        <img src="/krs/assets/images/header/loupe.png" height="22" alt="search">
                    </button>
                </form>
                <ul class="navbar-nav ml-auto ">

                    <li class="nav-item align-content-center">
                        <a class="nav-link nav-link-underline" href="/krs/ContactServlet?action=createContactMessage">Contact</a>
                    </li>

                    <c:if test="${sessionScope.name==null}">
                        <li class="nav-item">
                            <a class="btn btn-outline-dark nav-link" href="/krs/UserServlet?action=login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-signup btn-outline-primary  nav-link ml-2" href="UserServlet?action=register">Sign up</a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.name!=null}">
                        <!--                        <li class="nav-item align-content-center dropdown">
                        
                                                    <a class="nav-link" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                        <img src="/krs/assets/images/header/add.png" alt="Add" class="avatar">
                                                    </a>
                                                    <div class="dropdown-menu dropdown-menu-end shadow bg-white rounded" aria-labelledby="navbarDropdown">
                        
                                                        <a class="d-flex dropdown-item align-items-center p-2" href="admin/dashboard.jsp">
                                                            <img src="/krs/assets/images/header/studyset.png" class="mr-4 ml-2" width="15" height="15" alt="alt"/>
                                                            <span>Study Set</span>
                                                        </a>
                        
                                                       
                        
                                                    </div>
                        
                                                </li>-->

                        <li class="nav-item align-content-center dropdown">

                            <a class="nav-link" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <c:choose>
                                    <c:when test="${sessionScope.User.thumbnailUrl!=null}">
                                        <c:set var="folder" value="/krs/assets/images/imageUser/"/>
                                        <img src="<c:out value='${folder}'/><c:out value='${sessionScope.User.thumbnailUrl}'/>" alt="Avatar" class="avatar">

                                    </c:when>
                                    <c:otherwise>
                                        <img src="/krs/assets/images/imageUser/avatar-trang-4.jpg" alt="Avatar" class="avatar">

                                    </c:otherwise>
                                </c:choose>
                                
                            </a>
                            <div class="dropdown-menu dropdown-menu-end shadow bg-white rounded" aria-labelledby="navbarDropdown">

                                <a class="d-flex dropdown-item align-items-center p-2" href="admin/dashboard.jsp">
                                    <img src="/krs/assets/images/header/dashboard-icon.png" class="mr-4 ml-2" width="15" height="15" alt="alt"/>
                                    <span>Dashboard</span>
                                </a>

                                <a class="d-flex dropdown-item align-items-center p-2" href="UserServlet?action=updateProfile">
                                    <img src="/krs/assets/images/header/user-icon.png" class="mr-4 ml-2" width="15" height="15" alt="alt"/>
                                    <span>Profile</span>
                                </a>
                                <a class="d-flex dropdown-item align-items-center p-2 mb-1 mr-2" href="UserServlet?action=changepassword">
                                    <img src="/krs/assets/images/header/padlock.png" class="mr-4 ml-2" width="15" height="15" alt="alt"/>
                                    <span>Change password</span>
                                </a>

                                <a class="d-flex dropdown-item align-items-center p-2 border-top" href="UserServlet?action=logout">
                                    <img src="/krs/assets/images/header/logout-icon.png" class="mr-4 ml-2" width="15" height="15" alt="alt"/>
                                    <span>Log Out</span>
                                </a>


                            </div>

                        </li>
                    </c:if>


                </ul>
            </div>
        </nav>



    </body>
</html>