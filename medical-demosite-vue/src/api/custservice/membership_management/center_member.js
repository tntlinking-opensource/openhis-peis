// 中心会员 开发人：麦沃德科技矢北
import request from '@/utils/request'

//分页查询
export function getListData(query) {
  return request({
    url: '/member/member/page',
    method: 'get',
    params: query
  })
}
//详情
export function getDetail(query) {
  return request({
    url: '/member/member/edit',
    method: 'get',
    params: query
  })
}
//保存
export function saveData(data) {
  return request({
    url: '/member/member/saveOrUpdate',
    method: 'post',
    data: data
  })
}
// 会员升级
export function memberUp(query) {
  return request({
    url: '/member/member/up',
    method: 'put',
    params: query
  })
}
//充值积分详情
export function rechargeDetail(query) {
  return request({
    url: '/member/member/recharge',
    method: 'get',
    params: query
  })
}
// 获取会员卡号
export function getIsMember(query) {
  return request({
    url: '/member/member/getMemberData',
    method: 'get',
    params: query
  })
}
//积分充值
export function addJf(data) {
  return request({
    url: '/member/member/addJf',
    method: 'POST',
    data: data
  })
}
//挂失数据详情
export function loseData(query) {
  return request({
    url: '/member/member/report',
    method: 'get',
    params: query
  })
}
//挂失数据保存
export function saveMend(data) {
  return request({
    url: '/member/member/saveReport',
    method: 'post',
    data: data
  })
}
//获取分中心数据
export function branchData(params) {
  return request({
    url: '/sell/createorder/getBranchData',
    method: 'get',
    params,
  })
}

// 获取全部会员类型
export function userLevelList() {
  return request({
    url: '/member/userLevel/list',
    method: 'get',
  })
}
