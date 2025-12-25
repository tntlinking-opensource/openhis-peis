package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.param.ItemListParam;
import com.center.medical.reception.bean.param.ListDataParam;
import com.center.medical.reception.bean.vo.ItemListVo;
import com.center.medical.reception.bean.vo.ListDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-02-02 15:30:32
 */
public interface ItemListMapper extends BaseMapper<Peispatient> {

    /**
    * 分页查询[QT体检者表]列表
    *
    * @param page 分页参数
    * @param param Peispatient查询参数
    * @return 分页数据
    */
    IPage<ItemListVo> getList(PageParam<ItemListVo> page, @Param("param") ItemListParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * itemListMapper
     * @param page
     * @param param
     * @return
     */
    IPage<ListDataVo> getListData(PageParam<ListDataVo> page,@Param("param") ListDataParam param);

    /**
     * 团检退项匹配最小套餐
     * @param harm
     * @param ids
     * @param zytjlb
     * @return
     */
    String compareMinTcContent(@Param("strharm")String harm,@Param("strids") List<String> ids,@Param("zytjlb") String zytjlb);
}
