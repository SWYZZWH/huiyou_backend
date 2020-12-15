package xyz.zwhzwhzwh.models;
/**
 * 前端报告某些事件如点赞投币等，会用post方法访问/api/topVideos
 * 使用如下类接收post的body
 */
public class EventPost {
	private String uid;
	private String bvid;
	private String event;
	private String play; // b站的播放量为String
	
	public EventPost(String uid, String bvid, String event, String play) {
		super();
		this.uid = uid;
		this.bvid = bvid;
		this.event = event;
		this.play = play;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid){
		this.uid = uid;
	}
	public String getBvid() {
		return bvid;
	}

	public void setBvid(String bvid) {
		this.bvid = bvid;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bvid == null) ? 0 : bvid.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((play == null) ? 0 : play.hashCode());
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
		EventPost other = (EventPost) obj;
		if (bvid == null) {
			if (other.bvid != null)
				return false;
		} else if (!bvid.equals(other.bvid))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (play == null) {
			if (other.play != null)
				return false;
		} else if (!play.equals(other.play))
			return false;
		return true;
	}
	
}
