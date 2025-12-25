package com.center.medical.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.vo.ListWithSrmVo;
import com.center.medical.system.bean.model.Keshi;
import com.center.medical.system.bean.param.KeshiParam;

import java.util.List;

/**
 * 科室管理业务接口
 *
 * @author 路飞船长
 * @since 2022-11-29 11:53:34
 */
public interface KeshiService extends IService<Keshi> {

    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    List<ListWithSrmVo> getList(KeshiParam param);
}

