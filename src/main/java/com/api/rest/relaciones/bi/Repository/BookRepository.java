package com.api.rest.relaciones.bi.Repository;

import com.api.rest.relaciones.bi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
