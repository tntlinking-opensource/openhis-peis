package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeisorgreservationgroup;

import java.util.List;

/**
 * 体检者任务分组(MdPeisorgreservationgroup)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:53:29
 */
public interface MdPeisorgreservationgroupService extends IService<MdPeisorgreservationgroup> {

    /**
     * 分页查询[体检者任务分组]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeisorgreservationgroup> getPage(PageParam<MdPeisorgreservationgroup> page, MdPeisorgreservationgroup param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisorgreservationgroup getInfoById(String id);

    /**
     * 批量保存
     *
     * @param mapAsList
     */
    void saOrUpList(List<MdPeisorgreservationgroup> mapAsList);

    /**
     * 通过任务id和套餐id查询
     * @param id
     * @param idExamsuite
     * @return
     */
    MdPeisorgreservationgroup getByVationIdAndTcId(String id, String idExamsuite);

    /**
     * 通过任务id查询
     * @param id
     * @return
     */
    List<MdPeisorgreservationgroup> getByVationId(String id);
}

