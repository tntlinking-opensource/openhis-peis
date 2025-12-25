package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeisOl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者线上信息(MdPeisOl)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
public interface MdPeisOlMapper extends BaseMapper<MdPeisOl> {

    /**
     * 分页查询[体检者线上信息]列表
     *
     * @param page  分页参数
     * @param param MdPeisOl查询参数
     * @return 分页数据
     */
    IPage<MdPeisOl> getPage(PageParam<MdPeisOl> page, @Param("param") MdPeisOl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisOl getInfoById(@Param("id") String id);

}
