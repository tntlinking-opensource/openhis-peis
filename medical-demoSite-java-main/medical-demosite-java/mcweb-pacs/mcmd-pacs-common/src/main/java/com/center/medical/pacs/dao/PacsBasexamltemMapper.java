package com.center.medical.pacs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacs.bean.model.PacsBasexamltem;
import com.center.medical.pacs.bean.param.PacsBasexamltemListParam;
import com.center.medical.pacs.bean.vo.PacsBasexamltemSignVo;
import com.center.medical.pacs.bean.vo.PacsBasexamltemVo;
import com.center.medical.pacs.bean.vo.PacsConclusionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-29 8:00
 */
@Repository
public interface PacsBasexamltemMapper extends BaseMapper<PacsBasexamltem> {

    IPage<PacsBasexamltemVo> getPage(PageParam page, @Param("pacsBasexamltemListParam") PacsBasexamltemListParam pacsBasexamltemListParam);

    IPage<PacsBasexamltemSignVo> getSignPage(PageParam page,@Param("id") String id);

    List<PacsBasexamltemSignVo> getSignList(@Param("id") String id);

    IPage<PacsConclusionVo> getConclusionList(PageParam pageParam,@Param("inputCode") String inputCode);
}
