package com.center.medical.query.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.FinishStatusParam;
import com.center.medical.query.bean.vo.FCChargeDataVo;
import com.center.medical.query.bean.vo.FinishStatusVo;
import com.center.medical.query.dao.FinishStatusMapper;
import com.center.medical.query.service.FinishStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 未检项目查询(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-13 16:32:13
 */
@Slf4j
@Service("finishStatusService")
@RequiredArgsConstructor
public class FinishStatusServiceImpl extends ServiceImpl<FinishStatusMapper, Peispatient> implements FinishStatusService {

    private final FinishStatusMapper finishStatusMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FinishStatusVo> getList(PageParam<FinishStatusVo> page, FinishStatusParam param) {
        IPage<FinishStatusVo> iPage = finishStatusMapper.getList(page, param);
        List<FinishStatusVo> list = iPage.getRecords();
        for (FinishStatusVo vo : list) {
            //设置 体检状态
            if(vo.getFReadytofinal()==null){
                vo.setTjzt("13");
            }
            else if(vo.getFReadytofinal().equals(1)){
                if(vo.getJkwc()!=null&&!vo.getJkwc().equals("")){
                    if(vo.getZywc()!=null&&!vo.getZywc().equals("")){
                        int a=0;
                        if(vo.getJkwc()>vo.getZywc()){
                            a=vo.getJkwc();
                        }else{
                            a=vo.getZywc();
                        }
                        vo.setTjzt(String.valueOf(a));
                    }else{
                        vo.setTjzt(String.valueOf(vo.getJkwc()));
                    }
                }else{
                    if(vo.getZywc()!=null && !vo.getZywc().equals("")){
                        vo.setTjzt(String.valueOf(vo.getZywc()));
                    }else{
                        vo.setTjzt("12");
                    }
                }
            }else{
                vo.setTjzt("13");
            }
        }
        iPage.setRecords(list);
        return iPage;
    }



    /**
     * 点击获取收费项目信息
     * @param page
     * @param patientcode
     * @return
     */
    @Override
    public IPage<FCChargeDataVo> getChargeData(PageParam<FCChargeDataVo> page, String patientcode) {
        return finishStatusMapper.getChargeData(page,patientcode);
    }
}

