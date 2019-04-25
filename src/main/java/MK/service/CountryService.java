package MK.service;

import MK.dto.CountryDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.repository.impl.CountryRepository;
import MK.validator.impl.model.CountryModelValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryModelValidator countryModelValidator;
    private final ModelMappers modelMapper;

    public void addCountry(CountryDto countryDto) {

        if (countryDto == null) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY OBJECT IS NULL");
        }

        if (!countryModelValidator.validateCountryFields(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY FIELDS ARE NOT VALID");
        }

        Country country = modelMapper.fromCountryDtoToCountry(countryDto);

        countryRepository.saveOrUpdate(country);

    }
}







