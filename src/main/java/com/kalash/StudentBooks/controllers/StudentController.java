package com.kalash.StudentBooks.controllers;

import com.kalash.StudentBooks.models.Student;
import com.kalash.StudentBooks.models.responses.ErrorResponse;
import com.kalash.StudentBooks.models.responses.SuccessResponse;
import com.kalash.StudentBooks.services.StudentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(
            @RequestBody Student student,
            @PathVariable ObjectId id
    ) {
        Student updatedStudent = studentService.updateStudent(student, id);
        if (updatedStudent != null) {
            return new ResponseEntity<>(new SuccessResponse<>(200, updatedStudent), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse(404, "Student not found with id: " + id), HttpStatus.NOT_FOUND);
        }
    }
}
