package com.api.rest.relaciones.bi.Controller;

import com.api.rest.relaciones.bi.Repository.LibraryRepository;
import com.api.rest.relaciones.bi.entity.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryRepository libraryRepository;

    @PostMapping
    public ResponseEntity<Library> save(@Valid @RequestBody Library library){

        Library librarySave =libraryRepository.save(library);

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(librarySave.getId()).toUri();
        return  ResponseEntity.created(ubicacion).body(librarySave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> update(@PathVariable Long id,@Valid @RequestBody Library library){
        Optional<Library> library1 =libraryRepository.findById(id);
        if(!library1.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        library1.map(library2 -> {
            library2.setNombre(library.getNombre());
            return libraryRepository.save(library2);
        });

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Library> library1 =libraryRepository.findById(id);
        if(!library1.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
             libraryRepository.deleteById(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> getId(@PathVariable Long id){

        Optional<Library> library1 =libraryRepository.findById(id);
        if(!library1.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(library1.get());
    }
    @GetMapping
    public ResponseEntity<Page<Library>> getAll(Pageable  pageable){

        return ResponseEntity.ok(libraryRepository.findAll(pageable));
    }


}
