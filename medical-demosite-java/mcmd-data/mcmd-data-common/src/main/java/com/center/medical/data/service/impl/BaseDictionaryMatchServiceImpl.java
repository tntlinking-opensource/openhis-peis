package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.data.bean.model.BaseDictionary;
import com.center.medical.data.bean.model.BaseDictionaryMatch;
import com.center.medical.data.bean.param.BaseDictionaryParam;
import com.center.medical.data.dao.BaseDictionaryMapper;
import com.center.medical.data.dao.BaseDictionaryMatchMapper;
import com.center.medical.data.service.BaseDictionaryMatchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 体检系统-职业卫生管理平台 字典匹配(BaseDictionaryMatch)表服务实现类
 *
 * @author ay
 * @since 2022-11-18 18:16:14
 */
@Slf4j
@Service("baseDictionaryMatchService")
@RequiredArgsConstructor
public class BaseDictionaryMatchServiceImpl extends ServiceImpl<BaseDictionaryMatchMapper, BaseDictionaryMatch> implements BaseDictionaryMatchService {

    private final BaseDictionaryMatchMapper baseDictionaryMatchMapper;
    private final BaseDictionaryMapper baseDictionaryMapper;
    private final Snowflake snowflake;

    /**
    * 分页查询[体检系统-职业卫生管理平台 字典匹配]列表
    *
    * @param page 分页参数
    * @param param BaseDictionaryMatch查询参数
    * @return 分页数据
    */
    @Override
    public IPage<BaseDictionaryMatch> getList(PageParam<BaseDictionaryMatch> page, BaseDictionaryMatch param) {
        return baseDictionaryMatchMapper .getList(page, param);
    }

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    @Override
    public BaseDictionaryMatch getInfoById(String id){
        return baseDictionaryMatchMapper .getInfoById(id);
    }

    /**
     * 通过搜索条件分页查询列表
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    @Override
    public PageParam<BaseDictionaryMatch> getDataMatch(PageParam<BaseDictionaryMatch> page, BaseDictionaryParam baseDictionaryParam) {
        return baseDictionaryMatchMapper.getDataMatch(page,baseDictionaryParam);
    }

    /**
     * 建立或更新数据字典匹配
     * @param baseDictionaryMatch
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setDataMatch(BaseDictionaryMatch baseDictionaryMatch) {
        String nowId = baseDictionaryMatch.getNowId();
        String dictionaryId = baseDictionaryMatch.getDictionaryId();

        //nowId是空的 找的字典ID不是空的才能新建一条
        if(StringUtils.isEmpty(nowId) && StringUtils.isNotEmpty(dictionaryId)){
            baseDictionaryMatchMapper.insert(baseDictionaryMatch);
        }
        //两个不是空的只能更新
        if(StringUtils.isNotEmpty(nowId) && StringUtils.isNotEmpty(dictionaryId)){
            //用sql语句更新，同步拦截器会报错，暂时先这样更新
            List<String> targetDictionaryIds = baseDictionaryMapper.selectList(
                    new QueryWrapper<BaseDictionary>()
                            .select("id")
                            .eq("dictionary_type", baseDictionaryMatch.getClassCode())
                            .eq("dictionary_code", baseDictionaryMatch.getNowId())
            ).stream().map(BaseDictionary::getId).collect(Collectors.toList());
            if (targetDictionaryIds.isEmpty()) {
                return false;
            }
            // 更新操作
            return update(
                    new UpdateWrapper<BaseDictionaryMatch>()
                            .set("dictionary_id", baseDictionaryMatch.getDictionaryId())
                            .in("dictionary_id", targetDictionaryIds)
                            .eq("medical_id", baseDictionaryMatch.getMedicalId())
            );
        }
        //nowId不是空的 找的字典是空的 说明要删除这条
        if(StringUtils.isNotEmpty(nowId) && StringUtils.isEmpty(dictionaryId)){
            baseDictionaryMatchMapper.deleteBaseDictionaryMatch(baseDictionaryMatch);
        }
        return Boolean.TRUE;
    }
}

