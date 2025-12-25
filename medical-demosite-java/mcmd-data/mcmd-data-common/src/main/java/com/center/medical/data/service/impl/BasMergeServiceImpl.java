package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BasMerge;
import com.center.medical.data.bean.model.BasMergeConclusion;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.vo.BasMergeVo;
import com.center.medical.data.dao.BasMergeConclusionMapper;
import com.center.medical.data.dao.BasMergeMapper;
import com.center.medical.data.service.BasMergeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 合并结伦词(BasMerge)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
@Slf4j
@Service("basMergeService")
@RequiredArgsConstructor
public class BasMergeServiceImpl extends ServiceImpl<BasMergeMapper, BasMerge> implements BasMergeService {

    private final BasMergeMapper basMergeMapper;
    private final Snowflake snowflake;
    private final BasMergeConclusionMapper basMergeConclusionMapper;


    /**
     * 分页查询[合并结伦词]列表
     *
     * @param page  分页参数
     * @param param BasMerge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BasMerge> getList(PageParam<BasMerge> page, BasMerge param) {
        return basMergeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BasMerge getInfoById(String id) {
        return basMergeMapper.getInfoById(id);
    }


    /**
     * 新增或更新
     *
     * @param basMergeVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(BasMergeVo basMergeVo) {
        //判断是否为空
        BasMerge merge = basMergeVo.getBasMerge();
        if (ObjectUtils.isEmpty(merge)) {
            throw new ServiceException("数据错误，请查证后重新操作");
        }
        List<Basconclusion> gridList = basMergeVo.getBasconclusionList();
        if (CollectionUtils.isEmpty(gridList)) {
            throw new ServiceException("数据错误，请查证后重新操作");
        }
        String mainId = merge.getId();
        if (StringUtils.isEmpty(mainId)) {//保存
            //设置属性
            merge.setIsDelete(0);
            merge.setCreatedate(new Date());
            merge.setCreater(SecurityUtils.getUsername());
            save(merge);
            mainId = merge.getId();
            log.info("增加合并结论词,增加" + merge.getMergeName());

        } else {
            BasMerge oldM = basMergeMapper.selectOne(new QueryWrapper<BasMerge>().eq("id", mainId));
            if (ObjectUtils.isEmpty(oldM)) {
                throw new ServiceException("数据已不存在，请查证后重新操作");
            }
            //更新
            merge.setModifydate(new Date());
            updateById(merge);
            log.info("编辑合并结论词,编辑{}" + merge.getMergeName());
            //删除中间表
            basMergeConclusionMapper.delete(new QueryWrapper<BasMergeConclusion>().eq("merge_id", mainId));
        }
        Set<String> conIds = new HashSet<String>();
        for (Basconclusion basconclusion : gridList) {
            String conclusionId = basconclusion.getConclusionId();
            if (StringUtils.isEmpty(conclusionId)) {
                throw new ServiceException("数据错误，请查证后重新操作");
            }
            String state = basconclusion.getState();
            if (!"removed".equals(state)) {
                if (conIds.contains(conclusionId)) {
                    throw new ServiceException("存在重复的结伦词：" + basconclusion.getConName());
                }
                //设置属性，插入
                BasMergeConclusion basMergeConclusion = new BasMergeConclusion();
                basMergeConclusion.setConclusionId(conclusionId);
                basMergeConclusion.setMergeId(mainId);
                basMergeConclusion.setCreatedate(new Date());
                basMergeConclusionMapper.insert(basMergeConclusion);
                conIds.add(conclusionId);
            }
        }

        BasMerge repeat_bm = getMergeByCon(conIds.toArray(new String[conIds.size()]), mainId);
        if (repeat_bm != null) {
            throw new ServiceException("已存在由所选结伦词组成的合并结伦词:" + repeat_bm.getMergeName());
        }
        return true;
    }

    /**
     * 通过Con和mid获取合并结伦词
     *
     * @param conIds
     * @param mId
     * @return
     */
    @Override
    public BasMerge getMergeByCon(String[] conIds, String mId) {
        BasMerge merge = null;
        if (conIds.length != 0) {
            StringBuilder str = new StringBuilder();
            for (String s : conIds) {
                str.append(s + "','");
            }
            str.delete(str.length() - 3, str.length());
            List<BasMerge> list = basMergeMapper.getMergeByCon(str.toString(), mId, conIds.length);
            if (list.size() > 0) {
                merge = (BasMerge) (list.get(0));
            }
        }
        return merge;
    }
}

