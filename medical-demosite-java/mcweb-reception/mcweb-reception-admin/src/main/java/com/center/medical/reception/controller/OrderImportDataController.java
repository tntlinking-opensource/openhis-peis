package com.center.medical.reception.controller;

import cn.hutool.json.JSONUtil;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.reception.bean.param.ImportDataParam;
import com.center.medical.reception.service.OrderImportDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


/**
 * QT体检者表(Peispatient)接口
 *
 * @author ay
 * @since 2023-07-21 14:17:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "根据订单号导入数据")
@RequestMapping("/reception/orderImportData")
public class OrderImportDataController extends BaseController {
    /**
     * 服务对象
     */
    private final OrderImportDataService orderImportDataService;
    private final MapperFacade mapperFacade;


    /**
     * 根据订单号导入老数据到新系统中
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/importData")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "根据订单号导入老数据到新系统中", notes = "根据订单号导入老数据到新系统中")
    public R importData(@RequestBody ImportDataParam param) {
        String s = HttpUtils.sendPost(Constants.dataMoveUrl, JSONUtil.toJsonStr(param));
        return R.ok(s);
    }


    /**
     * 数据迁移
     *
     * @return
     * @throws SQLException
     */
    @GetMapping("/dataMove")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "数据迁移", notes = "数据迁移")
    public R dataMove() throws SQLException {
        return R.ok(orderImportDataService.dataMove());
    }


}

