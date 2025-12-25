// 职业健康检查结果汇总表(按危害因素) 开发人:麦沃德科技清风
import request from '@/utils/request'

//分页
export function getListData(query){
  return  request({
    url:"/statistics/harmQuery/getListData",
    method: 'get',
    params:query
  })
}