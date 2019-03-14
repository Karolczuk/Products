package MK.service;

import MK.dto.CountryDto;
import MK.dto.TradeDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.model.Trade;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.CustomerRepository;
import MK.repository.impl.TradeRepository;
import MK.repository.impl.TradeRepositoryImpl;
import MK.validator.ManagmentProductsValidator;

public class BasicOperationTrade {

    private final TradeRepository tradeRepository;
    private final ManagmentProductsValidator managmentProductsValidator;
    private final ModelMappers modelMapper;


    public BasicOperationTrade(
            TradeRepository tradeRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper) {
        this.tradeRepository = tradeRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
    }

    public void addTrade(TradeDto tradeDto) {

        if (tradeDto == null) {
            throw new MyException(ExceptionCode.TRADE, "TRADE OBJECT IS NULL");
        }

        if (!managmentProductsValidator.validateTradeFields(tradeDto)) {
            throw new MyException(ExceptionCode.TRADE, "TRADE FIELDS ARE NOT VALID");
        }

        if (managmentProductsValidator.validateTradeInsideDB(tradeDto)) {
            throw new MyException(ExceptionCode.TRADE, "TRADE ALREADY EXISTS");
        }

        Trade trade = modelMapper.fromTradeDtoToTrade(tradeDto);

        tradeRepository.saveOrUpdate(trade);


    }
}
