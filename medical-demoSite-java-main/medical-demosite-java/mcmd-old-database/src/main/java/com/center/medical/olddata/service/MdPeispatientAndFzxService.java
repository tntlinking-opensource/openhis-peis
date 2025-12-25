package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientAndFzx;

import java.util.List;

/**
 * 分组分中心(MdPeispatientAndFzx)服务接口
 *
 * @author ay
 * @since 2024-04-17 10:34:47
 */
public interface MdPeispatientAndFzxService extends IService<MdPeispatientAndFzx> {

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientAndFzx> getPage(PageParam<MdPeispatientAndFzx> page, MdPeispatientAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientAndFzx getInfoById(String id);

    /**
     * 通过体检者id和分中心id查询
     * @param patientId
     * @param fzxId
     * @return
     */
    MdPeispatientAndFzx getByPidAndFzx(String patientId, String fzxId);

    /**
     * 批量保存
     * @param mdPeispatientAndFzxList
     */
    void saOrUpList(List<MdPeispatientAndFzx> mdPeispatientAndFzxList);
}

