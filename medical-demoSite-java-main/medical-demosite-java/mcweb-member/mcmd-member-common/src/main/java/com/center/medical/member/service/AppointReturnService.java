package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.AdvanceVisitWrite;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.ARsaOrUpParam;
import com.center.medical.member.bean.param.AppointReturnParam;
import com.center.medical.member.bean.vo.AppointReturnVo;

import java.util.List;

/**
 * 主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环(AdvanceVisitWrite)表服务接口
 *
 * @author makejava
 * @since 2023-02-16 14:03:32
 */
public interface AppointReturnService extends IService<AdvanceVisitWrite> {

    /**
     * 分页查询[主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppointReturnVo> getPage(PageParam<AppointReturnVo> page, AppointReturnParam param);

    /**
     * 导出Excel
     * @param param
     * @return
     */
    List<AppointReturnVo> export(AppointReturnParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AdvanceVisitWrite getInfoById(String id);

    /**
     * 预约来检回访
     * @param
     * @return
     */
    Boolean saOrUp(ARsaOrUpParam param);

}

