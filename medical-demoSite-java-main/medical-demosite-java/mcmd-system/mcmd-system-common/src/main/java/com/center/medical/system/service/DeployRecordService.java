package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.DeployRecord;
import com.center.medical.system.bean.param.DeployRecordManualParam;
import com.center.medical.system.bean.vo.DeployRecordListVo;

import java.util.List;

/**
 * 自动部署-更新记录(DeployRecord)服务接口
 *
 * @author makejava
 * @since 2023-11-15 08:28:43
 */
public interface DeployRecordService extends IService<DeployRecord> {

    /**
     * 分页查询[自动部署-更新记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DeployRecord> getPage(PageParam<DeployRecord> page, DeployRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    DeployRecord getInfoById(String id);

    /**
     * 根据版本信息分中心关联表id查询此中心各服务更新状态
     * @param id
     * @return
     */
    List<DeployRecordListVo> selectRecordListByBranchId(int id);

    /**
     * 人工处理
     * @param param
     */
    void manual(DeployRecordManualParam param);
}
