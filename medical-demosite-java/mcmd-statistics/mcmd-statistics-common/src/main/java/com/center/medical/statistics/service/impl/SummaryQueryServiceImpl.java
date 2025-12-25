package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.dto.SQZrsDto;
import com.center.medical.statistics.bean.dto.YcrsDto;
import com.center.medical.statistics.bean.param.ComponyQueryParam;
import com.center.medical.statistics.bean.param.SQPageParam;
import com.center.medical.statistics.bean.param.SummaryDataParam;
import com.center.medical.statistics.bean.vo.ComponyQueryVo;
import com.center.medical.statistics.bean.vo.HarmQueryVo;
import com.center.medical.statistics.bean.vo.SQPageVo;
import com.center.medical.statistics.bean.vo.SummaryDataVo;
import com.center.medical.statistics.dao.SummaryQueryMapper;
import com.center.medical.statistics.service.PositiveQueryService;
import com.center.medical.statistics.service.SummaryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
@Slf4j
@Service("summaryQueryService")
@RequiredArgsConstructor
public class SummaryQueryServiceImpl extends ServiceImpl<SummaryQueryMapper, Peispatient> implements SummaryQueryService {

    private final SummaryQueryMapper summaryQueryMapper;
    private final PositiveQueryService positiveQueryService;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SQPageVo> getList(PageParam<SQPageVo> page, SQPageParam param) {
        IPage<SQPageVo> iPage = summaryQueryMapper.getList(page, param);
        List<SQPageVo> list = iPage.getRecords();
        //循环
        for (SQPageVo vo : list) {
            //接害因素
            String harmStr = positiveQueryService.getHarmStr(vo.getJhys());
            vo.setJhys(harmStr);
        }
        iPage.setRecords(list);
        return iPage;
    }



    /**
     * 导出本次职业健康检查拒检补检人员
     * @param param
     * @return
     */
    @Override
    public List<SQPageVo> getExportData(SQPageParam param) {
        List<SQPageVo> list = summaryQueryMapper.getExportData(param);
        //循环
        int i = 0;
        for (SQPageVo vo : list) {
            //序列
            i += 1;
            vo.setRownum(i);
            //接害因素
            String harmStr = positiveQueryService.getHarmStr(vo.getJhys());
            vo.setJhys(harmStr);
        }
        return list;
    }


    /**
     * 分页职业健康检查职业禁忌证人员名单
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<SummaryDataVo> getSummaryData(PageParam<SummaryDataVo> page, SummaryDataParam param) {
        IPage<SummaryDataVo> iPage = summaryQueryMapper.getSummaryData(page, param);
        List<SummaryDataVo> list = iPage.getRecords();
        for (SummaryDataVo vo : list) {
            //接害因素
            String harmStr = positiveQueryService.getHarmStr(vo.getJhys());
            vo.setJhys(harmStr);
        }
        iPage.setRecords(list);
        return iPage;
    }


    /**
     * 导出禁忌症
     * @param param
     * @return
     */
    @Override
    public List<SummaryDataVo> exportJjz(SummaryDataParam param) {
        List<SummaryDataVo> list = summaryQueryMapper.exportJjz(param);
        for (SummaryDataVo vo : list) {
            //接害因素
            String harmStr = positiveQueryService.getHarmStr(vo.getJhys());
            vo.setJhys(harmStr);
        }
        return list;
    }


    /**
     * 分页查询职业健康检查结果汇总表 (按单位)
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ComponyQueryVo> getComponyQuery(PageParam<ComponyQueryVo> page, ComponyQueryParam param) {
        return summaryQueryMapper.getComponyQuery(page,param);
    }


    /**
     * 导出单位职业健康检查结果附表（按用人单位统计）
     * @param param
     * @return
     */
    @Override
    public List<ComponyQueryVo> exportComponyQuery(ComponyQueryParam param) {
        List<ComponyQueryVo> list = summaryQueryMapper.exportComponyQuery(param);
        for (int i = 0; i < list.size(); i++) {
            ComponyQueryVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        return list;
    }


    /**
     * 查询职业健康检查结果汇总表 (按危害因素)
     * @param param
     * @return
     */
    @Override
    public List<HarmQueryVo> getHarmQuery(ComponyQueryParam param) {
        // 合计
        int sgq = 0, sgq_jjz = 0, zg = 0, zg_jjz = 0, zg_zyb = 0, zg_fc = 0, lg = 0, lg_zyb = 0, lg_fc = 0, yj = 0,
                yj_zyb = 0, yj_fc = 0, lgsf = 0, lgsf_zyb = 0, lgsf_fc = 0;
        //总人数
        List<SQZrsDto> data = summaryQueryMapper.getHarmQuery(param);
        //序号
        int xh = data.size() + 1;
        LinkedHashMap<String, ArrayList<Object>> result = new LinkedHashMap<String, ArrayList<Object>>();
        //循环
        for (int i = 0, s = data.size(); i < s; i++) {
            SQZrsDto os = data.get(i);
            result.put(os.getId().toString(), new ArrayList<Object>(Arrays.asList(
                    new Object[] { i + 1, os.getHarmClass(), os.getType0(), 0, os.getType1(), 0, 0, 0, os.getType2(), 0, 0, os.getType4(), 0, 0, os.getType3(), 0, 0 })));
            sgq += Integer.parseInt(os.getType0().toString());
            zg += Integer.parseInt(os.getType1().toString());
            lg += Integer.parseInt(os.getType2().toString());
            yj += Integer.parseInt(os.getType4().toString());
            lgsf += Integer.parseInt(os.getType3().toString());
        }
        //异常人数
        List<YcrsDto> ycrsDto  = summaryQueryMapper.getYcrs(param);
        for (int i = 0, s = ycrsDto.size(); i < s; i++) {
            YcrsDto os = ycrsDto.get(i);
            String harmClassId = os.getHealthEvaluationClass() == null ? "" : os.getHealthEvaluationClass().toString();
            ArrayList<Object> ros = result.get(harmClassId);
            if (ros == null) {
                continue;
            }
            ros.set(3, os.getType02());
            ros.set(5, os.getType12());
            ros.set(6, os.getType11());
            ros.set(7, os.getType13());
            ros.set(9, os.getType21());
            ros.set(10, os.getType23());
            ros.set(12, os.getType41());
            ros.set(13, os.getType43());
            ros.set(15, os.getType31());
            ros.set(16, os.getType33());
            sgq_jjz += Integer.parseInt(os.getType02().toString());
            zg_jjz += Integer.parseInt(os.getType12().toString());
            zg_zyb += Integer.parseInt(os.getType11().toString());
            zg_fc += Integer.parseInt(os.getType13().toString());
            lg_zyb += Integer.parseInt(os.getType21().toString());
            lg_fc += Integer.parseInt(os.getType23().toString());
            yj_zyb += Integer.parseInt(os.getType41().toString());
            yj_fc += Integer.parseInt(os.getType43().toString());
            lgsf_zyb += Integer.parseInt(os.getType31().toString());
            lgsf_fc += Integer.parseInt(os.getType33().toString());
        }
        result.put("", new ArrayList<Object>(Arrays.asList(new Object[] { xh, "合计", sgq, sgq_jjz, zg, zg_jjz, zg_zyb,
                zg_fc, lg, lg_zyb, lg_fc, yj, yj_zyb, yj_fc, lgsf, lgsf_zyb, lgsf_fc })));

        //封装成返回数据
        List<HarmQueryVo> lists = new ArrayList<>();
        int i = 0;
        for (ArrayList<Object> list : result.values()) {
            HarmQueryVo ma = new HarmQueryVo();
            i += 1;
            ma.setRownum(i);
            ma.setHarmClass(list.get(1));
            ma.setSgq(list.get(2));
            ma.setSgq_jjz(list.get(3));
            ma.setZg(list.get(4));
            ma.setZg_jjz(list.get(5));
            ma.setZg_zyb(list.get(6));
            ma.setZg_fc(list.get(7));
            ma.setLg(list.get(8));
            ma.setLg_zyb(list.get(9));
            ma.setLg_fc(list.get(10));
            ma.setYj(list.get(11));
            ma.setYj_zyb(list.get(12));
            ma.setYj_fc(list.get(13));
            ma.setLgsf(list.get(14));
            ma.setLgsf_zyb(list.get(15));
            ma.setLgsf_fc(list.get(16));
            lists.add(ma);
        }
        return lists;
    }


}

