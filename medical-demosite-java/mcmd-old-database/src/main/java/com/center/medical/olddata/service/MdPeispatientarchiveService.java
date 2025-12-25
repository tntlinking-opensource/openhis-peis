package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientarchive;

import java.util.List;

/**
 * 体检者档案表(MdPeispatientarchive)服务接口
 *
 * @author ay
 * @since 2023-09-04 09:16:14
 */
public interface MdPeispatientarchiveService extends IService<MdPeispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientarchive> getPage(PageParam<MdPeispatientarchive> page, MdPeispatientarchive param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientarchive getInfoById(String id);

    /**
     * 单条添加或修改
     * @param map
     */
    void saOrUp(MdPeispatientarchive map);

    /**
     * 根据身份证查询
     * @param idcardno
     * @return
     */
    List<MdPeispatientarchive> getInfoByIdCard(String idcardno);
}

