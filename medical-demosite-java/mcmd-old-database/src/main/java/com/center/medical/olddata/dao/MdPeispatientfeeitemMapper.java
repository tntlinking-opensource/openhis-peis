package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientfeeitem;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者收费项目表(MdPeispatientfeeitem)数据库访问层
 *
 * @author ay
 * @since 2023-08-12 11:53:32
 */
public interface MdPeispatientfeeitemMapper extends BaseMapper<MdPeispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientfeeitem> getPage(PageParam<MdPeispatientfeeitem> page, @Param("param") MdPeispatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientfeeitem getInfoById(@Param("id") String id);

}
