// 每日体检量统计 开发人麦沃德科技 清风
import request from "@/utils/request"

//分页
export function page(params) {
  return request({
    url: "/statistics/everyPhysical/page",
    method: "get",
    params
  })
}

//导出
export function exportTable(query) {
  return request({
    url: "/statistics/everyPhysical/export",
    method: "post",
    data: query
  })
}

//总计
export function total(params) {
  return request({
    url: "/statistics/everyPhysical/total",
    method: "get",
    params
  })
}