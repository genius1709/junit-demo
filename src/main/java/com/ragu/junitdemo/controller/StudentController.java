package com.ragu.junitdemo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ragu.junitdemo.entity.StudentDetails;
import com.ragu.junitdemo.repository.StudentDetailsRepository;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	private StudentDetailsRepository repository;

	@GetMapping("/students")
	public List<StudentDetails> getStudents() {
		return repository.findAll();
	}

	@GetMapping("/student/{id}")
	public StudentDetails getStudent(@PathVariable(value = "id") Long id) {
		//return repository.getById(id);
		return repository.findById(id).get();
	}
	
	@PostMapping("/student")
	public StudentDetails addStudent(@RequestBody @Validated StudentDetails student) {
		return repository.save(student);
	}
	
	@PutMapping("/student")
	public StudentDetails updateStudent(@RequestBody StudentDetails student) {
		
		if(student == null || student.getId() == null) {
			throw new IllegalArgumentException();
		}
		Optional<StudentDetails> studentDetails = repository.findById(student.getId());
		StudentDetails updatedStudent = studentDetails.get();
		updatedStudent.setName(student.getName());
		updatedStudent.setAge(student.getAge());
		updatedStudent.setAddress(student.getAddress());
		return repository.save(updatedStudent);
	}
	
	@DeleteMapping("student/{id}")
	public void deleteStudent(@RequestParam("id") Long id) {
		if(id == null) {
			throw new IllegalArgumentException();
		}
		repository.deleteById(id);
	}
	
}
