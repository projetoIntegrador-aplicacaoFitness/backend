package com.generation.projetoFitness.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.projetoFitness.model.Usuario;
import com.generation.projetoFitness.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/usuarios")
@CrossOrigin (origins = "*", allowedHeaders="*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> getAll(){
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id){
		return usuarioRepository.findById(id).map(resposta->ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());	
	}
	
	@PostMapping
	public ResponseEntity<Usuario> post(@Valid @RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
	}
	
	@PutMapping
	public ResponseEntity<Usuario> put(@Valid @RequestBody Usuario usuario){
		if (usuarioRepository.existsById(usuario.getId())){
			return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuario));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} else {
			usuarioRepository.deleteById(id);
		}
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Usuario>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(usuarioRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
}
