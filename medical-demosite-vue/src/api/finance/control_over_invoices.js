//发票管理api-js 开发人：麦沃德科技清风
import request from '@/utils/request'

//分页查询
export function page(query){
  return request ({
    url:'/finance/invoiceRequest/page',
    method:'get',
    params:query
  })
}

//获取图表数据
export function getBarData(query){
  return request({
    url:'/finance/invoiceRequest/getBarData',
    method:'get',
    params:query
  })
}

//获取发票类型
export function getTypeData(){
  return request({
    url:'/finance/invoiceRequest/getTypeData',
    method:'get',
  })
}

//发票申请保存
export function saveOrUpdate(query){
  return request({
    url:'/finance/invoiceRequest/saveOrUpdate',
    method:'post',
    data:query
  })
}

//删除
export function deleteTable(query){
  return request({
    url:`/finance/invoiceRequest/${query}`,
    method:'delete',
  })
}

//详情
export function detailsTable(query){
  return request({
    url:`/finance/invoiceRequest/${query}`,
    method:'get',
  })
}

//审核
export function examine(query){
  return request({
    url:'/finance/invoiceRequest/examine',
    method:'put',
    params:query
  })
}

//反审核
export function unauditSave(query){
  return request({
    url:'/finance/invoiceRequest/unauditSave',
    method:'put',
    params:query
  })
}

//审核不通过
export function unapprove(query){
  return request({
    url:'/finance/invoiceRequest/unapprove',
    method:'put',
    params:query
  })
}

//出票信息保存
export function saveOrUpdatePrint(query){
  return request({
    url:'/finance/invoiceRequest/saveOrUpdatePrint',
    method:'post',
    data:query
  })
}

//换票申请保存
export function saveReturnApply(query){
  return request({
    url:'/finance/invoiceRequest/saveReturnApply',
    method:'post',
    data:query
  })
}

//换票审核
export function saveReturnAudit(query){
  return request({
    url:'/finance/invoiceRequest/saveReturnAudit',
    method:'post',
    data:query
  })
}

//换票撤回
export function saveReturnCancle(query){
  return request({
    url:'/finance/invoiceRequest/saveReturnCancle',
    method:'put',
    params:query
  })
}

//换票反审核
export function saveReturnUnaudit(query){
  return request({
    url:'/finance/invoiceRequest/saveReturnUnaudit',
    method:'post',
    data:query
  })
}

//换票
export function saveReturnConfirm(query){
  return request({
    url:'/finance/invoiceRequest/saveReturnConfirm',
    method:'post',
    data:query
  })
}