package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Wsjg;
import com.center.medical.data.bean.param.WsjgParam;
import com.center.medical.data.bean.vo.WsjgDataVo;

import java.util.List;

/**
 * 外送机构(Wsjg)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-29 11:22:57
 */
public interface WsjgService extends IService<Wsjg> {

    /**
     * 分页查询[外送机构]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Wsjg> getPage(PageParam<Wsjg> page, WsjgParam param);

    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    List<Wsjg> getList(WsjgParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Wsjg getInfoById(String id);

    /**
     * 外送机构下拉
     * @param page
     * @param srm
     * @return
     */
    IPage<WsjgDataVo> getWsjgData(PageParam<WsjgDataVo> page, String srm);
}

