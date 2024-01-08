package com.becourse.domain.user.dto;

import lombok.Data;

@Data
public class DAuthResourceResponse {
    private String uniqueId;

    private String email;

    private String name;

    private String provider = "dauth";
}
