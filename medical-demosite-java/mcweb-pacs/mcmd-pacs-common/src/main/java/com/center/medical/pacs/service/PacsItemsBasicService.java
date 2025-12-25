package com.center.medical.pacs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsItems;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacs.bean.param.PacsItemsListParam;
import com.center.medical.pacs.bean.param.PacsItemsSaveParam;
import com.center.medical.pacs.bean.vo.PacsItemExamPageVo;
import com.center.medical.pacs.bean.vo.PacsItemExamListVo;
import com.center.medical.pacs.bean.vo.PacsItemsListVo;
import com.center.medical.pacs.bean.vo.PacsItemsVo;

import java.util.List;

/**
 * pacs收费项目基础数据维护
 * @author xhp
 * @since 2023-03-31 14:30
 */
public interface PacsItemsBasicService extends IService<PacsItems> {

    /**
     * pacs收费项目分页查询
     * @param pageParam
     * @param pacsItemsListParam
     * @return
     */
    IPage<PacsItemsListVo> getPage(PageParam pageParam, PacsItemsListParam pacsItemsListParam);

    /**
     * 分页查询收费项目下所有检查项目
     * @param pageParam
     * @param id
     * @return
     */
    IPage<PacsItemExamPageVo> getExamPage(PageParam pageParam, String id);

    /**
     * 查询收费项目下所有检查项目
     * @param id
     * @return
     */
    List<PacsItemExamListVo> getExamList(String id);

    /**
     * 删除pacs收费项目
     * @param ids
     */
    void delete(List<String>ids);

    /**
     * pacs收费项目详情
     * @param id
     * @return
     */
    PacsItemsVo selectOne(String id);

    /**
     * 保存更新
     * @param pacsItemsSaveParam
     */
    void saOrUp(PacsItemsSaveParam pacsItemsSaveParam);
}
