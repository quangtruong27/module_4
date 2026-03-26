package com.sqc.academy;

import com.sqc.academy.model.Employee;
import com.sqc.academy.model.Gender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	private final List<Employee> employees = new ArrayList<>(
			Arrays.asList(
					new Employee(UUID.randomUUID(), "Hoàng Văn Hải", LocalDate.of(1990, 1, 15), Gender.MALE, 15000000.00, "0975123542"),
					new Employee(UUID.randomUUID(), "Trần Thị Hoài", LocalDate.of(1985, 5, 20), Gender.FEMALE, 14500000.00, "0967869868"),
					new Employee(UUID.randomUUID(), "Lê Văn Sỹ", LocalDate.of(1992, 3, 10), Gender.MALE, 15500000.00, "0988881110"),
					new Employee(UUID.randomUUID(), "Phạm Duy Khánh", LocalDate.of(1988, 7, 5), Gender.FEMALE, 14800000.00, "0986555333"),
					new Employee(UUID.randomUUID(), "Hoàng Văn Quý", LocalDate.of(1995, 9, 25), Gender.MALE, 15200000.00, "0973388668")
			)
	);


	@GetMapping
	public ResponseEntity<List<Employee>> getAll() {
		// Trả về danh sách nhân viên với mã trạng thái HTTP 200 (OK)
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getById(@PathVariable("id") UUID id) { // @PathVariable lấy biến {id} từ URL
		return employees.stream() // Mở luồng xử lý dữ liệu (Stream API)
				.filter(e -> e.getId().equals(id)) // Lọc ra nhân viên có ID trùng với ID truyền vào
				.findFirst() // Lấy nhân viên đầu tiên tìm thấy
				.map(ResponseEntity::ok) // Nếu tìm thấy, bọc vào ResponseEntity với mã 200 (OK)
				.orElse(ResponseEntity.notFound().build()); // Nếu không tìm thấy, trả về mã 404 (Not Found)
	}

	@PostMapping
	public ResponseEntity<Employee> create(@RequestBody Employee employee) { // @RequestBody hứng dữ liệu JSON từ client và chuyển thành object Employee
		// Tự động tạo một ID mới ngẫu nhiên và gán cho nhân viên này
		employee.setId(UUID.randomUUID());
		// Thêm nhân viên mới vào danh sách
		employees.add(employee);
		// Trả về thông tin nhân viên vừa tạo cùng với mã trạng thái HTTP 201 (Created)
		return ResponseEntity.status(HttpStatus.CREATED).body(employee);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> update(@PathVariable("id") UUID id, @RequestBody Employee employee) {
		return employees.stream()
				.filter(e -> e.getId().equals(id)) // Tìm nhân viên theo ID
				.findFirst()
				.map(e -> { // Nếu tìm thấy (biến 'e' là nhân viên cũ trong list)
					// Cập nhật các thông tin của 'e' bằng dữ liệu mới từ 'employee' (được gửi lên qua body)
					e.setName(employee.getName());
					e.setDob(employee.getDob());
					e.setGender(employee.getGender());
					e.setSalary(employee.getSalary());
					e.setPhone(employee.getPhone());
					// Trả về thông tin đã cập nhật với mã 200 (OK)
					return ResponseEntity.ok(e);
				})
				.orElse(ResponseEntity.notFound().build()); // Nếu không tìm thấy ID, trả về 404
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
		return employees.stream()
				.filter(e -> e.getId().equals(id)) // Tìm nhân viên theo ID
				.findFirst()
				.map(s -> { // Nếu tìm thấy (biến 's' là nhân viên cần xóa)
					// Xóa nhân viên đó khỏi danh sách
					employees.remove(s);
					// Trả về mã 200 (OK) nhưng không có nội dung body (build rỗng)
					return ResponseEntity.ok().build();
				})
				.orElse(ResponseEntity.notFound().build()); // Nếu không tìm thấy, trả về 404
	}
}