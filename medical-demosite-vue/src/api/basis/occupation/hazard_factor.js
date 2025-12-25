// 职业健康危害因素 开发人：麦沃德科技 予安
import request from '@/utils/request'

// 查询JC危害因素列表
export function listHarm(query) {
  return request({
    url: '/harm/getHarmData',
    method: 'get',
    params: query
  })
}

// 查询JC危害因素详细
export function getHarm(id) {
  return request({
    url: '/harm/' + id,
    method: 'get'
  })
}

// 新增JC危害因素
export function addHarm(data) {
  return request({
    url: '/harm/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改JC危害因素
export function updateHarm(data) {
  return request({
    url: '/medical/harm',
    method: 'put',
    data: data
  })
}

// 删除JC危害因素
export function delHarm(id) {
  return request({
    url: '/harm/remove/' + id,
    method: 'delete'
  })
}
