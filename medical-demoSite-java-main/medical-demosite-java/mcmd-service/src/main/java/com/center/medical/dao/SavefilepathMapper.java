package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Savefilepath;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 存放文件路径表(Savefilepath)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:01
 */
public interface SavefilepathMapper extends BaseMapper<Savefilepath> {

    /**
     * 分页查询[存放文件路径表]列表
     *
     * @param page  分页参数
     * @param param Savefilepath查询参数
     * @return 分页数据
     */
    IPage<Savefilepath> getList(PageParam<Savefilepath> page, @Param("param") Savefilepath param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Savefilepath getInfoById(@Param("id") String id);

}
