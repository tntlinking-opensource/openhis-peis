package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdPeisorgreservation;

import java.util.List;

/**
 * 体检者团体任务(MdPeisorgreservation)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:53:30
 */
public interface MdPeisorgreservationService extends IService<MdPeisorgreservation> {

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeisorgreservation> getPage(PageParam<MdPeisorgreservation> page, MdPeisorgreservation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisorgreservation getInfoById(String id);

    /**
     * 批量添加
     *
     * @param mapAsList
     */
    void saOrUpList(List<MdPeisorgreservation> mapAsList);

    /**
     * 单条保存
     * @param map
     */
    void saorUp(MdPeisorgreservation map);
}

