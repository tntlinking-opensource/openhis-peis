import request from '@/utils/request'

// 返回年份
export function getAllYear() {
  return request({
    url: '/sell/monthtarget/getAllYear',
    method: 'get',
  })
}

// 获取分中心数据
export function getBranchData() {
  return request({
    url: '/sell/monthtarget/getBranchData',
    method: 'get',
  })
}

// 查询季度目标列表
export function listTarget(query) {
  return request({
    url: '/sell/monthtarget/page',
    method: 'get',
    params: query
  })
}

// 查询季度目标详细
export function getTarget(id) {
  return request({
    url: '/sell/monthtarget/' + id,
    method: 'get'
  })
}

// 新增或修改季度目标
export function addTarget(data) {
  return request({
    url: '/sell/monthtarget/saveOrUpdate',
    method: 'post',
    data
  })
}

// 获取总结数据
export function getSummaryData(query) {
  return request({
    url: '/sell/monthtarget/getSummaryData',
    method: 'get',
    params: query
  })
}

// 判断当前登录用户是否为领导
export function getIsLeader() {
  return request({
    url: '/sell/leadertarget/isLeader',
    method: 'get'
  })
}