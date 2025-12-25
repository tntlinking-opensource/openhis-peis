// 每日体检项目统计 开发人:麦沃德科技清风
import request from '@/utils/request'

//分页查询
export function page(query){
  return request({
    url:"/query/everyProject/page",
    method:"get",
    params:query
  })
}

//右表查询
export function analyseInfo(query){
  return request({
    url:"/query/everyProject/analyseInfo",
    method:"get",
    params:query
  })
}

//获取科室
export function ksBasic(){
  return request({
    url:"/query/everyProject/ks",
    method: "get"
  })
}

//导出人员清单
export function exportInfo(query){
  return request({
    url:"/query/everyProject/exportInfo",
    method: "post",
    data:query
  })
}

//导出统计
export function exportTotal(query){
  return request({
    url:"/query/everyProject/export",
    method: "post",
    data:query
  })
}