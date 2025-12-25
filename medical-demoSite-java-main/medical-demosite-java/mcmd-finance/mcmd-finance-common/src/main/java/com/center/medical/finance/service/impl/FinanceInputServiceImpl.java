package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Financeinput;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.FIPageParam;
import com.center.medical.finance.bean.vo.FIPageVo;
import com.center.medical.finance.dao.FinanceInputMapper;
import com.center.medical.finance.service.FinanceInputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-06 16:22:37
 */
@Slf4j
@Service("financeInputService")
@RequiredArgsConstructor
public class FinanceInputServiceImpl extends ServiceImpl<FinanceInputMapper, Financeinput> implements FinanceInputService {

    private final FinanceInputMapper financeInputMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FIPageVo> getList(PageParam<FIPageVo> page, FIPageParam param) {
        param.setFzxid(SecurityUtils.getCId());
        return financeInputMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Financeinput getInfoById(String id) {
        return financeInputMapper.getInfoById(id);
    }


    /**
     * 是否允许编辑
     *
     * @param euserId
     * @param eyear
     * @return
     */
    @Override
    public String isEdit(String euserId, String eyear) {
        String state = "error";
        //获取当前登录用户所在的分中心
        String fzxId = SecurityUtils.getCId();
        //销售财务录入表
        Financeinput financeInput = financeInputMapper.selectOne(new QueryWrapper<Financeinput>()
                .eq("xsjlid", euserId).eq("year", eyear).eq("fzxid", fzxId));
        if (null == financeInput) {
            state = "success";
        }
        return state;
    }


    /**
     * 是否允许查看
     *
     * @param viewUserId
     * @param viewYear
     * @return
     */
    @Override
    public String isView(String viewUserId, String viewYear) {
        String state = "error";
        //获取当前登录用户所在的分中心
        String fzxId = SecurityUtils.getCId();
        Financeinput financeInput = financeInputMapper.selectOne(new QueryWrapper<Financeinput>()
                .eq("xsjlid", viewUserId).eq("year", viewYear).eq("fzxid", fzxId));
        if (null == financeInput) {
            state = "success";
        }
        return state;
    }


    /**
     * 财务录入-是否已填写
     *
     * @param fuserId
     * @param fyear
     * @return
     */
    @Override
    public String isFinanceInput(String fuserId, String fyear) {
        String state = "error";
        //获取当前登录用户所在的分中心
        String fzxId = SecurityUtils.getCId();
        Financeinput financeInput = financeInputMapper.selectOne(new QueryWrapper<Financeinput>()
                .eq("xsjlid", fuserId).eq("year", fyear).eq("fzxid", fzxId));
        if (null == financeInput) {
            state = "success";
        }
        return state;
    }


    /**
     * 数据保存或修改
     *
     * @param financeInput
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saOrUp(Financeinput financeInput) {
        if (StringUtils.isBlank(financeInput.getId())) {
            //获取当前登录用户分中心id
            String fzxId = SecurityUtils.getCId();
            //保存操作
            financeInput.setFzxid(fzxId);
            financeInputMapper.insert(financeInput);
        } else {
            //更新操作
            Financeinput financeinput = financeInputMapper.getInfoById(financeInput.getId());
            if (ObjectUtils.isEmpty(financeinput)) {
                throw new ServiceException("该id不存在!");
            }
            financeInputMapper.updateById(financeInput);
        }
        return Boolean.TRUE;
    }
}

