package MK.service;

import MK.dto.CategoryDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Category;
import MK.repository.impl.CategoryRepository;
import MK.validator.ManagmentProductsValidator;

public class BasicOperationCategory {
    private final CategoryRepository categoryRepository;
    private final ManagmentProductsValidator managmentProductsValidator;
    private final ModelMappers modelMapper;


    public BasicOperationCategory(
            CategoryRepository categoryRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper) {
        this.categoryRepository = categoryRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
    }

    public void addCategory(CategoryDto categoryDto) {

        if (categoryDto == null) {
            throw new MyException(ExceptionCode.CATEGORY, "CATEGORY OBJECT IS NULL");
        }

        if (!managmentProductsValidator.validateCategoryFields(categoryDto)) {
            throw new MyException(ExceptionCode.CATEGORY, "CATEGORY FIELDS ARE NOT VALID");
        }

        if (managmentProductsValidator.validateCategoryInsideDB(categoryDto)) {
            throw new MyException(ExceptionCode.CATEGORY, "CATEGORY ALREADY EXISTS");
        }

        Category category = modelMapper.fromCategoryDtoToCategory(categoryDto);

        categoryRepository.saveOrUpdate(category);


    }
}
