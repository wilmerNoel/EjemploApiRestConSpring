package com.wilmer.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wilmer.app.entity.User;
import com.wilmer.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	//create new user
	@PostMapping
	public ResponseEntity<?> create(@RequestBody User user){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
	}
	//read an user
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id){
		Optional<User> oUser = userService.findById(id);
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oUser);
	}
	//read all user
	@GetMapping
	public List<User> readAll(){
		List<User> lista = StreamSupport
				.stream(userService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return lista;
	}
	//Update User
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody User userDetails,@PathVariable Long id){
		Optional<User> user=userService.findById(id);
		if(!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		//util las propiedades get y set
		user.get().setNombre(userDetails.getNombre());
		user.get().setEmail(userDetails.getEmail());
		user.get().setPassword(userDetails.getPassword());
		user.get().setUsername(userDetails.getUsername());
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
	}
	//delete an user
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		if(!userService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		userService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
