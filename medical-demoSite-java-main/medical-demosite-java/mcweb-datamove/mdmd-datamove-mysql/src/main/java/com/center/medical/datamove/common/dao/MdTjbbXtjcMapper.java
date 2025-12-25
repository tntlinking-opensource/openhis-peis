package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTjbbXtjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS血糖检测体检报表(MdTjbbXtjc)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:02
 */
public interface MdTjbbXtjcMapper extends BaseMapper<MdTjbbXtjc> {

    /**
     * 分页查询[KS血糖检测体检报表]列表
     *
     * @param page  分页参数
     * @param param MdTjbbXtjc查询参数
     * @return 分页数据
     */
    IPage<MdTjbbXtjc> getPage(PageParam<MdTjbbXtjc> page, @Param("param") MdTjbbXtjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjbbXtjc getInfoById(@Param("id") String id);

}
