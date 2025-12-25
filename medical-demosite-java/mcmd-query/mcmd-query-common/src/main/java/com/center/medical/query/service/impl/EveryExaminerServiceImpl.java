package com.center.medical.query.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.NumUtil;
import com.center.medical.common.utils.Render;
import com.center.medical.common.utils.page.PageAdapter;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.dto.EETimeListDto;
import com.center.medical.query.bean.dto.EETimeListDto1;
import com.center.medical.query.bean.param.ECGroupDataParam;
import com.center.medical.query.bean.param.EEChargeDataParam;
import com.center.medical.query.bean.param.EveryExaminerParam;
import com.center.medical.query.bean.vo.EEChargeDataVo;
import com.center.medical.query.bean.vo.EveryExaminerVo;
import com.center.medical.query.dao.EveryExaminerMapper;
import com.center.medical.query.service.EveryExaminerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 每日体检明细(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
@Slf4j
@Service("everyExaminerService")
@RequiredArgsConstructor
public class EveryExaminerServiceImpl extends ServiceImpl<EveryExaminerMapper, Peispatient> implements EveryExaminerService {

    private final EveryExaminerMapper everyExaminerMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<EveryExaminerVo> getList(PageParam<EveryExaminerVo> page, EveryExaminerParam param) {
        verifyFilterTime(param.getStartTime(),param.getEndTime());
        PageAdapter pageAdapter = new PageAdapter(page);
        List<EveryExaminerVo> list = everyExaminerMapper.getList(pageAdapter, param);
        //循环
        for (EveryExaminerVo vo : list) {
            vo.setZkl(Render.getZkl(vo.getMoneyamount(), vo.getYjhj()));
        }
        Long total = everyExaminerMapper.countList(param);
        page.setRecords(list);
        page.setTotal(total);
        return page;
    }

    /**
     * 验证筛选时间
     * @param startTime
     * @param endTime
     */
    private void verifyFilterTime(Date startTime, Date endTime) {
        if (ObjectUtils.isEmpty(startTime) || ObjectUtils.isEmpty(endTime)) {
            throw new ServiceException("开始时间或结束时间不能为空!");
        }
        long mouth = DateUtil.betweenMonth(startTime, endTime, true);
        if (mouth > 13) {
            throw new ServiceException("筛选时间不能超过12个月!");
        }
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return everyExaminerMapper.getInfoById(id);
    }


    /**
     * 获取分组数据
     *
     * @param param
     * @return
     */
    @Override
    public List<EETimeListDto> getGroupData(ECGroupDataParam param) {
        verifyFilterTime(param.getStartTime(),param.getEndTime());
        List<EETimeListDto> dataList = new ArrayList();

        switch (param.getIndex()) {
            case 1:
                //统计某段时间所有体检者
                List<EETimeListDto> list = everyExaminerMapper.getTimeList(param);
                EETimeListDto obj1 = null;
                //集合不为空
                if (CollectionUtils.isNotEmpty(list)) {
                    obj1 = list.get(0);
                } else {
                    obj1 = new EETimeListDto();
                }
                //添加
                obj1.setTjtt("所有体检者");
                obj1 = getEveryDays(obj1);
                dataList.add(obj1);
                break;
            case 2:
                param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
                List<EETimeListDto> l21 = everyExaminerMapper.getSqlNew1(param, 1);
                List<EETimeListDto1> l22 = everyExaminerMapper.getSqlNew2(param, 1);
                List<EETimeListDto1> l23 = everyExaminerMapper.getSqlNew3(param, 1);
                int l2i = 0;
                for (EETimeListDto item : l21) {
                    item.setJxfy(l22.get(l2i).getJxfy());
                    item.setJxrc(l22.get(l2i).getJxrc());
                    item.setYjhj(l23.get(l2i).getYjhj());
                    l2i++;
                }
                EETimeListDto dto1 = getEveryDays(l21.get(0));
                dto1.setTjtt("团体体检");
                dataList.add(dto1);
                break;
            case 3:
                param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
                List<EETimeListDto> l31 = everyExaminerMapper.getSqlNew1(param, 2);
                List<EETimeListDto1> l32 = everyExaminerMapper.getSqlNew2(param, 2);
                List<EETimeListDto1> l33 = everyExaminerMapper.getSqlNew3(param, 2);
                int l3i = 0;
                for (EETimeListDto item : l31) {
                    item.setJxfy(l32.get(l3i).getJxfy());
                    item.setJxrc(l32.get(l3i).getJxrc());
                    item.setYjhj(l33.get(l3i).getYjhj());
                    l3i++;
                }
                EETimeListDto dto2 = getEveryDays(l31.get(0));
                dto2.setTjtt("团体疫苗");
                dataList.add(dto2);
                break;
            case 4:
                param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
                List<EETimeListDto> l41 = everyExaminerMapper.getSqlNew1(param, 3);
                List<EETimeListDto1> l42 = everyExaminerMapper.getSqlNew2(param, 3);
                List<EETimeListDto1> l43 = everyExaminerMapper.getSqlNew3(param, 3);
                int l4i = 0;
                for (EETimeListDto item : l41) {
                    item.setJxfy(l42.get(l4i).getJxfy());
                    item.setJxrc(l42.get(l4i).getJxrc());
                    item.setYjhj(l43.get(l4i).getYjhj());
                    l4i++;
                }
                EETimeListDto dto3 = getEveryDays(l41.get(0));
                dto3.setTjtt("团体核酸");
                dataList.add(dto3);
                break;
            case 5:
                param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
                List<EETimeListDto> l51 = everyExaminerMapper.getSqlNew1(param, 4);
                List<EETimeListDto1> l52 = everyExaminerMapper.getSqlNew2(param, 4);
                List<EETimeListDto1> l53 = everyExaminerMapper.getSqlNew3(param, 4);
                int l5i = 0;
                for (EETimeListDto item : l51) {
                    item.setJxfy(l52.get(l5i).getJxfy());
                    item.setJxrc(l52.get(l5i).getJxrc());
                    item.setYjhj(l53.get(l5i).getYjhj());
                    l5i++;
                }
                EETimeListDto dto4 = getEveryDays(l51.get(0));
                dto4.setTjtt("个检体检");
                dataList.add(dto4);
                break;
            case 6:
                param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
                List<EETimeListDto> l61 = everyExaminerMapper.getSqlNew1(param, 5);
                List<EETimeListDto1> l62 = everyExaminerMapper.getSqlNew2(param, 5);
                List<EETimeListDto1> l63 = everyExaminerMapper.getSqlNew3(param, 5);
                int l6i = 0;
                for (EETimeListDto item : l61) {
                    item.setJxfy(l62.get(l6i).getJxfy());
                    item.setJxrc(l62.get(l6i).getJxrc());
                    item.setYjhj(l63.get(l6i).getYjhj());
                    l6i++;
                }
                EETimeListDto dto5 = getEveryDays(l61.get(0));
                dto5.setTjtt("个检疫苗");
                dataList.add(dto5);
                break;
            case 7:
                param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
                List<EETimeListDto> l71 = everyExaminerMapper.getSqlNew1(param, 6);
                List<EETimeListDto1> l72 = everyExaminerMapper.getSqlNew2(param, 6);
                List<EETimeListDto1> l73 = everyExaminerMapper.getSqlNew3(param, 6);
                int l7i = 0;
                for (EETimeListDto item : l71) {
                    item.setJxfy(l72.get(l7i).getJxfy());
                    item.setJxrc(l72.get(l7i).getJxrc());
                    item.setYjhj(l73.get(l7i).getYjhj());
                    l7i++;
                }
                EETimeListDto dto6 = getEveryDays(l71.get(0));
                dto6.setTjtt("个检核酸");
                dataList.add(dto6);
                break;
            case 8:
                param.setItemIds(Constants.NUCLEIN_ITEMS_IDS);
                //查询所有单位
                List<EETimeListDto> records = everyExaminerMapper.getGroupSql(param);
                if (CollectionUtils.isNotEmpty(records)) {
                    for (int i = 0; i < records.size(); i++) {
                        EETimeListDto obj = records.get(i);
                        obj = getEveryDays(obj);
                        obj.setTjtt(obj.getKhdwmc());
                        dataList.add(obj);
                    }
                }
                break;
            default:

        }
        for (EETimeListDto dto : dataList) {
            //折扣率
            String zkl = "";
            Double yjhj = ObjectUtils.isNotEmpty(dto.getYjhj()) ? Double.parseDouble(dto.getYjhj()) : 0.0;
            Double yfhj = ObjectUtils.isNotEmpty(dto.getYfhj()) ? Double.parseDouble(dto.getYfhj().toString()) : 0.0;
            if (yjhj != null && yjhj != 0.0) {
                zkl = NumUtil.DrivedF(yfhj, yjhj, 2) + "";
            }
            dto.setZkl(zkl);
        }
        return dataList;
    }

    /**
     * 算出 unitPrice 价格
     *
     * @param obj1
     * @return
     */
    private EETimeListDto getEveryDays(EETimeListDto obj1) {

        BigDecimal ydj = obj1.getYdj() == null ? BigDecimal.ZERO : new BigDecimal(obj1.getYdj().toString());
        BigDecimal yfhj = obj1.getYfhj() == null ? BigDecimal.ZERO : new BigDecimal(obj1.getYfhj().toString());
        String kdj = ydj.doubleValue() == 0 ? "" : (yfhj.divide(ydj, 2, RoundingMode.HALF_UP).toString());
        obj1.setUnitprice(kdj);
        obj1.setYfhj(ObjectUtils.isNotEmpty(obj1.getYfhj()) ? obj1.getYfhj() : new BigDecimal(0));
        obj1.setJkrs(ObjectUtils.isNotEmpty(obj1.getJkrs()) ? obj1.getJkrs() : "0");
        obj1.setZyrs(ObjectUtils.isNotEmpty(obj1.getZyrs()) ? obj1.getZyrs() : "0");
        obj1.setYdj(ObjectUtils.isNotEmpty(obj1.getYdj()) ? obj1.getYdj() : new BigDecimal(0));
        obj1.setWdj(ObjectUtils.isNotEmpty(obj1.getWdj()) ? obj1.getWdj() : "0");
        obj1.setNan(ObjectUtils.isNotEmpty(obj1.getNan()) ? obj1.getNan() : "0");
        obj1.setNv(ObjectUtils.isNotEmpty(obj1.getNv()) ? obj1.getNv() : "0");
        obj1.setFfwc(ObjectUtils.isNotEmpty(obj1.getFfwc()) ? obj1.getFfwc() : "0");
        obj1.setFjww(ObjectUtils.isNotEmpty(obj1.getFjww()) ? obj1.getFjww() : "0");
        obj1.setJkwc(ObjectUtils.isNotEmpty(obj1.getJkwc()) ? obj1.getJkwc() : "0");
        obj1.setJkww(ObjectUtils.isNotEmpty(obj1.getJkww()) ? obj1.getJkww() : "0");
        obj1.setZywc(ObjectUtils.isNotEmpty(obj1.getZywc()) ? obj1.getZywc() : "0");
        obj1.setZyww(ObjectUtils.isNotEmpty(obj1.getZyww()) ? obj1.getZyww() : "0");
        obj1.setJkbgyd(ObjectUtils.isNotEmpty(obj1.getJkbgyd()) ? obj1.getJkbgyd() : "0");
        obj1.setJkbgwd(ObjectUtils.isNotEmpty(obj1.getJkbgwd()) ? obj1.getJkbgwd() : "0");
        obj1.setZybgyd(ObjectUtils.isNotEmpty(obj1.getZybgyd()) ? obj1.getZybgyd() : "0");
        obj1.setZybgwd(ObjectUtils.isNotEmpty(obj1.getZybgwd()) ? obj1.getZybgwd() : "0");
        obj1.setJxrc(ObjectUtils.isNotEmpty(obj1.getJxrc()) ? obj1.getJxrc() : "0");
        obj1.setJxfy(ObjectUtils.isNotEmpty(obj1.getJxfy()) ? obj1.getJxfy() : "0.00");
        obj1.setYjhj(ObjectUtils.isNotEmpty(obj1.getYjhj()) ? obj1.getYjhj() : "0.00");
        obj1.setFcrs(ObjectUtils.isNotEmpty(obj1.getFcrs()) ? obj1.getFcrs() : "0");
        return obj1;
    }

    /**
     * 点击获取收费项目信息
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<EEChargeDataVo> getChargeData(PageParam<EEChargeDataVo> page, EEChargeDataParam param) {
        return everyExaminerMapper.getChargeData(page, param);
    }


    /**
     * 导出每日体检者构成人员
     *
     * @param param
     * @return
     */
    @Override
    public List<EveryExaminerVo> getExportData(EveryExaminerParam param) {
        verifyFilterTime(param.getStartTime(),param.getEndTime());
        List<EveryExaminerVo> list = everyExaminerMapper.getExportData(param);
        //循环
        int i = 1;
        for (EveryExaminerVo vo : list) {
            vo.setRownum(i++);
            //折扣率
            vo.setZkl(Render.getZkl(vo.getMoneyamount(), vo.getYjhj()));
            //导出记账未结
            if (null != vo.getIdPayway() && "记账".equals(vo.getIdPayway())) {
                vo.setJzwjdc(vo.getMoneyamountpaid().toString());
            } else {
                vo.setJzwjdc(vo.getPatientcode());
            }

        }
        return list;
    }
}

