package com.center.medical.olddata.service;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.bean.param.MigrationOldDataParam;
import com.center.medical.bean.param.OfflineImportPeiParam;
import com.center.medical.bean.param.OnlineImportParam;
import com.center.medical.olddata.bean.param.AddUserFzxParam;
import com.center.medical.olddata.bean.param.ImportItemsParam;
import com.center.medical.olddata.bean.param.ImportPeiParam;
import com.center.medical.olddata.bean.param.ImportTjPeopleParam;

import java.util.List;

/**
 * @author: ay
 * @date: 2023-08-26 11:28
 * @description: 导入未完成的体检者
 */
public interface OldDatabaseQueryService {


    /**
     * 导入未完成的体检者
     *
     * @return
     */
    Boolean importPeispatient(ImportPeiParam param);


    /**
     * 根据订单号导入老数据到新系统中
     * @param ddh
     * @return
     */
    Boolean importData(List<String> ddh);

    /**
     * 删除测试体检者
     * @return
     */
    Boolean deleteOtherPeispatient();

    /**
     * 导入老系统个检人员
     * @return
     */
    Boolean importPeople(String fzxId);

    /**
     * 导入老系统团检人员
     * @return
     */
    Boolean importTjPeople(ImportTjPeopleParam param);

    /**
     * 导入订单相关数据
     * @param param
     */
    void importOrder(ImportTjPeopleParam param);

    /**
     * 修改收费备注老系统预收
     * @return
     */
    Boolean oldSystemPrepayment(BaseParam param);

    /**
     * 根据订单号导入套餐相关
     * @param ddh
     * @return
     */
    Boolean importOrderByDdh(String ddh);

    /**
     * 线上导入订单
     * @param param
     * @return
     */
    Boolean onlineImportOrder(OnlineImportParam param);

    /**
     * 线下导入体检者
     * @param param
     * @return
     */
    Boolean offlineImportPei(OfflineImportPeiParam param);

    /**
     * 导入对比报告数据
     * @param patientCodes
     * @return
     */
    Boolean importComparativeReport(List<String> patientCodes);

    /**
     * 导入收费项目
     * @param param
     * @return
     */
    Boolean importItems(ImportItemsParam param);

    /**
     * 增加用户分中心
     * @param param
     * @return
     */
    Boolean addUserFzx(AddUserFzxParam param);

    /**
     * 迁移老系统团检数据
     * @param param
     * @return
     */
    Boolean migrationOldData(MigrationOldDataParam param);
}
