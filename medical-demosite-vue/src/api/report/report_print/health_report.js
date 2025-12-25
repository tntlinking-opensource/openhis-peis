// 健康报告  开发人：麦沃德科技矢北
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 获取分页

export function getListData(query) {
  return request({
    url: `/report/health/getPage`,
    method: 'get',
    params: query
  })
}

// 获取详情
export function getPatientData(patientcodes) {
  return request({
    url: '/report/healthFirstAudit/getPatientData',
    method: 'get',
    params: {
      patientcodes
    }
  })
}
// 删除
export function deleteList(id) {
  return request({
    url: '/report/health/' + id,
    method: 'delete',
  })
}

//已经打印
export function printAlready(patientcode) {
  return request({
    url: `/report/health/backOut/${patientcode}/0`,
    method: 'get',
  })
}
//批量打印
export function sampleBatch(patientCode) {
  return request({
    url: '/report/health/sampleBatch/' + patientCode,
    method: 'get'
  })
}

///PDF打印
export function downloadPDF(patientCode) {
  return request({
    url: '/report/health/downloadPdf/' + patientCode,
    method: 'get'
  })
}
//电子报告
export function electronicReport(ids) {
  return request({
    url: '/report/health/electronicReportHandover/' + ids,
    method: 'get'
  })
}

//预览 
export function simpleImg(patientCode) {
  return request({
    url: '/report/health/sample/patientcode/' + patientCode,
    method: 'get'
  })
}

//终审交接查询
export function searchZsjjCode(patientcode) {
  return request({
    url: '/report/health/searchZsjjCode/' + patientcode,
    method: 'get'
  })
}
//终审交接
export function saveZsjj(ids) {
  return request({
    url: '/report/health/saveZsjj/' + ids,
    method: 'get'
  })
}
//速成或生成检验报告
export function createReport(query) {
  return request2({
    url: '/report/iPersonalReport/create',
    method: 'get',
    params: query
  })
}

//隐私报告生成
export function createPrivate(data) {
  return request2({
    url: '/report/health/createTiming',
    method: 'post',
    data: data
  })
}

// 生成分享报告信息
export function reportShare(data) {
  return request({
    url: '/report/reportShareMain/reportShare',
    method: 'post',
    data
  })
}
// 更新分享报告
export function updateReportShare(data) {
  return request({
    url: '/report/reportShareMain/updateReportShare',
    method: 'put',
    data
  })
}

// 更新分享报告
export function zsjjTempApi(params) {
  return request({
    url: '/query/checkQuery/zsjj',
    method: 'put',
    params
  })
}

// 批量下载pdf
export function batchDownload(data) {
  return request2({
    url: '/report/health/batchDownload',
    method: 'post',
    responseType: 'blob',
    headers: { 'Content-Type': 'application/json;application/octet-stream' },
    data
  })
}

// 检验url是否有效
export function checkUrl(data) {
  return request({
    url: '/report/health/checkUrl',
    method: 'post',
    data
  })
}