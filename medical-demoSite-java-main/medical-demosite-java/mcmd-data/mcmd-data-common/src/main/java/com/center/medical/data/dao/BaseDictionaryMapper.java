package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseDictionary;
import com.center.medical.data.bean.param.BaseDictionaryParam;
import com.center.medical.data.bean.vo.BaseDictionaryVo;
import com.center.medical.data.bean.vo.CrptSizeCodeVo;
import com.center.medical.data.bean.vo.EconomyCodeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典(BaseDictionary)表数据库访问层
 *
 * @author ay
 * @since 2022-11-18 18:16:13
 */
public interface BaseDictionaryMapper extends BaseMapper<BaseDictionary> {

    /**
    * 分页查询[字典]列表
    *
    * @param page 分页参数
    * @param param BaseDictionary查询参数
    * @return 分页数据
    */
    IPage<BaseDictionary> getList(PageParam<BaseDictionary> page, @Param("param") BaseDictionary param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    BaseDictionary getInfoById(@Param("id") String id,@Param("classCode")String classCode);



    /**
     * 获取classCode为308的数据 体检项目
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    IPage<BaseDictionaryVo> getOurDictionary308(PageParam<BaseDictionaryVo> page, @Param("param") BaseDictionaryParam baseDictionaryParam);

    /**
     * 获取classCode为312的数据 危害因素
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    IPage<BaseDictionaryVo> getOurDictionary312(PageParam<BaseDictionaryVo> page,@Param("param") BaseDictionaryParam baseDictionaryParam);

    /**
     * 获取classCode为310的数据 症状
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    IPage<BaseDictionaryVo> getOurDictionary310(PageParam<BaseDictionaryVo> page,@Param("param") BaseDictionaryParam baseDictionaryParam);

    /**
     * 获取classCode为313的数据 职业禁忌症
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    IPage<BaseDictionaryVo> getOurDictionary313(PageParam<BaseDictionaryVo> page, @Param("param")BaseDictionaryParam baseDictionaryParam);

    /**
     * 获取classCode为314的数据 疑似职业病
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    IPage<BaseDictionaryVo> getOurDictionary314(PageParam<BaseDictionaryVo> page, @Param("param")BaseDictionaryParam baseDictionaryParam);

    /**
     * 获取classCode为323的数据 收费项目
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    IPage<BaseDictionaryVo> getOurDictionary323(PageParam<BaseDictionaryVo> page, @Param("param")BaseDictionaryParam baseDictionaryParam);

    /**
     * 获取经济类型
     * @return
     */
    List<EconomyCodeVo> getEconomyCode();

    /**
     * 获取经济类型317
     * @return
     */
    List<CrptSizeCodeVo> getCrptSizeCode();
}
