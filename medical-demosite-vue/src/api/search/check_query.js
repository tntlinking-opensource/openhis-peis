// 登记信息查询 开发人：麦沃德科技 予安 
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 分页查询
export function getListData(params) {
  return request({
    url: '/query/checkQuery/getPatientForRegister',
    method: 'get',
    params
  })
}

// 查看体检者信息
export function getPatientData(params) {
  return request({
    url: '/SignInInspection/getPatientData',
    method: 'get',
    params
  })
}

// 终审交接
export function handoverApi(params) {
  return request({
    url: '/query/checkQuery/zsjj',
    method: 'PUT',
    params
  })
}

// 结案
export function updateclose(params) {
  return request({
    url: '/query/checkQuery/updateclose',
    method: 'PUT',
    params
  })
}

// 旧案召回
export function reSaveHistory(params) {
  return request({
    url: '/query/checkQuery/reSaveHistory',
    method: 'PUT',
    params
  })
}

// 加急
export function urgentApi(params) {
  return request({
    url: '/query/checkQuery/jiaji',
    method: 'PUT',
    params
  })
}

//速成或生成检验报告
export function createReport(query) {
  return request({
    url: '/report/iPersonalReport/create',
    method: 'get',
    params: query
  })
}

//生成临时报告
export function createTemp(data) {
  return request2({
    url: '/query/checkQuery/createTemp',
    method: 'post',
    params: data
  })
}