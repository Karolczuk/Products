package MK.repository.impl;

import MK.model.Category;
import MK.repository.generic.GenericRepository;

import java.util.Optional;

public interface CategoryRepository extends GenericRepository<Category> {
    Optional<Category> findByName(String name);

}
