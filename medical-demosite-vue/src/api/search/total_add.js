// 登记信息查询 开发人：麦沃德科技 矢北 
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/query/totalAdd/page',
    method: 'get',
    params:query
  })
}

// 分页查询
export function getPageTotal(query) {
  return request({
    url: '/query/totalAdd/getPageTotal',
    method: 'get',
    params:query
  })
}