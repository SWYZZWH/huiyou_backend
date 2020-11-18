package xyz.zwhzwhzwh.controllers;

import java.util.*;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.querydsl.QSort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xyz.zwhzwhzwh.models.EventPost;
import xyz.zwhzwhzwh.models.HistoryRecord;
import xyz.zwhzwhzwh.models.TopVideo;
import xyz.zwhzwhzwh.models.Log;
import xyz.zwhzwhzwh.repositories.RecordRepository;
import xyz.zwhzwhzwh.repositories.VideoRepository;
import xyz.zwhzwhzwh.repositories.LogRepository;

/**
 * 解析url，并调用对应的方法操作数据库
 **/
class ErrorCode {
    public static final Integer Record_Post_Error = 151;
    public static final Integer Record_Get_Error = 152;
    public static final Integer Log_Post_Error = 251;
    public static final Integer Log_Get_Error = 252;
	public static final Integer Video_Promote_Error = 351;
	public static final Integer Video_Update_Error = 352;
	public static final List<Integer> list = Arrays.asList(151, 152, 251, 252,351,352);
}

@RestController
@RequestMapping("/api")
public class ApiController {

    //全局变量定义
    final private int initialScore = 10;

    @Autowired
    private RecordRepository record_repository;

    @Autowired
    private VideoRepository video_repository;

    @Autowired
    private LogRepository log_repository;

    @GetMapping(value = "/all")
    public List<HistoryRecord> getAllHistoryRecords() {
        return record_repository.findAll();
    }

    @GetMapping(value = "/records")
    public List<HistoryRecord> getAllHistoryRecords(@RequestParam Map<String, String> queryParams) {
        try {
            String uid = queryParams.get("uid");
            String bvid = queryParams.get("bvid");
            if (uid == null && bvid == null)
                return record_repository.findAll();
            else if (uid == null)
                return record_repository.findByBvid(bvid);
            else if (bvid == null)
                return record_repository.findByUid(uid);
            else
                return record_repository.findByUidAndBvid(uid, bvid);
        } catch (Exception e) {
            Log error = new Log(ErrorCode.Record_Get_Error, e.toString());
            log_repository.save(error);
            return new ArrayList<HistoryRecord>();
        }
    }

    @GetMapping(value = "/test")
    public List<HistoryRecord> test4sorting(@RequestParam Map<String, String> queryParams) {
        try {
            String uid = queryParams.get("uid");
            String bvid = queryParams.get("bvid");

            List<HistoryRecord> user = record_repository.findByUid(uid);
            System.out.println("origin:");
            for (HistoryRecord record : user) {
                System.out.println(record.getTime());
            }
            if (user.size() >= 1) {
                //按照时间戳排序
                Collections.sort(user);
                System.out.println("sorted:");
                for (HistoryRecord historyRecord : user) {
                    System.out.println(historyRecord.getTime());
                }
                return user.subList(0, user.size());
            } else return user;
        } catch (Exception e) {
            return new ArrayList<HistoryRecord>();
        }
    }

    @PostMapping(value = "/records")
    public ResponseEntity<?> saveOrUpdateRecord(@RequestBody HistoryRecord history_record) {
        try {
            if (history_record.getBvid() == null || history_record.getUid() == null || history_record.getBvid().equals("") || history_record.getUid().equals("")) {
				Log error = new Log(ErrorCode.Record_Post_Error, "Invalid record");
				log_repository.save(error);
				return new ResponseEntity<>("Record added failed", HttpStatus.BAD_REQUEST);
            }
            List<HistoryRecord> user = record_repository.findByUid(history_record.getUid());
//            testing
//            System.out.println("origin:");
//            for (HistoryRecord record : user) {
//                System.out.println(record.getTime());
//            }
            if (user.size() >= 100) {
                //按照时间戳排序
                Collections.sort(user);
                //testing
//                System.out.println("sorted:");
//                for (HistoryRecord historyRecord : user) {
//                    System.out.println(historyRecord.getTime());
//                }
                //取前5个
                List<HistoryRecord> delete = user.subList(0, 5);
                //删除
                record_repository.deleteAll(delete);
            }
            record_repository.save(history_record);
            //如果这条记录是由前端随机选取视频产生的，那么要把视频保存下来，播放量可以先置为1
            String bvid = history_record.getBvid();
            if (!video_repository.existsByBvid(bvid)) {

                TopVideo video = new TopVideo(bvid, initialScore, 1);
                video_repository.save(video);
            }
            return new ResponseEntity<>("Record added successfully", HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            Log error = new Log(ErrorCode.Record_Post_Error, e.toString());
            log_repository.save(error);
            // logging...
            return new ResponseEntity<>("Record added failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/records")
    public void deleteRecords(@RequestParam Map<String, String> queryParams) {
        String bvid = queryParams.get("bvid");
        String uid = queryParams.get("uid");

        if (bvid == null && uid == null) {
            record_repository.deleteAll();
        } else if (bvid == null) {
            List<HistoryRecord> records = record_repository.findByUid(uid);
            record_repository.deleteAll(records);
        } else if (uid == null) {
            List<HistoryRecord> records = record_repository.findByBvid(bvid);
            record_repository.deleteAll(records);
        } else {
            List<HistoryRecord> records = record_repository.findByUidAndBvid(uid, bvid);
            record_repository.deleteAll(records);
        }
    }

    @DeleteMapping(value = "/videos")
    public void deleteVideos(@RequestParam Map<String, String> queryParams) {
        String bvid = queryParams.get("bvid");
        if (bvid == null) {
            video_repository.deleteAll();
        } else {
            List<TopVideo> videos = video_repository.findByBvid(bvid);
            video_repository.deleteAll(videos);
        }
    }

    @GetMapping(value = "/videos")
    public ResponseEntity<?> getTopVideo(@RequestParam Map<String, String> queryParams) {
        //返回下一条推荐的视频
        //String event = queryParams.get("event");
        //需要判断待推送的视频是否曾经推给过用户
        String uid = queryParams.get("uid");

        TopVideo retVideo = null;
        int counter = 0;
        do {
            //一直摇随机数，直到获得的视频列表非空
            //但同时应该注意到没有可以推荐的视频的情况
            //比如所有视频都已经过热被封档，或者分数都已经掉到0以下，或者本来表里就没有视频
            //因此要设置一个计数器跳出，防止无限循环
            double r = Math.random();
            int upperbound = 0;
            int lowerbound = 0;
            if (r < 0.25) {
                //低热度视频
                upperbound = 25;
            } else if (r < 0.55) {
                //中热度视频
                lowerbound = 25;
                upperbound = 55;
            } else {
                //高热度视频
                lowerbound = 55;
                upperbound = 100;
            }

            List<TopVideo> videoWaitlist = video_repository.findByScoreBetween(lowerbound, upperbound);
            int len = videoWaitlist.size();
            if (len != 0) {
                Random rr = new Random();
                retVideo = videoWaitlist.get(rr.nextInt(len));
            }
            //判空 + 判重
        } while ((retVideo == null || !record_repository.findByUidAndBvid(uid, retVideo.getBvid()).isEmpty()) && counter++ <= 5);

        if (counter <= 5) {
            //决定推荐该视频，这时，要扣除该视频的热度值 -2
            retVideo.setScore(retVideo.getScore() - 2);
            video_repository.save(retVideo);
            return new ResponseEntity<>(retVideo, HttpStatus.OK);
        } else {
            //logging...
            //返回错误信息
			Log error = new Log(ErrorCode.Video_Promote_Error, "No Video to promote");
			log_repository.save(error);
            return new ResponseEntity<>("null", HttpStatus.OK);
        }

    }

    @PatchMapping(value = "/videos") //或者patch方法 //用于更新表中视频的热度值
    public ResponseEntity<?> saveOrUpdateVideos(@RequestBody EventPost body) {

        String event = body.getEvent();
        String bvid = body.getBvid();

        //当用户未传来这些参数时，仍可以顺利构造Topvideo，但是String的值是null还是""?
        if (bvid == null || event == null) {
            //logging...
			Log error = new Log(ErrorCode.Video_Update_Error, "Invalid Parameters");
			log_repository.save(error);
            return new ResponseEntity<>("Invalid parameters.", HttpStatus.BAD_REQUEST);
        }

        if (!video_repository.existsByBvid(bvid)) {
            //logging...
			Log error = new Log(ErrorCode.Video_Update_Error, "Invalid Video");
			log_repository.save(error);
            return new ResponseEntity<>("Video is not in the table. Do nothing.", HttpStatus.OK);
        }

        TopVideo video = video_repository.findByBvid(bvid).get(0);

        //根据不同事件，增加不同分值，等待拓展更多事件
        int additionScore = 0;
		switch (event) {
			case "longEnough":
				//观看时长达到一定百分比
				additionScore = 2;
				break;
			case "like":
				//点赞
				additionScore = 4;
				break;
			case "coin":
				//投币
				additionScore = 6;
				break;
			case "favorite":
				//收藏
				additionScore = 8;
				break;
			case "share":
				//分享
				additionScore = 16;
				break;
			default:
				//不合法的事件
				Log error = new Log(ErrorCode.Video_Update_Error, "Invalid Event");
				log_repository.save(error);
				return new ResponseEntity<>("Event " + event + " is not defined.", HttpStatus.BAD_REQUEST);
		}
        video.setScore(video.getScore() + additionScore);

        //更新播放量(可选)
        if (body.getPlay() != null) {
            try {
                video.setPlay(Integer.parseInt(body.getPlay()));
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //logging... 播放量有误
				Log error = new Log(ErrorCode.Video_Update_Error, "Invalid play parameter");
				log_repository.save(error);
                return new ResponseEntity<>("Play is not a valid number.", HttpStatus.BAD_REQUEST);
            }
            //如果播放量过高，直接加分加到最高分
            if (video.getPlay() > 100) {
                video.setScore(70);
            }
        }

        //save 能实现覆盖更新，而不用先删除再插入
        video_repository.save(video);

        return new ResponseEntity<>("Update score of video successfully.", HttpStatus.OK);
    }

    @PostMapping(value = "/videos")
    public ResponseEntity saveVideos(@RequestBody Map<String, List<TopVideo>> body) {
        try {
            //post body的格式为{content:[TopVideo1, TopVideo2]}
            //将所有的TopVideo存入表中，可能存在空或不合法的body的情况，要处理异常
            for (int i = 0; i < body.get("content").size(); i++) {
                TopVideo v = body.get("content").get(i);
                if (!video_repository.existsByBvid(v.getBvid())) {
                    //播放量应该在前端设置好
                    v.setScore(initialScore);
                }
            }
            video_repository.saveAll(body.get("content"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity<>("Videos have been added failed.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Videos have been added successfully.", HttpStatus.OK);
    }

    @PostMapping(value = "/log")
    public ResponseEntity saveLog(@RequestBody Log log_info) {
        try {
            if (!ErrorCode.list.contains(log_info.getErrCode())) {
                Log error = new Log(ErrorCode.Log_Post_Error, "Invalid Error Code");
                log_repository.save(error);
                return new ResponseEntity<>("Invalid Error Code", HttpStatus.BAD_REQUEST);
            }
            log_repository.save(log_info);
            return new ResponseEntity<>("Log added successfully", HttpStatus.OK);
        } catch (Exception e) {
            //TODO: exception handling
            //试了一下如果没有body的话 不是走这里handle的 所以不会加到数据库里...
            Log error = new Log(ErrorCode.Log_Post_Error, e.toString());
            log_repository.save(error);
            return new ResponseEntity<>("Log added failed", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/log")
    public List<Log> getLog() {
        try {
            return log_repository.findAll();
        } catch (Exception e) {
            //TODO: exception handling
            Log error = new Log(ErrorCode.Log_Get_Error, e.toString());
            log_repository.save(error);
            return new ArrayList<Log>();
        }
    }
}
