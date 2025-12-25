package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.KdSaleer;
import com.center.medical.system.bean.param.KdSaleerParam;
import org.apache.ibatis.annotations.Param;

/**
 * 金蝶销售员(Saleer)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:36
 */
public interface KdSaleerMapper extends BaseMapper<KdSaleer> {

    /**
     * 分页查询[金蝶销售员]列表
     *
     * @param page  分页参数
     * @param param Saleer查询参数
     * @return 分页数据
     */
    IPage<KdSaleer> getList(PageParam<KdSaleer> page, @Param("param") KdSaleerParam param);

    /**
     * 通过账户号查询
     * @param kingdeeAccountNo
     * @return
     */
    KdSaleer getByAccountNo(@Param("kingdeeAccountNo") String kingdeeAccountNo);
}
