package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientarchive;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者档案表(MdPeispatientarchive)数据库访问层
 *
 * @author ay
 * @since 2023-09-04 09:16:13
 */
public interface MdPeispatientarchiveMapper extends BaseMapper<MdPeispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientarchive查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientarchive> getPage(PageParam<MdPeispatientarchive> page, @Param("param") MdPeispatientarchive param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientarchive getInfoById(@Param("id") String id);

}
