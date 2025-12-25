// 公共池  开发人：麦沃德科技 予安/矢北
import request from '@/utils/request'

// 查询公共池列表
export function listPool(query) {
  return request({
    url: '/crm/clientcommon/list',
    method: 'get',
    params: query
  })
}
//详情
 export  function getDetail(id){
  return request({
    url:'/crm/clientcommon/view/'+id,
    method:'get',

  })
 }

// 添加公共池
export function AddPool(data) {
  return request({
    url: '/crm/clientcommon',
    method: 'post',
    data: data
  })
}

// 删除公共池
export function delPool(id) {
  return request({
    url: '/crm/clientcommon/remove/' + id,
    method: 'delete'
  })
}

// 查询公共池详细
export function getPoolDetails(id) {
  return request({
    url: '/crm/clientcommon/view/' + id,
    method: 'get'
  })
}

// 修改公共池
export function updatePool(data) {
  return request({
    url: '/crm/clientcommon/edit',
    method: 'put',
    data
  })
}

// 判断领取次数为3或被强制分配
export function isLqAndFp(ids) {
  return request({
    url: '/crm/clientcommon/isLqAndFp',
    method: 'get',
    params: ids
  })
}

// 判断是否领取过
export function isReceive(ids) {
  return request({
    url: '/crm/clientcommon/isReceive',
    method: 'get',
    params: ids
  })
}

// 获取领取人员的信息
export function getLqryData(query) {
  return request({
    url: '/crm/clientcommon/getLqryData',
    method: 'get',
    params: query
  })
}

// 进行客户领取
export function receive(ids) {
  return request({
    url: '/crm/clientcommon/receive',
    method: 'get',
    params: ids
  })
}

// 判断是否领取过
export function isLeader() {
  return request({
    url: '/crm/clientcommon/isLeader',
    method: 'get',
  })
}
// 领导释放
export function leaderRelease(query) {
  return request({
    url: '/crm/clientcommon/release',
    method: 'put',
    params: query
  })
}

//获取列表
export function getSaleList(saleName)
{
  return request({
    url:'/crm/clientcommon/getXsryData',
    method:'get',
    params:saleName
  })
}
// 判断是否存在领取过的记录
export function isDistribution(query){
  return request({
    url:'/crm/clientcommon/distribution',
    method:'put',
    params:query
  })
}
// 判断是否存在分配过的记录
export function isAllocation(query){
  return request({
    url:'/crm/clientcommon/isAllocation',
    method:'put',
    params:query
  })
}
// 进行保存
 export function  saveData(query){
  return request({
    url:'/crm/clientcommon/allocation',
    method:'put',
    params:query
  })
 }