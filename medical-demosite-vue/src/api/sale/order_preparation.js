// 线上备单 开发人：麦沃德科技 清风
import request from '@/utils/request'

//根据id查订单表详情
export function editOrder(query){
  return request({
    url: '/sell/itemListOnline/edit',
    method: "get",
    params: query
  })
}

//获取体检团体分组信息
export function getGroupDataOrder(query){
  return request({
    url: '/sell/itemListOnline/getGroupData',
    method: "get",
    params: query
  })
}

//获取分组下相对应的人员信息
export function getPatientDataOrder(query){
  return request({
    url: '/sell/itemListOnline/getPatientData',
    method: "get",
    params: query
  })
}

//分页查询
export function pageOrder(query){
  return request({
    url: '/sell/itemListOnline/page',
    method: "get",
    params: query
  })
}

//保存右下
export function saveOrUpdatePatientBcOrder(query){
  return request({
    url: '/sell/itemListOnline/saveOrUpdatePatientBc',
    method: "post",
    data: query
  })
}

//保存左下
export function saveOrUpdateGroupOrder(query){
  return request({
    url: '/sell/itemListOnline/saveOrUpdateGroup',
    method: "post",
    data: query
  })
}

//收款方式下拉
export function getPayWayDataOrder(query){
  return request({
    url: '/dictpayway/getPayWayData',
    method: "get",
    params: query
  })
}

//获取套餐分中心
export function getBranchDataOrder(query){
  return request({
    url: '/sell/itemListOnline/getBranchData',
    method: "get",
    params: query
  })
}

//获取婚姻下拉
export function getMarriageDataOrder(query){
  return request({
    url: '/sell/itemListOnline/getMarriageData',
    method: "get",
    params: query
  })
}

//获取民族下拉
export function getNationDataOrder(query){
  return request({
    url: '/sell/itemListOnline/getNationData',
    method: "get",
    params: query
  })
}

//清除
export function removeAllOrder(query){
  return request({
    url: '/sell/itemListOnline/removeAll',
    method: "delete",
    params: query
  })
}

//备单确认
export function groupConfirmOrder(query){
  return request({
    url: '/sell/itemListOnline/groupConfirm',
    method: "put",
    params: query
  })
}

//预登记
export function saveOrUpdatePatientOrder(query){
  return request({
    url: '/sell/itemListOnline/saveOrUpdatePatient',
    method: "post",
    data: query
  })
}

// 更改已备单
export function markTbzt(params){
  return request({
    url: '/sell/itemListOnline/markTbzt',
    method: "post",
    params
  })
}

// 更改订单结束
export function finishOrder(params){
  return request({
    url: '/sell/itemListOnline/finishOrder',
    method: "put",
    params
  })
}
