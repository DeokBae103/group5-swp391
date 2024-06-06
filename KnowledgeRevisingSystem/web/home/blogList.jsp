<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Knowledge Revising Page</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
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
                background-color: #f4f4f4;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }
            .blog-card img {
                aspect-ratio: 3/1.5;
            }
        </style>
    </head>
    <body>
        <jsp:include page="layout/header.jsp"/>
        <div class="container-fluid mt-4">
            <div class="row">
                <div class="col-8 row p-4">
                    <c:forEach items="${blogList}" var="i">
                        <c:if test="${i.getStatus()}">
                            <div class="blog-card row m-3">
                                <img class="col-4" src="assets/images/blog/${i.getThumbnail_url()!=null?i.getThumbnail_url():'no-image.png'}" alt="Image unavailable">
                                <div class="col-8 position-relative">
                                    <h4 class="mt-1">${i.getTitle()}</h4>
                                    <i>${i.getSummary()}</i>
                                    <h6>Mode: <span class="text-primary">${i.getSetting_name()}</span></h6>
                                    <a class="position-absolute bottom-0" href="BlogServlet?blogId=${i.getPost_id()}">View more...</a>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
                <div class="col-4 sticky-top flex-column">
                    <button class="btn btn-light" onclick="window.location.href = '${sessionScope.name!=null?'UserServlet?action=displayFollowByRole':'homepage.jsp'}'">&larr; Back</button>
                    <div class="mt-3">
                        <form action="BlogServlet" class="form-inline flex-row">
                            <input type="text" class="flex-grow-1 form-control" name="query" placeholder="Search for blog title..." value="${query}">
                            <select name="settingName" class="form-control custom-select">
                                <option value="all">All types</option>
                                <c:forEach items="${settingList}" var="i">
                                    <option value="${i}" <c:if test="${currentSetting != null && i.equals(currentSetting)}">selected</c:if>>${i}</option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="mode" value="query">
                            <button class="btn btn-light" type="submit">Search</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
