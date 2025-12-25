package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.model.MdSysBranch;
import com.center.medical.appadmin.bean.param.MdSysBranchParam;
import com.center.medical.appadmin.bean.param.SysBranchSaOrUpParam;
import com.center.medical.appadmin.dao.MdSysBranchMapper;
import com.center.medical.appadmin.service.MdSysBranchService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分中心维护表(MdSysBranch)服务实现类
 *
 * @author ay
 * @since 2024-06-11 16:03:16
 */
@Slf4j
@Service("mdSysBranchService")
@RequiredArgsConstructor
public class MdSysBranchServiceImpl extends ServiceImpl<MdSysBranchMapper, MdSysBranch> implements MdSysBranchService {

    private final MdSysBranchMapper mdSysBranchMapper;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param MdSysBranch查询参数
     * @return 分页数据
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public IPage<MdSysBranch> getPage(PageParam<MdSysBranch> page, MdSysBranchParam param) {
        return mdSysBranchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public MdSysBranch getInfoById(Integer id) {
        return mdSysBranchMapper.getInfoById(id);
    }

    /**
     * 添加或修改
     * @param param
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public Boolean saOrUp(SysBranchSaOrUpParam param) {
        MdSysBranch mdSysBranch = mapperFacade.map(param, MdSysBranch.class);
        if (ObjectUtils.isNotEmpty(mdSysBranch.getId())){
            //更新
            MdSysBranch mdSysBranch1 = mdSysBranchMapper.getInfoById(mdSysBranch.getId());
            if (ObjectUtils.isEmpty(mdSysBranch1)){
                throw new ServiceException("该id不存在!");
            }
            mdSysBranchMapper.updateById(mdSysBranch);
        }else {
            mdSysBranch.setIsDelete(0);
            //新增
            MdSysBranch old = this.mdSysBranchMapper.selectOne(new LambdaQueryWrapper<MdSysBranch>()
                    .eq(MdSysBranch::getJm, mdSysBranch.getJm())
                    .eq(MdSysBranch::getIsDelete, 0));
            if (old != null) {
                throw new ServiceException("输入的简码：" + mdSysBranch.getJm() + "在系统中已存在,请重新输入!");
            }
            MdSysBranch old1 = this.mdSysBranchMapper.selectOne(new LambdaQueryWrapper<MdSysBranch>()
                    .eq(MdSysBranch::getFzx, mdSysBranch.getFzx())
                    .eq(MdSysBranch::getIsDelete, 0));
            if (old1 != null) {
                throw new ServiceException("输入的名称：" + mdSysBranch.getFzx() + "在系统中已存在,请重新输入!");
            }
//            mdSysBranch.setJm(mdSysBranch.getJm().toUpperCase());
            //添加
            mdSysBranchMapper.insert(mdSysBranch);
        }
        return Boolean.TRUE;
    }

    /**
     * 删除数据
     * @param ids
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public Boolean deleteByIds(List<Integer> ids) {
        mdSysBranchMapper.deleteBatchIds(ids);
        return Boolean.TRUE;
    }
}

