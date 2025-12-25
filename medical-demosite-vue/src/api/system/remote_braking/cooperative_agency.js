import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/businessSourc/page',
    method: 'get',
    params
  })
}

// 添加合作机构
export function addConfig(data) {
  return request({
    url: '/businessSourc',
    method: 'post',
    data
  })
}
// 删除合作机构
export function removeConfig(ids) {
  return request({
    url: '/businessSourc/' + ids,
    method: 'delete',
    params: {
      ids
    }
  })
}
// 获取合作机构详情
export function ConfigDetails(id) {
  return request({
    url: '/businessSourc/' + id,
    method: 'get',
    params: {
      id
    }
  })
}
// 修改合作机构
export function editConfig(data) {
  return request({
    url: '/businessSourc',
    method: 'put',
    data
  })
}

// 合作机构添加授权信息
export function generateApi(params) {
  return request({
    url: '/system/auth/generate',
    method: 'get',
    params
  })
}