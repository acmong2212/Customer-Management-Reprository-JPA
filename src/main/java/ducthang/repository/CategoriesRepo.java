package ducthang.repository;

import ducthang.model.Categories;
import org.springframework.data.repository.CrudRepository;

public interface CategoriesRepo extends CrudRepository<Categories, Integer> {
}
