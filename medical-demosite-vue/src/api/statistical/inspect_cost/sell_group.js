// 体检费用统计-团检费用统计 开发人:麦沃德科技清风
import request from '@/utils/request'

//分页查询
export function page(query){
  return request({
    url:"/statistics/personalTotal/page",
    method:"get",
    params:query
  })
}

//导出
export function exportTable(query){
  return request({
    url:"/statistics/personalTotal/export",
    method:"post",
    data:query
  })
}

//获取右表数据
export function getTotalList(query){
  return request({
    url:"/statistics/personalTotal/getTotalList",
    method:"get",
    params:query
  })
}

//获取分中心
export function getBranchData(){
  return request({
    url:"/sell/monthtarget/getBranchData",
    method:"get"
  })
}