package com.center.medical.reception.controller;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.bean.param.MigrationOldDataParam;
import com.center.medical.bean.param.OfflineImportPeiParam;
import com.center.medical.bean.param.OnlineImportParam;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.olddata.bean.param.AddUserFzxParam;
import com.center.medical.olddata.bean.param.ImportItemsParam;
import com.center.medical.olddata.bean.param.ImportPeiParam;
import com.center.medical.olddata.bean.param.ImportTjPeopleParam;
import com.center.medical.olddata.service.OldDatabaseQueryService;
import com.center.medical.reception.bean.param.ImportDataParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: ay
 * @date: 2023-08-26 11:28
 * @description: 导入未完成的体检者
 */
@RestController
@RequestMapping("/reception/datamove")
@Api(tags = "数据迁移")
@RequiredArgsConstructor
public class DatamoveController extends BaseController {

    @Autowired
    private OldDatabaseQueryService oldDatabaseQueryService;


    /**
     * 根据订单号导入老数据到新系统中
     *
     * @return
     * @throws SQLException
     */
    @PostMapping("/importPeispatient")
    @ApiOperation(value = "导入未完成的体检者", notes = "导入未完成的体检者")
    public R importPeispatient(@RequestBody ImportPeiParam param) {
        new Thread(() -> oldDatabaseQueryService.importPeispatient(param)).start();
        return R.ok("数据正在更新中，请耐心等待。");
    }


    /**
     * 根据订单号导入老数据到新系统中
     *
     * @param param
     * @return
     * @throws SQLException
     */
    @PostMapping("/importData")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "导入订单", notes = "导入订单")
    public R importData(@RequestBody ImportDataParam param) {
        List<String> ddh = param.getDdh();
        new Thread(() -> oldDatabaseQueryService.importData(ddh)).start();
        return R.ok("数据正在更新中，请耐心等待。");
    }


    /**
     * 根据订单号导入老数据到新系统中
     *
     * @return
     * @throws SQLException
     */
    @PostMapping("/importPeople")
    @ApiOperation(value = "导入老系统个检人员", notes = "导入老系统个检人员")
    public R importPeople(String fzxId) {
        Boolean b = oldDatabaseQueryService.importPeople(fzxId);
        return R.ok(b);
    }
//
//
//
//    /**
//     * 导入老系统团检人员
//     *
//     * @return
//     * @throws SQLException
//     */
//    @PostMapping("/importTjPeople")
//    @ApiOperation(value = "导入老系统团检人员", notes = "导入老系统团检人员")
//    public R importTjPeople(ImportTjPeopleParam param) {
//        new Thread(() -> oldDatabaseQueryService.importTjPeople(param)).start();
//        return R.ok("正在进行中");
//    }
//
//
//
//

    /**
     * 导入订单相关数据
     *
     * @return
     * @throws SQLException
     */
    @PostMapping("/importOrder")
    @ApiOperation(value = "导入订单相关数据", notes = "导入订单相关数据")
    public R importOrder(ImportTjPeopleParam param) {
        new Thread(() -> oldDatabaseQueryService.importOrder(param)).start();
        return R.ok("正在进行中");
    }


    /**
     * 老系统预收
     *
     * @return
     * @throws SQLException
     */
    @PostMapping("/oldSystemPrepayment")
    @ApiOperation(value = "修改收费备注老系统预收", notes = "修改收费备注老系统预收")
    public R oldSystemPrepayment(BaseParam param) {
        Boolean b = oldDatabaseQueryService.oldSystemPrepayment(param);
        return R.ok(b);
    }


    /**
     * 导入订单相关数据
     *
     * @return
     * @throws SQLException
     */
    @PostMapping("/importOrderByDdh")
    @ApiOperation(value = "根据订单号导入套餐相关", notes = "根据订单号导入套餐相关")
    public R importOrderByDdh(String ddh) {
        Boolean b = oldDatabaseQueryService.importOrderByDdh(ddh);
        return R.ok(b);
    }


    /**
     * 根据订单号导入老数据到新系统中
     *
     * @param param
     * @return
     * @throws SQLException
     */
    @PostMapping("/onlineImportOrder")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "线上导入订单", notes = "线上导入订单")
    public R onlineImport(@RequestBody OnlineImportParam param) {
        Boolean b = oldDatabaseQueryService.onlineImportOrder(param);
        return R.ok(b);
    }


    /**
     * 根据订单号导入老数据到新系统中
     *
     * @param param
     * @return
     * @throws SQLException
     */
    @PostMapping("/offlineImportPei")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "线下导入体检者", notes = "线下导入体检者")
    public R offlineImportPei(@RequestBody OfflineImportPeiParam param) {
        Boolean b = oldDatabaseQueryService.offlineImportPei(param);
        return R.ok(b);
    }




    /**
     * 根据订单号导入老数据到新系统中
     *
     * @param patientCodes
     * @return
     * @throws SQLException
     */
    @PostMapping("/importComparativeReport")
    @ApiOperation(value = "导入对比报告数据", notes = "导入对比报告数据")
    public R importComparativeReport(@RequestBody List<String> patientCodes) {
        Boolean b = oldDatabaseQueryService.importComparativeReport(patientCodes);
        return R.ok(b);
    }




    /**
     * 导入收费项目,输入收费项目id，从老系统线上导入收费项目
     *
     * @return
     * @throws SQLException
     */
    @PostMapping("/importItems")
    @ApiOperation(value = "导入收费项目", notes = "导入收费项目(items)")
    public R importItems(@RequestBody ImportItemsParam param) {
        Boolean b = oldDatabaseQueryService.importItems(param);
        return R.ok(b);
    }


    /**
     * 增加用户分中心
     *
     * @return
     * @throws SQLException
     */
    @PostMapping("/addUserFzx")
    @ApiOperation(value = "增加用户分中心", notes = "增加用户分中心")
    public R importItems(@RequestBody AddUserFzxParam param) {
        Boolean b = oldDatabaseQueryService.addUserFzx(param);
        return R.ok(b);
    }




    /**
     * 根据订单号导入老数据到新系统中
     *
     * @param param
     * @return
     * @throws SQLException
     */
    @PostMapping("/migrationOldData")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "迁移老系统团检数据", notes = "注意连接的库，需要用老系统线下及新系统线上迁移，然后同步下来")
    public R migrationOldData(@RequestBody MigrationOldDataParam param) {
        Boolean b = oldDatabaseQueryService.migrationOldData(param);
        return R.ok(b);
    }
}
