<%-- 
    Document   : contactAdmin
    Created on : May 17, 2024, 8:51:55 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Knownledge Revising Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </head>

    <body>
        <style>
            body::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.3); /* Semi-transparent black overlay */
                z-index: 1;
            }
            /* CSS để căn giữa chiều dọc */
            body, html {
                height: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            body{
                background-image: url("assets/images/contactBG.jpg");
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;

            }

            .custom-border {
                border: 2px solid white; /* Border độ dày 2px màu đỏ */
                border-radius: 5px; /* Bo tròn góc */
            }
            .custom-form {
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.4); /* Thêm box shadow với màu sắc và độ mờ cụ thể */

            }

            .z-index-2{
                z-index: 2;
            }
            .back-button {
                position: absolute;
                top: 20px;
                left: 20px;
                z-index: 3; /* Ensure it's above the overlay */
                border-radius: 50%;

            }
        </style>

        <a href="${sessionScope.User!=null?"UserServlet?action=displayFollowByRole":"home/homepage.jsp"}" class="btn back-button">
            <img src="assets/images/home-icon.png" height="50px" alt="home"/>
        </a>
        <div class="container p-5 bg-white custom-border custom-form z-index-2" style="min-width:700px">
            <div class="row"> 
                <form action="ContactServlet?action=createContactMessage" method="post">
                    <div class="row">

                        <div class="form-group col-md-12">
                            <h3>Contact Admin</h3>
                            <i><p>We will respond within 48 hours!</p></i>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-6">
                            <label for="exampleInputEmail1">Full name <span class="text-danger">*</span></label>
                            <input type="text"  name="fullName" value="${requestScope.fullName == null ?sessionScope.User.getFullName() : requestScope.fullName}" class="form-control" >
                            <div class=" d-flex align-items-center">
                                <c:if test="${!requestScope.errorFullName eq ''}">
                                    <img style="margin-right:4px"src="/krs/assets/images/error.png" height="15px">
                                    <small class="text-danger pl-2">${requestScope.errorFullName}</small> 
                                </c:if>
                            </div>

                        </div>
                        <div class="form-group col-md-6">
                            <label for="adminId">Admin <span class="text-danger">*</span></label>


                            <select name="adminId" id="adminId" class="d-block form-control">
                                <c:forEach var="item" items="${requestScope.vectorAdmin}">
                                    <option value="${item.getUserId()}" ${item.getUserId() eq requestScope.adminId ? "checked" : ""}>${item.getUserName()}</option>
                                </c:forEach>
                            </select>
                        </div>

                    </div>

            </div>

            <div class="row">
                <div class="form-group col-md-6">
                    <label for="exampleInputEmail1">Email <span class="text-danger">*</span></label>
                    <input type="text" value="${requestScope.email == null ?sessionScope.User.getEmail() : requestScope.email}"  class="form-control" name="email">

                </div>
                <div class="form-group col-md-6">
                    <label for="exampleInputPassword1">Phone <span class="text-danger">*</span></label>
                    <input type="text" value="${requestScope.phone == null ?sessionScope.User.getPhone() : requestScope.phone}"  class="form-control" name="phone">
                    <c:if test="${!requestScope.errorPhone eq ''}">
                        <img style="margin-right:4px"src="/krs/assets/images/error.png" height="15px">
                        <small class="text-danger pl-2">${requestScope.errorPhone}</small> 
                    </c:if>
                </div>
            </div>
            <c:if test="${!requestScope.errorEmail eq ''}">   
                <div class="d-flex justify-content-md-start align-items-center">
                    <img style="margin-right:4px"src="/krs/assets/images/error.png" height="15px">
                    <small class="text-danger pl-2">${requestScope.errorEmail}</small> 
                </div>
            </c:if>
            <div class="row">
                <div class="form-group col-md-12">
                    <label for="exampleInputTitle">Title <span class="text-danger">*</span></label>
                    <input type="text" value="${requestScope.subject}" class="form-control" name="subject">
                </div>

            </div>
            <c:if test="${!requestScope.errorSubject eq ''}">
                <img style="margin-right:4px"src="/krs/assets/images/error.png" height="15px">
                <small class="text-danger pl-2">${requestScope.errorSubject}</small> 
            </c:if>
            <div class="row">
                <div class="form-group col-md-12 mb-3">
                    <label for="exampleInputEmail1">Message <span class="text-danger">*</span></label>
                    <textarea class="form-control" value="${requestScope.message}" name="message" rows="3" placeholder="Enter your messages here">${requestScope.message}</textarea>
                </div>

            </div>
            <c:if test="${!requestScope.errorMessage eq ''}">
                <img style="margin-right:4px"src="/krs/assets/images/error.png" height="15px">
                <small class="text-danger pl-2">${requestScope.errorMessage}</small> 
            </c:if>
            <button type="submit" class="btn btn-primary" name="submit" value="submit">Submit</button>
            <c:if test="${requestScope.isSuccess == 'true'}">
                <img class="ml-3"src="/krs/assets/images/check.png" width="15px">
                <span class="text-success pl-1">
                    Your form has been sent. We will respond as soon as possible!!</span> 
            </c:if>


        </form>




    </div>


</body>
</html>
