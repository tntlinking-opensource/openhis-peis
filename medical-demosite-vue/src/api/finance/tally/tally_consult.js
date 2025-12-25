// 记账结算明细 开发人：麦沃德矢北 
import request from '@/utils/request'
 
export function getList(query){
  return request({
    url:'/finance/tallyQuery/page',
    method:'get',
    params:query 
  })
}