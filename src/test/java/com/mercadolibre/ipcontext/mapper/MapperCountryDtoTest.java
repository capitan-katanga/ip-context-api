package com.mercadolibre.ipcontext.mapper;

import com.mercadolibre.ipcontext.dto.fixerapi.FixerApiDto;
import com.mercadolibre.ipcontext.dto.geographyapi.GeographyApiDto;
import com.mercadolibre.ipcontext.dto.ipapi.IpApiDto;
import com.mercadolibre.ipcontext.dto.ipcontext.IpContextResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.mercadolibre.ipcontext.util.DataMock.ipApiDto;
import static com.mercadolibre.ipcontext.util.DataMock.geographyApiDto;
import static com.mercadolibre.ipcontext.util.DataMock.fixerApiDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MapperCountryDtoTest {

        @InjectMocks
        private MapperCountryDto mapperCountryDto;

        @Test
        void toCountryInformationDto() {

                IpContextResponseDto result = mapperCountryDto.toCountryInformationDto(ipApiDto, geographyApiDto,
                                fixerApiDto);

                assertEquals("127.0.0.1", result.ipAddress());
                assertEquals("Argentina", result.countryName());
                assertEquals("ARG", result.isoCode());
                assertEquals("USD", result.base());
                assertEquals(Map.of("ARS", 100.0), result.localCurrencyAndRate());
        }
}
