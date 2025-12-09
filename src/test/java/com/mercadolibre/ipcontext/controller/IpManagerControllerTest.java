package com.mercadolibre.ipcontext.controller;

import com.mercadolibre.ipcontext.exception.ClientApiErrorException;
import com.mercadolibre.ipcontext.service.impl.IpBlacklistServiceImpl;
import com.mercadolibre.ipcontext.service.impl.IpContextServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static com.mercadolibre.ipcontext.util.DataMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IpManagerControllerTest {

        @Mock
        private IpContextServiceImpl ipContextService;

        @Mock
        private IpBlacklistServiceImpl ipBlacklistService;

        @InjectMocks
        private IpManagerController ipManagerController;

        @Test
        void getIpInfoSuccess() {

                when(ipContextService.getIpContext(anyString()))
                                .thenReturn(IP_CONTEXT_RESPONSE_DTO);

                var response = ipManagerController.getIpInfo(IP_ADDRESS);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(IP_CONTEXT_RESPONSE_DTO, response.getBody());
        }

        @Test
        void clientRequestException() {
                when(ipContextService.getIpContext(anyString()))
                                .thenThrow(new ClientApiErrorException("Error"));

                assertThrows(ClientApiErrorException.class, () -> ipManagerController.getIpInfo(IP_ADDRESS));
        }

        @Test
        void addIpAddressToBlacklistSuccess() {

                when(ipBlacklistService.banIpAddress(any()))
                                .thenReturn(GET_IP_BLACKLIST_DTO);

                var response = ipManagerController.addIpAddressToBlacklist(ADD_IP_BLACKLIST_DTO);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(GET_IP_BLACKLIST_DTO, response.getBody());
        }

}
