<%-- 
    Document   : userProfile
    Created on : May 17, 2024, 8:52:48 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>Knownledge Revising Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Premium Bootstrap 4 Landing Page Template" />
        <meta name="keywords" content="Appointment, Booking, System, Dashboard, Health" />
        <meta name="author" content="Shreethemes" />
        <meta name="email" content="support@shreethemes.in" />
        <meta name="website" content="https://shreethemes.in" />
        <meta name="Version" content="v1.2.0" />
        <!-- Css -->

        <!-- this link make button dropdown menu right -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- Bootstrap CSS CDN -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>

        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">


    </head>

    <body>
        <style>
            .rounded-circle {
                width: 100px; /* Adjust the size as needed */
                height: 100px; /* Adjust the size as needed */
                border-radius: 50%;
                object-fit: cover; /* Ensure the image fits within the circle without distortion */
            }
        </style>
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
        <jsp:include page="/layout/header.jsp"/>
        <!-- Start -->
        <section class="bg-dashboard p-0">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-xl-4 col-lg-4 col-md-5 col-12">
                        <div class="rounded shadow overflow-hidden">


                            <div class="text-center avatar-profile margin-nagative mt-5 position-relative pb-4 border-bottom">
                                <c:choose>
                                    <c:when test="${sessionScope.User.thumbnailUrl!=null}">
                                        <c:set var="folder" value="/krs/assets/images/imageUser/"/>
                                        <img src="<c:out value='${folder}'/><c:out value='${sessionScope.User.thumbnailUrl}'/>" alt="Avatar" class="rounded-circle shadow-md">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="/krs/assets/images/imageUser/avatar-trang-4.jpg" alt="Avatar" class="rounded-circle shadow-md">

                                    </c:otherwise>
                                </c:choose>
                                <p class="text-muted mb-0">@${sessionScope.User.getUserName()}</p>
                            </div>


                        </div>
                    </div><!--end col-->

                    <div class="col-xl-8 col-lg-8 col-md-7 mt-4 pt-2 mt-sm-0 pt-sm-0">

                        <div class="rounded shadow mt-4">
                            <div class="p-4 border-bottom">
                                <h5 class="mb-0">User Profile</h5>
                            </div>

                            <div class="p-4 border-bottom">
                                <form action="/krs/UserServlet?action=updateAvatar" method="post" enctype="multipart/form-data" >

                                    <div class="row align-items-center">
                                        <div class="col-lg-2 col-md-4">
                                            <c:choose>
                                                <c:when test="${sessionScope.User.thumbnailUrl!=null}">
                                                    <c:set var="folder" value="/krs/assets/images/imageUser/"/>
                                                    <img src="<c:out value='${folder}'/><c:out value='${sessionScope.User.thumbnailUrl}'/>" alt="Avatar" class="rounded-circle rounded-pill shadow mx-auto d-block">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="/krs/assets/images/imageUser/avatar-trang-4.jpg" alt="Avatar" class="rounded-circle shadow-md">

                                                </c:otherwise>
                                            </c:choose>
                                        </div><!--end col-->

                                        <div class="col-lg-5 col-md-8 text-center text-md-start mt-4 mt-sm-0">
                                            <h5 class="">Upload your picture</h5>
                                            <p class="text-muted mb-0">For best results, use an image at least 256px by 256px in either .jpg or .png format</p>
                                        </div><!--end col-->

                                        <div class="col-lg-5 col-md-12 text-lg-end text-center mt-4 mt-lg-0">  
                                            <input type="file" name="file" id="chooseFile"  accept="image/*" hidden>
                                            <!--accept="image/*": accept all .jpg, .png, .gif-->
                                            <label for="chooseFile"class="btn  mb-0 btn-secondary">Choose file</label>

                                            <input type="submit" name="submit" class="btn btn-primary" value="Upload">

                                        </div><!--end col-->
                                    </div><!--end row-->
                                </form>
                            </div>

                            <div class="p-4">
                                <form action="/krs/UserServlet?action=updateProfile" method="post">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="mb-2">
                                                <label class="form-label">Full Name <span class="text-danger">*</span></label>
                                                <input name="fullName" id="name" type="text" class="form-control" value="${requestScope.fullName != null ? requestScope.fullName : sessionScope.User.fullName}" placeholder="Full Name">
                                                <c:if test="${requestScope.isErrorFullName == 'true'}">
                                                    <img style="margin-right:4px"src="/krs/assets/images/error.png" height="15px">
                                                    <small class="text-danger">${requestScope.errorFullName}</small> 
                                                </c:if>
                                            </div>

                                        </div><!--end col-->

                                        <div class="col-md-6">
                                            <!-- Create radio for choose gender male female -->
                                            <div class="mb-2">
                                                <div class="row">
                                                    <label class="form-label">Gender</label>
                                                </div>
                                                <div class="d-flex">
                                                    <div class="form-check me-3">
                                                        <input class="form-check-input" ${(requestScope.gender)==true || sessionScope.User.getGender() == true?"checked":""} type="radio" name="gender" id="genderMale" value=true>
                                                        <label class="form-check-label" for="genderMale">Male</label>
                                                    </div>
                                                    <div class="form-check">
                                                        <input class="form-check-input" ${(requestScope.gender)==false || sessionScope.User.getGender() == false?"checked":""} type="radio" name="gender" id="genderFemale" value=false>
                                                        <label class="form-check-label" for="genderFemale">Female</label>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <div class="mb-2">
                                                <label class="form-label">Gmail <span class="text-danger">*</span></label>
                                                <input name="email" id="email"  value="${requestScope.email != null ? requestScope.email : sessionScope.User.email}" type="text" class="form-control" placeholder="Your gmail">
                                            </div> 
                                        </div><!--end col-->

                                        <div class="col-md-6">
                                            <div class="mb-2">
                                                <label class="form-label">Phone <span class="text-danger">*</span></label>
                                                <input name="phone" id="phone" value="${requestScope.phone != null ? requestScope.phone : sessionScope.User.phone}" type="text" class="form-control" placeholder="Phone no">
                                                <c:if test="${requestScope.isErrorPhone == 'true'}">
                                                    <img style="margin-right:4px"src="/krs/assets/images/error.png" height="15px">
                                                    <small class="text-danger">${requestScope.errorPhone}</small> 
                                                </c:if>
                                            </div>                                                                               
                                        </div><!--end col-->
                                        <c:if test="${requestScope.isErrorEmail == 'true'}">
                                            <div class="d-flex justify-content-md-start align-items-center">
                                                <img style="margin-right:4px"src="/krs/assets/images/error.png" height="15px" width="15px">
                                                <small class="text-danger">${requestScope.errorEmail}</small> 
                                            </div>
                                        </c:if>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Bio</label>
                                                <textarea name="note" id="comments" rows="4" class="form-control" placeholder=""><c:out value="${requestScope.note != null ? requestScope.note : sessionScope.User.note}"/></textarea> 
                                                <c:if test="${requestScope.isErrorBio == 'true'}">
                                                    <div class="mt-1 d-flex justify-content-md-start">
                                                        <img style="margin-right:4px"src="/krs/assets/images/error.png" height="15px" width="15px">
                                                        <small class="text-danger">${requestScope.errorBio}</small> 
                                                    </div>
                                                </c:if>
                                            </div>

                                        </div>
                                    </div><!--end row-->

                                    <div class="row">
                                        <div class="col-sm-12 d-flex align-items-center">
                                            <input type="submit" id="submit" name="submit" class="btn btn-primary" value="Save changes">
                                            <input type="reset"   class=" ml-2 btn btn-secondary" value="Reset">

                                            <c:if test="${requestScope.isSuccess == true}">
                                                <img class="ml-2"src="/krs/assets/images/check.png" width="15px">
                                                <span class="text-success pl-1">Updated successfully</span> 
                                            </c:if>
                                            <c:if test="${requestScope.isSuccess == false}">
                                                <img class="ml-2"src="/krs/assets/images/unchecked.png" width="15px">
                                                <span class="text-success pl-1">${requestScope.error}</span> 
                                            </c:if>

                                        </div><!--end col-->
                                    </div><!--end row-->
                                </form><!--end form--> 
                            </div>
                        </div>


                    </div><!--end col-->
                </div><!--end row-->
            </div><!--end container-->
        </section><!--end section-->
        <!-- End -->

        <!-- Footer Start -->
        <!--        <footer class="bg-footer py-4">
                    <div class="container-fluid">
                        <div class="row align-items-center">
                            <div class="col-sm-6">
                                <div class="text-sm-start text-center">
                                </div>
                            </div>end col
        
                            <div class="col-sm-6 mt-4 mt-sm-0">
                                <ul class="list-unstyled footer-list text-sm-end text-center mb-0">
                                    <li class="list-inline-item"><a href="terms.html" class="text-foot me-2">Terms</a></li>
                                    <li class="list-inline-item"><a href="privacy.html" class="text-foot me-2">Privacy</a></li>
                                    <li class="list-inline-item"><a href="aboutus.html" class="text-foot me-2">About</a></li>
                                    <li class="list-inline-item"><a href="contact.html" class="text-foot me-2">Contact</a></li>
                                </ul>
                            </div>end col
                        </div>end row
                    </div>end container
                </footer>end footer
                 End -->

        <!-- javascript -->
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <!-- Icons -->
        <script src="assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="assets/js/app.js"></script>
    </body>

</html>
