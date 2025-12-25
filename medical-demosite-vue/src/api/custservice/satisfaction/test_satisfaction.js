//  开发人：麦沃德科技矢北
import request from '@/utils/request'


// 分页查询体检满意度列表
export function listTestSatisfaction(query) {
  return request({
    url: '/member/appSatisfactionLevel/page',  
    // url: '/addSatisficing/page',
    method: 'get',
    params: query
  })
}
//  查看详情
export function getDetailData(id){
  return request({
    url:'/member/appSatisfactionLevel/details'+id,
    method:'get',
  })
}
//  详情保存
export function saveOrUpdate(data)
{
  return request({
    url:'/member/appSatisfactionLevel/update',
    method:'PUT',
    data:data
  })
}
