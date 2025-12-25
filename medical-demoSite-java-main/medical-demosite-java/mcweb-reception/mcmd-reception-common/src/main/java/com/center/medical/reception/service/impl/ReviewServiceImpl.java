package com.center.medical.reception.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.bean.model.Review;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.bean.param.ReviewParam;
import com.center.medical.reception.bean.vo.ReviewPrintVo;
import com.center.medical.reception.dao.ReviewMapper;
import com.center.medical.reception.dao.ReviewProjectMapper;
import com.center.medical.reception.service.ReviewService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ZJ复查表(Review)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:26
 */
@Slf4j
@Service("reviewService")
@RequiredArgsConstructor
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewProjectMapper reviewProjectMapper;
//    private final SectionResultTwoService sectionResultTwoService;
    private final SysDeptMapper sysDeptMapper;
    private final SysBranchMapper sysBranchMapper;

    /**
     * 分页查询[ZJ复查表]列表
     *
     * @param page  分页参数
     * @param param Review查询参数
     * @return 分页数据
     */
    @Override

    public IPage<Review> getPage(PageParam<Review> page, ReviewParam param) {
        return reviewMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Review getInfoById(String id) {
        return reviewMapper.getInfoById(id);
    }

    /**
     * 打印
     *
     * @param ids 打印对象主键{id}集合，多个以英文逗号（,）隔开
     * @return
     */
    @Override
    public List<ReviewPrintVo> printData(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要打印的数据！");
        }
        List<ReviewPrintVo> reviews = reviewMapper.getListByIds(ids);
        for (ReviewPrintVo review : reviews) {
            List<ReviewProject> rw = reviewProjectMapper.selectList(new LambdaQueryWrapper<ReviewProject>()
                    .eq(ReviewProject::getReviewId, review.getId()));
            List itemVoList = new ArrayList();
//            if (CollectionUtil.isEmpty(rw)) {
//                for (ReviewProject project : rw) {
//                    List<SectionResultTwo> sr = sectionResultTwoService.list(new LambdaQueryWrapper<SectionResultTwo>()
//                            .eq(SectionResultTwo::getChargesId, project.getItemsId()));
//                    if (CollectionUtil.isNotEmpty(sr)) {
//                        SysDept de = sysDeptMapper.getByDeptNo(sr.get(0).getDivisionId());
//                        if (Objects.nonNull(de)) {
//                            ReviewItemVo itemVo = new ReviewItemVo();
//                            itemVo.setXm(project.getItemsName());
//                            itemVo.setKs(de.getDeptName());
//                            itemVoList.add(itemVo);
//                        }
//                    }
//                }
//            }
            review.setItems(itemVoList);
            // 当前分中心
            SysBranch branch = sysBranchMapper.selectById(SecurityUtils.getCId());
            review.setFzxAddress(branch.getAddress());
            review.setSuccess(true);
        }

        List<String> resultIds = reviews.stream().map(ReviewPrintVo::getId).collect(Collectors.toList());
        int i = 0;
        for (String id : ids) {
            if (!CollectionUtil.contains(resultIds, id)) {
                ReviewPrintVo reviewPrintVo = new ReviewPrintVo();
                reviewPrintVo.setSuccess(false);
                reviewPrintVo.setMsg("复查单打印失败：第" + (i + 1) + "个体检者!");
                i++;
            }
        }
        return reviews;
    }

}

