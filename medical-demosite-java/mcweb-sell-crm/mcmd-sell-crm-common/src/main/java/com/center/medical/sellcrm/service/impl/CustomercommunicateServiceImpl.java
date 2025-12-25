package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Customercommunicate;
import com.center.medical.sellcrm.bean.param.CustomercommunicateParam;
import com.center.medical.sellcrm.dao.CustomercommunicateMapper;
import com.center.medical.sellcrm.service.CustomercommunicateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 客户沟通表(Customercommunicate)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:49
 */
@Slf4j
@Service("customercommunicateService")
@RequiredArgsConstructor
public class CustomercommunicateServiceImpl extends ServiceImpl<CustomercommunicateMapper, Customercommunicate> implements CustomercommunicateService {

    private final CustomercommunicateMapper customercommunicateMapper;

    /**
     * 分页查询[客户沟通表]列表
     *
     * @param page                     分页参数
     * @param customercommunicateParam Customercommunicate查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Customercommunicate> getPage(PageParam<Customercommunicate> page, CustomercommunicateParam customercommunicateParam) {
        //判断当前登录用户是否为领导
        Boolean isLeader = SecurityUtils.isLeader();
        //获取当前分中心id
        String fzxId = SecurityUtils.getCId();
        String userNo = SecurityUtils.getUserNo();
        //判断当前登录用户是否为领导
        if (!isLeader) {
            //不是领导,显示当前登录用户名下的信息
            customercommunicateParam.setXsjlid(userNo);
        } else {
            //是领导,显示本分中心下的数据
            customercommunicateParam.setFzxid(fzxId);
        }
        return customercommunicateMapper.getPage(page, customercommunicateParam);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Customercommunicate getInfoById(String id) {
        return customercommunicateMapper.getInfoById(id);
    }

}

