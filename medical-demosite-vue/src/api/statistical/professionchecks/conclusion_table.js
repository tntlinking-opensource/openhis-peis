// 职业健康检查结果结论附表 开发人:麦沃德科技清风

import request from '@/utils/request'

//导出
export function page(query){
  return  request({
    url:"/statistics/positiveQuery/page",
    method: 'get',
    params:query
  })
}

//分公司
export function getBranchData(){
  return request({
    url: "/sell/sellDate/getBranchData",
    method: 'get'
  })
}

//单位
export function getAllOrg(){
  return request({
    url: "/statistics/positiveQuery/getAllOrg",
    method: 'get'
  })
}

//检查结论
export function getJcjlData(){
  return request({
    url:"/zyVsSummary/getJcjlData",
    method:'get'
  })
}

//导出
export function exportTable(query){
  return request({
    url:"/statistics/positiveQuery/export",
    method: 'post',
    data:query
  })
}