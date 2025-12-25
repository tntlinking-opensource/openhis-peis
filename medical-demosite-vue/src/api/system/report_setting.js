import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/report/reportConfig/page',
    method: 'get',
    params
  })
}
// 添加报告配置
export function addReportConfig(data) {
  return request({
    url: '/report/reportConfig',
    method: 'post',
    data
  })
}
// 删除报告配置
export function removeReportConfig(ids) {
  return request({
    url: '/report/reportConfig/' + ids,
    method: 'delete',
    params: {
      ids
    }
  })
}
// 获取报告配置详情
export function reportConfigDetails(ids) {
  return request({
    url: '/report/reportConfig/' + ids,
    method: 'get',
    params: {
      ids
    }
  })
}
// 修改报告配置
export function editreportConfig(data) {
  return request({
    url: '/report/reportConfig',
    method: 'put',
    data
  })
}
