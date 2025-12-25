// 卡消费明细 开发人：麦沃德矢北 
import request from '@/utils/request'

export function getListData(query){
  return request({
    url:'/finance/spendDetail/page',
    method:'get',
    params:query
  })
}
 export function GetFZXData(query){
  return request({
    url:'/sell/createorder/getBranchData',
    method:'get',
    params:query
  })
 }