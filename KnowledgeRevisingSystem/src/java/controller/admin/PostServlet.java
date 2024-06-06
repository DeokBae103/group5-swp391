package controller.admin;

import dao.DAOPost;
import dao.DAOPost_Category;
import dao.DAOSetting;
import entity.Post;
import entity.Setting;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Vector;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;

/**
 *
 * @author VKHOANG
 */
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Get DAO layer
        DAOPost daopost = new DAOPost();
        DAOSetting daosetting = new DAOSetting();
        
        //Get all post types
        //request.setAttribute("settingList", daosetting.getPostSetting());
        Vector<Post> vector = new Vector<>();
        //Get the postId for viewing details, or the query for searching keywords
        String query = request.getParameter("query");
        String settingName = request.getParameter("settingName");
        String mode = request.getParameter("mode");
        //Check if id of the post is specified to view blog details
        if (mode != null && mode.equals("query")) {
            //Query and display posts that contains keyword
            vector = daopost.getPostsByKeyword(query, settingName);
            request.setAttribute("query", query);
            request.setAttribute("currentSetting", settingName);
        } else if (mode != null && mode.equals("Addnew")) {
            request.getRequestDispatcher("admin/addPost.jsp").forward(request, response);
            return;
        } else {
            //Get all posts and return to Post List page
            vector = daopost.getAllPosts();
        }
        request.setAttribute("postList", vector);
        request.getRequestDispatcher("admin/postList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //Get DAO layer
        DAOPost daopost = new DAOPost();
        DAOSetting daosetting = new DAOSetting();
        //Get post mode: update or delete
        String mode = request.getParameter("mode");
        //request.setAttribute("settingList", daosetting.getPostSetting());
        //Get postId
        int postId = 0;
        if (request.getParameter("postId") != null) {
            postId = Integer.parseInt(request.getParameter("postId"));
        }
        switch (mode) {
            case "Edit": {
                //Get post information
                Post p = daopost.getPostByPostId(postId);
                request.setAttribute("postDetail", p);
                request.setAttribute("currentSetting", daosetting.getSettingDetails("SELECT * FROM Setting WHERE name= '" + p.getSetting_name() + "'").get(0).getName());
                //Redirect to post details page
                request.getRequestDispatcher("admin/postDetails.jsp").forward(request, response);
                return;
            }
            case "Enable": {
                //enable post
                //Get post by postId
                Post p = daopost.getPostByPostId(postId);
                //Set status so that post can be visible by other user
                p.setStatus(true);
                daopost.updatePosts(p);
                break;
            }
            case "Disable": {
                //disable post
                //Get post by postId
                Post p = daopost.getPostByPostId(postId);
                //Set status so that post cannot be visible by other user
                p.setStatus(false);
                daopost.updatePosts(p);
                break;
            }
            case "Update": {
                String title = null, summary = null, content = null;
                String thumbnail_url = null;
                boolean status = false;
                String setting_name = null;

                ServletContext servletContext = this.getServletConfig().getServletContext();

                //Set image folder
                String folder = servletContext.getRealPath("assets/images/blog");
                folder = folder.replace("build\\web", "web");
                DiskFileItemFactory factory = DiskFileItemFactory.builder()
                        .setPath(folder)
                        .get();

                JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);

                List<FileItem> files = upload.parseRequest(request);

                for (FileItem fileItem : files) {
                    if (!fileItem.isFormField()) {
                        //Get submitted file name
                        String fileName = fileItem.getName();
                        if (!fileName.equals("")) {
                            //get extension
                            String extension = fileName.substring(fileName.lastIndexOf("."));
                            //get file name that will be saved (by Id)
                            fileName = daopost.getPostByPostId(postId).getThumbnail_url();
                            if (fileName == null) {
                                fileName = postId + extension;
                            }
                            String path = folder + "\\" + fileName;
                            File file = new File(path);
                            //Replace file if no blogs existed and the image of blog with id = 1 existed
                            if (file.exists()) {
                                file.delete();
                            }
                            //Write to file (took a bit long)
                            fileItem.write(file.toPath());
                            thumbnail_url = fileName;
                        } else {
                            thumbnail_url = daopost.getPostByPostId(postId).getThumbnail_url();
                        }
                    } else {
                        switch (fileItem.getFieldName()) {
                            case "postId":
                                postId = Integer.parseInt(fileItem.getString());
                            case "title":
                                title = fileItem.getString();
                                break;
                            case "summary":
                                summary = fileItem.getString();
                                break;
                            case "content":
                                content = fileItem.getString();
                                break;
                            case "status":
                                status = Boolean.parseBoolean(fileItem.getString());
                                break;
                            case "setting_name":
                                setting_name = fileItem.getString();
                                break;
                        }
                    }
                }
                Post p = new Post(postId, title, summary, thumbnail_url, content, status, setting_name);
                //Update
                daopost.updatePosts(p);

                //Update to Post_Category table
                Setting s = daosetting.getSettingDetails("SELECT * FROM Setting WHERE name = '" + p.getSetting_name() + "'").get(0);
                p = daopost.getPostByPostId(postId);
                DAOPost_Category pcdao = new DAOPost_Category();
                pcdao.updatePostCategory(p.getPost_id(), s.getSetting_id());
                break;
            }
            case "Add": {
                String title = null, summary = null, content = null;
                String thumbnail_url = null;
                String setting_name = null;

                ServletContext servletContext = this.getServletConfig().getServletContext();

                //Set image folder
                String folder = servletContext.getRealPath("assets/images/blog");
                folder = folder.replace("build\\web", "web");
                DiskFileItemFactory factory = DiskFileItemFactory.builder()
                        .setPath(folder)
                        .get();

                JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);

                List<FileItem> files = upload.parseRequest(request);

                for (FileItem fileItem : files) {
                    if (!fileItem.isFormField()) {
                        //Get submitted file name
                        String fileName = fileItem.getName();
                        if (!fileName.equals("")) {
                            //get extension
                            String extension = fileName.substring(fileName.lastIndexOf("."));
                            DAOPost pdao = new DAOPost();
                            //get file name that will be saved (by Id)
                            fileName = (pdao.getLastPostID() + 1) + extension;
                            String path = folder + "\\" + fileName;
                            File file = new File(path);
                            //Replace file if no blogs existed and the image of blog with id = 1 existed
                            if (file.exists()) {
                                file.delete();
                            }
                            //Write to file (took a bit long)
                            fileItem.write(file.toPath());
                            thumbnail_url = fileName;
                        }
                    } else {
                        switch (fileItem.getFieldName()) {
                            case "title":
                                title = fileItem.getString();
                                break;
                            case "summary":
                                summary = fileItem.getString();
                                break;
                            case "content":
                                content = fileItem.getString();
                                break;
                            case "setting_name":
                                setting_name = fileItem.getString();
                                break;
                        }
                    }
                }
                Post p = new Post(postId, title, summary, thumbnail_url, content, false, setting_name);
                //Add to post table
                daopost.addPost(p);

                //Add to Post_Category table
                Setting s = daosetting.getSettingDetails("SELECT * FROM Setting WHERE name = '" + p.getSetting_name() + "'").get(0);
                int lastRow = daopost.getLastPostID();
                DAOPost_Category pcdao = new DAOPost_Category();
                pcdao.insertPostCategory(lastRow, s.getSetting_id());
                break;
            }
            case "Delete": {
                daopost.deletePosts(postId);
                break;
            }
            default:
                System.out.println("error occurred");
                break;
        }
        //Run doGet to return to post list page after enable or disable or update post
        response.sendRedirect("PostServlet");
    }
}
