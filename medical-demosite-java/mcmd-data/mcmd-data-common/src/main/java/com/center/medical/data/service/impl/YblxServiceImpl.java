package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Yblx;
import com.center.medical.data.bean.param.YblxParam;
import com.center.medical.data.dao.YblxMapper;
import com.center.medical.data.service.YblxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 样本类型(Yblx)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-29 11:22:59
 */
@Slf4j
@Service("yblxService")
@RequiredArgsConstructor
public class YblxServiceImpl extends ServiceImpl<YblxMapper, Yblx> implements YblxService {

    private final YblxMapper yblxMapper;

    /**
     * 分页查询[样本类型]列表
     *
     * @param page  分页参数
     * @param param Yblx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Yblx> getPage(PageParam<Yblx> page, YblxParam param) {
        return yblxMapper.getPage(page, param);
    }

    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    @Override
    public List<Yblx> getList(YblxParam param) {
        return yblxMapper.getList(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Yblx getInfoById(String id) {
        return yblxMapper.getInfoById(id);
    }

}

