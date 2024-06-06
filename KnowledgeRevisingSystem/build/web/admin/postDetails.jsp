<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Knowledge Revising Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 20px;
                box-sizing: border-box;
            }
            .container {
                max-width: 800px;
                margin: 20px auto;
                border: 1px solid #ccc;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            button {
                margin-bottom: 20px;
                padding: 10px 15px;
                background-color: #f4f4f4;
                border: 1px solid #ccc;
                border-radius: 5px;
                color: #333;
                cursor: pointer;
            }
            button:hover {
                background-color: #e9e9e9;
            }
            .blog-title {
                font-size: 24px;
                margin-bottom: 20px;
                color: #333;
            }
            .blog-image {
                width: 50%;
                height: auto;
                margin: 20px auto;
                transform: translateX(50%);
            }
            .blog-content {
                font-size: 16px;
                color: #555;
                line-height: 1.6;
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
                    <button class="align-self-start mb-3 rounded-2" onclick="history.back()">&larr; Back</button>
                    <c:set var="x" value="${postDetail}"></c:set>
                        <form class="d-flex flex-column justify-content-around align-items-stretch" action="PostServlet?mode=Update" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="postId" value="${x.getPost_id()}">    
                        <table>
                            <tr class="form-group">
                                <td>Title <span class="text-danger">*</span>: </td>
                                <td><input class="form-control mb-3" type="text" name="title" value="${x.getTitle()}" required/></td>
                            </tr>
                            <tr class="form-group">
                                <td>Summary <span class="text-danger">*</span>:</td>
                                <td><input class="form-control mb-3"  type="text" name="summary" value="${x.getSummary()}" required/></td>
                            </tr>
                            <tr class="form-group">
                                <td>Content <span class="text-danger">*</span>:</td>
                                <td><textarea class="form-control mb-3" name="content" rows="10" required>${x.getContent()}</textarea></td>
                            </tr>
                            <tr>
                                <td>Mode: <span class="text-danger">*</span>:</td>
                                <td>
                                    <select name="setting_name">
                                        <c:forEach items="${settingList}" var="i">
                                            <option value="${i}" <c:if test="${i.equals(currentSetting)}">selected</c:if>>${i}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr class="form-group">
                                <td>Thumbnail: </td>
                                <td><input type="file" name="image" accept="image/*" onchange="previewImage(event)"/><img id="preview" style="width: 150px;height: 120px" src="./assets/images/blog/${x.getThumbnail_url()}"/></td>
                            </tr>
                        </table>
                        <input type="hidden" name="status" value="${x.getStatus()}">
                        <button class="align-self-end mt-3 rounded-2" type="submit">Save</button>
                    </form>
                </div>
            </main>
        </div>
        <script>
            function previewImage(event) {
                var input = event.target;
                var image = document.getElementById('preview');
                if (input.files && input.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        image.src = e.target.result;
                        console.log(image.src);
                    };
                    reader.readAsDataURL(input.files[0]);
                }
            }
        </script>
    </body>
</html>