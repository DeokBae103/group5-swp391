<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Post Detail</title>
        <link rel="stylesheet" href="../assets/css/bootstrap.min.css"/>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 20px;
                box-sizing: border-box;
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
            <jsp:include page="/admin/layout/menu.jsp"/>
            <main class="page-content bg-light">
                <div class="top-header">
                    <jsp:include page="/admin/layout/header.jsp"/>
                </div>
                <div class="container layout-specing">
                    <button class="align-self-start mb-3 rounded-2" onclick="history.back()">&larr; Back</button>
                    <h1>Add New Post</h1>
                    <form class="d-flex flex-column justify-content-around align-items-stretch" action="/krs/PostServlet?mode=Add" method="post" enctype="multipart/form-data">
                        <table>
                            <tr class="form-group">
                                <td>Title <span class="text-danger">*</span>: </td>
                                <td><input id="titleLength" class="form-control mb-3" type="text" name="title" maxlength="50"/></td>
                            </tr>
                            <tr class="form-group">
                                <td>Summary <span class="text-danger">*</span>:</td>
                                <td><input id="summaryLength" class="form-control mb-3"  type="text" name="summary" maxlength="255"/></td>
                            </tr>
                            <tr class="form-group">
                                <td>Content <span class="text-danger">*</span>:</td>
                                <td><textarea id="contentLength" class="form-control mb-3" name="content" rows="10" maxlength="255"></textarea></td>
                            </tr>
                            <tr class="form-group">
                                <td>Mode <span class="text-danger">*</span>:</td>
                                <td>
                                    <select name="setting_name" class="form-select mb-3">
                                        <c:forEach items="${settingList}" var="i">
                                            <option value="${i}">${i}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr class="form-group">
                                <td>Thumbnail: </td>
                                <td><input type="file" name="image" accept="image/*" onchange="previewImage(event)"/><img id="preview" style="width: 150px;height: 120px"/></td>
                            </tr>
                        </table>
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