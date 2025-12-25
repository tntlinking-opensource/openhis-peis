package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.vo.EPTotalVo;
import com.center.medical.statistics.bean.vo.EveryPhysicalVo;
import com.center.medical.statistics.dao.EveryPhysicalMapper;
import com.center.medical.statistics.service.EveryPhysicalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 每日体检量统计(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
@Slf4j
@Service("everyPhysicalService")
@RequiredArgsConstructor
public class EveryPhysicalServiceImpl extends ServiceImpl<EveryPhysicalMapper, Peispatient> implements EveryPhysicalService {

    private final EveryPhysicalMapper everyPhysicalMapper;
    private final MapperFacade mapperFacade;
    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<EveryPhysicalVo> getList(PageParam<EveryPhysicalVo> page, BaseParam param) {
        IPage<EveryPhysicalVo> iPage = everyPhysicalMapper.getList(page, param);
        List<EveryPhysicalVo> list = iPage.getRecords();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //设置属性
        for (EveryPhysicalVo vo : list) {
            String date = vo.getDate();
            if(date!=null){
                Calendar c = Calendar.getInstance();
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.setTime(d);
                int dayForWeek = 0;
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
                vo.setDayForWeek(dayForWeek);
            }
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 导出每日体检量统计
     * @param param
     * @return
     */
    @Override
    public List<EveryPhysicalVo> exportData(BaseParam param) {
        List<EveryPhysicalVo> list = everyPhysicalMapper.exportData(param);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //设置属性
        for (int i = 0; i < list.size(); i++) {
            EveryPhysicalVo vo = list.get(i);
            vo.setRownum(i+1);
            String date = vo.getDate();
            if(date!=null){
                Calendar c = Calendar.getInstance();
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.setTime(d);
                int dayForWeek = 0;
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
                vo.setDayForWeek(dayForWeek);
            }
        }
        return list;
    }


    /**
     * 页面下方总计数据
     * @param param
     * @return
     */
    @Override
    public EPTotalVo getTotal(BaseParam param) {
        EPTotalVo data = new EPTotalVo();
        //登记，开始
        EPTotalVo vo1 = everyPhysicalMapper.getTotal1(param);
        data = mapperFacade.map(vo1, EPTotalVo.class);
        data.setRegitsOrg(vo1.getRegistAll() - vo1.getRegitsPersonal());
        data.setStartOrg(vo1.getStartAll() - vo1.getStartPersonal());

        //健康，职业总检
        EPTotalVo vo2 = everyPhysicalMapper.getTotal2(param);
        data.setJkzjAll(vo2.getJkzjAll());
        data.setJkzjPersonal(vo2.getJkzjPersonal());
        data.setJkzjOrg(vo2.getJkzjAll() - vo2.getJkzjPersonal());
        data.setZyzjAll(vo2.getZyzjAll());
        data.setZyzjPersonal(vo2.getZyzjPersonal());
        data.setZyzjOrg(vo2.getZyzjAll() - vo2.getZyzjPersonal());


        //健康领取,职业领取
        EPTotalVo vo3 = everyPhysicalMapper.getTotal3(param);
        data.setJklqAll(vo3.getJklqAll());
        data.setJklqPersonal(vo3.getJklqPersonal());
        data.setJklqOrg(vo3.getJklqAll() - vo3.getJklqPersonal());
        data.setZylqAll(vo3.getZylqAll());
        data.setZylqPersonal(vo3.getZylqPersonal());
        data.setZylqOrg(vo3.getZylqAll()-vo3.getZylqPersonal());
        return data;
    }
}

