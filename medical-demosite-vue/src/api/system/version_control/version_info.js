// 版本信息
import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/sysVersion/page',
    method: 'get',
    params
  })
}
// 分页查询系统服务列表
export function getServiceType(params) {
  return request({
    url: '/sysServiceType/page',
    method: 'get',
    params
  })
}
// 查询子版本分页列表
export function getChildService(params) {
  return request({
    url: '/sysVersion/list',
    method: 'get',
    params
  })
}
// 查询子版本下包含的服务分页列表
export function getChildServiceList(params) {
  return request({
    url: '/sysVersionService/list',
    method: 'get',
    params
  })
}

// 添加版本信息
export function addConfig(data) {
  return request({
    url: '/sysVersion',
    method: 'post',
    data
  })
}
// 删除版本信息
export function removeConfig(ids) {
  return request({
    url: '/sysVersion/' + ids,
    method: 'delete',
    params: {
      ids
    }
  })
}
// 获取版本信息详情
export function ConfigDetails(id) {
  return request({
    url: '/sysVersion/' + id,
    method: 'get',
    params: {
      id
    }
  })
}
// 修改版本信息
export function editConfig(data) {
  return request({
    url: '/sysVersion',
    method: 'put',
    data
  })
}

// 添加系统服务
export function addSysVersionService(data) {
  return request({
    url: '/sysVersionService',
    method: 'post',
    data
  })
}
// 获取系统服务详情
export function sysVersionServiceDetails(id) {
  return request({
    url: '/sysVersionService/' + id,
    method: 'get',
    params: {
      id
    }
  })
}
// 修改系统服务
export function editSysVersionService(data) {
  return request({
    url: '/sysVersionService',
    method: 'put',
    data
  })
}

// 获取历史版本信息列表
export function lastList(params) {
  return request({
    url: '/sysVersion/lastList',
    method: 'get',
    params
  })
}

// 检查更新
export function getVersion(params) {
  return request({
    url: '/sysVersion/getVersion',
    method: 'get',
    params
  })
}
// 记录用户新版已读记录
export function saveVersionNotify() {
  return request({
    url: '/sysVersion/saveVersionNotify',
    method: 'get',
  })
}

// 版本更新内容***************
// 分页查询
export function getVersionPage(params) {
  return request({
    url: '/sysVersionItem/page',
    method: 'get',
    params
  })
}
// 添加
export function addVersionItem(data) {
  return request({
    url: '/sysVersionItem',
    method: 'post',
    data
  })
}
// 详情
export function detailsVersionItem(logId) {
  return request({
    url: '/sysVersionItem/' + logId,
    method: 'get',
    params: { logId }
  })
}
// 修改
export function editVersionItem(data) {
  return request({
    url: '/sysVersionItem',
    method: 'put',
    data
  })
}
// 删除
export function deleteVersionItem(ids) {
  return request({
    url: '/sysVersionItem/' + ids,
    method: 'delete',
    // params: {
    //   ids,
    //   logIds: ids
    // }
  })
}