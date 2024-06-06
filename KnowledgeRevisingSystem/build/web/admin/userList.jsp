<%-- 
    Document   : userList
    Created on : May 17, 2024, 9:20:23 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>Knowledge Revising Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="Version" content="v1.2.0" />
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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- Bootstrap CSS CDN -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>
    </head>

    <body>

        <div class="page-wrapper doctris-theme toggled">
            <!-- menu-->
            <jsp:include page="/admin/layout/menu.jsp"/>

            <main class="page-content bg-light">
                <!--Header-->
                <div class="top-header">
                    <jsp:include page="layout/header.jsp"/>
                </div>

                <div class="container-fluid">
                    <div class="layout-specing">
                        <div class="row">
                            <div class="col-xl-3 col-lg-4 col-md-4">
                                <h5 class="mb-0">LIST USER</h5>
                                <nav aria-label="breadcrumb" class="d-inline-block mt-2">
                                    <ul class="breadcrumb breadcrumb-muted bg-transparent rounded mb-0 p-0">
                                        <li class="breadcrumb-item"><a href="javascript:void(0)">User Management</a></li>
                                        <li class="breadcrumb-item active" aria-current="page"><a href="/krs/AdminUserServlet">User List</a></li>
                                    </ul>
                                </nav>
                            </div><!--end col-->

                            <div class="col-xl-4 col-lg-4 col-md-4">
                                <div class="search-bar p-0 d-none d-lg-block ms-2">
                                    <div id="search" class="menu-search mb-0">
                                        <form role="search" method="get" id="searchform" class="searchform">
                                            <div>
                                                <input type="text" class="form-control border rounded-pill" name="search" id="s"
                                                       placeholder="Search ..." value="${requestScope.search}">
                                                <input type="hidden" name="status" value="${status}">
                                                <input type="hidden" name="role" value="${role}">
                                                <input type="hidden" name="sort" value="${sort}">
                                                <input type="hidden" name="page" value="1">
                                                <input type="submit" id="searchsubmit" value="Search">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xl-5 col-lg-4 col-md-4 mt-4 mt-md-0">
                                <div class="justify-content-md-end">
                                    <div class="row justify-content-between align-items-center">
                                        <div class="col-sm-12 col-md-4">
                                            <div class="mb-0 position-relative">
                                                <form id="formStatus" method="get" action="/krs/AdminUserServlet">
                                                    <select name="status" class="form-control time-during select2input" onchange="this.form.submit()">
                                                        <option value="" <c:if test="${requestScope.status eq ''}">selected</c:if> >All status</option>
                                                        <c:forEach var="c" items="${requestScope.statusList}">
                                                            <option value="${c}" <c:if test="${requestScope.status eq c}">selected</c:if>  >${c}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <input type="hidden" name="role" value="${role}">
                                                    <input type="hidden" name="search" value="${search}">
                                                    <input type="hidden" name="sort" value="${sort}">
                                                    <input type="hidden" name="page" value="1">
                                                </form>

                                            </div>
                                        </div><!--end col-->

                                        <div class="col-sm-12 col-md-4">
                                            <div class="mb-0 position-relative" >
                                                <form id="formRole" method="get" action="/krs/AdminUserServlet">
                                                    <select name="role" class="form-control time-during select2input" onchange="this.form.submit()">
                                                        <option value="" <c:if test="${requestScope.role eq ''}">selected</c:if> >All role</option>
                                                        <c:forEach var="c" items="${requestScope.roles}">
                                                            <option value="${c}" <c:if test="${requestScope.role eq c}">selected</c:if>>${c}</option>    
                                                        </c:forEach>
                                                    </select>
                                                    <input type="hidden" name="status" value="${status}">
                                                    <input type="hidden" name="search" value="${search}">
                                                    <input type="hidden" name="sort" value="${sort}">
                                                    <input type="hidden" name="page" value="1">
                                                </form>

                                            </div>
                                        </div><!--end col-->

                                        <div class="col-sm-12 col-md-4 mt-4 mt-sm-0">
                                            <div class="d-grid">
                                                <a href="/krs/AdminUserServlet?action=newUser" class="btn btn-primary">Add user</a>
                                            </div>
                                        </div><!--end col-->
                                    </div><!--end row-->
                                </div>
                            </div><!--end col-->
                        </div><!--end row-->

                        <div class="row">
                            <div class="col-12 mt-4">
                                <div class="table-responsive bg-white shadow rounded">
                                    <table class="table mb-0 table-center">
                                        <thead>
                                            <tr>
                                                <th class="border-bottom p-3" style="min-width: 50px;">
                                                    ID<a href="AdminUserServlet?change=c&sort=${sort}&status=${status}&role=${role}&search=${search}&page=${page}">
                                                        <i class="fa-solid fa-sort"></i></a>
                                                </th>
                                                <th class="border-bottom p-3" style="min-width: 180px;">
                                                    Full Name<a href="AdminUserServlet?change=c&sort=${sort}&status=${status}&role=${role}&search=${search}&page=${page}">
                                                        <i class="fa-solid fa-sort"></i></a></th>
                                                <th class="border-bottom p-3" style="min-width: 150px;">
                                                    Phone<a href="AdminUserServlet?change=c&sort=${sort}&status=${status}&role=${role}&search=${search}&page=${page}">
                                                        <i class="fa-solid fa-sort"></i></a></th>
                                                <th class="border-bottom p-3" style="min-width: 150px;">
                                                    Email<a href="AdminUserServlet?change=c&sort=${sort}&status=${status}&role=${role}&search=${search}&page=${page}">
                                                        <i class="fa-solid fa-sort"></i></a></th>
                                                <th class="border-bottom p-3">
                                                    Status<a href="AdminUserServlet?change=c&sort=${sort}&status=${status}&role=${role}&search=${search}&page=${page}">
                                                        <i class="fa-solid fa-sort"></i></a></th>
                                                <th class="border-bottom p-3">
                                                    Role<a href="AdminUserServlet?change=c&sort=${sort}&status=${status}&role=${role}&search=${search}&page=${page}">
                                                        <i class="fa-solid fa-sort"></i></a></th>
                                                <th class="border-bottom p-3">
                                                    Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="c" items="${requestScope.userList}">
                                                <tr>
                                                    <th class="p-3">${c.getUserId()}</th>
                                                    <td class="p-3">${c.fullName}</td>
                                                    <td class="p-3">${c.phone}</td>
                                                    <td class="p-3">${c.email}</td>
                                                    <c:forEach var="r" items="${requestScope.statusList}" varStatus="i">
                                                        <c:if test="${c.status == i.index}">
                                                            <td class="p-3">${r}</td> 
                                                        </c:if>
                                                    </c:forEach>                                                  
                                                    <c:forEach var="s" items="${requestScope.setting}">
                                                        <c:if test="${c.settingId == s.setting_id}">
                                                            <td class="p-3">${s.name}</td>
                                                        </c:if>
                                                    </c:forEach>
                                                    <td class="p-3">
                                                        <a href="/krs/AdminUserServlet?action=Update&userId=${c.userId}" class="btn btn-icon btn-pills btn-soft-primary"><i
                                                                class="uil uil-eye"></i></a>
                                                        <a href="/krs/AdminUserServlet?action=Ban&userId=${c.userId}" class="btn btn-icon btn-pills btn-soft-danger"><i
                                                                class="uil uil-ban"></i></a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div><!--end row-->

                        <div class="row text-center">
                            <!-- PAGINATION START -->
                            <div class="col-12 mt-4">
                                <div class="d-md-flex align-items-center text-center justify-content-between">
                                    <ul class="pagination justify-content-center mb-0 mt-3 mt-sm-0">
                                        <c:if test="${page != 1 && page != null}">
                                            <li class="page-item "><a class="page-link"
                                                                      href="AdminUserServlet?page=${page-1}&status=${status}&role=${role}&sort=${sort}&search=${search}" aria-label="Previous">Prev</a></li>
                                            </c:if>                                    
                                            <c:forEach begin="${1}" end="${requestScope.numPage}" var="i" >
                                            <li class="page-item ${page == i ? "active":""}"><a class="page-link"
                                                                                                href="AdminUserServlet?page=${i}&status=${status}&role=${role}&sort=${sort}&search=${search}">${i}</a></li>
                                            </c:forEach>
                                        <c:if test="${page != numPage && size !=0}">
                                            <li class="page-item"><a class="page-link"
                                                                     href="AdminUserServlet?page=${page+1}&status=${status}&role=${role}&sort=${sort}&search=${search}" aria-label="Previous">Next</a></li>
                                            </c:if>   
                                    </ul>
                                    <c:if test="${size!=0}">
                                        <span class="text-muted me-3">Showing ${page*6-5} - ${page*6>size?size:page*6} out of ${size}</span>                                   
                                    </c:if>
                                </div>
                            </div><!--end col-->
                            <!-- PAGINATION END -->
                        </div><!--end row-->
                    </div>
                </div><!--end container-->
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

                                                        const handleChange = () => {
                                                            const fileUploader = document.querySelector('#input-file');
                                                            const getFile = fileUploader.files
                                                            if (getFile.length !== 0) {
                                                                const uploadedFile = getFile[0];
                                                                readFile(uploadedFile);
                                                            }
                                                        }

                                                        const readFile = (uploadedFile) => {
                                                            if (uploadedFile) {
                                                                const reader = new FileReader();
                                                                reader.onload = () => {
                                                                    const parent = document.querySelector('.preview-box');
                                                                    parent.innerHTML = `<img class="preview-content" src=${reader.result} />`;
                                                                };

                                                                reader.readAsDataURL(uploadedFile);
                                                            }
                                                        };
        </script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>
    </body>
