package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTjbbXzjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS血脂检测体检报表(MdTjbbXzjc)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:05
 */
public interface MdTjbbXzjcMapper extends BaseMapper<MdTjbbXzjc> {

    /**
     * 分页查询[KS血脂检测体检报表]列表
     *
     * @param page  分页参数
     * @param param MdTjbbXzjc查询参数
     * @return 分页数据
     */
    IPage<MdTjbbXzjc> getPage(PageParam<MdTjbbXzjc> page, @Param("param") MdTjbbXzjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjbbXzjc getInfoById(@Param("id") String id);

}
