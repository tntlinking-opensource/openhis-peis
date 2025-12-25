package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZyFhclGr;
import com.center.medical.data.bean.param.ZyFhclGrPageParam;
import com.center.medical.data.bean.vo.AllPersonalTypeVo;
import com.center.medical.data.bean.vo.ProtectiveEquipmentVo;
import com.center.medical.data.bean.vo.ZyFhclGrPageVo;
import com.center.medical.data.dao.ZyFhclGrMapper;
import com.center.medical.data.service.ZyFhclGrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * JC个人防护用品(MdZyFhclGr)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:28
 */
@Slf4j
@Service("mdZyFhclGrService")
@RequiredArgsConstructor
public class ZyFhclGrServiceImpl extends ServiceImpl<ZyFhclGrMapper, ZyFhclGr> implements ZyFhclGrService {

    private final ZyFhclGrMapper zyFhclGrMapper;

    /**
     * 分页查询[JC个人防护用品]列表
     *
     * @param page  分页参数
     * @param param MdZyFhclGr查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyFhclGrPageVo> getPage(PageParam<ZyFhclGrPageVo> page, ZyFhclGrPageParam param) {
        return zyFhclGrMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ZyFhclGr getInfoById(String id) {
        return zyFhclGrMapper.getInfoById(id);
    }

    /**
     * 添加或更新
     * @param zyFhclGr
     * @return
     */
    @Override
    public boolean saOrUp(ZyFhclGr fh) {
        // 判断是否为空
        if(StringUtils.isBlank(fh.getId())) {
            //判断是否存在重复的危害因素名称,排除删除数据有相同因素名称的影响
            ZyFhclGr fhn = zyFhclGrMapper.selectOne(new QueryWrapper<ZyFhclGr>().eq("defend_individual", fh.getDefendIndividual())
                            .eq("is_delete", 0));
            if (null != fhn) {
                throw new ServiceException("保存失败！存在相同名称");
            }
            else {
                //保存
                fh.setIsDelete(0);
                zyFhclGrMapper.insert(fh);
            }
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断名称是否重复
            Long l = zyFhclGrMapper.selectCount(new QueryWrapper<ZyFhclGr>().ne("id", fh.getId())
                    .eq("defend_individual", fh.getDefendIndividual()).eq("is_delete", 0));
            if (l > 0) {
                throw new ServiceException("保存失败！存在相同名称");
            }
            zyFhclGrMapper.updateById(fh);
        }
        return Boolean.TRUE;
    }

    /**
     * 获取防护用品分类
     * @param page
     * @param inputCode
     * @return
     */
    @Override
    public IPage<AllPersonalTypeVo> getAllPersonalType(PageParam<AllPersonalTypeVo> page, String inputCode) {
        return zyFhclGrMapper.getAllPersonalType(page,inputCode);
    }

    /**
     * 获取防护用品
     * @param page
     * @param inputCode
     * @return
     */
    @Override
    public IPage<ProtectiveEquipmentVo> getProtectiveEquipment(PageParam<ProtectiveEquipmentVo> page, String inputCode) {
        return zyFhclGrMapper.getProtectiveEquipment(page,inputCode);
    }


}


