package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.VisitMain;
import com.center.medical.member.bean.param.VisitMainParam;
import com.center.medical.member.bean.param.VmFormdata;
import com.center.medical.member.bean.vo.VisitMainVo;

import java.util.List;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:50
 */
public interface VisitMainService extends IService<VisitMain> {

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param VisitMain查询参数
     * @return 分页数据
     */
    IPage<VisitMainVo> getList(PageParam<VisitMainVo> page, VisitMainParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    VisitMain getInfoById(String id);

    /**
     * 新增数据
     *
     * @param vmFormdata
     * @return
     */
    Boolean saOrUp(VmFormdata vmFormdata);

    /**
     * 获取导出数据
     *
     * @param param
     * @return
     */
    List<VisitMainVo> getExportDate(VisitMainParam param);

    /**
     * 前台迟捡操作
     *
     * @param vm
     * @return
     */
    Boolean saveChijian(VisitMain vm);
}

