package com.center.medical.query.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.OriginalParam;
import com.center.medical.query.bean.param.StatusQueryParam;
import com.center.medical.query.bean.vo.OriginalVo;
import com.center.medical.query.bean.vo.StatusQueryVo;
import com.center.medical.query.dao.ItemsStatusQueryMapper;
import com.center.medical.query.service.ItemsStatusQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目检况查询(Peispatientfeeitem)表服务实现类
 *
 * @author ay
 * @since 2023-04-13 17:32:56
 */
@Slf4j
@Service("itemsStatusQueryService")
@RequiredArgsConstructor
public class ItemsStatusQueryServiceImpl extends ServiceImpl<ItemsStatusQueryMapper, Peispatientfeeitem> implements ItemsStatusQueryService {

    private final ItemsStatusQueryMapper itemsStatusQueryMapper;

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<StatusQueryVo> getList(PageParam<StatusQueryVo> page, StatusQueryParam param) {
        return itemsStatusQueryMapper.getList(page, param);
    }



    /**
     * 导出迟检、拒检、弃检、补检统计
     * @param param
     * @return
     */
    @Override
    public List<StatusQueryVo> getExportData(StatusQueryParam param) {
        List<StatusQueryVo> list = itemsStatusQueryMapper.getExportData(param);
        for (StatusQueryVo vo : list) {
            //数字转换成字符 拼接
            String types = getType(vo.getFDelayed(),vo.getFTransferedhl7(),vo.getFGiveup(),vo.getSfjj(),vo.getFExaminated());
            vo.setTypes(types);
        }
        return list;
    }


    //数字转换成字符 拼接
    public String getType(Integer cj,Integer bj,Integer qj,Integer jj,Integer yj) {
        List<String> types=new ArrayList<String>();
        if(cj!=null&&"1".equals(cj.toString())) {
            types.add("迟检");
        }
        if(bj!=null&&!"".equals(bj.toString())) {
            types.add("补检");
        }
        if(qj!=null&&"1".equals(qj.toString())) {
            types.add("弃检");
        }
        if(jj!=null&&"1".equals(jj.toString())) {
            types.add("拒检");
        }
        if(yj!=null&&"1".equals(yj.toString())){
            types.add("已检");
        }
        if(types.size()==0){
            types.add("未检");
        }
        return StringUtils.join(types.toArray(), "、");
    }


    /**
     * 分页查询科室拒检查询
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<OriginalVo> getOriginalRejection(PageParam<OriginalVo> page, OriginalParam param) {
        return itemsStatusQueryMapper.getOriginalRejection(page,param);
    }
}

