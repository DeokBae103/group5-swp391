package controller.home;

import dao.DAOPost;
import dao.DAOSetting;
import entity.Post;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;

/**
 *
 * @author VKHOANG
 */
public class BlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Vector<Post> vector;
        //Get DAO layer
        DAOPost daopost = new DAOPost();
        DAOSetting daosetting = new DAOSetting();
        //Get the blogId for viewing details, or the query for searching keywords
        String blogId = request.getParameter("blogId");
        String query = request.getParameter("query");
        String settingName = request.getParameter("settingName");
        String mode = request.getParameter("mode");

        //Check if id of the blog is specified to view blog details
        if (blogId != null && !blogId.isEmpty()) {
            //Get blog detail and redirect to Blog Details page
            Post post = daopost.getPostByPostId(Integer.parseInt(blogId));
            request.setAttribute("blogDetail", post);
            request.getRequestDispatcher("blogDetails.jsp").forward(request, response);
            return;
        } else if (mode != null && mode.equals("query")) {
            //Query and display blogs that contains keyword
            vector = daopost.getPostsByKeyword(query, settingName);
            request.setAttribute("query", query);
            request.setAttribute("currentSetting", settingName);
        } else {
            //Get all blogs and return to Blog List page
            vector = daopost.getAllPosts();
            //Pass to the view
        }
        //request.setAttribute("settingList", daosetting.getPostSetting());
        request.setAttribute("blogList", vector);
        request.getRequestDispatcher("blogList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
