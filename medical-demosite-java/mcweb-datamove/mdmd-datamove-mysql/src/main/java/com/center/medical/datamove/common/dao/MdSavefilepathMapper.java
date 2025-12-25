package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSavefilepath;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 存放文件路径表(MdSavefilepath)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:28
 */
public interface MdSavefilepathMapper extends BaseMapper<MdSavefilepath> {

    /**
     * 分页查询[存放文件路径表]列表
     *
     * @param page  分页参数
     * @param param MdSavefilepath查询参数
     * @return 分页数据
     */
    IPage<MdSavefilepath> getPage(PageParam<MdSavefilepath> page, @Param("param") MdSavefilepath param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSavefilepath getInfoById(@Param("id") String id);

}
