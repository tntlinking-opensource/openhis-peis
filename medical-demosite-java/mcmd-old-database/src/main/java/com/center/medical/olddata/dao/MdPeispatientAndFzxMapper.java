package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientAndFzx;
import org.apache.ibatis.annotations.Param;

/**
 * 分组分中心(MdPeispatientAndFzx)数据库访问层
 *
 * @author ay
 * @since 2024-04-17 10:34:47
 */
public interface MdPeispatientAndFzxMapper extends BaseMapper<MdPeispatientAndFzx> {

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientAndFzx查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientAndFzx> getPage(PageParam<MdPeispatientAndFzx> page, @Param("param") MdPeispatientAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientAndFzx getInfoById(@Param("id") String id);

}
