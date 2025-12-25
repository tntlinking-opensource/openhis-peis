package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientarchive;
import com.center.medical.olddata.dao.MdPeispatientarchiveMapper;
import com.center.medical.olddata.service.MdPeispatientarchiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检者档案表(MdPeispatientarchive)服务实现类
 *
 * @author ay
 * @since 2023-09-04 09:16:14
 */
@Slf4j
@Service("mdPeispatientarchiveService")
@RequiredArgsConstructor
public class MdPeispatientarchiveServiceImpl extends ServiceImpl<MdPeispatientarchiveMapper, MdPeispatientarchive> implements MdPeispatientarchiveService {

    private final MdPeispatientarchiveMapper mdPeispatientarchiveMapper;

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientarchive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientarchive> getPage(PageParam<MdPeispatientarchive> page, MdPeispatientarchive param) {
        return mdPeispatientarchiveMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public MdPeispatientarchive getInfoById(String id) {
        return mdPeispatientarchiveMapper.getInfoById(id);
    }


    /**
     * 单条添加或修改
     * @param map
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUp(MdPeispatientarchive map) {
        saveOrUpdate(map);
    }

    /**
     * 根据身份证查询
     * @param idcardno
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public List<MdPeispatientarchive> getInfoByIdCard(String idcardno) {
        return mdPeispatientarchiveMapper.selectList(new LambdaQueryWrapper<MdPeispatientarchive>()
                .eq(MdPeispatientarchive::getIdcardno,idcardno)
                .eq(MdPeispatientarchive::getIsDelete,0)
        );
    }
}

