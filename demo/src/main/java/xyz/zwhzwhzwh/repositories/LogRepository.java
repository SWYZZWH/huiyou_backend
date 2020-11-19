package xyz.zwhzwhzwh.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import xyz.zwhzwhzwh.models.Log;

// 暂时没啥函数要写的，就先放着
public interface LogRepository extends MongoRepository<Log, Long> {

}
