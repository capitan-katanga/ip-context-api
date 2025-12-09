package com.mercadolibre.ipcontext.service;

import com.mercadolibre.ipcontext.client.FixerApiClient;
import com.mercadolibre.ipcontext.client.GeographyApiClient;
import com.mercadolibre.ipcontext.client.IpApiClient;

import com.mercadolibre.ipcontext.exception.IpAddressIsBannedException;
import com.mercadolibre.ipcontext.mapper.MapperCountryDto;
import com.mercadolibre.ipcontext.service.impl.IpContextServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mercadolibre.ipcontext.util.DataMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IpContextServiceImplTest {

        @Mock
        private IpApiClient ipApiClient;
        @Mock
        private GeographyApiClient geographyApiClient;
        @Mock
        private FixerApiClient fixerApiClient;
        @Mock
        private IpBlacklistService ipBlacklistService;
        @Mock
        private MapperCountryDto mapperCountryDto;

        @InjectMocks
        private IpContextServiceImpl ipContextService;

        @Test
        void getIpContext() {
                var ipAddress = IP_ADDRESS;

                when(ipApiClient.getIpApi(anyString()))
                                .thenReturn(IP_API_DTO);
                when(geographyApiClient.getGeographyInfo(anyString()))
                                .thenReturn(GEOGRAPHY_API_DTO);
                when(fixerApiClient.getFixer(anyList(), anyString()))
                                .thenReturn(FIXER_API_DTO);
                when(ipBlacklistService.ipAddressIsBaned(anyString()))
                                .thenReturn(false);
                when(mapperCountryDto.toCountryInformationDto(any(), any(),
                                any()))
                                .thenReturn(IP_CONTEXT_RESPONSE_DTO);

                var response = ipContextService.getIpContext(ipAddress);

                assertEquals(IP_CONTEXT_RESPONSE_DTO, response);

        }

        @Test
        void getIpContextWithIpAddressBanned() {
                var ipAddress = IP_ADDRESS;
                when(ipBlacklistService.ipAddressIsBaned(anyString()))
                                .thenReturn(true);

                var exception = assertThrows(IpAddressIsBannedException.class,
                                () -> ipContextService.getIpContext(ipAddress));

                assertEquals("The ip address: " + ipAddress + " is banned.", exception.getMessage());
        }

}