package spring.springbootintro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.springbootintro.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
