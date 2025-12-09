package com.mercadolibre.ipcontext.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageCustom {
    private Timestamp timestamp;
    private Integer code;
    private String detail;
}
