// 体检费用统计-收费明细 开发人:麦沃德科技清风
// 体检费用统计-费用合计 开发人:麦沃德科技清风
import request from '@/utils/request'

//收费明细--分页查询
export function page(query){
  return request({
    url:"/statistics/financeCount/page",
    method:"get",
    params:query
  })
}

//导出财务统计
export function exportTable(query){
  return request({
    url:"/statistics/financeCount/export",
    method:"post",
    data:query
  })
}

// 费用合计--分页查询
export function getTotalList(query){
  return request({
    url:"/statistics/financeCount/getTotalList",
    method:"get",
    params:query
  })
}

//导出财务收费汇总
export function totalExport(query){
  return request({
    url:"/statistics/financeCount/totalExport",
    method:"post",
    data:query
  })
}