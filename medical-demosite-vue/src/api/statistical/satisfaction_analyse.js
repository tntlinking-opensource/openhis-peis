// 科室满意度排名 开发人 麦沃德科技清风
import request from "@/utils/request"

//分页查询
export function page(query){
  return request({
    url:"/statistics/satisfactionRank/page",
    method: "get",
    params:query
  })
}