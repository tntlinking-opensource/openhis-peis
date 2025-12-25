package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Savefilepath;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 存放文件路径表(Savefilepath)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:38
 */
public interface SavefilepathMapper extends BaseMapper<Savefilepath> {

    /**
     * 分页查询[存放文件路径表]列表
     *
     * @param page  分页参数
     * @param param Savefilepath查询参数
     * @return 分页数据
     */
    IPage<Savefilepath> getPage(PageParam<Savefilepath> page, @Param("param") Savefilepath param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Savefilepath getInfoById(@Param("id") String id);

}
