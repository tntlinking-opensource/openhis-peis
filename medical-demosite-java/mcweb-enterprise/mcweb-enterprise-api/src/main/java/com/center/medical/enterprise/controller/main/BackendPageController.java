package com.center.medical.enterprise.controller.main;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.center.medical.enterprise.bean.vo.KongzhitaiDataVo;
import com.center.medical.enterprise.common.core.domain.R;
import com.center.medical.enterprise.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * QT体检者表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-08 09:31:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "后台页面")
@RequestMapping("system/backendPage")
public class BackendPageController{
    /**
     * 服务对象
     */
    private final PeispatientService peispatientService;
    private final MapperFacade mapperFacade;

    /**
     * 获取控制台界面数据
     *
     * @return 所有数据
     */
    @GetMapping("/getKongzhitaiData")
    @ApiOperation(value = "获取控制台界面数据", notes = "获取控制台界面数据")
    public R<KongzhitaiDataVo> getKongzhitaiData() {
        KongzhitaiDataVo vo = peispatientService.getKongzhitaiData();
        return R.ok(vo);
    }



}

