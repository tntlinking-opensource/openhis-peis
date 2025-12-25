import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/report/reportDefaultDoctor/page',
    method: 'get',
    params
  })
}
// 添加或修改
export function addReportDefaultDoctor(data) {
  return request({
    url: '/report/reportDefaultDoctor/saOrUp',
    method: 'post',
    data
  })
}
// 删除
export function removeReportDefaultDoctor(id) {
  return request({
    url: '/report/reportDefaultDoctor/' + id,
    method: 'delete'
  })
}
// 获取详情
export function getReportDefaultDoctor(id) {
  return request({
    url: '/report/reportDefaultDoctor/' + id,
    method: 'get'
  })
}