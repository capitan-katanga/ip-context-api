package com.mercadolibre.ipcontext.mapper;

import com.mercadolibre.ipcontext.dto.ipaddressblacklist.AddIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.GetIpBlacklistDto;
import com.mercadolibre.ipcontext.entity.IpAddressBlacklist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MapperBlacklistTest {

    @InjectMocks
    private MapperBlacklist mapperBlacklist;

    @Test
    void toGetIpBlacklistDto() {
        LocalDateTime now = LocalDateTime.now();
        IpAddressBlacklist entity = new IpAddressBlacklist();
        entity.setIpAddress("127.0.0.1");
        entity.setBanDateActivated(now);

        GetIpBlacklistDto dto = mapperBlacklist.toGetIpBlacklistDto(entity);

        assertEquals("127.0.0.1", dto.ipAddress());
        assertEquals(now, dto.banDateActivated());
    }

    @Test
    void toIpAddressBlacklist() {
        AddIpBlacklistDto dto = AddIpBlacklistDto.builder()
                .ipAddress("127.0.0.1")
                .build();

        IpAddressBlacklist entity = mapperBlacklist.toIpAddressBlacklist(dto);

        assertEquals("127.0.0.1", entity.getIpAddress());
        // We can't strictly assert creation date is 'now' without mocking static clock
        // or accepting a range,
        // but checking it's not null is a good start for this simple mapper.
        // Or we just check the mapped field.
    }
}
