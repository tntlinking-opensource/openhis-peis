// 来检客户统计  开发人:麦沃德科技 予安
import request from '@/utils/request'

// 分页查询（左侧）
export function getListData(params){
  return request({
    url:'/statistics/customerStatistics/page',
    method:"get",
    params
  })
}
// 分页查询（右侧）
export function getPhysicalExaminer(params){
  return request({
    url:'/statistics/customerStatistics/getPhysicalExaminer',
    method:"get",
    params
  })
}
