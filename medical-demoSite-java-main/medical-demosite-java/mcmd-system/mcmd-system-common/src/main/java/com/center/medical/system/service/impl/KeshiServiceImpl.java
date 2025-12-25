package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.vo.ListWithSrmVo;
import com.center.medical.system.bean.model.Keshi;
import com.center.medical.system.bean.param.KeshiParam;
import com.center.medical.system.dao.KeshiMapper;
import com.center.medical.system.service.KeshiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 科室管理业务实现
 *
 * @author 路飞船长
 * @since 2022-11-29 11:53:34
 */
@Slf4j
@Service("keshiService")
@RequiredArgsConstructor
public class KeshiServiceImpl extends ServiceImpl<KeshiMapper, Keshi> implements KeshiService {

    private final KeshiMapper keshiMapper;


    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    @Override
    public List<ListWithSrmVo> getList(KeshiParam param) {
        return keshiMapper.getList(param);
    }
}

