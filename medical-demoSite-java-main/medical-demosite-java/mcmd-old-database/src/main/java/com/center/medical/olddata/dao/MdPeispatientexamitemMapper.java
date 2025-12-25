package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientexamitem;
import org.apache.ibatis.annotations.Param;

/**
 * LIS结果(LisPacs数据)(MdPeispatientexamitem)数据库访问层
 *
 * @author ay
 * @since 2024-06-05 15:19:03
 */
public interface MdPeispatientexamitemMapper extends BaseMapper<MdPeispatientexamitem> {

    /**
     * 分页查询[LIS结果(LisPacs数据)]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientexamitem查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientexamitem> getPage(PageParam<MdPeispatientexamitem> page, @Param("param") MdPeispatientexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientexamitem getInfoById(@Param("id") String id);

}
