package com.api.rest.relaciones.bi.Controller;

import com.api.rest.relaciones.bi.Repository.BookRepository;
import com.api.rest.relaciones.bi.Repository.LibraryRepository;
import com.api.rest.relaciones.bi.entity.Book;
import com.api.rest.relaciones.bi.entity.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibraryRepository libraryRepository;

    @PostMapping
    public ResponseEntity<Book> save(@Valid @RequestBody Book book){
        Optional<Library> libraryOptional =libraryRepository.findById(book.getLibrary().getId());
        if (!libraryOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        book.setLibrary(libraryOptional.get());
        Book bookSave =bookRepository.save(book);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookSave.getId()).toUri();
        return  ResponseEntity.created(ubicacion).body(bookSave);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updatelibro(@PathVariable Long id,@Valid @RequestBody Book book){
        Optional<Library> libraryOptional =libraryRepository.findById(book.getLibrary().getId());
        if (!libraryOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Book> bookOptional =bookRepository.findById(id);
        if (!bookOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        bookOptional.map(book1 -> {
            book1.setNombre(book.getNombre());
            book1.setLibrary(libraryOptional.get());
            return bookRepository.save(book1);
        });

        return  ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){

        Optional<Book> bookOptional =bookRepository.findById(id);
        if(!bookOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        bookRepository.delete(bookOptional.get());

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAll(Pageable pageable){
        return ResponseEntity.ok(bookRepository.findAll(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id){
        Optional<Book> bookOptional =bookRepository.findById(id);
        if(!bookOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(bookOptional.get());

    }


}
