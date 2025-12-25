package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Wsjg;
import com.center.medical.data.bean.param.WsjgParam;
import com.center.medical.data.bean.vo.WsjgDataVo;
import com.center.medical.data.dao.WsjgMapper;
import com.center.medical.data.service.WsjgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外送机构(Wsjg)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-29 11:22:58
 */
@Slf4j
@Service("wsjgService")
@RequiredArgsConstructor
public class WsjgServiceImpl extends ServiceImpl<WsjgMapper, Wsjg> implements WsjgService {

    private final WsjgMapper wsjgMapper;

    /**
     * 分页查询[外送机构]列表
     *
     * @param page  分页参数
     * @param param Wsjg查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Wsjg> getPage(PageParam<Wsjg> page, WsjgParam param) {
        return wsjgMapper.getPage(page, param);
    }

    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    @Override
    public List<Wsjg> getList(WsjgParam param) {
        return wsjgMapper.getList(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Wsjg getInfoById(String id) {
        return wsjgMapper.getInfoById(id);
    }

    /**
     * 外送机构下拉
     * @param page
     * @param srm
     * @return
     */
    @Override
    public IPage<WsjgDataVo> getWsjgData(PageParam<WsjgDataVo> page, String srm) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(srm)){
            srm = srm.trim().toUpperCase();
        }
        return wsjgMapper.getWsjgData(page,srm);
    }
}

