package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Chest;
import com.center.medical.bean.param.ChestParam;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.ChestMapper;
import com.center.medical.service.ChestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单柜子信息(Chest)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-06 08:59:23
 */
@Slf4j
@Service("chestService")
@RequiredArgsConstructor
public class ChestServiceImpl extends ServiceImpl<ChestMapper, Chest> implements ChestService {

    private final ChestMapper chestMapper;

    /**
     * 分页查询[订单柜子信息]列表
     *
     * @param page  分页参数
     * @param param Chest查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Chest> getPage(PageParam<Chest> page, ChestParam param) {
        return chestMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Chest getInfoById(String id) {
        return chestMapper.getInfoById(id);
    }

    /**
     * 保存或更新
     * @param odis
     * @return
     */
    @Override
    public Boolean saOrUp(Chest odis) {
        // 判断是否为空
        if(StringUtils.isBlank(odis.getId())) {
            //保存
            //设置isDelete字段为0
            odis.setIsDelete(0);
            this.save(odis);
        } else {
            // 判断是否假删、ID是否重复

            Chest harmNew =  chestMapper.getInfoById(odis.getId());
            if(ObjectUtils.isNotEmpty(harmNew)){
                // 更新实体类
                this.updateById(odis);
            }else{
                throw new ServiceException("对象已删除，请刷新页面");
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 获取订单柜子信息导出数据
     * @param param
     * @return
     */
    @Override
    public List<Chest> getExportData(Chest param) {
        return chestMapper.getExportData(param);
    }
}

