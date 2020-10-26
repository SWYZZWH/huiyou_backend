package xyz.zwhzwhzwh.models;

import java.time.ZonedDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 数据模型
 */
@Document(collection = "records") //与record数据库链接
public class HistoryRecord {
    @Id
    private ObjectId id;

    private String uid;
    private String bv;

    private String arcrank;
    private String arcurl;
    private String author;
    private boolean badgepay;
    private String description;
    private int duration;
    private int favourites;
    private int is_pay;
    private int is_union_video;
    private int mid;
    private String pic;
    private String play;
    private String pubdate;
    private int rank_offset;
    private int rank_index;
    private int rank_score;
    private int review;
    private int senddate;
    private String tag;
    private String title;
    private String type;
    private int video_review;

    //观看相关信息
    //private ZonedDateTime watch_time;
    //private int watch_length;

    //,String author,String description,int duration,int favourites,String pic, String play
    //	, String pubdate,String tag,String title,String type

    public HistoryRecord(ObjectId id, String uid, String bv, String arcrank, String arcurl, String author, boolean badgepay, String description, int duration, int favourites, int is_pay, int is_union_video, int mid, String pic, String play, String pubdate, int rank_offset, int rank_index, int rank_score, int review,int senddate,String tag, String title, String type, int video_review) {
        super();
        this.id = id;
        this.uid = uid;
        this.bv = bv;
        this.arcrank = arcrank;
        this.arcurl = arcurl;
        this.author = author;
        this.badgepay = badgepay;
        this.description = description;
        this.duration = duration;
        this.favourites = favourites;
        this.is_pay = is_pay;
        this.is_union_video = is_union_video;
        this.pic = pic;
        this.play = play;
        this.pubdate = pubdate;
        this.rank_index = rank_index;
        this.rank_offset = rank_offset;
        this.rank_score = rank_score;
        this.review = review;
        this.senddate =senddate;
        this.tag = tag;
        this.title = title;
        this.type = type;
        this.video_review = video_review;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }

    public String getArcrank() {
        return arcrank;
    }

    public void setArcrank(String arcrank) {
        this.arcrank = arcrank;
    }

    public String getArcurl() {
        return arcurl;
    }

    public void setArcurl(String arcurl) {
        this.arcurl = arcurl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isBadgepay() {
        return badgepay;
    }

    public void setBadgepay(boolean badgepay) {
        this.badgepay = badgepay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFavourites() {
        return favourites;
    }

    public void setFavourites(int favourites) {
        this.favourites = favourites;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay() {
        this.is_pay = is_pay;
    }

    public int getIs_union_video() {
        return is_union_video;
    }

    public void setIs_union_video(int is_union_video) {
        this.is_union_video = is_union_video;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public int getRank_offset() {
        return rank_offset;
    }

    public void setRank_offset(int rank_offset) {
        this.rank_offset = rank_offset;
    }

    public int getRank_index() {
        return rank_index;
    }

    public void setRank_index(int rank_index) {
        this.rank_index = rank_index;
    }

    public int getRank_score() {
        return rank_score;
    }

    public void setRank_score(int rank_score) {
        this.rank_score = rank_score;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getSenddate() {
        return senddate;
    }

    public void setSenddate(int senddate) {
        this.senddate = senddate;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVideo_review() {
        return video_review;
    }

    public void setVideo_review(int video_review) {
        this.video_review = video_review;
    }

	/*
	public ZonedDateTime getWatch_time() {
		return watch_time;
	}
	public void setWatch_time(ZonedDateTime watch_time) {
		this.watch_time = watch_time;
	}
	public int getWatch_length() {
		return watch_length;
	}
	public void setWatch_length(int watch_length) {
		this.watch_length = watch_length;
	}*/


    @Override
    public String toString() {
        return "HistoryRecord [uid=" + uid + ", bv=" + bv + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HistoryRecord other = (HistoryRecord) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
