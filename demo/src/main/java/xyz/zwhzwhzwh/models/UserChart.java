package xyz.zwhzwhzwh.models;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 用来储存单个视频数据
class VideoInfo {
    private String bvid;
    private long postime;
    private long toptime;

    // 第一次正向反馈的时候储存新数据
    public VideoInfo(String bvid) {
        this.bvid = bvid;
        Date now = new Date();
        this.postime = now.getTime();
        this.toptime = -1;
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public long getPostime() {
        return postime;
    }

    public long getToptime() {
        return toptime;
    }

    public void setToptime() {
        Date now = new Date();
        this.toptime = now.getTime();
    }

    public void print(){
        System.out.println(bvid + " " + postime + " " + toptime);
    }
}


public class UserChart {

    @Id
    private String uid;
    private List<VideoInfo> PosNTop;
    private List<VideoInfo> PosTop;

    public UserChart(String uid) {
        this.uid = uid;
        PosNTop = new ArrayList<VideoInfo>();
        PosTop = new ArrayList<VideoInfo>();
    }

    public void addNewVideo(String bvid) {
        if(getVideoInfo(bvid)==null) {
            PosNTop.add(new VideoInfo(bvid));
        }
    }

    public void moveToTop(String bvid) {
        for (VideoInfo e : PosNTop) {
            if (e.getBvid().equals(bvid)) {
                e.setToptime();
                PosTop.add(e);
                PosNTop.remove(e);
                break;
            }
        }
    }

    public VideoInfo getVideoInfo(String bvid){
        for (VideoInfo e : PosNTop) {
            if (e.getBvid().equals(bvid)) {
                return e;
            }
        }
        for(VideoInfo e : PosTop){
            if (e.getBvid().equals(bvid)) {
                return e;
            }
        }
        return null;
    }

    public List<Video> getChart(){
        List<Video> ret = new ArrayList<Video>();

        for(VideoInfo e : PosTop){
            Video n = new Video(e.getBvid(),e.getPostime(),e.getToptime());
            ret.add(n);
        }
        return ret;
    }

    public String getUid() {
        return uid;
    }

    public List<VideoInfo> getPosNTop() {
        return PosNTop;
    }

    public List<VideoInfo> getPosTop() {
        return PosTop;
    }

    public void printPosNTop(){
        for(VideoInfo e: PosNTop){
            e.print();
        }
    }

    public void printPosTop(){
        for(VideoInfo e: PosTop){
            e.print();
        }
    }
}
