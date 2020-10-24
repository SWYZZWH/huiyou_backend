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
    private String author;
    private String description;
    private int duration;
    private int favourites;
    private String pic;
    private String play;
    private String pubdate;
    private String tag;
    private String title;
    private String type;

    //观看相关信息
    //private ZonedDateTime watch_time;
    //private int watch_length;

    //,String author,String description,int duration,int favourites,String pic, String play
    //	, String pubdate,String tag,String title,String type

    public HistoryRecord(ObjectId id, String uid, String bv, String author, String description, int duration, int favourites, String pic, String play, String pubdate, String tag, String title, String type) {
        super();
        this.id = id;
        this.uid = uid;
        this.bv = bv;
        this.author = author;
        this.description = description;
        this.duration = duration;
        this.favourites = favourites;
        this.pic = pic;
        this.play = play;
        this.pubdate = pubdate;
        this.tag = tag;
        this.title = title;
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBv() { return bv; }

    public void setBv(String bv) { this.bv = bv; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

	public String getDescription() { return description; }

	public void setDescription(String description) { this.description = description; }

	public int getDuration() { return duration; }

	public void setDuration(int duration) { this.duration = duration; }

	public int getFavourites() { return favourites; }

	public void setFavourites(int favourites) { this.favourites = favourites; }

	public String getPic() { return pic; }

	public void setPic(String pic) { this.pic = pic; }

	public String getPlay() { return play; }

	public void setPlay(String play) { this.play = play; }

	public String getPubdate() { return pubdate; }

	public void setPubdate(String pubdate) { this.pubdate = pubdate; }

	public String getTag() { return tag; }

	public void setTag(String tag) { this.tag = tag; }

	public String getTitle() { return title; }

	public void setTitle(String title) { this.title = title; }

	public String getType() { return type; }

	public void setType(String type) { this.type = type; }

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
