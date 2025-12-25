package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.OutsidePictrue;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KS外送项目图片结果(OutsidePictrue)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:07
 */
public interface OutsidePictrueMapper extends BaseMapper<OutsidePictrue> {

    /**
     * 分页查询[KS外送项目图片结果]列表
     *
     * @param page  分页参数
     * @param param OutsidePictrue查询参数
     * @return 分页数据
     */
    IPage<OutsidePictrue> getList(PageParam<OutsidePictrue> page, @Param("param") OutsidePictrue param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OutsidePictrue getInfoById(@Param("id") String id);

    /**
     * 根据体检号查询最大序号
     * @param patientcode
     * @return
     */
    int selectMaxIndex(@Param("patientcode")String patientcode);

    /**
     * 获取外送图片
     * @param patientcode
     * @return
     */
    List<OutsidePictrue> getOutSidePicture(@Param("patientcode") String patientcode);
}
