package xyz.zwhzwhzwh.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zwh
 * 视频热度表
 */
@Document(collection = "videos")
public class TopVideo {
	/**
	 * 
	 */
	@Id
	private String bvid;
	private int score;
	private int play; // 播放量，注意是String
	
	public TopVideo(String bvid, int score, int play) {
		super();
		this.bvid = bvid;
		this.score = score;
		this.play = play;
	}

	public String getBvid() {
		return bvid;
	}

	public void setBvid(String bvid) {
		this.bvid = bvid;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPlay() {
		return play;
	}

	public void setPlay(int play) {
		this.play = play;
	}

	@Override
	public String toString() {
		return "TopVideo [bvid=" + bvid + ", score=" + score + ", play=" + play + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bvid == null) ? 0 : bvid.hashCode());
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
		TopVideo other = (TopVideo) obj;
		if (bvid == null) {
			if (other.bvid != null)
				return false;
		} else if (!bvid.equals(other.bvid))
			return false;
		return true;
	}
		
}
