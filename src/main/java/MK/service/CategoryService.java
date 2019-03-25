package MK.service;

import MK.dto.CategoryDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Category;
import MK.repository.impl.CategoryRepository;
import MK.validator.impl.model.CategoryModelValidator;

public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryModelValidator categoryModelValidator;
    private final ModelMappers modelMapper;


    public CategoryService(
            CategoryRepository categoryRepository,
            CategoryModelValidator categoryModelValidator,
            ModelMappers modelMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryModelValidator = categoryModelValidator;
        this.modelMapper = modelMapper;
    }

    public void addCategory(CategoryDto categoryDto) {

        if (categoryDto == null) {
            throw new MyException(ExceptionCode.CATEGORY, "CATEGORY OBJECT IS NULL");
        }

        if (!categoryModelValidator.validateCategoryFields(categoryDto)) {
            throw new MyException(ExceptionCode.CATEGORY, "CATEGORY FIELDS ARE NOT VALID");
        }

        Category category = modelMapper.fromCategoryDtoToCategory(categoryDto);

        categoryRepository.saveOrUpdate(category);


    }
}
