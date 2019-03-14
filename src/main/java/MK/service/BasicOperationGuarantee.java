package MK.service;

import MK.dto.GuaranteeComponentsDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.GuaranteeComponents;
import MK.repository.impl.GuaranteeRepository;
import MK.repository.impl.ProducerRepository;
import MK.repository.impl.ProductRepository;
import MK.validator.ManagmentProductsValidator;

public class BasicOperationGuarantee {

    private final GuaranteeRepository guaranteeRepository;
    private final ProductRepository productRepository;
    private final ManagmentProductsValidator managmentProductsValidator;
    private final ModelMappers modelMapper;


    public BasicOperationGuarantee(
            GuaranteeRepository guaranteeRepository,
            ProductRepository productRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper) {
        this.guaranteeRepository =guaranteeRepository;
        this.productRepository = productRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
    }

    public void addGuarantee(GuaranteeComponentsDto guaranteeComponentsDto) {

        if (guaranteeComponentsDto== null) {
            throw new MyException(ExceptionCode.GUARANTEECOMPONENTS, "GUARANTEE_COMPONENTS OBJECT IS NULL");
        }

        if (managmentProductsValidator.validateGuaranteeInsideDB(guaranteeComponentsDto)) {
            throw new MyException(ExceptionCode.GUARANTEECOMPONENTS, "GUARANTEE_COMPONENTS ALREADY EXISTS");
        }

        GuaranteeComponents guarantee = modelMapper.fromGuaranteeDtoToGuarantee(guaranteeComponentsDto);

        guaranteeRepository.saveOrUpdate(guarantee);


    }
}
