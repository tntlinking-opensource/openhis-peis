// 订单总结  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 分页查询
export function listSummary(query) {
  return request({
    url: '/sell/orderSummary/page',
    method: 'get',
    params: query
  })
}

// 查询详情
export function getSummary(id) {
  return request({
    url: '/sell/orderSummary/' + id,
    method: 'get'
  })
}

// 新增
export function addSummary(data) {
  return request({
    url: '/sell/orderSummary',
    method: 'post',
    data: data
  })
}

// 编辑
export function updateSummary(data) {
  return request({
    url: '/sell/orderSummary',
    method: 'put',
    data: data
  })
}

// 删除普通套餐
export function delSummary(id) {
  return request({
    url: '/sell/orderSummary/' + id,
    method: 'delete'
  })
}

// 获取关联的客户单位名称
export function getKhdwmc(id) {
  return request({
    url: '/sell/orderSummary/getKhdwmc/' + id,
    method: 'get'
  })
}

// 获取订单名称
export function getListByKey(query) {
  return request({
    url: '/sell/createorder/getListByKey',
    method: 'get',
    params: query
  })
}