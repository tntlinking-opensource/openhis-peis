// 团检报告  开发人：麦沃德科技半夏
import request from '@/utils/request'
import request2 from '@/utils/request2'


// 生成团检报告数据
export function createReportData(params) {
  return request2({
    url: '/report/groupReport/createReprotNewD',
    method: 'get',
    params
  })
}

// 查询团检报告数据
export function getReportData(params) {
  return request({
    url: '/report/reportContent/getData',
    method: 'get',
    params
  })
}

// 获取分中心报告配置
export function getBranchConfig(params) {
  return request({
    url: '/report/reportConfig/getBranchConfig',
    method: 'get',
    params
  })
}