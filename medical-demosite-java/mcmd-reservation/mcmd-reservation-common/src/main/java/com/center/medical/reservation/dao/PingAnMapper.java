package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.PaPeissortexam;
import com.center.medical.reservation.bean.dto.GetPingAnCodeDto;
import com.center.medical.reservation.bean.param.PingAnNumsParam;
import com.center.medical.reservation.bean.vo.PingAnNumsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 平安软件-排检(PaPeissortexam)数据库访问层
 *
 * @author ay
 * @since 2023-10-20 13:51:10
 */
public interface PingAnMapper extends BaseMapper<PaPeissortexam> {

    /**
     * 分页查询[平安软件-排检]列表
     *
     * @param page  分页参数
     * @param param PaPeissortexam查询参数
     * @return 分页数据
     */
    IPage<PaPeissortexam> getPage(PageParam<PaPeissortexam> page, @Param("param") PaPeissortexam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PaPeissortexam getInfoById(@Param("id") String id);

    /**
     * 体检预约可用人数
     * @param param
     * @return
     */
    List<PingAnNumsVo> getAvailableNums(@Param("param") PingAnNumsParam param);

    /**
     * 获取平安好医生需要上传pdf的体检号
     * @return
     */
    List<GetPingAnCodeDto> getPingAnCode();
}
