package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Review;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.RecheckItemsDto;
import com.center.medical.reception.bean.param.ReviewParam;
import com.center.medical.reception.bean.vo.ReviewPrintVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ZJ复查表(Review)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:26
 */
public interface ReviewMapper extends BaseMapper<Review> {

    /**
     * 分页查询[ZJ复查表]列表
     *
     * @param page  分页参数
     * @param param Review查询参数
     * @return 分页数据
     */
    IPage<Review> getPage(PageParam<Review> page, @Param("param") ReviewParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Review getInfoById(@Param("id") String id);

    /**
     * 根据id获取列表
     *
     * @param ids 对象id集合
     * @return 分页数据
     */
    List<ReviewPrintVo> getListByIds(@Param("ids") List<String> ids);

    /**
     * 查询复查项目(pacs)
     *
     * @param patientCode
     * @return
     */
    List<RecheckItemsDto> getRecheckItems(@Param("patientcode") String patientCode);

    /**
     * 查询复查项目
     *
     * @param patientCode
     * @return
     */
    List<RecheckItemsDto> getRecheckItem(@Param("patientcode") String patientCode);
}
