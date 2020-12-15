package xyz.zwhzwhzwh.models;

public class Video {
    private String bvid;
    private String pic;
    private String author;
    private String title;
    private long postime;
    private long toptime;

    public Video(String bvid,long postime,long toptime){
        this.bvid = bvid;
        this.postime = postime;
        this.toptime = toptime;
        this.pic = null;
        this.author = null;
        this.title = null;
    }

    public String getBvid() {
        return bvid;
    }

    public long getPostime() {
        return postime;
    }

    public long getToptime() {
        return toptime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
