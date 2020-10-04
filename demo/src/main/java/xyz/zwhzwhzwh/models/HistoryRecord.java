package xyz.zwhzwhzwh.models;

import java.time.ZonedDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 数据模型
 * */
@Document(collection = "records") //与record数据库链接
public class HistoryRecord {
	@Id private ObjectId id;
	private String uid;
	private String bv;
	
	//观看相关信息
	//private ZonedDateTime watch_time;
	//private int watch_length;

	
	public HistoryRecord(ObjectId id, String uid, String bv) {
		super();
		this.id = id;
		this.uid = uid;
		this.bv = bv;
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
