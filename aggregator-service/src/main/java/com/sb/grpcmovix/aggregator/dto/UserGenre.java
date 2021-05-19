package com.sb.grpcmovix.aggregator.dto;

import lombok.Data;

@Data
public class UserGenre {

    private String loginId;
    private String genre;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
