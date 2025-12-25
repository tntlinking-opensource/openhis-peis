// 正式客户 麦沃德科技 暴雨
import request from '@/utils/request'
  // 查询列表
export function getList(query) {
  return request({
    url: '/sell/formalCustomer/page',
    method: 'get',
    params: query
  })
  }
