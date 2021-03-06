package com.zoothii.finaljavabackend.api.controllers;

import com.zoothii.finaljavabackend.business.abstracts.UserService;
import com.zoothii.finaljavabackend.core.entities.Role;
import com.zoothii.finaljavabackend.core.entities.User;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.ErrorDataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public DataResult<List<User>> getUsers(){
        return this.userService.getUsers();
    }

    @DeleteMapping("user")
    public ResponseEntity<Result> deleteUSer(@Valid @RequestBody User user) {
        var result = this.userService.deleteUser(user);
        if (!result.isSuccess()){
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("setnewroles")
    public ResponseEntity<Result> setNewRolesToUser(@RequestParam String username, @RequestBody Set<Role> roles){
        return new ResponseEntity<>(userService.setNewRolesToUser(username, roles), HttpStatus.OK);
    }

    @PostMapping("setroles")
    public ResponseEntity<Result> setRolesToUser(@RequestParam String username, @RequestBody Set<Role> roles){
        return new ResponseEntity<>(userService.setRolesToUser(username, roles), HttpStatus.OK);
    }
    


    /*@PostMapping("user")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(this.userService.createUser(user));

    }*/

    /*@PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public DataResult<User> createUser(@Valid @RequestBody User user) { // to validation add @Valid annotation
        return this.userService.createUser(user);

    }*/

    /*@PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public DataResult<User> createUser(@RequestBody User user) {
        return this.userService.createUser(user);

    }*/

    /*@GetMapping(value = "users/", params = "eMail")
    public DataResult<User> getByEMail(@RequestParam String eMail) {
        return this.userService.getByEMail(eMail);
    }*/




    // ****** VALIDATION ******
    // todo tek seferlik kullan??m haline getir
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> validationExceptionHandler(MethodArgumentNotValidException exception){
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ErrorDataResult<>(validationErrors, "validation errors");
    }


}
