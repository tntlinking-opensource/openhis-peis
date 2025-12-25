package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysUserBranch;
import com.center.medical.system.bean.param.AddSysUserBranchParam;
import com.center.medical.system.dao.SysUserBranchMapper;
import com.center.medical.system.service.SysUserBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户关联的分中心(SysUserBranch)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-05-20 15:27:58
 */
@Slf4j
@Service("sysUserBranchService")
@RequiredArgsConstructor
public class SysUserBranchServiceImpl extends ServiceImpl<SysUserBranchMapper, SysUserBranch> implements SysUserBranchService {

    private final SysUserBranchMapper sysUserBranchMapper;

    /**
     * 分页查询[系统用户关联的分中心]列表
     *
     * @param page  分页参数
     * @param param SysUserBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysUserBranch> getPage(PageParam<SysUserBranch> page, SysUserBranch param) {
        return sysUserBranchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysUserBranch getInfoById(String id) {
        return sysUserBranchMapper.getInfoById(id);
    }

    /**
     * 批量添加用户和分中心
     * @param param
     * @return
     */
    @Override
    public Boolean addSysUserBranch(AddSysUserBranchParam param) {
        List<SysUserBranch> sysUserBranchList = new ArrayList<>();
        List<String> userNos = param.getUserNos();
        String fzx = param.getFzx();
        for (String userNo : userNos) {
            Long count = sysUserBranchMapper.selectCount(new LambdaQueryWrapper<SysUserBranch>()
                    .eq(SysUserBranch::getUserId, userNo).eq(SysUserBranch::getBranchId, fzx));
            if (count > 0){
                continue;
            }else {
                SysUserBranch sysUserBranch = new SysUserBranch();
                sysUserBranch.setUserId(userNo);
                sysUserBranch.setBranchId(fzx);
                sysUserBranchList.add(sysUserBranch);
            }
        }
        saveBatch(sysUserBranchList);
        return Boolean.TRUE;
    }
}

