package com.mercadolibre.ipcontext.service;

import com.mercadolibre.ipcontext.exception.IpAddressIsBannedException;
import com.mercadolibre.ipcontext.mapper.MapperBlacklist;
import com.mercadolibre.ipcontext.repository.IpAddressBlacklistRepo;
import com.mercadolibre.ipcontext.service.impl.IpBlacklistServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mercadolibre.ipcontext.util.DataMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IpBlacklistServiceImplTest {

        @Mock
        private IpAddressBlacklistRepo ipAddressBlacklistRepo;

        @Mock
        private MapperBlacklist mapperBlacklist;

        @InjectMocks
        private IpBlacklistServiceImpl ipBlacklistService;

        @Test
        void banIpSuccess() {

                when(ipAddressBlacklistRepo.findByIpAddress(anyString()))
                                .thenReturn(Optional.empty());

                when(mapperBlacklist.toIpAddressBlacklist(any()))
                                .thenReturn(IP_ADDRESS_BLACKLIST);

                when(ipAddressBlacklistRepo.save(any()))
                                .thenReturn(IP_ADDRESS_BLACKLIST);

                when(mapperBlacklist.toGetIpBlacklistDto(any()))
                                .thenReturn(GET_IP_BLACKLIST_DTO);

                var getIpBlacklistDto = ipBlacklistService.banIpAddress(ADD_IP_BLACKLIST_DTO);

                assertEquals(GET_IP_BLACKLIST_DTO, getIpBlacklistDto);
        }

        @Test
        void banIpBaned() {

                when(ipAddressBlacklistRepo.findByIpAddress(anyString()))
                                .thenReturn(Optional.of(IP_ADDRESS_BLACKLIST));

                var exception = assertThrows(IpAddressIsBannedException.class,
                                () -> ipBlacklistService.banIpAddress(ADD_IP_BLACKLIST_DTO));

                assertEquals("The ip address: " + IP_ADDRESS + " is already banned.",
                                exception.getMessage());
        }

}
