package com.kalash.StudentBooks.controllers;

import com.kalash.StudentBooks.jwt.JwtHelper;
import com.kalash.StudentBooks.models.JwtRequest;
import com.kalash.StudentBooks.models.JwtResponse;
import com.kalash.StudentBooks.models.Student;
import com.kalash.StudentBooks.models.responses.ErrorResponse;
import com.kalash.StudentBooks.models.responses.SuccessResponse;
import com.kalash.StudentBooks.services.StudentService;
import com.kalash.StudentBooks.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private AuthenticationManager manager;

    @GetMapping("/all")
    public List<Student> all(){
        return studentService.all();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Student student){
        Boolean doesExist = studentService.doesExistInDb(student.getEmail());
        if(!doesExist){
            Student createdStudent = studentService.createStudent(student);
            return new ResponseEntity<>(new SuccessResponse<>(201, createdStudent), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ErrorResponse(400, "User already exists with the same email address"),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        //authenticate
        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = new JwtResponse(userDetails.getUsername(), token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credentials Invalid !!");
        }
    }
}
