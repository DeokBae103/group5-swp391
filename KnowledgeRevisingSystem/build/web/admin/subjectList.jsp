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

                <div class="container-fluid">
                    <div class="layout-specing">                        
                        <div class="row justify-content-between">                             

                            <form action="TrainingServlet" class="row justify-content-between">
                                <div class="col-md-2 mt-2 mt-sm-0">
                                    <h5 class="mb-0">Subject List</h5>
                                </div>
                                <div class="col-sm-2 col-md-2 mt-2 mt-sm-0">
                                    <div class="mb-0 position-relative" >
                                        <div class="col-md-9">
                                            <select name="status" class="form-control time-during select2input" onchange="this.form.submit()">
                                                <option value="All" ${status.equals("All")==true?"selected":""}>All Status</option>
                                                <option value="0" ${status.equals("0")==true?"selected":""}>Activate</option>
                                                <option value="1" ${status.equals("1")==true?"selected":""}>Deactivate</option>
                                            </select>
                                            <input type="hidden" name="action" value="listAllSubject">
                                        </div></div>
                                </div><!--end col--> 


                                <div class="col-xl-4 col-lg-4 col-md-4">
                                    <div class="search-bar p-0 d-none d-lg-block ms-2">
                                        <div id="search" class="menu-search mb-0">  
                                            <div role="search" method="get" id="searchform" class="searchform">
                                                <input type="text" class="form-control border rounded-pill" name="search" id="s" value="${search}">
                                                <input type="submit" id="searchsubmit" value="Search">
                                            </div>                                                   
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-lg-2 col-md-2 d-flex justify-content-end">
                                    <div>

                                        <div class="d-grid">
                                            <a href="/krs/admin/addSubject.jsp" class="btn btn-primary">Add</a>
                                        </div>

                                    </div>
                                </div>
                            </form>
                            <!--end row-->
                        </div><!--end row-->
                        <div class="col-12 mt-4">
                            <div class="table-responsive bg-white shadow rounded">
                                <table class="table mb-0 table-center">
                                    <thead>
                                        <tr>
                                            <th class="col-1">ID</th>
                                            <th class="col-5">Name</th>
                                            <th class="col-2">Code</th>
                                            <th class="col-2">Status</th>
                                            <th class="col-1">Action</th>
                                            <th class="col-1"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${subjectList}" var="i">
                                            <tr>
                                                <td class="font-weight-bold">${i.getSubject_id()}</td>
                                                <td>${i.getSubject_name()}</td>
                                                <td>${i.getSubject_code()}</td>               
                                                <c:if test="${i.isStatus() eq false}">
                                                    <td class="badge bg-soft-success">${i.isStatus() eq false?"Activate":"Deactivate"}</td>
                                                </c:if>
                                                <c:if test="${i.isStatus() eq true}">
                                                    <td class="badge bg-soft-danger">${i.isStatus() eq false?"Activate":"Deactivate"}</td>
                                                </c:if>
                                                <td><a href="<c:url value="/TrainingServlet">
                                                           <c:param name="action" value="edit"/>  
                                                           <c:param name="subject_id" value="${i.getSubject_id()}"/> 
                                                       </c:url>">Edit</a></td>
                                                    <c:url var="url" value="/TrainingServlet">
                                                        <c:param name="action" value="update"/>  
                                                        <c:param name="subject_id" value="${i.getSubject_id()}"/> 
                                                        <c:param name="subject_code" value="${i.getSubject_code()}"/>
                                                        <c:param name="subject_name" value="${i.getSubject_name()}"/>                                       
                                                        <c:param name="status" value='${i.isStatus()==false?"true":"false"}'/>
                                                        <c:param name="editStatus" value="editStatus"/>  
                                                    </c:url>
                                                 <c:if test="${i.isStatus() eq false}">
                                                    <td class="text-center"><a class="bg-soft-danger badge" onclick="confirmStatus('${url}')" href="javascript:void(0);">${i.isStatus()==false?"Deactivate":"Activate"}</a></td>
                                                    </c:if>
                                                    <c:if test="${i.isStatus() eq true}">
                                                    <td class="text-center" ><a class="bg-soft-success badge" onclick="confirmStatus('${url}')" href="javascript:void(0);">${i.isStatus()==false?"Deactivate":"Activate"}</a></td>
                                                    </c:if>

                                            </tr> 
                                        </c:forEach>                                     
                                    </tbody>
                                </table>

                            </div>
                        </div>
                    </div>
                    <div class="row text-center">
                        <!-- PAGINATION START -->
                        <div class="col-12 mt-4">
                            <div class="d-md-flex align-items-center text-center justify-content-between">
                                <ul class="pagination justify-content-center mb-0 mt-3 mt-sm-0">
                                    <c:if test="${currentPage>1}">
                                        <li class="page-item"><a class="page-link" href="<c:url value="/TrainingServlet">
                                                                     <c:param name="action" value="listAllSubject"/>  
                                                                     <c:param name="currentPage" value="${currentPage-1}"/> 
                                                                     <c:param name="search" value="${search}"/>


                                                                     <c:param name="status" value="${status}"/>
                                                                 </c:url>" aria-label="Previous">Prev</a></li>
                                        </c:if>

                                    <c:forEach step="1" begin="1" end="${pageNumber}" var="i">
                                        <li class="page-item ${currentPage.equals(i)==true?"active":""}"><a  class="page-link" href="<c:url value="/TrainingServlet">
                                                                                                                 <c:param name="action" value="listAllSubject"/>  
                                                                                                                 <c:param name="currentPage" value="${i}"/> 
                                                                                                                 <c:param name="search" value="${search}"/>

                                                                                                                 <c:param name="status" value="${status}"/>
                                                                                                             </c:url>">${i}</a></li>
                                        </c:forEach>                         



                                    <c:if test="${currentPage<pageNumber}">
                                        <li class="page-item"><a class="page-link" href="<c:url value="/TrainingServlet">
                                                                     <c:param name="action" value="listAllSubject"/>  
                                                                     <c:param name="currentPage" value="${currentPage+1}"/> 
                                                                     <c:param name="search" value="${search}"/>

                                                                     <c:param name="status" value="${status}"/>
                                                                 </c:url>" aria-label="Next">Next</a></li>
                                        </c:if>
                                </ul>
                                <span class="text-muted me-3">Showing ${currentPage*subjectList.size()-subjectList.size()+1} - ${currentPage*subjectList.size()} out of ${size}</span>

                            </div>
                        </div><!--end col-->
                        <!-- PAGINATION END -->
                    </div><!--end row-->
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
        <script>
                                                        function confirmStatus(url) {

                                                            if (confirm("Press OK to confirm change this status")) {
                                                                window.location.href = url;
                                                            }
                                                        }
        </script>

    </body>
</html>
