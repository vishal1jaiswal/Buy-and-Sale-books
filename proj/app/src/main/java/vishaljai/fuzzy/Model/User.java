package vishaljai.fuzzy.Model;

public class User {
    private String id;
    private String emailId;
    private String imageUrl;

    public User(String id, String emailId, String imageUrl) {
        this.id = id;
        this.emailId = emailId;
        this.imageUrl = imageUrl;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
