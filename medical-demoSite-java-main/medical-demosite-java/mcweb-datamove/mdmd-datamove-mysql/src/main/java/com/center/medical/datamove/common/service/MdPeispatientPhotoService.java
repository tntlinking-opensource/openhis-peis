package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPeispatientPhoto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者头像(MdPeispatientPhoto)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:13
 */
public interface MdPeispatientPhotoService extends IService<MdPeispatientPhoto> {

    /**
     * 分页查询[体检者头像]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientPhoto> getPage(PageParam<MdPeispatientPhoto> page, MdPeispatientPhoto param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientPhoto getInfoById(String id);

}

