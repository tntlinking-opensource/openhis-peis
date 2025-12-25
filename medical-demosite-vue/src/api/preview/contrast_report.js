// 对比报告  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 生成团检报告数据
export function createReport(query) {
  return request({
    url: '/total/RecordManage/previewContrastReport',
    method: 'get',
    params: query
  })
}

// 查询报告内容
export function getReportData(params) {
  return request({
    url: '/report/reportContent/getData',
    method: 'get',
    params
  })
}

// 查询报告内容
export function updateReportData(data) {
  return request({
    url: '/report/reportContent/update',
    method: 'put',
    data
  })
}