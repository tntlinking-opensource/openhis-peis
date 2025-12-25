package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsItemPart;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 项目部位表(MdPacsItemPart)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:33
 */
public interface MdPacsItemPartMapper extends BaseMapper<MdPacsItemPart> {

    /**
     * 分页查询[项目部位表]列表
     *
     * @param page  分页参数
     * @param param MdPacsItemPart查询参数
     * @return 分页数据
     */
    IPage<MdPacsItemPart> getPage(PageParam<MdPacsItemPart> page, @Param("param") MdPacsItemPart param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsItemPart getInfoById(@Param("id") String id);

}
