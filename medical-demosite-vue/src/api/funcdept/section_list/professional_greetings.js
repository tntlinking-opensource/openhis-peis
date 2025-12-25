// 问诊检查  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 获取列队信息
export function getRankData(query) {
  return request({
    url: '/abteilung/division/getPatientData',
    method: 'get',
    params: query
  })
}

// 获取饮酒种类下拉数据
export function getDrinkType() {
  return request({
    url: '/abteilung/division/getDrinkTypeShowData',
    method: 'get',
  })
}

// 问诊检查审核
export function saveOrUpdateInquiry(query) {
  return request({
    url: '/abteilung/division/commonjobinquirySave',
    method: 'post',
    data: query
  })
}

// 职业病史列表数据
export function getZybsData(query) {
  return request({
    url: '/abteilung/division/getZybsData',
    method: 'get',
    params: query
  })
}

// 职业史列表数据
export function getZysData(query) {
  return request({
    url: '/abteilung/division/getZysData',
    method: 'get',
    params: query
  })
}

// 家族病史数据
export function getJzbData(query) {
  return request({
    url: '/abteilung/division/getJzbData',
    method: 'get',
    params: query
  })
}

// 症状数据
export function getSymptom(query) {
  return request({
    url: '/occupationSymptom/getShowData',
    method: 'get',
    params: query
  })
}

// 上传
export function uploadFile(data) {
  return request({
    url: '/common/upload',
    method: 'post',
    data: data
  })
}