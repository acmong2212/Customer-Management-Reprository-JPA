package ducthang.service;

import ducthang.model.Categories;
import ducthang.repository.CategoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService implements ICategoriesService{
    @Autowired
    CategoriesRepo categoriesRepo;

    @Override
    public List<Categories> findAllCategories() {
        return (List<Categories>) categoriesRepo.findAll();
    }
}
