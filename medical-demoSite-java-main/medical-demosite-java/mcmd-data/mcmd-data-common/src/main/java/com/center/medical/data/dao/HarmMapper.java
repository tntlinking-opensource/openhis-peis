package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.param.HarmParam;
import com.center.medical.data.bean.vo.JhysDataVo;
import org.apache.ibatis.annotations.Param;

/**
 * JC危害因素(Harm)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:18
 */
public interface HarmMapper extends BaseMapper<Harm> {

    /**
     * 分页查询[JC危害因素]列表
     *
     * @param page  分页参数
     * @param param Harm查询参数
     * @return 分页数据
     */
    IPage<Harm> getList(PageParam<Harm> page, @Param("param") HarmParam param);

    /**
     * 分页查询[JC危害因素]简单列表
     *
     * @param page  分页参数
     * @param param Harm查询参数
     * @return 分页数据
     */
    IPage<Harm> getListSimple(PageParam<Harm> page, @Param("param") HarmParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Harm getInfoById(@Param("id") String id);

    /**
     * 根据筛选条件查询对应的数据
     *
     * @param param
     * @return
     */
    PageParam<Harm> getHarmData(PageParam<Harm> page, @Param("param") HarmParam param);

    /**
     * 格式化接害因素以"，"分隔
     *
     * @param jhys
     * @return
     */
    String getHarmText(@Param("strharm") String[] jhys);

    /**
     * 选择当前用户分中心的危害因素
     *
     * @param page
     * @param key
     * @return
     */
    IPage<JhysDataVo> getJhysDataByFzx(PageParam<JhysDataVo> page, @Param("cId") String cId, @Param("key") String key);
}
