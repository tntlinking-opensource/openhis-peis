package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdHarmAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 危害因素和分中心(MdHarmAndFzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
public interface MdHarmAndFzxMapper extends BaseMapper<MdHarmAndFzx> {

    /**
     * 分页查询[危害因素和分中心]列表
     *
     * @param page  分页参数
     * @param param MdHarmAndFzx查询参数
     * @return 分页数据
     */
    IPage<MdHarmAndFzx> getPage(PageParam<MdHarmAndFzx> page, @Param("param") MdHarmAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdHarmAndFzx getInfoById(@Param("id") String id);

}
