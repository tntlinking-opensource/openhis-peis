package com.center.medical.reception.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SurrenderDocuments;
import com.center.medical.bean.param.SurrenderDocumentParam;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.service.SurrenderDocumentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交单记录表(SurrenderDocuments)接口
 *
 * @author ay
 * @since 2024-01-04 15:58:02
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "交单记录表")
@RequestMapping("/reception/surrenderDocuments")
public class SurrenderDocumentsController extends BaseController {
    /**
     * 服务对象
     */
    private final SurrenderDocumentsService surrenderDocumentsService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple    分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询交单记录表")
    public R<IPage<SurrenderDocuments>> getPage(PageParamSimple pageParamSimple, SurrenderDocumentParam param) {
        PageParam<SurrenderDocuments> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.surrenderDocumentsService.getPage(page, param));
    }





}
