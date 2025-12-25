// 报告审核工作量  开发人:麦沃德科技 予安
import request from '@/utils/request'

// 获取时间内审核工作量
export function getListData(url,params){
  return request({
    url,
    method:"get",
    params
  })
}
// 获取所有审核工作量
export function getTotalData(url,params){
  return request({
    url,
    method:"get",
    params
  })
}
