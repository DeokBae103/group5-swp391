package entity;

/**
 * 
 * @author VKHOANG
 */
public class Post_Category {
    private Post post;
    private Setting setting;
    private String category_name;

    public Post_Category() {
    }

    public Post_Category(Post post, Setting setting, String category_name) {
        this.post = post;
        this.setting = setting;
        this.category_name = category_name;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

}
