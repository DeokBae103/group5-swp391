<%-- 
    Document   : Homepage
    Created on : May 17, 2024, 8:49:44 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Knowledge Revising Page</title>
    </head>
    <body>
        <!--Navbar section-->
        <jsp:include page="/layout/header.jsp"/>
        <!--Banner section-->

        <style>
            .container-background{
               min-height: 81vh;
            }
            .card-course{
                min-height: 198px;
                width: 30vw;
            }
            .card-title-course{
                min-height: 50px;
            }
            /* Style for the button */
            .underline-button {
                background-color: transparent;
                border: none;
                color: #000; /* Change this to your desired text color */
                text-decoration: none;
                font-size: 16px; /* Adjust font size as needed */
                cursor: pointer;
                position: relative;
            }

            /* Style for the underline */
            .underline-button::before {
                content: "";
                position: absolute;
                bottom: 0;
                left: 0;
                width: 0;
                height: 2px; /* Change this to your desired underline thickness */
                background-color: rgb(25, 118, 210); /* Change this to your desired underline color */
                transition: width 0.2s ease;
            }


            /* Style for the underline when hovered over */
            .underline-button.active::before {
                width: 100%;
            }
            /* Banner */
            #banner-guest{
                background-image: url(/krs/assets/images/homepageBG.png);
                height: 82vh; /* Full viewport height */
                background-size: cover; /* Ensure the image covers the entire banner */
                background-position: center; /* Center the image */
                background-repeat: no-repeat; /* Prevent the image from repeating */
                display: flex;
                flex-direction: column;
                justify-content: center; /* Center content vertically */
                align-items: center; /* Center content horizontally */
                color: white; /* Text color */
                /* text-align: center; */
                padding: 20px;
            }
            .banner-logged {
                width: 100%;
                height: 30vh;
            }

            .linear-gradient {
                background: linear-gradient(to right, #ceecff, #9ae4f1);
            }
        </style>


        <!--Background for guest-->
        <c:if test="${sessionScope.name==null}">
            <section id="banner-guest">
                <div class="container">
                    <div class="col-md-5 ml-auto d-flex justify-content-center align-items-center flex-column ">
                        <h2 class="krs-title text-center font-weight-bold">About KRS</h2>
                        <p class="h5 font-weight-normal krs-description text-center">Lorem Ipsum is simply dummy text of the printing
                            and typesetting industry. Lorem Ipsum has been 
                            the industry's standard dummy text since 
                            the 1500s. </p>
                        <a class="nav-link nav-link-underline" href="login.jsp">
                            <button type="button" class="btn btn-warning btn-outline-primary text-dark border-0 ">Get started</button>

                        </a>

                    </div>
                </div>
            </section>
        </c:if>
        <c:if test="${sessionScope.name!=null}">
            <!--Banner-->
<!--            <div class="banner-logged">
                <img src="assets/images/homepage/bannerLogged.jpg" width="100%" height="100%">
            </div>-->
        </c:if>
        <!--Course list for user-->
        <c:if test="${sessionScope.name!=null}">
            <div class="container-background">  
        </c:if>
         
            <!--Course list for student-->
            <c:if test="${sessionScope.name!=null && (sessionScope.User.getSettingId() == 4)}">
                <div class="container-xl">      
                    <div class="d-flex pt-1 pb-3 mt-1 tablist">
                        <Span class="p-3 fw-bolder fs-6" style="color: rgb(25, 118, 210)">STUDENT COURSE</span>    

                    </div>
                    <div class="listCourse">
                        <div class="row ">
                            <c:forEach var="course" items="${requestScope.courseList}">                  
                                <div class="col-4 justify-content-center d-flex">
                                    <div class="card card-course border-primary mb-3 shadow" style="max-width: 18rem;">
                                        <div class="card-header">${course.getSubjectCode()}</div>
                                        <div class="card-body">
                                            <h5 class="card-title-course">${course.getSubjectName()}</h5>
                                            <div class="row">
                                                <div class="col-2">
                                                    <img src="/krs/assets/images/homepage/class-icon.png"class="align-center" width="20px" height="20px">
                                                </div>
                                                <div class="col-10">
                                                    <p class="card-text">${course.getClassName()}</p>
                                                </div>                          
                                            </div>
                                            <div class="row">
                                                <div class="col-2">
                                                    <img src="/krs/assets/images/homepage/teacher-icon.png"class="align-center" width="20px" height="20px">
                                                </div>
                                                <div class="col-10">
                                                    <p class="card-text">${course.getTeacher()}</p>
                                                </div>                          
                                            </div>


                                        </div>
                                    </div>
                                </div> 
                            </c:forEach>
                        </div>
                    </div>

                </div>
            </c:if>
            <!--Course list for teacher-->

            <c:if test="${sessionScope.name!=null && (sessionScope.User.getSettingId() == 3)}">

                <div class="container-xl">      
                    <div class="d-flex pt-1 pb-3 mt-1 tablist">
                        <button class="underline-button p-3 fw-bolder fs-6" id="myButton" style="color: rgb(25, 118, 210)">TEACHING COURSE</button>      
                    </div>

                    <div class="listCourse">
                        <div class="row">
                            <c:forEach var="course" items="${requestScope.courseList}">                  
                                <div class="col-4">
                                    <div class="card border-primary mb-3" style="max-width: 18rem;">
                                        <div class="card-header">${course.getSubjectCode()}</div>
                                        <div class="card-body">
                                            <h5 class="card-title">${course.getSubjectName()}</h5>
                                            <div class="row">
                                                <div class="col-2">
                                                    <img src="/krs/assets/images/homepage/class-icon.png"class="align-center" width="20px" height="20px">
                                                </div>
                                                <div class="col-10">
                                                    <p class="card-text">${course.getClassName()}</p>
                                                </div>                          
                                            </div>
                                            <div class="row">
                                                <div class="col-2">
                                                    <img src="/krs/assets/images/homepage/student-icon.png"class="align-center" width="20px" height="20px">
                                                </div>
                                                <div class="col-10">
                                                    <p class="card-text">${course.getTotalStudent()}</p>
                                                </div>                          
                                            </div>


                                        </div>
                                    </div>
                                </div> 
                            </c:forEach>


                        </div>
                    </div>
                    <c:if test="${sessionScope.name!=null}">
                          </div>
                    </c:if>
            

            </c:if>
            <c:if test="${sessionScope.name!=null}">
<!--                <div class="container-xl">   
                    <div class="d-flex pt-1 pb-3 mt-1 tablist">
                        <Span class="p-3 fw-bolder fs-6" style="color: rgb(25, 118, 210)">STUDY SETS</span>    
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="card shadow linear-gradient">
                                <div class="card-body">
                                    <h5 class="card-title">Special title treatment</h5>
                                    <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                    <a href="#" class="btn btn-primary">Go somewhere</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="card shadow linear-gradient">
                                <div class="card-body">
                                    <h5 class="card-title">Special title treatment</h5>
                                    <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                                    <a href="#" class="btn btn-primary">Go somewhere</a>
                                </div>
                            </div>
                        </div>
                    </div>-->
                </div>
            </c:if>
        </div>
        <!--Footer-->
        <jsp:include page="/layout/footer.jsp"/>
    </body>

</html>
