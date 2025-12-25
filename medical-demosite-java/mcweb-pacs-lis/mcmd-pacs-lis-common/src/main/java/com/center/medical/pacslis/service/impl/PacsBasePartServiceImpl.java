package com.center.medical.pacslis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.pacslis.bean.model.PacsBasePart;
import com.center.medical.pacslis.dao.PacsBasePartMapper;
import com.center.medical.pacslis.service.PacsBasePartService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * PACS-部位(PacsBasePart)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:16
 */
@Slf4j
@Service("pacsBasePartService")
@RequiredArgsConstructor
public class PacsBasePartServiceImpl extends ServiceImpl<PacsBasePartMapper, PacsBasePart> implements PacsBasePartService {

    private final PacsBasePartMapper pacsBasePartMapper;

    /**
     * 分页查询[PACS-部位]列表
     *
     * @param page  分页参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsBasePart> getPage(PageParam<PacsBasePart> page) {
        return pacsBasePartMapper.getPage(page);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsBasePart getInfoById(String id) {
        return pacsBasePartMapper.getInfoById(id);
    }


    /**
     * 添加或修改
     * @param pacsBasePart
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean saOrUp(PacsBasePart pacsBasePart) {
        //没id就是添加，有id就是修改
        if(StringUtils.isEmpty(pacsBasePart.getId())){
            save(pacsBasePart);
        }else{
            //父id不为空
            if(ObjectUtils.isNotEmpty(pacsBasePart.getPid())){
                if(pacsBasePart.getPid().equals(pacsBasePart.getId())){
                    throw new ServiceException("上级不能是自己！");
                }
            }
            updateById(pacsBasePart);
        }
        return Boolean.TRUE;
    }
}

