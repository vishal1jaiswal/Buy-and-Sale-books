package vishaljai.fuzzy;

public class Books {

    String bookid;
    String bookname;
    String bookpublish;
    String bookyear;
    String bookaddress;
    String imagename;
    String url;
    String userId;



public Books()
{

}

    public Books(String bookid, String bookname, String bookpublish, String bookyear, String bookaddress, String imagename, String url, String userId) {
        this.bookid = bookid;
        this.bookname = bookname;
        this.bookpublish = bookpublish;
        this.bookyear = bookyear;
        this.bookaddress = bookaddress;
        this.imagename = imagename;
        this.url = url;
        this.userId = userId;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookpublish() {
        return bookpublish;
    }

    public void setBookpublish(String bookpublish) {
        this.bookpublish = bookpublish;
    }

    public String getBookyear() {
        return bookyear;
    }

    public void setBookyear(String bookyear) {
        this.bookyear = bookyear;
    }

    public String getBookaddress() {
        return bookaddress;
    }

    public void setBookaddress(String bookaddress) {
        this.bookaddress = bookaddress;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
