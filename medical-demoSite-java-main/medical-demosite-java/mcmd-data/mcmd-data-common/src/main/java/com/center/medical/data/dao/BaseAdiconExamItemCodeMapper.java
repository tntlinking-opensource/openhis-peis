package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseAdiconExamItemCode;
import com.center.medical.data.bean.vo.AdiconSelectVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 艾迪康项目代码表(BaseAdiconExamItemCode)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:28
 */
public interface BaseAdiconExamItemCodeMapper extends BaseMapper<BaseAdiconExamItemCode> {

    /**
     * 分页查询[艾迪康项目代码表]列表
     *
     * @param page  分页参数
     * @param param BaseAdiconExamItemCode查询参数
     * @return 分页数据
     */
    IPage<BaseAdiconExamItemCode> getList(PageParam<BaseAdiconExamItemCode> page, @Param("param") BaseAdiconExamItemCode param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseAdiconExamItemCode getInfoById(@Param("id") String id);

    /**
     * 通过输入码查询艾迪康项目代码表
     *
     * @param key
     * @return
     */
    List<AdiconSelectVo> getAdiconSelectData(@Param("key") String key);
}
