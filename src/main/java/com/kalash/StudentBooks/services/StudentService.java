package com.kalash.StudentBooks.services;

import com.kalash.StudentBooks.models.Student;
import com.kalash.StudentBooks.repositories.StudentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder encoder;

    public List<Student> all(){
        return studentRepository.findAll();
    }

    public Boolean doesExistInDb(String email) {
        Student student = studentRepository.findByEmail(email);
        return student != null;
    }

    public Student createStudent(Student student) {
        student.setPassword(encoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    public Student updateStudent(Student newStudent, ObjectId id) {
        Optional<Student> s = studentRepository.findById(id);
        if (s.isPresent()) {
            Student studentInDb = s.get();
            studentInDb.setName(newStudent.getName());
            studentInDb.setPassword(encoder.encode(newStudent.getPassword()));
            return studentRepository.save(studentInDb);
        }
        return null;
    }
}
