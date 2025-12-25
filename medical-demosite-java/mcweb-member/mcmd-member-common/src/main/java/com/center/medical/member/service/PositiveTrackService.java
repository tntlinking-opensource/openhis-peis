package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.PeispatientParam;
import com.center.medical.member.bean.param.PositiveTracktParam;
import com.center.medical.member.bean.vo.PeispatientEditVo;
import com.center.medical.member.bean.vo.PeispatientVo;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author makejava
 * @since 2023-02-02 10:12:36
 */
public interface PositiveTrackService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeispatientVo> getList(PageParam<Peispatient> page, PeispatientParam param);

    /**
     * 导出Excel
     * @param param
     * @return
     */
    List<PeispatientVo> export(PeispatientParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    List<PeispatientEditVo>  getInfoById(String id);

    /**
     * 阳性结果回访
     * @param peispatientEditVo
     * @return
     */
    Boolean saOrUpPositiveTrackt(PositiveTracktParam peispatientEditVo);

}

