package com.kalash.StudentBooks.services;

import com.kalash.StudentBooks.models.Student;
import com.kalash.StudentBooks.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(email);

        if (student != null) {
            return User
                    .builder()
                    .username(student.getEmail())
                    .password(student.getPassword())
                    .build();
        } else {
            throw new UsernameNotFoundException("Student not found with email " + email);
        }
    }
}
