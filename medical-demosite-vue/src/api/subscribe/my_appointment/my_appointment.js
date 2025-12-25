// 预约列表  开发人：麦沃德科技 暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/reservation/pf/page',
    method: 'get',
    params: query
  })
}

// 新增预约
export function addListData(query) {
  return request({
    url: '/reservation/pf',
    method: 'POST',
    data: query
  })
}

// 获取预约时段列表
export function getLists(query) {
  return request({
    url: 'open/api/oldSystem/reservation/list',
    headers: {
      isToken: false,
    },
    method: 'post',
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

// 改变状态
export function setstatus(query) {
  return request({
    url: '/reservation/pf',
    method: 'PUT',
    data: query
  })
}

// 批量修改
export function plupadata(query) {
  return request({
    url: '/reservation/pf/batchModify',
    method: 'PUT',
    data: query
  })
}

// 批量删除
export function delData(ids) {
  return request({
    url: '/reservation/pf/' + ids,
    method: 'DELETE',
  })
}

// 查看详情
export function ck(reservationNo) {
  return request({
    url: '/reservation/pf/' + reservationNo,
    method: 'GET',
  })
}

// 获取预约详情
export function getReservation(params) {
  return request({
    url: '/open/api/oldSystem/reservation/getReservation',
    headers: {
      isToken: false,
    },
    method: 'get',
    params
  })
}
// 新增或修改预约
export function saOrUp(data) {
  return request({
    url: '/open/api/oldSystem/reservation/saOrUp',
    headers: {
      isToken: false,
    },
    method: 'post',
    data
  })
}

// 取消预约
export function cancelApi(data) {
  return request({
    url: '/open/api/oldSystem/reservation/cancel',
    headers: {
      isToken: false,
    },
    method: 'post',
    data
  })
}