package com.center.medical.pacs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PacsItems;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacs.bean.param.PacsItemsListParam;
import com.center.medical.pacs.bean.vo.PacsItemExamPageVo;
import com.center.medical.pacs.bean.vo.PacsItemExamListVo;
import com.center.medical.pacs.bean.vo.PacsItemsListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-31 14:31
 */
@Repository
public interface PacsItemsBasicMapper extends BaseMapper<PacsItems> {

    IPage<PacsItemsListVo> getPage(PageParam pageParam,@Param("param") PacsItemsListParam pacsItemsListParam);

    IPage<PacsItemExamPageVo> getExamPage(PageParam pageParam, @Param("id") String id);

    List<PacsItemExamListVo> getExamList(@Param("id") String id);
}
