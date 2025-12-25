package com.center.medical.abteilung.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.param.AdditionProcessingParam;
import com.center.medical.abteilung.bean.vo.AdditionProcessingVo;
import com.center.medical.abteilung.dao.AdditionProcessingMapper;
import com.center.medical.abteilung.service.AdditionProcessingService;
import com.center.medical.bean.model.HandleNewProjects;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * KS检验科加项处理(HandleNewProjects)表服务实现类
 *
 * @author makejava
 * @since 2023-01-29 11:05:53
 */
@Slf4j
@Service("additionProcessingService")
@RequiredArgsConstructor
public class AdditionProcessingServiceImpl extends ServiceImpl<AdditionProcessingMapper, HandleNewProjects> implements AdditionProcessingService {

    private final AdditionProcessingMapper additionProcessingMapper;

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param HandleNewProjects查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AdditionProcessingVo> getPage(PageParam<HandleNewProjects> page, AdditionProcessingParam param) {
        //去空格大写
        //体检码
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //姓名
        if (ObjectUtils.isNotEmpty(param.getName())) {
            param.setName(param.getName().trim().toUpperCase());
        }
        //体检团体
        if (ObjectUtils.isNotEmpty(param.getKhdwmcid())) {
            param.setKhdwmcid(param.getKhdwmcid().trim().toUpperCase());
        }
        //开始时间00点00分
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        //结束时间23点59分
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return additionProcessingMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public HandleNewProjects getInfoById(String id) {
        return additionProcessingMapper.getInfoById(id);
    }

    ;

    /**
     * 批量处理
     *
     * @param id
     * @param type
     * @return
     */
    public Boolean CLSaveBatch(List<String> id, String type) {
        //状态 0未处理，2检验结束
        int kind = type.equals("0") ? 2 : 0;
        //0处理，1反处理
        String info = type.equals("0") ? "处理" : "反处理";
        List<HandleNewProjects> list = new ArrayList<HandleNewProjects>();
        //登录用户id
        String userId = SecurityUtils.getUserNo();
        Date now = new Date();
        for (int i = 0; i < id.size(); i++) {
            HandleNewProjects other = getInfoById(id.get(i));
            if (ObjectUtils.isEmpty(other)) {
                throw new ServiceException(info + "失败：第" + (i + 1) + "条数据不存在，已经被删除");
            }
            other.setHandleNameId(userId);
            //设置状态
            other.setStatus(kind);
            other.setIdea("无");
            //处理时间
            other.setHandleTime(now);
            //是否已处理：0.未处理 1.已处理
            if (kind == 2) {
                other.setIsHandle(1);
            } else {
                other.setIsHandle(0);
            }
            list.add(other);
        }
        //批量更新
        return updateBatchById(list);
    }

}

