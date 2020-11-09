package xyz.zwhzwhzwh.models;

import java.time.ZonedDateTime;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 数据模型
 * 历史记录表
 */
@Document(collection = "records") //与record数据库链接
public class HistoryRecord {
    @Id
    private ObjectId id;

    private String uid;
    private String bvid;
    private long time;
    
	public HistoryRecord(String uid, String bvid) {
		super();
		this.uid = uid;
		this.bvid = bvid;
		Date date = new Date();
		this.time = date.getTime();
	}
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getBvid() {
		return bvid;
	}
	public void setBvid(String bvid) {
		this.bvid = bvid;
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
