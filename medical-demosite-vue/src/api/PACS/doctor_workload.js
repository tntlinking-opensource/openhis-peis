// PACS医生工作量统计  开发人：麦沃德科技暴雨
import request from '@/utils/request'

//分页查询
export function page(query){
  return request({
    url:"/statistics/pacsDoctorQuery/getWorkData",
    method:"get",
    params:query
  })
}

// 折线图--分页查询
export function getecharts(query){
  return request({
    url:"/statistics/pacsDoctorQuery/getTableData",
    method:"get",
    params:query
  })
}

//右表查询
export function analyseInfo(query){
  return request({
    url:"/statistics/pacsDoctorQuery/analysePacsTotal",
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