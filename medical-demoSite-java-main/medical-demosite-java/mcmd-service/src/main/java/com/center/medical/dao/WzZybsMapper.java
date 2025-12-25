package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.WzZybs;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KS问诊——职业病史(WzZybs)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:48
 */
public interface WzZybsMapper extends BaseMapper<WzZybs> {

    /**
     * 分页查询[KS问诊——职业病史]列表
     *
     * @param page  分页参数
     * @param param WzZybs查询参数
     * @return 分页数据
     */
    IPage<WzZybs> getList(PageParam<WzZybs> page, @Param("param") WzZybs param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzZybs getInfoById(@Param("id") String id);

    /**
     * 通过档案号获取职业病史
     * @param idPatientarchive
     * @return
     */
    List<WzZybs> getByPatientarchiveno(@Param("idPatientarchive")String idPatientarchive);
}
