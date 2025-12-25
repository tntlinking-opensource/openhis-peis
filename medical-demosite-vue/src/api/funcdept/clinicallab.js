// 加项处理  开发人：麦沃德科技矢北
import request from '@/utils/request'

export function getListData(query){
  return request({
    url:'/abteilung/additionProcessing/page',
    method:'get',
    params:query,
  })
}
// 处理
export function handleDetail(data){
  return request({
    url:'/abteilung/additionProcessing/saveBatch',
    method:'put',
    params:data,
  })
}