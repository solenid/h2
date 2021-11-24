package com.memory.h2;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


    @RestController
    @RequestMapping("/api")
    public class UserController {
        private final Repository userRepository;
        public UserController(Repository userRepository) {
            this.userRepository = userRepository;
        }
        @GetMapping("/users")
        public List<User> getAllUsers(@RequestParam(required = false) String username) {
            List<User> users = new ArrayList<User>();
            if (username == null) {
                users.addAll(userRepository.findAll());
            } else {
                users.addAll(userRepository.findByUsernameContaining(username));
            }
            return users;
        }
        @GetMapping("/users/{id}")
        public User getUserById(@PathVariable("id") long id) {
            Optional<User> userData = userRepository.findById(id);
            if (userData.isPresent()) {
                return userData.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        @PostMapping("/users")
        public void createUser(@RequestBody User user) {
            List<User> users = new ArrayList<User>(userRepository.findByUsernameContaining(user.getUsername()));
            if(users.size()!=0){
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }else {
                userRepository.save(new User(user.getUsername(), user.getPassword(), user.getAge()));
            }

        }
        @PutMapping("/users/{id}")
        public void updateUser(@PathVariable("id") long id, @RequestBody User user) {
            Optional<User> userData = userRepository.findById(id);
            if (userData.isPresent()) {
                User _user = userData.get();
                _user.setUsername(user.getUsername());
                _user.setPassword(user.getPassword());
                _user.setAge(user.getAge());

            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        @DeleteMapping("/users/{id}")
        public void deleteUser(@PathVariable("id") long id) {
            Optional<User> userData = userRepository.findById(id);
            if (userData.isPresent()) {
                userRepository.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }


        }
        @DeleteMapping("/users")
        public void deleteAllUsers() {
            userRepository.deleteAll();

        }


    }

