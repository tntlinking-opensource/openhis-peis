package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PeisState;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ProfessionUploadResultParam;
import com.center.medical.statistics.bean.vo.ProfessionUploadResultVo;
import com.center.medical.statistics.dao.ProfessionUploadResultMapper;
import com.center.medical.statistics.service.ProfessionUploadResultService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 云平台上传结果
 * @author xhp
 * @since 2023-11-28 10:15
 */
@Service
public class ProfessionUploadResultServiceImpl extends ServiceImpl<ProfessionUploadResultMapper, PeisState> implements ProfessionUploadResultService {
    /**
     * 分页查询
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ProfessionUploadResultVo> getPage(PageParam page, ProfessionUploadResultParam param){
        return baseMapper.getPage(page,param);
    }

    /**
     * 重新上传
     * @param patientcodes
     */
    @Override
    @Transactional
    public void updateStatus(List<String> patientcodes){
        List<PeisState> peisStates=baseMapper.selectList(
                new QueryWrapper<PeisState>()
                        .in("patientcode",patientcodes)
        );
        if(peisStates.size()>0){
            for(PeisState peisState:peisStates){
                peisState.setJinanStatus(0);
            }
            updateBatchById(peisStates);
        }
    }

    /**
     * 获取导出数据
     * @param param
     * @return
     */
    @Override
    public List<ProfessionUploadResultVo> getList(ProfessionUploadResultParam param){
        return baseMapper.getList(param);
    }
}
