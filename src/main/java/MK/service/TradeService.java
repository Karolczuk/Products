package MK.service;

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
import MK.validator.impl.model.TradeModelValidator;

public class TradeService {

    private final TradeRepository tradeRepository;
    private final TradeModelValidator tradeModelValidator;
    private final ModelMappers modelMapper;


    public TradeService(
            TradeRepository tradeRepository,
            TradeModelValidator tradeModelValidator,
            ModelMappers modelMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeModelValidator = tradeModelValidator;
        this.modelMapper = modelMapper;
    }

    public void addTrade(TradeDto tradeDto) {

        if (tradeDto == null) {
            throw new MyException(ExceptionCode.TRADE, "TRADE OBJECT IS NULL");
        }

        if (!tradeModelValidator.validateTradeFields(tradeDto)) {
            throw new MyException(ExceptionCode.TRADE, "TRADE FIELDS ARE NOT VALID");
        }

        Trade trade = modelMapper.fromTradeDtoToTrade(tradeDto);

        tradeRepository.saveOrUpdate(trade);


    }
}
