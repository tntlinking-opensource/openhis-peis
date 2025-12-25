// 影像科室  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 普通预览科室报告
export function diagnosticReport(query) {
  return request({
    url: '/abteilung/division/diagnosticReport',
    method: 'get',
    params: query
  })
}

// 图片科室预览报告
export function picReport(query) {
  return request({
    url: '/abteilung/division/picReport',
    method: 'get',
    params: query
  })
}

// 检验科预览报告
export function inspectReport(query) {
  return request({
    url: '/abteilung/division/inspectReport',
    method: 'get',
    params: query
  })
}