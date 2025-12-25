// 体检团体分布情况 开发人:麦沃德科技清风
import request from '@/utils/request'

//分页
export function page(query){
  return request({
    url:"/statistics/groupDistribute/page",
    method:"get",
    params:query
  })
}