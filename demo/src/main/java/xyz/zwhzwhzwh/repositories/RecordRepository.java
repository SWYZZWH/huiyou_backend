package xyz.zwhzwhzwh.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import xyz.zwhzwhzwh.models.HistoryRecord;

/**
 * 这里声明的方法会在controller中调用<br>
 * 直接调用这些方法操作数据库，无需知道mongo语句的语法规则<br>
 * 这些方法只需要声明，不需要实现，spring boot会在运行中实现这些方法
 * 
 * @author zwh
 *
 */
public interface RecordRepository extends MongoRepository<HistoryRecord, ObjectId> {
	
	List<HistoryRecord> findAll();
	
	List<HistoryRecord> findByUid(@Param("uid") String uid);

	List<HistoryRecord> findByBvid(@Param("bvid") String bvid);
	
	List<HistoryRecord> findByUidAndBvid(@Param("uid") String uid, @Param("bvid") String bvid);
	
	@SuppressWarnings("unchecked")
	HistoryRecord save(HistoryRecord history_record);
	
}
