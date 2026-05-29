package spring.springbootintro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.springbootintro.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
