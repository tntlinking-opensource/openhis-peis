package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.vo.BasconclusionVo;

import java.util.List;

/**
 * 总检结论词(Basconclusion)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:18
 */
public interface BasconclusionService extends IService<Basconclusion> {

    /**
     * 分页查询[总检结论词]列表
     *
     * @param page  分页参数
     * @param param Basconclusion查询参数
     * @return 分页数据
     */
    IPage<Basconclusion> getList(PageParam<Basconclusion> page, Basconclusion param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Basconclusion getInfoById(String id);

    /**
     * 根据拼音获取结论词
     * @param page
     * @param key
     * @return
     */
    IPage<Basconclusion> getConclusion(PageParam<Basconclusion> page, String key);

    /**
     * 检查项目查询 下拉数据
     * @param page
     * @param key
     * @param id
     * @return
     */
    IPage<Basconclusion> getInspectConclusion(PageParam<Basconclusion> page, String key, String id);

    /**
     * 删除
     * @param ids
     * @return
     */
    boolean removebasconclusion(List<String> ids);

    /**
     * 保存或更新
     * @param basconclusion
     * @return
     */
    Boolean saOrUp(Basconclusion basconclusion);

    /**
     * 审核
     * @param ids
     * @return
     */
    boolean audit(List<String> ids);

    /**
     *健康+职业 结论词下拉
     * @param page
     * @param srm
     * @return
     */
    IPage<BasconclusionVo> getConclusionListData(PageParam<BasconclusionVo> page, String srm);
}

