import request from '@/utils/request'

// 返回年份
export function listYears() {
  return request({
    url: '/sell/leadertarget/getAllYear',
    method: 'get',
  })
}

// 获取分中心数据
export function getBranchData() {
  return request({
    url: '/sell/sellDate/getBranchData',
    method: 'get',
  })
}

// 查询年度目标列表
export function listYearTarget(query) {
  return request({
    url: '/sell/leadertarget/page',
    method: 'get',
    params: query
  })
}

// 查询年度目标详细
export function getYearTarget(id) {
  return request({
    url: '/sell/leadertarget/' + id,
    method: 'get'
  })
}

// 新增或修改年度目标
export function addYearTarget(data) {
  return request({
    url: '/sell/leadertarget',
    method: 'post',
    data
  })
}

// 获取总结数据
export function getSummaryData(query) {
  return request({
    url: '/sell/leadertarget/getSummaryData',
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