package com.center.medical.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.member.dao.MemberInterflowMapper;
import com.center.medical.member.service.MemberInterflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 会员管理-沟通记录服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-24 09:59:56
 */
@Slf4j
@Service("memberInterflowService")
@RequiredArgsConstructor
public class MemberInterflowServiceImpl extends ServiceImpl<MemberInterflowMapper, Peispatientarchive> implements MemberInterflowService {


}

