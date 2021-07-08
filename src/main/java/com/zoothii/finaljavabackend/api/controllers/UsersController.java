package com.zoothii.finaljavabackend.api.controllers;

import com.zoothii.finaljavabackend.business.abstracts.UserService;
import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.ErrorDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    /*@PostMapping("user")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(this.userService.createUser(user));

    }*/

    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public DataResult<User> createUser(@RequestBody User user) {
        return this.userService.createUser(user);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> validationExceptionHandler(MethodArgumentNotValidException exception){
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ErrorDataResult<>(validationErrors, "validation errors");
    }



    /*@PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public DataResult<User> createUser(@RequestBody User user) {
        return this.userService.createUser(user);

    }*/

    /*@GetMapping(value = "users/", params = "eMail")
    public DataResult<User> getByEMail(@RequestParam String eMail) {
        return this.userService.getByEMail(eMail);
    }*/
}
