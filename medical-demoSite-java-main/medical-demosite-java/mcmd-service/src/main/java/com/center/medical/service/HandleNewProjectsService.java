package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.HandleNewProjects;
import com.center.medical.bean.param.HNSOUParam;
import com.center.medical.bean.param.HandleNewParam;
import com.center.medical.bean.vo.HandleNewVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * KS检验科加项处理(HandleNewProjects)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:20
 */
public interface HandleNewProjectsService extends IService<HandleNewProjects> {

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param HandleNewProjects查询参数
     * @return 分页数据
     */
    IPage<HandleNewVo> getList(PageParam<HandleNewProjects> page, HandleNewParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    HandleNewProjects getInfoById(String id);

    /**
     * 处理
     *
     * @param param
     * @return
     */
    Boolean saOrUp(HNSOUParam param);

    /**
     * 批量处理
     *
     * @param ids
     * @param type
     * @return
     */
    Boolean CLSaveBatch(List<String> ids, String type);

    /**
     * 检验科加项自动处理
     * @param patientcode
     */
    void autoProcess(String patientcode);
}

