package com.center.medical.ai.admin.controller;

import com.center.medical.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 路飞
 * @date: 2025/03/4 15:57
 * @description: AI智能助手接口
 */
@RestController
@RequestMapping("/open/api/ai")
@Api(tags = "AI智能助手")
@RequiredArgsConstructor
@Validated
public class ChatController {



    /**
     * AI聊天
     * @param message 聊天内容
     * @return
     */
    @PostMapping("/chat")
    @ApiOperation(value = "聊天", notes = "AI助手聊天")
    public R insert(String message) {
        return R.ok(message);
    }
}
