package entity;

/**
 * 
 * @author VKHOANG
 */
public class Post {
    private int post_id;
    private String title, summary, thumbnail_url, content;
    private boolean status;
    private String setting_name;

    public Post() {
    }

    public Post(int post_id, String title, String summary, String thumbnail_url, String content, boolean status, String setting_name) {
        this.post_id = post_id;
        this.title = title;
        this.summary = summary;
        this.thumbnail_url = thumbnail_url;
        this.content = content;
        this.status = status;
        this.setting_name = setting_name;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSetting_name() {
        return setting_name;
    }

    public void setSetting_name(String setting_name) {
        this.setting_name = setting_name;
    }
}
