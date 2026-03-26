package com.sqc.academy;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Lession2 {
	private final Map<String, String> lsMap = Map.ofEntries(
			Map.entry("hello", "xin chao"),
			Map.entry("motorbike", "xe may"),
			Map.entry("teacher", "giao vien"),
			Map.entry("age", "tuoi"),
			Map.entry("egg", "trung"),
			Map.entry("oh my god", "oi chua oi"),
			Map.entry("india", "anhdomixi")
	);

	@GetMapping("/dictionary")
	public ResponseEntity<String> dictionary(@RequestParam(defaultValue = "") String word) {
		String translation = lsMap.get(word.trim().toLowerCase());

		//neu tim thay thi tra ve trang thai 200
		if (translation != null) {
			return ResponseEntity.ok(translation);
		}

		//neu khong tim thay thif tra ve tb loi, trang thai 404
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Khong tim thay du lieu trong tu dien");
	}

}
