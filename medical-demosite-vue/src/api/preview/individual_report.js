// 个检报告  开发人：麦沃德科技半夏
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 速成或生成个检报告
export function createReportData(params) {
  return request2({
    url: '/report/iPersonalReport/create',
    method: 'get',
    params
  })
}

// 获取隐私报告数据
export function getPrivateData(data) {
  return request({
    url: '/report/health/createTiming',
    method: 'post',
    data: data
  })
}

// 获取临时报告数据
export function getTemporaryData(data) {
  return request({
    url: '/query/checkQuery/createTemp',
    method: 'post',
    params: data
  })
}

//查询报告
export function getData(data) {
  return request({
    url: '/report/reportContent/getData',
    method: 'get',
    params: data
  })
}

// 获取报告配置
export function getBranchConfig(params) {
  return request({
    url: '/report/reportConfig/getBranchConfig',
    method: 'get',
    params
  })
}
