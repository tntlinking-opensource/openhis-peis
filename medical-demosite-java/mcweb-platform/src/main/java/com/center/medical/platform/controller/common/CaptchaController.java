package com.center.medical.platform.controller.common;

import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.common.utils.sign.Base64;
import com.center.medical.common.utils.uuid.IdUtils;
import com.center.medical.system.service.ISysConfigService;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码操作处理
 *
 * @author 路飞
 */
@RestController
@Api(tags = "验证码操作")
@RequiredArgsConstructor
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;
    @Resource
    private ISysConfigService configService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    @ApiOperation(value = "生成验证码", notes = "生成验证码")
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return ajax;
        }
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = ZhongkangConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        RedisUtil.set(verifyKey, code, Constants.CAPTCHA_EXPIRATION);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }
}
