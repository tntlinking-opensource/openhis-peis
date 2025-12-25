//  体检结账单  开发人：麦沃德科技 清风 
import request from '@/utils/request'

// 获取所有分中心
export function fzxApi(params){
  return request({
    url: '/finance/physicalCheckout/fzx',
    method: 'get',
    params
  })
}

//左上
export function analyse(query){
  return request({
    url: '/finance/physicalCheckout/analyse',
    method: 'get',
    params: query
  })
}

//右上
export function getGroupData(query){
  return request({
    url: '/finance/physicalCheckout/getGroupData',
    method: 'get',
    params: query
  })
}

//右上-禁检/反禁检
export function updateGroupLimit(query){
  return request({
    url: '/finance/physicalCheckout/updateGroupLimit',
    method: 'PUT',
    params: query
  })
}

//左中  体检人数据
export function getAccountsInfoData(query){
  return request({
    url: '/finance/physicalCheckout/getAccountsInfoData',
    method: 'get',
    params: query
  })
}

//左中 禁检/反禁检
export function savePausedStatus(query){
  return request({
    url: '/finance/physicalCheckout/savePausedStatus',
    method: 'PUT',
    params: query
  })
}

//左中 未检-禁检
export function unSavePausedStatus(query){
  return request({
    url: '/finance/physicalCheckout/unSavePausedStatus',
    method: 'PUT',
    params: query
  })
}

//左中 已结账
export function finishStatus(query){
  return request({
    url: '/finance/physicalCheckout/finishStatus',
    method: 'PUT',
    params: query
  })
}

//左中 反结账
export function unfinishStatus(query){
  return request({
    url: '/finance/physicalCheckout/unfinishStatus',
    method: 'PUT',
    params: query
  })
}

//左中 导出体检人员
export function exportAccountsInfoData(query){
  return request({
    url: '/finance/physicalCheckout/exportAccountsInfoData',
    method: 'post',
    params: query
  })
}

//右中 右中-项目列表数据
export function getItemListData(query){
  return request({
    url: "/finance/physicalCheckout/getItemListData",
    method: 'get',
    params: query
  })
}

//右中 获取全部科室
export function getAllks(query){
  return request({
    url:'/SignInInspection/getAllks',
    method: 'get',
    params:query
  })
}
//右中 付款方式
export function getPayWayData(query){
  return request({
    url:'/dictpayway/getPayWayData',
    method: 'get',
    params:query
  })
}

//右中 导出收费项目
export function exportItems(query){
  return request({
    url: '/finance/physicalCheckout/exportItems',
    method: 'post',
    data:query
  })
}


//右下 收费信息
export function getChargeData(query){
  return request({
    url:'/finance/physicalCheckout/getChargeData',
    method: 'get',
    params: query
  })
}

// 同步已检
export function synchronizedChecked(params){
  return request({
    url:'/finance/physicalCheckout/synchronizedChecked',
    method: 'get',
    params
  })
}

// 获取合计数据
export function getAccountsTotalDto(params){
  return request({
    url:'/finance/physicalCheckout/getAccountsTotalDto',
    method: 'get',
    params
  })
}