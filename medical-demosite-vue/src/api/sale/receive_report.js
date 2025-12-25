// 待领取报告  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 分页查询
export function listReport(query) {
  return request({
    url: '/crm/pendingReport/page',
    method: 'get',
    params: query
  })
}

// 详情
export function getReport(id) {
  return request({
    url: '/crm/pendingReport/' + id,
    method: 'get',
  })
}