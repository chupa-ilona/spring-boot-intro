package spring.springbootintro.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import spring.springbootintro.exception.EntityNotFoundException;
import spring.springbootintro.model.Book;
import spring.springbootintro.repository.BookRepository;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book save(Book book) {
        entityManager.persist(book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.id = :id", Book.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
}
