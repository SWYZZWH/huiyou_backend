package xyz.zwhzwhzwh.models;

public class Video {
    private String bvid;
//    private String url;
//    private String author;
//    private String title;
    private long PosTime;
    private long TopTime;

    //为了测试/chart添加的key
    private int score;

    public Video(String bvid,long PosTime,long TopTime){
        this.bvid = bvid;
        this.PosTime = PosTime;
        this.TopTime = TopTime;
        this.score = -1;
//        this.url = null;
//        this.author = null;
//        this.title = null;
    }

    public String getBvid() {
        return bvid;
    }

    public long getPosTime() {
        return PosTime;
    }

    public long getTopTime() {
        return TopTime;
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//    public void setTitle(String title) {
//        this.title = title;
//    }


    // 测试用
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
