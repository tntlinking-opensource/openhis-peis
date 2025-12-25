package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.MedicalType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZyVsSummary;
import com.center.medical.data.dao.EmphasisAskSymptomMapper;
import com.center.medical.data.dao.ZySummaryMapper;
import com.center.medical.data.dao.ZyVsSummaryMapper;
import com.center.medical.data.service.ZyVsSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * JC职业病处理意见(ZyVsSummary)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
@Slf4j
@Service("zyVsSummaryService")
@RequiredArgsConstructor
public class ZyVsSummaryServiceImpl extends ServiceImpl<ZyVsSummaryMapper, ZyVsSummary> implements ZyVsSummaryService {

    private final ZyVsSummaryMapper zyVsSummaryMapper;
    private final Snowflake snowflake;
    private final EmphasisAskSymptomMapper emphasisAskSymptomMapper;
    private final ZySummaryMapper zySummaryMapper;

    /**
     * 分页查询[JC职业病处理意见]列表
     *
     * @param page  分页参数
     * @param param ZyVsSummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyVsSummary> getPage(PageParam<ZyVsSummary> page, ZyVsSummary param) {
        return zyVsSummaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public ZyVsSummary getInfoById(String id) {
        return zyVsSummaryMapper.getInfoById(id);
    }


    /**
     * 添加或修改
     *
     * @param odis
     * @return
     */
    @Override
    public String saveOrUpdateZyVsSummary(ZyVsSummary odis) {
        String name = SecurityUtils.getLoginUser().getUsername();
        // 判断是否为空
        if (StringUtils.isBlank(odis.getId())) {
            //保存
            //设置isDelete字段为0
            odis.setDbUser(name);
            odis.setIsDelete(0);
            odis.setCreatedate(new Date());
            odis.setId(String.valueOf(snowflake.nextId()));
            this.save(odis);
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            ZyVsSummary harmNew = zyVsSummaryMapper.getZyVsSummaryById(odis.getId());
            if (harmNew != null) {
                // 更新实体类
                odis.setDbUser(name);
                odis.setModifydate(new Date());
                this.updateById(odis);

            } else {
                throw new ServiceException("对象已经删除，请刷新页面");
            }
        }
        return "success";
    }

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    @Override
    public String removeZyVsSummary(String ids) {
        String id[] = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            ZyVsSummary zyoc = zyVsSummaryMapper.getZyVsSummaryById(id[i]);
            if (null != zyoc) {
                //将isDelete设置为1，为删除
                zyoc.setIsDelete(1);
                this.updateById(zyoc);
            }
        }
        return "success";
    }


    @Override
    public String synchonize() {
        // TODO: 2022/11/21 同步有问题，待处理
        boolean result = executeUpdateSql(
                "MERGE INTO ZY_SUMMARY ZS "
                        + "USING ZY_SUMMARY@" + "NETLINK" + " ZSO "
                        + "ON (ZS.ID=ZSO.ID) "
                        + "WHEN MATCHED THEN "
                        + "UPDATE SET "
                        + "ZS.MODIFYDATE=ZSO.MODIFYDATE, "
                        + "ZS.OCCUPATION_SUMMARY_CODE=ZSO.OCCUPATION_SUMMARY_CODE, "
                        + "ZS.OCCUPATION_SUMMARY=ZSO.OCCUPATION_SUMMARY, "
                        + "ZS.PRINT_FOR_SHORT=ZSO.PRINT_FOR_SHORT, "
                        + "ZS.INPUT_CODE=ZSO.INPUT_CODE, "
                        + "ZS.IS_DELETE=ZSO.IS_DELETE, "
                        + "ZS.OCCUPATION_SUMMARY_EXPLAIN=ZSO.OCCUPATION_SUMMARY_EXPLAIN, "
                        + "ZS.SERIAL_NO=ZSO.SERIAL_NO "
                        + "WHEN NOT MATCHED THEN "
                        + "INSERT(ID,CREATEDATE,MODIFYDATE,OCCUPATION_SUMMARY_CODE,OCCUPATION_SUMMARY,PRINT_FOR_SHORT,INPUT_CODE,IS_DELETE,"
                        + "OCCUPATION_SUMMARY_EXPLAIN,SERIAL_NO) "
                        + "VALUES(ZSO.ID,ZSO.CREATEDATE,ZSO.MODIFYDATE,ZSO.OCCUPATION_SUMMARY_CODE,ZSO.OCCUPATION_SUMMARY,ZSO.PRINT_FOR_SHORT,ZSO.INPUT_CODE,ZSO.IS_DELETE,"
                        + "ZSO.OCCUPATION_SUMMARY_EXPLAIN,ZSO.SERIAL_NO)  "
        );
        if (!result) {
            throw new ServiceException("同步职业检查结论失败！");
        }
        result = executeUpdateSql(
                "MERGE INTO OCCUPATION_DISEAST_CLASS OD "
                        + "USING OCCUPATION_DISEAST_CLASS@" + "NETLINK" + " ODO "
                        + "ON (OD.ID=ODO.ID) "
                        + "WHEN MATCHED THEN "
                        + "UPDATE SET "
                        + "OD.MODIFYDATE=ODO.MODIFYDATE, "
                        + "OD.OCCUPATION_DISEAST_CLASS_CODE=ODO.OCCUPATION_DISEAST_CLASS_CODE, "
                        + "OD.OCCUPATION_DISEAST_CLASS_NAME=ODO.OCCUPATION_DISEAST_CLASS_NAME, "
                        + "OD.INPUT_CODE=ODO.INPUT_CODE, "
                        + "OD.DB_USER=ODO.DB_USER, "
                        + "OD.IS_DELETE=ODO.IS_DELETE "
                        + "WHEN NOT MATCHED THEN "
                        + "INSERT(ID,CREATEDATE,MODIFYDATE,OCCUPATION_DISEAST_CLASS_CODE,OCCUPATION_DISEAST_CLASS_NAME,INPUT_CODE,"
                        + "DB_USER,IS_DELETE) "
                        + "VALUES(ODO.ID,ODO.CREATEDATE,ODO.MODIFYDATE,ODO.OCCUPATION_DISEAST_CLASS_CODE,ODO.OCCUPATION_DISEAST_CLASS_NAME,ODO.INPUT_CODE,"
                        + "ODO.DB_USER,ODO.IS_DELETE) "
        );
        if (!result) {
            throw new ServiceException("同步职业病分类失败！");
        }
        result = executeUpdateSql(
                "MERGE INTO OCCUPATION_DISEAST OD "
                        + "USING OCCUPATION_DISEAST@" + "NETLINK" + " ODO "
                        + "ON (OD.ID=ODO.ID) "
                        + "WHEN MATCHED THEN "
                        + "UPDATE SET "
                        + "OD.MODIFYDATE=ODO.MODIFYDATE, "
                        + "OD.OCCUPATION_DISEAST_CODE=ODO.OCCUPATION_DISEAST_CODE, "
                        + "OD.OCCUPATION_DISEAST=ODO.OCCUPATION_DISEAST, "
                        + "OD.INPUT_CODE=ODO.INPUT_CODE, "
                        + "OD.DB_USER=ODO.DB_USER, "
                        + "OD.OCCUPATION_DISEAST_CLASS=ODO.OCCUPATION_DISEAST_CLASS, "
                        + "OD.IS_DELETE=ODO.IS_DELETE "
                        + "WHEN NOT MATCHED THEN "
                        + "INSERT(ID,CREATEDATE,MODIFYDATE,OCCUPATION_DISEAST_CODE,OCCUPATION_DISEAST,INPUT_CODE,DB_USER,OCCUPATION_DISEAST_CLASS,IS_DELETE) "
                        + "VALUES(ODO.ID,ODO.CREATEDATE,ODO.MODIFYDATE,ODO.OCCUPATION_DISEAST_CODE,ODO.OCCUPATION_DISEAST"
                        + ",ODO.INPUT_CODE,ODO.DB_USER,ODO.OCCUPATION_DISEAST_CLASS,ODO.IS_DELETE) "
        );
        if (!result) {
            throw new ServiceException("同步职业病失败！");
        }
        result = executeUpdateSql(
                "MERGE INTO ZY_VS_SUMMARY ZVS "
                        + "USING ZY_VS_SUMMARY@" + "NETLINK" + " ZVSO "
                        + "ON (ZVS.ID=ZVSO.ID) "
                        + "WHEN MATCHED THEN "
                        + "UPDATE SET "
                        + "ZVS.MODIFYDATE=ZVSO.MODIFYDATE, "
                        + "ZVS.REGIMENTATION_NOTE=ZVSO.REGIMENTATION_NOTE, "
                        + "ZVS.OCCUPATION_SUMMARY_CLASS=ZVSO.OCCUPATION_SUMMARY_CLASS, "
                        + "ZVS.OCCUPATION_SUMMARY_CODE=ZVSO.OCCUPATION_SUMMARY_CODE, "
                        + "ZVS.DIAGNOSIS_FROM=ZVSO.DIAGNOSIS_FROM, "
                        + "ZVS.OCCUPATION_DIAGNOSIS_CODE=ZVSO.OCCUPATION_DIAGNOSIS_CODE, "
                        + "ZVS.OCCUPATION_DIAGNOSIS=ZVSO.OCCUPATION_DIAGNOSIS, "
                        + "ZVS.DIAGNOSIS=ZVSO.DIAGNOSIS, "
                        + "ZVS.SUMMARY_TEXT=ZVSO.SUMMARY_TEXT, "
                        + "ZVS.FOR_PERSON_INFLUENCE=ZVSO.FOR_PERSON_INFLUENCE, "
                        + "ZVS.INPUT_CODE=ZVSO.INPUT_CODE, "
                        + "ZVS.DB_USER=ZVSO.DB_USER, "
                        + "ZVS.OCCUPATION_DISEAST=ZVSO.OCCUPATION_DISEAST, "
                        + "ZVS.OCCUPATION_SUMMARY=ZVSO.OCCUPATION_SUMMARY, "
                        + "ZVS.HEALTH_EVALUATION_CLASS=ZVSO.HEALTH_EVALUATION_CLASS, "
                        + "ZVS.IS_DELETE=ZVSO.IS_DELETE, "
                        + "ZVS.ITEM_ID=ZVSO.ITEM_ID, "
                        + "ZVS.ALWAYS_IN_REPORT=ZVSO.ALWAYS_IN_REPORT "
                        + "WHEN NOT MATCHED THEN "
                        + "INSERT (ID,CREATEDATE,MODIFYDATE,REGIMENTATION_NOTE,OCCUPATION_SUMMARY_CLASS,OCCUPATION_SUMMARY_CODE,DIAGNOSIS_FROM,"
                        + "OCCUPATION_DIAGNOSIS_CODE,OCCUPATION_DIAGNOSIS,DIAGNOSIS,SUMMARY_TEXT,FOR_PERSON_INFLUENCE,INPUT_CODE,DB_USER,OCCUPATION_DISEAST,"
                        + "OCCUPATION_SUMMARY,HEALTH_EVALUATION_CLASS,ZZJJZDM,IS_DELETE,ITEM_ID,ALWAYS_IN_REPORT) "
                        + "VALUES (ZVSO.ID,ZVSO.CREATEDATE,ZVSO.MODIFYDATE,ZVSO.REGIMENTATION_NOTE,ZVSO.OCCUPATION_SUMMARY_CLASS,ZVSO.OCCUPATION_SUMMARY_CODE,ZVSO.DIAGNOSIS_FROM,"
                        + "ZVSO.OCCUPATION_DIAGNOSIS_CODE,ZVSO.OCCUPATION_DIAGNOSIS,ZVSO.DIAGNOSIS,ZVSO.SUMMARY_TEXT,ZVSO.FOR_PERSON_INFLUENCE,ZVSO.INPUT_CODE,ZVSO.DB_USER,ZVSO.OCCUPATION_DISEAST,"
                        + "ZVSO.OCCUPATION_SUMMARY,ZVSO.HEALTH_EVALUATION_CLASS,ZVSO.ZZJJZDM,ZVSO.IS_DELETE,ZVSO.ITEM_ID,ZVSO.ALWAYS_IN_REPORT) "
        );
        if (!result) {
            throw new ServiceException("同步职业处理意见失败！");
        }
        return "success";
    }

    /**
     * 根据条件获取职业病处理意见ID
     *
     * @param medicaltype 职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急
     * @param odList      危害因素ID集合
     * @param itemIds     收费项目ID集合
     * @return
     * @see MedicalType
     */
    @Override
    public List<String> getIdList(String medicaltype, List<String> odList, List<String> itemIds) {
        return zyVsSummaryMapper.getIdList(medicaltype, odList, itemIds);
    }


    private boolean executeUpdateSql(String sql) {
        try {
            implementSql(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    //执行sql
    private void implementSql(String sql) {
        emphasisAskSymptomMapper.implementSql(sql);
    }


}

