package com.center.medical.machine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.config.QueueConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.MachineConstants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.machine.bean.dto.CurrentDateInfoDto;
import com.center.medical.machine.bean.dto.FindDeptDto;
import com.center.medical.machine.bean.dto.PersonalReportDto;
import com.center.medical.machine.bean.param.ReadCardParam;
import com.center.medical.machine.dao.ReadCardMapper;
import com.center.medical.machine.service.ReadCardService;
import com.center.medical.report.service.ReportService;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.dao.PeisorgreservationgroupMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
@Slf4j
@Service("readCardService")
@RequiredArgsConstructor
public class ReadCardServiceImpl extends ServiceImpl<ReadCardMapper, Peispatient> implements ReadCardService {

    private final ReadCardMapper readCardMapper;
    private final ReportService reportService;
    private final PeispatientMapper peispatientMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final ISysConfigService iSysConfigService;

    //保存图片到内存(页面base64出现头像和信息串了的现象) key身份证号 ； value base64
    public static ConcurrentHashMap<String, String> photo = new ConcurrentHashMap<String, String>();
    public static ConcurrentHashMap<String, String> nameList = new ConcurrentHashMap<String, String>();
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;


    /**
     * 查询对应的卡号及体检者信息
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> onMessage(ReadCardParam param) {
        String message = param.getMessage();
        log.info("来自客户端的消息:" + message);
        Map<String, Object> map = new HashMap<String, Object>();

        String idcardno = param.getIdcardno();
        String sex = param.getSex();
        String name = param.getName();
        nameList.putIfAbsent(idcardno, name);
        //科室加项
        if ("addItem".equals(message)) {
            map.put("status", 1);
            map.put("idcardno", idcardno);
            map.put("name", name);
            map.put("patientcode", checkAddNo(idcardno));
            //报告打印
        } else if ("report".equals(message)) {
            map.put("status", 1);
            map.put("idcardno", idcardno);
            map.put("name", name);
            map.put("dep", getDepReportList(idcardno));
            map.put("patientcodes", getPersonalReportList(idcardno));
            map.put("currentDatePatientCode", getCurrentDateInfoByIdCardNo(idcardno));
            //排队调整
        } else if ("queue".equals(message)) {
            //排队系统连接
            QueueConfig queueConfig = iSysConfigService.getSysConfigObject(Constants.QUEUE_CONFIG,QueueConfig.class);
            String url = queueConfig.getQrCodeUrl();
            map.put("QUEUEING_SYSTEM_URL", url);
            //查找未登记的体检号
            map.put("patientcodes", getCurrentDateInfoByIdCardNo(idcardno));
            //自助登记
        } else if ("register".equals(message)) {
            String img = param.getImg();
            photo.putIfAbsent(idcardno, img);
            map.put("status", 1);
            map.put("patients", checkNo(idcardno));
            map.put("idcardno", idcardno);
            map.put("name", name);
            map.put("sex", sex);
            map.put("img", img);
        } else {
            String img = param.getImg();
            photo.putIfAbsent(idcardno, img);
            map.put("status", 1);
            map.put("patients", checkNo(idcardno));
            map.put("idcardno", idcardno);
            map.put("img", img);
            map.put("name", name);
            map.put("sex", sex);
        }
        return map;
    }

    /**
     * 查找登记时间是今天的体检号
     *
     * @param idcardno
     * @return
     */
    private List<String> unregisteredCode(String idcardno) {
        List<Peispatient> list = peispatientMapper.selectList(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getIdcardno, idcardno)
                .eq(Peispatient::getDateregister, LocalDate.now())
        );
        List<String> codes = list.stream()
                .map(Peispatient::getPatientcode)
                .collect(Collectors.toList());
        return codes;
    }

    /**
     * 通过身份证号获取体检号信息
     *
     * @param idcardno
     * @return
     */
    private List<Map<String, String>> checkNo(String idcardno) {
        List<Peispatient> patients = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                .orderByDesc("createdate")
                .eq("idcardno", idcardno)
                .eq("f_registered", 0));
        List<Map<String, String>> patientcodes = new ArrayList<Map<String, String>>();
        for (Peispatient patient : patients) {
            //禁检
            if (null != patient.getFPaused() && 1 == patient.getFPaused()) {
                continue;
            }
            if (patient.getIdOrgreservationgroup() != null) {
                //体检者任务分组
                Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(patient.getIdOrgreservationgroup());
                //组启用及组禁用
                if (group != null && group.getFGroupstarted() == 0 && group.getFGrouppaused() == 1) {
                    continue;
                }
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("patientcode", patient.getPatientcode());
            map.put("phone", patient.getPhone());
            map.put("orgName", patient.getOrgName() == null ? "" : patient.getOrgName());
            map.put("patientname", patient.getPatientname());
            map.put("sex", patient.getIdSex() == 0 ? "男" : "女");
            map.put("age", patient.getAge() == null ? "" : (patient.getAge().intValue() + ""));
            map.put("idcardno", idcardno);
            patientcodes.add(map);
        }
        return patientcodes;


    }

    /**
     * 按身份证获取当前日期信息
     *
     * @param idcardno
     * @return
     */
    private CurrentDateInfoDto getCurrentDateInfoByIdCardNo(String idcardno) {
        return readCardMapper.getCurrentDateInfoByIdCardNo(idcardno);
    }


    public static String queueStatusRender(Object renderId) {
        if (renderId == null || StringUtils.isEmpty(renderId.toString())) {
            return "";
        }
        switch (renderId.toString()) {
            case "1":
                return "正在排队";
            case "2":
                return "正在体检";
            case "3":
                return "已结束";
            case "4":
                return "已完成";
            case "6":
                return "未到";
            default:
                return "等待排队";
        }
    }


    //健康体检打印健康报告，职业体检打印职业报告，综合体检两份报告都打印
    //生成了就可以打印
    //最多显示最近5次
    //查询归档表
    private List<Map<String, Object>> getPersonalReportList(String idcardno) {
        List<PersonalReportDto> list = readCardMapper.getPersonalReportList(idcardno);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (PersonalReportDto os : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            result.add(map);
            map.put("id", os.getId());
            map.put("date", os.getDate());
            map.put("patientcode", os.getPatientcode());
        }
        return result;
    }

    //查询最近一次体检的，可打印报告的科室
    //可打印科室：检验科、彩超、CT\DR\CR\核磁  在report.xml中配置
    //存在已检项目的科室就可以生成报告。检验：获取自动审核。彩超+放射：审核时变为已检。
    private Map<String, Object> getDepReportList(String idcardno) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> depList = new ArrayList<Map<String, Object>>();
        result.put("deps", depList);
        List<Peispatient> patients = peispatientMapper.selectList(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getFReadytofinal, 1)
                .eq(Peispatient::getIdcardno, idcardno)
                .orderByDesc(Peispatient::getDateregister)
        );
        if (patients.size() == 0) return result;
        String patientcode = patients.get(0).getPatientcode();
        result.put("patientcode", patientcode);
        List<Object> params = new ArrayList<Object>();
        params.add(patientcode);
        String depIds = MachineConstants.DEP_IDS;
        List<FindDeptDto> list = readCardMapper.findDept(patientcode, depIds.split(","));
        for (FindDeptDto os : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", os.getDeptNo());
            map.put("name", os.getDeptName());
            depList.add(map);
        }
        return result;
    }

    /**
     * 查找科室加项体检号
     *
     * @param idcardno
     * @return
     */
    private String checkAddNo(String idcardno) {
        String str = readCardMapper.checkAddNo(idcardno);
        return str;
    }
}

