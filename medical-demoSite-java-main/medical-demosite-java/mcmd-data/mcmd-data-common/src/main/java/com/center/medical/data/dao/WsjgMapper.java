package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Wsjg;
import com.center.medical.data.bean.param.WsjgParam;
import com.center.medical.data.bean.vo.WsjgDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 外送机构(Wsjg)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-29 11:22:57
 */
public interface WsjgMapper extends BaseMapper<Wsjg> {

    /**
     * 分页查询[外送机构]列表
     *
     * @param page  分页参数
     * @param param Wsjg查询参数
     * @return 分页数据
     */
    IPage<Wsjg> getPage(PageParam<Wsjg> page, @Param("param") WsjgParam param);

    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    List<Wsjg> getList(@Param("param") WsjgParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Wsjg getInfoById(@Param("id") String id);

    /**
     * 外送机构下拉
     * @param page
     * @param srm
     * @return
     */
    IPage<WsjgDataVo> getWsjgData(PageParam<WsjgDataVo> page,@Param("srm") String srm);
}
