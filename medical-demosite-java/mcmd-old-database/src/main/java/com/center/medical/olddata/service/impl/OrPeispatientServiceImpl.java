package com.center.medical.olddata.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.*;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.RequestParam;
import com.center.medical.bean.param.UserExamDataParam;
import com.center.medical.bean.vo.PacsHistoryListVo;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.MD5;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatient;
import com.center.medical.olddata.dao.OrPeispatientMapper;
import com.center.medical.olddata.service.OrPeispatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * QT体检者表(Peispatient)服务实现类
 *
 * @author ay
 * @since 2023-08-12 11:55:00
 */
@Slf4j
@Service("orPeispatientService")
@RequiredArgsConstructor
public class OrPeispatientServiceImpl extends ServiceImpl<OrPeispatientMapper, OrPeispatient> implements OrPeispatientService {

    private final OrPeispatientMapper peispatientMapper;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrPeispatient> getPage(PageParam<OrPeispatient> page, OrPeispatient param) {
        return peispatientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrPeispatient getInfoById(String id) {
        return peispatientMapper.getInfoById(id);
    }


    /**
     * 通过分组id获取体检者
     *
     * @param groupId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeispatient> getByGroupId(String groupId) {
        //一个分组下,未完成登记，未被禁检的体检者
        return peispatientMapper.getByGroupId(groupId);
    }

    /**
     * 查询未完成登记的体检者
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeispatient> getUnRegistered() {
        return peispatientMapper.getUnRegistered();
    }


    /**
     * 通过体检号查询体检者
     * @param oldPatientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrPeispatient getInfoByPatientCode(String oldPatientCode) {
        return peispatientMapper.getInfoByPatientCode(oldPatientCode);
    }


    /**
     * 查询老系统个检人员
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeispatient> getImportPeople() {
        return peispatientMapper.getImportPeople();
    }


    /**
     * 老系统禁检
     * @param orPeispatient
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public void setFPaused(OrPeispatient orPeispatient) {
        orPeispatient.setFPaused("1");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用格式化对象将 Date 转换为字符串
        String dateString = dateFormat.format(new Date());
        orPeispatient.setNote(dateString + "新系统已登记,该体检号已被禁检");
        orPeispatient.setModifydate(new Date());
        peispatientMapper.updateById(orPeispatient);
    }


    /**
     * 通过订单号查询
     * @param ddh
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeispatient> getByDdh(String ddh) {
        return peispatientMapper.getByDdh(ddh);
    }

    /**
     * 批量查询
     * @param peispatient
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<Peispatient> getList(Peispatient peispatient) {
        List<OrPeispatient> list = peispatientMapper.getList(peispatient);
        //转换婚姻状态
        for (OrPeispatient orPeispatient : list) {
            if (StringUtils.isNotEmpty(orPeispatient.getIdMarriage())) {
                if ("402881a85417c6ec015417dfc285000e".equals(orPeispatient.getIdMarriage())) {
                    orPeispatient.setIdMarriage("2");
                } else if ("402881a85417c6ec015417df521f000d".equals(orPeispatient.getIdMarriage())) {
                    orPeispatient.setIdMarriage("1");
                }
            }
        }
        List<Peispatient> peispatients = mapperFacade.mapAsList(list, Peispatient.class);
        return peispatients;
    }


    /**
     * 通过体检号批量查询
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeispatient> getByPatientCodes(List<String> patientCodes) {
        List<OrPeispatient> list = peispatientMapper.getByPatientCodes(patientCodes);
        //转换婚姻状态
        for (OrPeispatient orPeispatient : list) {
            if (StringUtils.isNotEmpty(orPeispatient.getIdMarriage())) {
                if ("402881a85417c6ec015417dfc285000e".equals(orPeispatient.getIdMarriage())) {
                    orPeispatient.setIdMarriage("2");
                } else if ("402881a85417c6ec015417df521f000d".equals(orPeispatient.getIdMarriage())) {
                    orPeispatient.setIdMarriage("1");
                }
            }
        }
        return list;
    }


    /**
     * 项目完成情况
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindXmwcqkDto> findXmwcqk(List<String> patientCodes) {

        List<FindXmwcqkDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindXmwcqkDto> dtos = peispatientMapper.findXmwcqk(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }

        //转换婚姻状态
        for (FindXmwcqkDto orPeispatient : list) {
            if (StringUtils.isNotEmpty(orPeispatient.getIdMarriage())) {
                if ("402881a85417c6ec015417dfc285000e".equals(orPeispatient.getIdMarriage())) {
                    orPeispatient.setIdMarriage("2");
                } else if ("402881a85417c6ec015417df521f000d".equals(orPeispatient.getIdMarriage())) {
                    orPeispatient.setIdMarriage("1");
                }
            }
        }
        return list;
    }


    /**
     * 查询老系统项目參检
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindXmcjDto> findXmcj(List<String> patientCodes) {
        List<FindXmcjDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindXmcjDto> dtos = peispatientMapper.findXmcj(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }


    /**
     * 检出统计团体小结
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindJctjDto> findJctj(List<String> patientCodes) {
        List<FindJctjDto> list = new ArrayList<>();
        int i = 1;
        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindJctjDto> dtos = peispatientMapper.findJctj(sublist);
            for (FindJctjDto dto : dtos) {
                dto.setFlag(String.valueOf(i));
            }
            i++;
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }

    /**
     * 阳性结果
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindYangxjgDto> findYangxjg(ArrayList<String> patientCodes) {
        List<FindYangxjgDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindYangxjgDto> dtos = peispatientMapper.findYangxjg(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }


    /**
     * 查询阴性结果
     * @param cid
     * @param patientCode
     * @param bhys
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindYinxjgDto> findYinxjg(String cid, String patientCode, boolean bhys) {
        return peispatientMapper.findYinxjg(cid,patientCode,bhys);
    }


    /**
     * 查询职业报告体检者
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindDtjzDto> findDtjz(ArrayList<String> patientCodes) {
        List<FindDtjzDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindDtjzDto> dtos = peispatientMapper.findDtjz(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }


    /**
     * 人员一览表
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindZybcxmDto> findZybcxm(ArrayList<String> patientCodes) {
        List<FindZybcxmDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindZybcxmDto> dtos = peispatientMapper.findZybcxm(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }


    /**
     * 人员一览表
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindRyylDto> findRyyl(ArrayList<String> patientCodes) {
        List<FindRyylDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindRyylDto> dtos = peispatientMapper.findRyyl(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }


    /**
     * 复查明细
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindFcmxDto> findFcmx(ArrayList<String> patientCodes) {
        List<FindFcmxDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindFcmxDto> dtos = peispatientMapper.findFcmx(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }


    /**
     * 复查情况
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindFcqkDto> findFcqk(ArrayList<String> patientCodes) {
        List<FindFcqkDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindFcqkDto> dtos = peispatientMapper.findFcqk(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }


    /**
     * 根据复查体检号查询
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<Peispatient> getListByinPatientno(String patientCode) {
        List<OrPeispatient> list = peispatientMapper.getListByinPatientno(patientCode);
        //转换婚姻状态
        for (OrPeispatient orPeispatient : list) {
            if (StringUtils.isNotEmpty(orPeispatient.getIdMarriage())) {
                if ("402881a85417c6ec015417dfc285000e".equals(orPeispatient.getIdMarriage())) {
                    orPeispatient.setIdMarriage("2");
                } else if ("402881a85417c6ec015417df521f000d".equals(orPeispatient.getIdMarriage())) {
                    orPeispatient.setIdMarriage("1");
                }
            }
        }
        List<Peispatient> peispatients = mapperFacade.mapAsList(list, Peispatient.class);
        return peispatients;
    }

    /**
     * 查询复查结果
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindListInDto> findListIn(String patientCode) {
        return peispatientMapper.findListIn(patientCode);
    }


    /**
     * 查询复查数量
     * @param patientcode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public int findNumByPatientcode(String patientcode) {
        return peispatientMapper.findNumByPatientcode(patientcode);
    }

    /**
     * 查询老系统实查人数
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public int findScrs(List<String> patientCodes) {
        Long count = peispatientMapper.selectCount(new LambdaQueryWrapper<OrPeispatient>()
                .in(OrPeispatient::getPatientcode, patientCodes)
                .eq(OrPeispatient::getFRegistered,1)
        );
        return count.intValue();
    }

    /**
     * 查询检查人数
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindJcrsDto> findJcrs(List<String> patientCodes) {
        List<FindJcrsDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindJcrsDto> dtos = peispatientMapper.findJcrs(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }

    /**
     * 查询结论数据
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindListDateDto> findListDate(List<String> patientCodes) {
        List<FindListDateDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindListDateDto> dtos = peispatientMapper.findListDate(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }

    /**
     * 检查情况汇总
     * @param patientCodes
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public List<FindJcqkhzDto> findJcqkhz(List<String> patientCodes) {
        List<FindJcqkhzDto> list = new ArrayList<>();

        // oracle限制,每次循环迭代取出一千个元素，直到原始列表为空
        while (!patientCodes.isEmpty()) {
            int endIndex = Math.min(1000, patientCodes.size());
            List<String> sublist = patientCodes.subList(0, endIndex);
            //查询
            List<FindJcqkhzDto> dtos = peispatientMapper.findJcqkhz(sublist);
            list.addAll(dtos);
            patientCodes.removeAll(sublist);
        }
        return list;
    }

    /**
     * 查询老老系统历史
     * @param idcardno
     * @param itemId
     * @param ksId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<PacsHistoryListVo> getHistoryList(String idcardno, String itemId, String ksId,List<String> ids) {
        return peispatientMapper.getHistoryList(idcardno,itemId,ksId,ids);
    }

    /**
     * 查询老系统是否已登记
     * @param id
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public Boolean isRegistered(String id) {
        OrPeispatient orPeispatient = peispatientMapper.getInfoById(id);
        if (ObjectUtils.isNotEmpty(orPeispatient)) {
            if ("1".equals(orPeispatient.getFRegistered())) {
                return Boolean.TRUE;
            }
            if (StringUtils.isNotBlank(orPeispatient.getFPaused()) && "1".equals(orPeispatient.getFPaused())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 查询推送给第三方的旧系统的体检者数据
     *
     * @param params
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public void pushOldDataToCoo(RequestParam params) {
        String str = params.getParams();
        UserExamDataParam param = JSONUtil.toBean(str, UserExamDataParam.class);
        if (StringUtils.isBlank(param.getOrderId())){
            return;
        }

        List<UserExamDataDto> userExamDataList = new ArrayList<>();
        List<UserExamDataDto> peispatients = peispatientMapper.getPushData(param);
        log.info("查询推送给第三方的旧系统的体检者数据.peispatients:{}", peispatients);
        int i = 1;
        //获取体检结果
        for(UserExamDataDto item : peispatients){
            //获取每个科室的检查结果和小结
            List<ExamItemDataDto> itemList = peispatientMapper.getSectionResult(item.getPatientcode());
            item.setExamtemList(itemList);
//            log.info("查询推送给第三方的体检者数据.itemList:{}", itemList);
            //获取总检结果
            List<SectionTotalDto> sectionTotals = peispatientMapper.getSectionTotal(item.getPatientcode());
            for (SectionTotalDto sectionTotal : sectionTotals) {
                if (sectionTotal.getExamType() == 0){
                    //健康总检
                    item.setHealthResult(mapperFacade.map(sectionTotal, HealthResultDto.class));
                }
                if (sectionTotal.getExamType() == 1){
                    //职业总检
                    item.setOccupResult(mapperFacade.map(sectionTotal, OccupResultDto.class));
                }
            }
//            log.info("查询推送给第三方的体检者数据.sectionTotals:{}", sectionTotals);

            userExamDataList.add(item);
            if (i % 10 == 0){
                log.info("合作单位开放接口.查询订单ID下的客户体检数据列表userExamDataList.size：{}", userExamDataList.size());
                Map<String, Object> result = new HashMap<>();
                //生成签名：业务方授权码+业务标识+请求标识，拼接字符串后进行MD5（32位大写）加密
                result.put("sign", MD5.encode(params.getAuthCode() + params.getBsFlag() + param.getFlag()).toUpperCase());
                result.put("records", JSONUtil.toJsonStr(userExamDataList));
                String post = HttpUtil.post(param.getCallBack(), result);
                log.info("推送结果：{}", post);
                userExamDataList = new ArrayList<>();
            }
            i++;
        }

    }
}


