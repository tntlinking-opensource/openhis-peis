package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.abteilung.bean.model.DrugstoreClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.vo.ClassCheckDataVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 药品分类(DrugstoreClass)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:03
 */
public interface DrugstoreClassMapper extends BaseMapper<DrugstoreClass> {

    /**
     * 分页查询[药品分类]列表
     *
     * @param page  分页参数
     * @param param DrugstoreClass查询参数
     * @return 分页数据
     */
    IPage<DrugstoreClass> getList(PageParam<DrugstoreClass> page, @Param("param") DrugstoreClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrugstoreClass getInfoById(@Param("id") String id);

    /**
     * 获取类别代号下拉列表
     * @param key
     * @return
     */
    List<ClassCheckDataVo> getClassCheckData(@Param("key")String key);
}
