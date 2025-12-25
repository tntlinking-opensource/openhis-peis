package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.param.SDPageParam;
import com.center.medical.finance.bean.vo.SDPageVo;
import com.center.medical.finance.dao.SpendDetailMapper;
import com.center.medical.finance.service.SpendDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检卡管理-卡消费明细(Card)表服务实现类
 *
 * @author ay
 * @since 2023-03-30 18:47:31
 */
@Slf4j
@Service("spendDetailService")
@RequiredArgsConstructor
public class SpendDetailServiceImpl extends ServiceImpl<SpendDetailMapper, Card> implements SpendDetailService {

    private final SpendDetailMapper spendDetailMapper;

    /**
     * 分页查询[体检卡]列表
     *
     * @param page  分页参数
     * @param param Card查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SDPageVo> getList(PageParam<SDPageVo> page, SDPageParam param) {
        return spendDetailMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Card getInfoById(String id) {
        return spendDetailMapper.getInfoById(id);
    }


    /**
     * 导出体检卡消费明细
     *
     * @param param
     * @return
     */
    @Override
    public List<SDPageVo> getExportData(SDPageParam param) {
        return spendDetailMapper.getExportData(param);
    }
}

