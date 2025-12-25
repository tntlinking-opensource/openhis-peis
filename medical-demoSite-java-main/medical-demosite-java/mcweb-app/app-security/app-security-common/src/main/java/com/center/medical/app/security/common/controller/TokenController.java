package com.center.medical.app.security.common.controller;

import com.center.medical.app.security.common.bo.TokenInfoBO;
import com.center.medical.app.security.common.dto.RefreshTokenDTO;
import com.center.medical.app.security.common.manager.TokenStore;
import com.center.medical.app.security.common.vo.TokenInfoVO;
import io.swagger.annotations.Api;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author FrozenWatermelon
 * @date 2020/6/30
 */
@RestController
@Api(tags = "token")
public class TokenController {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private MapperFacade mapperFacade;

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenInfoVO> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        TokenInfoBO tokenInfoServerResponseEntity = tokenStore
                .refreshToken(refreshTokenDTO.getRefreshToken());
        return ResponseEntity
                .ok(mapperFacade.map(tokenInfoServerResponseEntity, TokenInfoVO.class));
    }

}
