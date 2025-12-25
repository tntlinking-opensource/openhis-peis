//销售个检费用 开发人 麦沃德科技清风
import request from '@/utils/request'

//分页查询
export function page(query){
  return request({
    url:"/finance/individualStatistics/page",
    method:"get",
    params:query
  })
}

//获取关联的数据
export function getListData(query){
  return request({
    url:"/finance/individualStatistics/getListData",
    method:"get",
    params:query
  })
}

//按天导出
export function exportByDay(query){
  return request({
    url:"/finance/individualStatistics/exportByDay",
    method:"post",
    params:query
  })
}

//导出
export function exportTable(query){
  return request({
    url:"/finance/individualStatistics/export",
    method:"post",
    params:query
  })
}