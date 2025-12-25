// 分享报告统计  开发人:麦沃德科技 予安
import request from '@/utils/request'

// 分享报告统计-分页查询
export function getListData(params){
  return request({
    url:'/report/reportShareMain/page',
    method:"get",
    params
  })
}
// 分享报告统计-详情
export function getDetails(params){
  return request({
    url:'/report/reportShareMain/details',
    method:"get",
    params
  })
}
