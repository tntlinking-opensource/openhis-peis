// 职业健康检查复查人员名单  开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(params){
  return request({
    url:'/statistics/groupReviewNotice/page',
    method:"get",
    params
  })
}

// 新增/修改
export function saveOrUpdate(data) {
  return request({
    url: '/statistics/groupReviewNotice/createReviewGroup',
    method: 'post',
    data: data
  })
}

// 生成pdf
export function createPdf(data) {
  return request({
    url: '/statistics/groupReviewNotice/createPdf',
    method: 'post',
    data: data
  })
}

