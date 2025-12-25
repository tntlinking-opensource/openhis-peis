// 医生工作量  开发人:麦沃德科技清风
import request from '@/utils/request'

//检验科科室医生工作量统计
export function analyseTest(query){
  return request({
    url:"/statistics/divisionDoctor/analyseTest",
    method:"get",
    params:query
  })
}

//检验科科室医生工作量统计总计
export function analyseTestTotal(query){
  return request({
    url:"/statistics/divisionDoctor/analyseTestTotal",
    method:"get",
    params:query
  })
}

//获取分中心
export function getBranchData(){
  return request({
    url:"/sell/monthtarget/getBranchData",
    method:"get",
  })
}

//pacs科室医生工作量统计
export function analysePacs(query){
  return request({
    url:"/statistics/divisionDoctor/analysePacs",
    method:"get",
    params:query
  })
}

//pacs科室医生工作量统计总计
export function analysePacsTotal(query){
  return request({
    url:"/statistics/divisionDoctor/analysePacsTotal",
    method:"get",
    params:query
  })
}

//其他科室医生工作量统计
export function analyse(query){
  return request({
    url:"/statistics/divisionDoctor/analyse",
    method:"get",
    params:query
  })
}

//其他科室医生工作量统计总计
export function analyseTotal(query){
  return request({
    url:"/statistics/divisionDoctor/analyseTotal",
    method:"get",
    params:query
  })
}