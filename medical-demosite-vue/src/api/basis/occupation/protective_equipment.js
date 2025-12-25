// 个人防护用品维护 开发人：麦沃德科技半夏
import request from '@/utils/request'

// 查询个人防护用品列表
export function listProtectiveEquipment(query) {
  return request({
    url: '/data/ZyFhclGr/page',
    method: 'get',
    params: query
  })
}

// 查询个人防护用品详细
export function detailsProtectiveEquipment(id) {
  return request({
    url: '/data/ZyFhclGr/' + id,
    method: 'get'
  })
}

// 添加/更新个人防护用品
export function editProtectiveEquipment(data) {
  return request({
    url: '/data/ZyFhclGr/saOrUp',
    method: 'post',
    data: data
  })
}

// 删除个人防护用品
export function delProtectiveEquipment(id) {
  return request({
    url: '/data/ZyFhclGr/' + id,
    method: 'delete'
  })
}

// 获取个人防护用品
export function getProtectiveEquipment(query) {
  return request({
    url: '/data/ZyFhclGr/getProtectiveEquipment',
    method: 'get',
    params: query
  })
}