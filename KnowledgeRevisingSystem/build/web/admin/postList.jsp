<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Knowledge Revising Page</title>
        <link rel="stylesheet" href="../assets/css/bootstrap.min.css"/>
        <style>
            .search-bar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin: 20px 0;
            }
            .search-bar button {
                padding: 10px 15px;
                background-color: #f4f4f4;
                border: 1px solid #ccc;
                border-radius: 5px;
                text-decoration: none;
                color: #333;
                cursor: pointer;
            }
            .search-bar button:hover{
                background-color: #e9e9e9;
            }
            .blog-card {
                width: 25%;
                background-color: #f4f4f4;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }
            .blog-card h3 {
                font-size: 18px;
                margin: 10px 0;
            }
            .blog-card img{
                aspect-ratio: 3/1.5;
            }
            .blog-card p {
                font-size: 14px;
                color: #555;
            }
            .blog-card form{
                transform: translateX(20%);
            }
        </style>
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
                <div class="container layout-specing">
                    <div class="search-bar">
                        <button onclick="window.location.href = '/krs/admin/dashboard.jsp'">&larr; Back</button>
                        <form action="PostServlet" class="col-7 form-inline flex-row justify-content-between">
                            <input type="text" class="col-7 form-control" name="query" placeholder="Search for post title..." value="${query}">
                            <select name="settingName" class="form-control custom-select">
                                <option value="all">All types</option>
                                <c:forEach items="${settingList}" var="i">
                                    <option value="${i}" <c:if test="${currentSetting != null && i.equals(currentSetting)}">selected</c:if>>${i}</option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="mode" value="query">
                            <button type="submit">Search</button>
                        </form>
                            <button onclick="window.location.href='PostServlet?mode=Addnew'">+ Add</button>
                    </div>
                    <div class="d-flex justify-content-between flex-wrap gap-5">
                        <c:forEach items="${postList}" var="i">
                            <div class="blog-card d-flex flex-column position-relative text-center pb-4 mb-2">
                                <img src="./assets/images/blog/${i.getThumbnail_url()!=null && !i.getThumbnail_url().equals("")?i.getThumbnail_url():"no-image.png"}" alt="Wait 5 seconds...">
                                <h3>${i.getTitle()}</h3>
                                <p>${i.getSummary()}</p>
                                <h6>Mode: <span class="text-primary">${i.getSetting_name()}</span></h6>
                                <form action="PostServlet" method="post" class="flex-1 position-absolute bottom-0" >
                                    <div>
                                        <input type="hidden" name="postId" value="${i.getPost_id()}">
                                        <input type="submit" class="btn-success" name="mode" value="Edit" />
                                        <c:if test="${i.getStatus()}">
                                            <input id="permission${i.getPost_id()}" type="button" class="btn-danger" name="mode" value="Disable" onclick="doChangePermission(${i.getPost_id()})"/>
                                        </c:if>
                                        <c:if test="${!i.getStatus()}">
                                            <input id="permission${i.getPost_id()}" type="button" class="btn-primary" name="mode" value="Enable" onclick="doChangePermission(${i.getPost_id()})"/>
                                        </c:if>
                                        <input id="delete${i.getPost_id()}" type="button" class="btn-danger" name="mode" value="Delete" onclick="doDelete(${i.getPost_id()})">
                                    </div>
                                </form>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </main>
        </div>
        <script>
            function doChangePermission(postId) {
                var mode = document.getElementById("permission" + postId).value;
                console.log(mode);
                var result = confirm("Are you sure you want to " + mode.toLowerCase() + " " + postId + "?");
                if (result) {
                    document.getElementById("permission" + postId).type = "submit";
                }
            }
            function doDelete(postId) {
                var result = confirm("Are you sure you want to delete " + postId + "?");
                if (result) {
                    document.getElementById("delete" + postId).type = "submit";
                }
            }
        </script>
    </body>
</html>s