package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.param.HarmParam;
import com.center.medical.data.bean.vo.JhysDataVo;

/**
 * JC危害因素(Harm)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:18
 */
public interface HarmService extends IService<Harm> {

    /**
     * 分页查询[JC危害因素]列表
     *
     * @param page  分页参数
     * @param param Harm查询参数
     * @return 分页数据
     */
    IPage<Harm> getList(PageParam<Harm> page, HarmParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Harm getInfoById(String id);

    /**
     * 新增或修改
     *
     * @param harm
     * @return
     */
    String saveOrUpdateHarm(Harm harm);

    /**
     * 分页查询JC危害因素
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Harm> getHarmData(PageParam<Harm> page, HarmParam param);

    /**
     * 假删危害因素分类
     *
     * @param ids
     * @return
     */
    String removeHarm(String ids);

    /**
     * 分页查询[JC危害因素]简单列表
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Harm> getHarmDataSimple(PageParam<Harm> page, HarmParam param);

    /**
     * 获取所有危害因素
     * @param page
     * @param inputCode
     * @return
     */
    IPage<Harm> getAllHarmname(PageParam<Harm> page, String inputCode);

    /**
     * 选择当前用户分中心的危害因素
     * @param page
     * @param key
     * @return
     */
    IPage<JhysDataVo> getJhysDataByFzx(PageParam<JhysDataVo> page, String key);
}

