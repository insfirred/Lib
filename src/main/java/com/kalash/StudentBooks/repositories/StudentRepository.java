package com.kalash.StudentBooks.repositories;

import com.kalash.StudentBooks.models.Student;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, ObjectId> {
    public Student findByEmail(String email);
}
