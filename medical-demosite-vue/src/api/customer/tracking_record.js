// 预检跟踪记录 麦沃德科技 开发人:暴雨
import request from '@/utils/request'

// 预检跟踪记录列表
export function listPrinttype(query) {
  return request({
    url: '/sell/customercommunicate/page',
    method: 'get',
    params: query
  })
}

// 详情
export function unicate(id) {
  return request({
    url: '/sell/customercommunicate/' + id,
    method: 'GET',

  })
}
