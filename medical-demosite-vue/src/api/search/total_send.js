// 外送 开发人：麦沃德科技 矢北 
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/query/totalSend/page',
    method: 'get',
    params:query
  })
}