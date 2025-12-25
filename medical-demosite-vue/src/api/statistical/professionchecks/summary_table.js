// 职业健康检查结果汇总表(按单位) 开发人:麦沃德科技清风
import request from '@/utils/request'

//分页
export function page(query){
  return  request({
    url:"/statistics/componyQuery/page",
    method: 'get',
    params:query
  })
}

//导出
export function exportComponyQuery(query){
  return request({
    url:"/statistics/componyQuery/exportComponyQuery",
    method: 'post',
    data:query
  })
}

//获取分中心
export function getBranchData(){
  return  request({
    url:"/sell/monthtarget/getBranchData",
    method: 'get',
  })
}