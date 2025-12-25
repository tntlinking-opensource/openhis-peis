import request from '@/utils/request'

// 查询科室默认医生列表
export function listPost(query) {
  return request({
    url: '/report/reportDefaultDoctor/page',
    method: 'get',
    params: query
  })
}

// 查询科室默认医生详细
export function getPost(postId) {
  return request({
    url: '/report/reportDefaultDoctor/' + postId,
    method: 'get'
  })
}

// 新增科室默认医生
export function addPost(data) {
  return request({
    url: '/report/reportDefaultDoctor/saOrUp',
    method: 'post',
    data: data
  })
}

// 修改科室默认医生
export function updatePost(data) {
  return request({
    url: '/report/reportDefaultDoctor/saOrUp',
    method: 'post',
    data: data
  })
}

// 删除岗位
export function delPost(postId) {
  return request({
    url: '/report/reportDefaultDoctor/' + postId,
    method: 'delete'
  })
}
