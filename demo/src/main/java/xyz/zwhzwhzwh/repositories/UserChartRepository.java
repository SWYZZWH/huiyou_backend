package xyz.zwhzwhzwh.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.repository.query.Param;
import xyz.zwhzwhzwh.models.UserChart;

// 暂时没啥函数要写的，就先放着
public interface UserChartRepository extends MongoRepository<UserChart, String> {

    UserChart findByUid(@Param("uid") String uid);
    Boolean existsByUid(@Param("uid") String uid);
    void deleteByUid(@Param("uid") String uid);
}