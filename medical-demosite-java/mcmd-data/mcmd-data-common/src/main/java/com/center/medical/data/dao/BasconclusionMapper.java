package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.vo.BasconclusionVo;
import org.apache.ibatis.annotations.Param;

/**
 * 总检结论词(Basconclusion)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:18
 */
public interface BasconclusionMapper extends BaseMapper<Basconclusion> {

    /**
     * 分页查询[总检结论词]列表
     *
     * @param page  分页参数
     * @param param Basconclusion查询参数
     * @return 分页数据
     */
    IPage<Basconclusion> getList(PageParam<Basconclusion> page, @Param("param") Basconclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Basconclusion getInfoById(@Param("id") String id);

    /**
     * 检查项目查询 下拉数据
     * @param page
     * @param key
     * @param id
     * @return
     */
    IPage<Basconclusion> getInspectConclusion(PageParam<Basconclusion> page, @Param("key")String key, @Param("id")String id);

    /**
     * 健康+职业 结论词下拉
     * @param page
     * @param srm
     * @return
     */
    IPage<BasconclusionVo> getConclusionListData(PageParam<BasconclusionVo> page, @Param("srm") String srm);
}
