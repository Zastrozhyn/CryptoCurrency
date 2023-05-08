package com.idfinance.service.mapper;

import com.idfinance.repository.entity.CurrentCrypto;
import com.idfinance.service.dto.CurrentCryptoDto;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface CurrentCryptoMapper {
    CurrentCrypto mapToEntity(CurrentCryptoDto model);

    CurrentCryptoDto mapToDto(CurrentCrypto entity);

    List<CurrentCryptoDto> mapToDto(Collection<CurrentCrypto> entities);
}
