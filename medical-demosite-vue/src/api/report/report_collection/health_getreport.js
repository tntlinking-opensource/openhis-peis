// 健康报告领取  开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/report/healthGetReport/page',
    method: 'get',
    params: query
  })
}

// 搜索输入码
export function getSousData(query) {
  return request({
    url: '/report/healthGetReport/searchCode',
    method: 'get',
    params: query
  })
}

// 领取报告
export function getReport(query) {
  return request({
    url: '/report/healthGetReport/receive',
    method: 'PUT',
    params: query
  })
}

// 反领取
export function getUnnotice(id) {
  return request({
    url: '/report/healthGetReport/unNotice?ids='+ id,
    method: 'PUT'
  })
}

// 获取发放方式
export function getBoxData() {
  return request({
    url: `notificationmethod/getBoxData`,
    method: 'GET'
  })
}

