/**
 * 
 */
package xyz.zwhzwhzwh.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import xyz.zwhzwhzwh.models.TopVideo;

/**
 * @author zwh
 *
 */
public interface VideoRepository extends MongoRepository<TopVideo, String> {
	//声明一些操作视频热度表的方法
	
	//统计方法
	long count();
	
	//增
	//已有的接口不需要再次声明
	//List<TopVideo> saveAll(List<TopVideo> videos);
	//@SuppressWarnings("unchecked")
	//TopVideo save(TopVideo video);
		
	//删
	//void delete(TopVideo video);
	void deleteByBvid(String bvid);
		
	//没有直接提供修改的方法
	//修改指定bvid的记录需要：findByBvid -> delete -> save	

	//查
	List<TopVideo> findByBvid(String bvid);
	List<TopVideo> findByScoreBetween(int from, int to);
	//检查指定视频是否存在
	boolean existsByBvid(String bvid);
	
}
