// 记账结算明细 开发人：麦沃德矢北 
import request from '@/utils/request'

// 分页查询
export function getList(query){
  return request({
    url:'/finance/tallyDetail/page',
    method:'get',
    params:query,
  })
}
