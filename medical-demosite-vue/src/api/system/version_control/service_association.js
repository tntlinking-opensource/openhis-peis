import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/sysServiceBranch/page',
    method: 'get',
    params
  })
}

// 添加第三方服务关联
export function addConfig(data) {
  return request({
    url: '/businessSourc',
    method: 'post',
    data
  })
}
// 删除第三方服务关联
export function removeConfig(ids) {
  return request({
    url: '/businessSourc/' + ids,
    method: 'delete',
    params: {
      ids
    }
  })
}
// 获取第三方服务关联详情
export function ConfigDetails(id) {
  return request({
    url: '/businessSourc/' + id,
    method: 'get',
    params: {
      id
    }
  })
}
// 修改第三方服务关联
export function editConfig(data) {
  return request({
    url: '/businessSourc',
    method: 'put',
    data
  })
}

// 第三方服务关联添加授权信息
export function generateApi(params) {
  return request({
    url: '/system/auth/generate',
    method: 'get',
    params
  })
}