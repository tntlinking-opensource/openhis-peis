package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.common.bean.model.MdPeispatientfeeitem;

import java.util.List;

/**
 * 体检者收费项目表(MdPeispatientfeeitem)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
public interface MdPeispatientfeeitemService extends IService<MdPeispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientfeeitem> getPage(PageParam<MdPeispatientfeeitem> page, MdPeispatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientfeeitem getInfoById(String id);

    /**
     * 批量保存
     *
     * @param mapAsList
     */
    void saOrUpList(List<MdPeispatientfeeitem> mapAsList);
}

