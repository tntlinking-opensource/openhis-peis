// 职业病名称维护 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 查询JC职业病名称列表
export function listDiseast(query) {
  return request({
    url: '/occupationDiseast/list',
    method: 'get',
    params: query
  })
}

// 查询JC职业病名称详细
export function getDiseast(id) {
  return request({
    url: '/occupationDiseast/' + id,
    method: 'get'
  })
}

// 新增JC职业病名称
export function addDiseast(data) {
  return request({
    url: '/occupationDiseast/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改JC职业病名称
export function updateDiseast(data) {
  return request({
    url: '/occupationDiseast/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 删除JC职业病名称
export function delDiseast(id) {
  return request({
    url: '/occupationDiseast/remove/' + id,
    method: 'delete'
  })
}
