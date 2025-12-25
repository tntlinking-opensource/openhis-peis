package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Userauthcode;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 用户授权码(Userauthcode)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
public interface UserauthcodeMapper extends BaseMapper<Userauthcode> {

    /**
     * 分页查询[用户授权码]列表
     *
     * @param page  分页参数
     * @param param Userauthcode查询参数
     * @return 分页数据
     */
    IPage<Userauthcode> getList(PageParam<Userauthcode> page, @Param("param") Userauthcode param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Userauthcode getInfoById(@Param("id") String id);

}
