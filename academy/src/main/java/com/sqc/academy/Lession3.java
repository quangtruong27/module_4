package com.sqc.academy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Lession3 {
	@GetMapping("/calculator")
	public ResponseEntity<String> caculator(
			@RequestParam(value = "so1", defaultValue = "") String firstNumber,
			@RequestParam(value = "so2", defaultValue = "") String secondNumber,
			@RequestParam(value = "operator", defaultValue = "") String operator) {

		if (firstNumber.isEmpty()) {
			return ResponseEntity.badRequest().body("So dau tien khong duoc de trong");
		} else if (secondNumber.isEmpty()) {
			return ResponseEntity.badRequest().body("So thu 2 khong duoc de trong");
		} else if (!isDouble(firstNumber)) {
			return ResponseEntity.badRequest().body("So dau tien phai la so");
		} else if (!isDouble(secondNumber)) {
			return ResponseEntity.badRequest().body("So thu 2 phai la so");
		}

		double first = Double.parseDouble(firstNumber);
		double second = Double.parseDouble(secondNumber);
		double result;

		switch (operator) {
			case "+":
				result = first + second;
				break;
			case "-":
				result = first - second;
				break;
			case "*":
				result = first * second;
				break;
			case  "/":
				if (second == 0) {
					return ResponseEntity.badRequest().body("Khong duoc chia cho so 0");
				}
				result = first / second;
				break;
			default:
				return ResponseEntity.badRequest().body("khong hop le");
		}
		return ResponseEntity.ok("Result: " + result);
	}

	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
