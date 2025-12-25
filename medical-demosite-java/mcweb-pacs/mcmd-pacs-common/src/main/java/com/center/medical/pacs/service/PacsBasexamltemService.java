package com.center.medical.pacs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacs.bean.model.PacsBasexamltem;
import com.center.medical.pacs.bean.param.PacsBasexamltemListParam;
import com.center.medical.pacs.bean.param.PacsBasexamltemSaveParam;
import com.center.medical.pacs.bean.vo.PacsBasexamltemSignVo;
import com.center.medical.pacs.bean.vo.PacsBasexamltemVo;
import com.center.medical.pacs.bean.vo.PacsConclusionVo;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-29 8:02
 */
public interface PacsBasexamltemService extends IService<PacsBasexamltem>{
    /**
     *
     * @param page
     * @param pacsBasexamltemListParam
     * @return
     */
    IPage<PacsBasexamltemVo> getPage(PageParam page, PacsBasexamltemListParam pacsBasexamltemListParam);

    /**
     *
     * @param page
     * @param id
     * @return
     */
    IPage<PacsBasexamltemSignVo> getSignPage(PageParam page, String id);

    /**
     *
     * @param id
     * @return
     */
    List<PacsBasexamltemSignVo> getSignList(String id);

    /**
     *
     * @param ids
     */
    void delete(List<String> ids);

    /**
     *
     * @param id
     * @return
     */
    PacsBasexamltemVo getInfoById(String id);

    /**
     *
     * @param pacsBasexamltemSaveParam
     */
    void saOrUp(PacsBasexamltemSaveParam pacsBasexamltemSaveParam);

    /**
     *
     * @param inputCode
     * @return
     */
    IPage<PacsConclusionVo> getConclusionList(PageParam pageParam,String inputCode);
}
