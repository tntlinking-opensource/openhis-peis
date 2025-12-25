package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCurrentQueueInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 当前排队绑定信息(MdCurrentQueueInfo)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
public interface MdCurrentQueueInfoMapper extends BaseMapper<MdCurrentQueueInfo> {

    /**
     * 分页查询[当前排队绑定信息]列表
     *
     * @param page  分页参数
     * @param param MdCurrentQueueInfo查询参数
     * @return 分页数据
     */
    IPage<MdCurrentQueueInfo> getPage(PageParam<MdCurrentQueueInfo> page, @Param("param") MdCurrentQueueInfo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCurrentQueueInfo getInfoById(@Param("id") String id);

}
