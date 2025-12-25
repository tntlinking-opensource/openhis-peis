package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.data.bean.model.BaseDictionary;
import com.center.medical.data.bean.param.BaseDictionaryParam;
import com.center.medical.data.bean.vo.BaseDictionaryVo;
import com.center.medical.data.dao.BaseDictionaryMapper;
import com.center.medical.data.service.BaseDictionaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 字典(BaseDictionary)表服务实现类
 *
 * @author ay
 * @since 2022-11-18 18:16:14
 */
@Slf4j
@Service("baseDictionaryService")
@RequiredArgsConstructor
public class BaseDictionaryServiceImpl extends ServiceImpl<BaseDictionaryMapper, BaseDictionary> implements BaseDictionaryService {

    private final BaseDictionaryMapper baseDictionaryMapper;

    /**
    * 分页查询[字典]列表
    *
    * @param page 分页参数
    * @param param BaseDictionary查询参数
    * @return 分页数据
    */
    @Override
    public IPage<BaseDictionary> getList(PageParam<BaseDictionary> page, BaseDictionary param) {
        return baseDictionaryMapper .getList(page, param);
    }

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    @Override
    public BaseDictionary getInfoById(String id,String classCode){
        return baseDictionaryMapper.getInfoById(id,classCode);
    }


    /**
     * 根据classCode分页查询
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    @Override
    public IPage<BaseDictionaryVo> getOurDictionary(PageParam<BaseDictionaryVo> page, BaseDictionaryParam baseDictionaryParam) {
        IPage<BaseDictionaryVo> baseDictionaryIPage = getOurDictionaryImpl(page,baseDictionaryParam);
        return baseDictionaryIPage;
    }


    /**
     * 根据搜索条件创建sql语句
     * @param page
     * @param baseDictionaryParam
     * @return
     */
    private IPage<BaseDictionaryVo> getOurDictionaryImpl(PageParam<BaseDictionaryVo> page, BaseDictionaryParam baseDictionaryParam) {
        //接收sql
        StringBuffer sql = new StringBuffer("");
        String classCode =baseDictionaryParam.getClassCode();
        String srm =baseDictionaryParam.getSrm();
        String mz =baseDictionaryParam.getMz();
        IPage<BaseDictionaryVo> pager = new PageParam<>();
        //危害因素
        if("312".equals(classCode)){
            pager = baseDictionaryMapper.getOurDictionary312(page,baseDictionaryParam);
        }
        //体检项目
        if("308".equals(classCode)){
             pager = baseDictionaryMapper.getOurDictionary308(page,baseDictionaryParam);
        }
        //症状
        if("310".equals(classCode)){
            pager = baseDictionaryMapper.getOurDictionary310(page,baseDictionaryParam);
        }
        //职业禁忌症
        if("313".equals(classCode)){
            pager = baseDictionaryMapper.getOurDictionary313(page,baseDictionaryParam);
        }
        //疑似职业病
        if("314".equals(classCode)){
            pager = baseDictionaryMapper.getOurDictionary314(page,baseDictionaryParam);
        }
        //收费项目
        if("323".equals(classCode)){
            pager = baseDictionaryMapper.getOurDictionary323(page,baseDictionaryParam);
        }
        return pager;
    }


}

