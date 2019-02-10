package vishaljai.fuzzy;

public class Books {

    String bookid;
    String bookname;
    String bookpublish;
    String bookyear;
    String bookaddress;
    String imagename;
    String url;


public Books()
{

}

    public Books(String bookid, String bookname, String bookpublish, String bookyear, String bookaddress,String imagename,String url) {
        this.bookid = bookid;
        this.bookname = bookname;
        this.bookpublish = bookpublish;
        this.bookyear = bookyear;
        this.bookaddress = bookaddress;
        this.imagename=imagename;
        this.url=url;
    }

    public String getBookid() {
        return bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public String getBookpublish() {
        return bookpublish;
    }

    public String getBookyear() {
        return bookyear;
    }

    public String getBookaddress() {
        return bookaddress;
    }

    public String getImagename(){
    return imagename;
}

    public  String getUrl(){return url;}


}
