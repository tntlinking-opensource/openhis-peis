package com.center.medical.center.qingdao.profession.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class JZTokenCache {

    private TokenInfo token;

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class TokenInfo {
        @NonNull
        private String token;
        private Date expiration = new Date();
    }


}
