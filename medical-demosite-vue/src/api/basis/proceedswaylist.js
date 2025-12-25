import request from '@/utils/request'

// 查询JC支付方式列表
export function listDictpayway(query) {
  return request({
    url: '/dictpayway/getPayWayData',
    method: 'get',
    params: query
  })
}

// 查询JC支付方式详细
export function getDictpayway(id) {
  return request({
    url: '/dictpayway/' + id,
    method: 'get'
  })
}

// 新增JC支付方式
export function addDictpayway(data) {
  return request({
    url: '/dictpayway/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改JC支付方式
export function updateDictpayway(data) {
  return request({
    url: '/dictpayway/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 删除JC支付方式
export function delDictpayway(id) {
  return request({
    url: '/dictpayway/remove/' + id,
    method: 'delete'
  })
}
//更新金蝶
  export function upDataJD(){
  return request({
    url: '/finance/kingdee/upgradeKingdeePayWay' ,
    method: 'post',
  })
}
