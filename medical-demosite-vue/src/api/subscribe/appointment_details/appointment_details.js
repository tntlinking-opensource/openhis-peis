// 预约设置  开发人：麦沃德科技 暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/reservation/Setting/page',
    method: 'get',
    params: query
  })
}

// 添加预约
export function addListData(query) {
  return request({
    url: '/reservation/Setting',
    method: 'POST',
    data: query
  })
}

// 详情
export function getData(id) {
  return request({
    url: '/reservation/Setting/' + id,
    method: 'GET',
  })
}

// 删除
export function deleteData(id) {
  return request({
    url: '/reservation/Setting/' + id,
    method: 'DELETE',
  })
}

// 更新预约
export function defaultData(query) {
  return request({
    url: '/reservation/Setting',
    method: 'PUT',
    data: query
  })
}

// 获取全部会员类型
export function getListUserData() {
  return request({
    url: '/member/userLevel/list',
    method: 'get',
  })
}

// 获取分中心数据
export function getBranchData() {
  return request({
    url: '/sell/sellDate/getBranchData',
    method: 'get',
  })
}

// 分页查询预约日期
export function queryReservationDate(params) {
  return request({
    url: '/reservation/Setting/queryReservationDate',
    method: 'get',
    params
  })
}
// 分页查询预约时间
export function queryReservationTime(params) {
  return request({
    url: '/reservation/Setting/queryReservationTime',
    method: 'get',
    params
  })
}
// 批量设置
export function batchSettings(data) {
  return request({
    url: '/reservation/Setting/batchSettings',
    method: 'post',
    data
  })
}
// 批量关闭预约场次
export function batchClose(data) {
  return request({
    url: '/reservation/Setting/batchClose',
    method: 'post',
    data
  })
}
