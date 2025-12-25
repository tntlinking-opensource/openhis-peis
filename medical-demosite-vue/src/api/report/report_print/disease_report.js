// 职业初审  开发人：麦沃德科技矢北
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 获取分页
export function getListData(query) {
  return request({
    url: '/report/profession/professionReport',
    method: 'get',
    params: query
  })
}
// 职业报告打印
export function printAlready(patientcode) {
  return request({
    url: `/report/profession/backOut/${patientcode}/1`,
    method: 'get',

  })
}
// 预览
export function simpleImg(patientCode) {
  return request({
    url: '/report/profession/sample/' + patientCode,
    method: 'get',
  })
}
// 详情
export function getPatientData(id) {
  return request({
    url: '/report/profession/' + id,
    method: 'get',
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