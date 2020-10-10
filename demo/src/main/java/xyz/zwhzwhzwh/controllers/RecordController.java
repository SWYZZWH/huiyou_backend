package xyz.zwhzwhzwh.controllers;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.zwhzwhzwh.models.HistoryRecord;
import xyz.zwhzwhzwh.repositories.RecordRepository;

/**
 * 解析url，并调用对应的方法操作数据库
 * */
@RestController
@RequestMapping("/api/records")
public class RecordController {
	
	@Autowired
	private RecordRepository record_repository;
	
	@GetMapping(value = "/")
	public List<HistoryRecord> getAllHistoryRecords(){
		return record_repository.findAll();
	}
	
	@GetMapping(value = "/byUid/{uid}")
	public List<HistoryRecord> getHistoryRecordsByUid(@PathVariable("uid") String uid) {
		return record_repository.findByUid(uid);
	}
	
	@GetMapping(value = "/byBv/{bv}")
	public List<HistoryRecord> getHsHistoryRecordsByBv(@PathVariable("bv") String bv){
		return record_repository.findByBv(bv);
	}
	
	@PostMapping(value = "/save")
	public ResponseEntity<?> saveOrUpdateRecord(@RequestBody HistoryRecord history_record){
		record_repository.save(history_record);
		return new ResponseEntity("Record added successfully", HttpStatus.OK);
	}
	
}
