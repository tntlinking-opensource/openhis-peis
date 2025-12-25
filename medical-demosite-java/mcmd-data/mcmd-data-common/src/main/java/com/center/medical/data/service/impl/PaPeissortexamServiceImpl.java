package com.center.medical.data.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.PaPeissortexam;
import com.center.medical.data.bean.param.PaPeiEParam;
import com.center.medical.data.dao.PaPeissortexamMapper;
import com.center.medical.data.service.PaPeissortexamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * 平安软件-排检(PaPeissortexam)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 11:26:55
 */
@Slf4j
@Service("paPeissortexamService")
@RequiredArgsConstructor
public class PaPeissortexamServiceImpl extends ServiceImpl<PaPeissortexamMapper, PaPeissortexam> implements PaPeissortexamService {

    private final PaPeissortexamMapper paPeissortexamMapper;

    /**
     * 分页查询[平安软件-排检]列表
     *
     * @param page  分页参数
     * @param param PaPeissortexam查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PaPeissortexam> getList(PageParam<PaPeissortexam> page, PaPeiEParam param) {
        return paPeissortexamMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id PaPeissortexam查询参数
     * @return 分页数据
     */
    @Override
    public PaPeissortexam getInfoById(String id) {
        return paPeissortexamMapper.getInfoById(id);
    }

    @Override
    public Boolean saOrUp(PaPeissortexam paPeissortexam) {
        Date startDate = paPeissortexam.getStartDate();
        Date endDate = paPeissortexam.getEndDate();
        paPeissortexam.setModifydate(new Date());

        if (StringUtils.isBlank(paPeissortexam.getId())) {
            //新增
            if (startDate.after(endDate)) {
                throw new ServiceException("结束时间不能早于开始时间！");
            }
            if (!(DateUtil.compare(startDate, new Date(), "yyyy-MM-dd") > 0)) {
                throw new ServiceException("预约日期不能早于当前日期！");
            }
            Calendar sc = Calendar.getInstance();
            sc.setTime(startDate);
            Calendar ec = Calendar.getInstance();
            ec.setTime(endDate);
            while (!sc.after(ec)) {
                if (paPeissortexamMapper.selectCount(new LambdaQueryWrapper<PaPeissortexam>()
                        .eq(PaPeissortexam::getBranchId, paPeissortexam.getBranchId())
                        .eq(PaPeissortexam::getSortDate, sc.getTime())) > 0) {
                    throw new ServiceException(DateUtil.format(sc.getTime(), "yyyy-MM-dd") + "的预约设置已存在！");
                }
                paPeissortexam.setSortDate(sc.getTime());
                paPeissortexam.setCreatedate(new Date());
                paPeissortexam.setId(null);
                paPeissortexamMapper.insert(paPeissortexam);
                sc.add(Calendar.DATE, 1);
            }
        } else {
            //更新
            PaPeissortexam ppDb = paPeissortexamMapper.getInfoById(paPeissortexam.getId());
            if (!(DateUtil.compare(ppDb.getSortDate(), new Date(), "yyyy-MM-dd") > 0)) {
                throw new ServiceException("过期预约设置不可修改！");
            }
            if (!(DateUtil.compare(paPeissortexam.getSortDate(), new Date(), "yyyy-MM-dd") > 0)) {
                throw new ServiceException("预约日期不能早于当前日期！");
            }
            if (paPeissortexamMapper.selectCount(new LambdaQueryWrapper<PaPeissortexam>()
                    .eq(PaPeissortexam::getBranchId, paPeissortexam.getBranchId())
                    .ne(PaPeissortexam::getId, paPeissortexam.getId())
                    .eq(PaPeissortexam::getSortDate, paPeissortexam.getSortDate())) > 0) {
                throw new ServiceException(paPeissortexam.getSortDate() + "的预约设置已存在！");
            }
            paPeissortexam.setModifydate(new Date());
            paPeissortexamMapper.updateById(paPeissortexam);
        }

        return Boolean.TRUE;
    }

}

