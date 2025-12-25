package com.center.medical.datamove.admin.controller;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.datamove.admin.service.DatamoveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * @author: 路飞
 * @date: 2023-06-17 11:28
 * @description: 数据迁移接口
 */
@RestController
@RequestMapping("/open/api/datamove")
@Api(tags = "数据迁移")
@RequiredArgsConstructor
public class DatamoveController extends BaseController {

    @Autowired
    private DatamoveService datamoveService;

    @GetMapping("/move")
    @ApiOperation(value = "开始迁移", notes = "开始迁移")
    public R move() throws SQLException {
        Boolean b = datamoveService.move();
        return R.ok(b);
    }


//    /**
//     * 根据订单号导入老数据到新系统中
//     *
//     * @param param
//     * @return
//     * @throws SQLException
//     */
//    @PostMapping("/importData")
//    //@PreAuthorize("@ss.hasPermi('::add')")
//    @ApiOperation(value = "根据订单号导入老数据到新系统中", notes = "根据订单号导入老数据到新系统中")
//    public R importData(@RequestBody ImportDataParam param) {
//        List<String> ddh = param.getDdh();
//        return R.ok(datamoveService.importData(ddh));
//    }
//
//
//    /**
//     * 根据订单号导入老数据到新系统中
//     *
//     * @return
//     * @throws SQLException
//     */
//    @PostMapping("/importPeispatient")
//    @ApiOperation(value = "导入未完成的体检者", notes = "导入未完成的体检者")
//    public R importPeispatient() {
//        return R.ok(datamoveService.importPeispatient());
//    }
}
