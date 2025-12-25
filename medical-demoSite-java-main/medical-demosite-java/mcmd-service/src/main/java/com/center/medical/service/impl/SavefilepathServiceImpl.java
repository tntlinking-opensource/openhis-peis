package com.center.medical.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Savefilepath;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.SavefilepathMapper;
import com.center.medical.service.SavefilepathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放文件路径表(Savefilepath)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:01
 */
@Slf4j
@Service("savefilepathService")
@RequiredArgsConstructor
public class SavefilepathServiceImpl extends ServiceImpl<SavefilepathMapper, Savefilepath> implements SavefilepathService {

    private final SavefilepathMapper savefilepathMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[存放文件路径表]列表
     *
     * @param page  分页参数
     * @param param Savefilepath查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Savefilepath> getList(PageParam<Savefilepath> page, Savefilepath param) {
        return savefilepathMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Savefilepath getInfoById(String id) {
        return savefilepathMapper.getInfoById(id);
    }


    /**
     * 批量保存文件
     *
     * @param saveFilePath
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String SaveFilePath(Savefilepath saveFilePath) {
        String filePath = saveFilePath.getFilepath();
        String strId = saveFilePath.getGgid();
        String[] path = filePath.replace("\\\\", "\\").split(",");
        Long count = savefilepathMapper.selectCount(new QueryWrapper<Savefilepath>().eq("ggid", strId));
        List<Savefilepath> newList = new ArrayList<>();

        if (count > 0) {
            //遍历更新实体文件的路径,将原先的文件路径删除,添加新的文件路径
            savefilepathMapper.delete(new QueryWrapper<Savefilepath>().eq("ggid", strId));
        }
        //没上传过文件,进行保存
        for (int i = 0; i < path.length; i++) {
            String filePaths = path[i].replace("\"", "");
            //设置数据
            saveFilePath.setId(snowflake.nextIdStr());
            if (path.length == 1) {
                saveFilePath.setFilepath(filePaths.substring(1, filePaths.length() - 1));
            } else {
                if (i == 0) {
                    saveFilePath.setFilepath(filePaths.substring(1));
                } else if (i == path.length - 1) {
                    saveFilePath.setFilepath(filePaths.substring(0, filePaths.length() - 1));
                } else {
                    saveFilePath.setFilepath(filePaths);
                }
            }
            newList.add(saveFilePath);
        }
        saveBatch(newList);
        return "success";
    }

    /**
     * 根据公共ID获取文件列表
     *
     * @param id
     * @return
     */
    @Override
    public List<Savefilepath> getListByGgid(String id) {
        return savefilepathMapper.selectList(new LambdaQueryWrapper<Savefilepath>().eq(Savefilepath::getGgid, id));
    }
}

