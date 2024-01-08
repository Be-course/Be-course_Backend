package com.becourse.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleResourceResponse {
    private String id;

    private String email;

    @JsonProperty("verified_email")
    private Boolean verifiedEmail;

    private String picture;

    private String provider = "google";
}
