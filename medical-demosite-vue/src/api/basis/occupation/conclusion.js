import request from '@/utils/request'

// 查询JC职业病检查结论列表
export function listSummary(query) {
  return request({
    url: '/zySummary/getListData',
    method: 'get',
    params: query
  })
}

// 查询JC职业病检查结论详细 
export function getSummary(id) {
  return request({
    url: '/zySummary/' + id,
    method: 'get'
  })
}

// 新增JC职业病检查结论
export function addSummary(data) {
  return request({
    url: '/zySummary/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 删除JC职业病检查结论
export function delSummary(id) {
  return request({
    url: '/zySummary/remove/' + id,
    method: 'delete'
  })
}
