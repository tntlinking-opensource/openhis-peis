// 公共客户状态日志  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 查询列表
export function listCustomer(query) {
  return request({
    url: '/customerOperateHistory/page',
    method: 'get',
    params: query
  })
}