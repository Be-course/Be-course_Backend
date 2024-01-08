package com.becourse.domain.user.dto;

import lombok.Data;

@Data
public class DAuthBaseResponse {
    private Long status;
    private String message;
    private DAuthResourceResponse data;
}
