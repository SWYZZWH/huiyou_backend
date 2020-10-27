package xyz.zwhzwhzwh.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xyz.zwhzwhzwh.models.HistoryRecord;
import xyz.zwhzwhzwh.repositories.RecordRepository;

/**
 * 解析url，并调用对应的方法操作数据库
 * */
@RestController
@RequestMapping("/api")
public class RecordController {
	
	@Autowired
	private RecordRepository record_repository;
	
	@GetMapping(value = "/all")
	public List<HistoryRecord> getAllHistoryRecords(){
		return record_repository.findAll();
	}
	
	@GetMapping(value = "/records")
	public List<HistoryRecord> getAllHistoryRecords(@RequestParam Map<String, String> queryParams){
		try {
			String uid = queryParams.get("uid");
			String bvid = queryParams.get("bvid");
			if(uid == null && bvid == null)
				return record_repository.findAll();
			else if(uid == null)
				return record_repository.findByBvid(bvid);
			else if(bvid == null)
				return record_repository.findByUid(uid);
			else
				return record_repository.findByUidAndBvid(uid, bvid);
		} catch (Exception e) {
			return new ArrayList<HistoryRecord>();
		}
	}

	/*
	@GetMapping(value = "/byUid/{uid}")
	public List<HistoryRecord> getHistoryRecordsByUid(@PathVariable("uid") String uid) {
		return record_repository.findByUid(uid);
	}

	@GetMapping(value = "/byBv/{bv}")
	public List<HistoryRecord> getHsHistoryRecordsByBv(@PathVariable("bv") String bv){
		return record_repository.findByBv(bv);
	}
	*/
	
	@PostMapping(value = "/records")
	public ResponseEntity<?> saveOrUpdateRecord(@RequestBody HistoryRecord history_record){
		try {
			record_repository.save(history_record);
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		return new ResponseEntity("Record added successfully", HttpStatus.OK);
	}
	
}
