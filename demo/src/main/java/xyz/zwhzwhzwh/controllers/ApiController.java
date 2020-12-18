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
import xyz.zwhzwhzwh.models.UserChart;
import xyz.zwhzwhzwh.models.Video;
import xyz.zwhzwhzwh.repositories.RecordRepository;
import xyz.zwhzwhzwh.repositories.VideoRepository;
import xyz.zwhzwhzwh.repositories.LogRepository;
import xyz.zwhzwhzwh.repositories.UserChartRepository;

/**
 * 解析url，并调用对应的方法操作数据库
 **/
// 错误码的定义
class ErrorCode {
    public static final Integer Record_Find_Error = 451;
    public static final Integer Record_Post_Invalid_Error = 452;
    public static final Integer Record_Save_Error = 453;
    public static final Integer Log_Post_Invalid_Error = 461;
    public static final Integer Log_Save_Error = 462;
    public static final Integer Log_Find_Error = 463;
    public static final Integer Video_Pick_Error = 471;
    public static final Integer Video_Unknown_Error = 472;
    public static final Integer Video_Update_Invalid_Error = 473;
    public static final Integer Video_Save_Error = 474;
    //用来在前端传log的时候检查是否存在 目前没有找到更好的办法解决这个空间问题
    public static final List<Integer> list = Arrays.asList(451, 452, 453, 461, 462, 463, 471, 472, 473, 474);
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

    @Autowired
    private UserChartRepository user_repository;

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
            // 错误码451
            Log error = new Log(ErrorCode.Record_Find_Error, e.toString());
            log_repository.save(error);
            return new ArrayList<HistoryRecord>();
        }
    }

    // 测试排序等功能
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
    
    //测试用api
    @PostMapping(value = "/records/all")
    public ResponseEntity saveRecords(@RequestBody Map<String, List<HistoryRecord>> body) {
        try {
            //post body的格式为{content:[TopVideo1, TopVideo2]}
            //将所有的TopVideo存入表中，可能存在空或不合法的body的情况，要处理异常
            record_repository.saveAll(body.get("content"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Records have been added failed.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Records have been added successfully.", HttpStatus.OK);
    }
    
    
    @PostMapping(value = "/records")
    public ResponseEntity<?> saveOrUpdateRecord(@RequestBody Map<String, String> queryParams) {
        try {
            String uid = queryParams.get("uid");
            String bvid = queryParams.get("bvid");
            String pic = queryParams.get("pic");
            String author = queryParams.get("author");
            String title = queryParams.get("title");
            System.out.println(uid);
            System.out.println(bvid);
            if (uid == null || bvid == null || uid.equals("") || bvid.equals("")) {
                // 错误码452
                Log error = new Log(ErrorCode.Record_Post_Invalid_Error, "Invalid record");
                log_repository.save(error);
                return new ResponseEntity<>("Record added failed", HttpStatus.BAD_REQUEST);
            }
            List<HistoryRecord> user = record_repository.findByUid(uid);
//            testing
//            System.out.println("origin:");
//            for (HistoryRecord record : user) {
//                System.out.println(record.getTime());
//            }
            if (user.size() >= 100) { //限制不能超过100个记录 超过一次删掉5个
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
            record_repository.save(new HistoryRecord(uid,bvid));
            //如果这条记录是由前端随机选取视频产生的，那么要把视频保存下来，播放量可以先置为1
            if (!video_repository.existsByBvid(bvid)) {

                TopVideo video = new TopVideo(bvid, initialScore, 1,pic,author,title);
                video_repository.save(video);
            }
            return new ResponseEntity<>("Record added successfully", HttpStatus.OK);
        } catch (Exception e) {
            // 错误码453
            Log error = new Log(ErrorCode.Record_Save_Error, e.toString());
            log_repository.save(error);
            return new ResponseEntity<>("Record added failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 不用的api暂时没有加log
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

    // 不用的api暂时没有加log
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
    
    //测试用api
    @GetMapping(value = "/videos/all")
    public List<TopVideo> getTopVideos(@RequestParam Map<String, String> queryParams) {
    	try {
			List<TopVideo> retVideos = video_repository.findAll();
			return retVideos;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<TopVideo>();
		}
    	
    }

    @GetMapping(value = "/videos")
    public ResponseEntity<?> getTopVideo(@RequestParam Map<String, String> queryParams) {
        try {  // TODO:这边确实不知道要咋包 我就先把整个do while包起来了
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
                //错误码471
                Log error = new Log(ErrorCode.Video_Pick_Error, "No Video to promote");
                log_repository.save(error);
                return new ResponseEntity<>("null", HttpStatus.OK);
            }
        }catch (Exception e) {
            //错误码472
            Log error = new Log(ErrorCode.Video_Unknown_Error, "VideoRepository Related Error");
            log_repository.save(error);
            return new ResponseEntity<>("null", HttpStatus.OK);
        }
    }

    @PatchMapping(value = "/videos") //或者patch方法 //用于更新表中视频的热度值
    public ResponseEntity<?> saveOrUpdateVideos(@RequestBody EventPost body) {

        String uid = body.getUid();
        String event = body.getEvent();
        String bvid = body.getBvid();

        //当用户未传来这些参数时，仍可以顺利构造Topvideo，但是String的值是null还是""?
        if (uid == null || bvid == null || event == null) {
            // 错误码473
            Log error = new Log(ErrorCode.Video_Update_Invalid_Error, "Invalid Parameters");
            log_repository.save(error);
            return new ResponseEntity<>("Invalid parameters.", HttpStatus.BAD_REQUEST);
        }

        if (!video_repository.existsByBvid(bvid)) {
            // 这里不算error
            return new ResponseEntity<>("Video is not in the table. Do nothing.", HttpStatus.OK);
        }

        TopVideo video = video_repository.findByBvid(bvid).get(0);

        UserChart user;
        if ((user = user_repository.findByUid(uid)) == null) {
            user = new UserChart(uid);
        }
        System.out.println(user.getUid());

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
                //不合法的事件 错误码473
                Log error = new Log(ErrorCode.Video_Update_Invalid_Error, "Invalid Event");
                log_repository.save(error);
                return new ResponseEntity<>("Event " + event + " is not defined.", HttpStatus.BAD_REQUEST);
        }
        video.setScore(video.getScore() + additionScore);
        user.addNewVideo(bvid);

        //更新播放量(可选)
        if (body.getPlay() != null) {
            try {
                video.setPlay(Integer.parseInt(body.getPlay()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // 错误码473
                Log error = new Log(ErrorCode.Video_Update_Invalid_Error, "Invalid play parameter");
                log_repository.save(error);
                return new ResponseEntity<>("Play is not a valid number.", HttpStatus.BAD_REQUEST);
            }
            //如果播放量过高，直接加分加到最高分
            if (video.getPlay() > 100) {
                video.setScore(101);
                //？这里为啥是70分
                user.moveToTop(bvid);
            }
        }

        try {
            //save 能实现覆盖更新，而不用先删除再插入
            video_repository.save(video);
            user_repository.save(user);
        } catch (Exception e){
            //错误码474
            Log error = new Log(ErrorCode.Video_Save_Error, "Save Error");
            log_repository.save(error);
            return new ResponseEntity<>("Saving failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Update score of video successfully.", HttpStatus.OK);
    }
    
    // 不用的api暂时没有加log
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
            e.printStackTrace();
            return new ResponseEntity<>("Videos have been added failed.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Videos have been added successfully.", HttpStatus.OK);
    }

    @PostMapping(value = "/log")
    public ResponseEntity saveLog(@RequestBody Log log_info) {
        try {
            if (!ErrorCode.list.contains(log_info.getErrCode())) {
                // 检查这个错误码是否有定义 错误码461
                Log error = new Log(ErrorCode.Log_Post_Invalid_Error, "Invalid Error Code");
                log_repository.save(error);
                return new ResponseEntity<>("Invalid Error Code", HttpStatus.BAD_REQUEST);
            }
            log_repository.save(log_info);
            return new ResponseEntity<>("Log added successfully", HttpStatus.OK);
        } catch (Exception e) {
            //错误码462
            //TODO: 试了一下如果没有body的话 不是走这里handle的 所以不会加到数据库里...
            Log error = new Log(ErrorCode.Log_Save_Error, e.toString());
            log_repository.save(error);
            return new ResponseEntity<>("Log added failed", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/log")
    public List<Log> getLog() {
        try {
            return log_repository.findAllByOrderByTimeDesc();
        } catch (Exception e) {
            //错误码463
            Log error = new Log(ErrorCode.Log_Find_Error, e.toString());
            log_repository.save(error);
            return new ArrayList<Log>();
        }
    }

    @GetMapping(value = "/chart")
    public List<Video> getChartVideos(@RequestParam Map<String, String> queryParams){
        try {
			String uid = queryParams.get("uid");
			if(null == uid)
				return new ArrayList<Video>();
			UserChart user = user_repository.findByUid(uid);
			if(null == user)
				return new ArrayList<Video>();
			List<Video> ret = user.getChart();
			for(Video e : ret){
			    TopVideo target = video_repository.findByBvid(e.getBvid()).get(0);
			    e.setPic(target.getPic());
			    e.setAuthor(target.getAuthor());
			    e.setTitle(target.getTitle());
			}
			return ret;
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return new ArrayList<Video>();
		}
    }

    @DeleteMapping(value = "/chart")
    public void deleteChart(@RequestParam Map<String, String> queryParams){
        String uid = queryParams.get("uid");
        if (uid == null) {
            user_repository.deleteAll();
        } else {
            user_repository.deleteByUid(uid);
        }
    }
}
