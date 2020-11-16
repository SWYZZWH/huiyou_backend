package xyz.zwhzwhzwh.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import xyz.zwhzwhzwh.models.Log;

public interface LogRepository extends MongoRepository<Log, Long> {

}
