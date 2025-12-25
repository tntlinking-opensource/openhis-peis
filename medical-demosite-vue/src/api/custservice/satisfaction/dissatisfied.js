//  开发人：麦沃德科技矢北
import request from '@/utils/request'

// 分页查询不满意客户列表
 
export function dissatisfiedList(query)
{
  return request({
    url: '/member/secondInterview/page',
    method: 'get',
    params: query
  })
}
// 获取不满意客户详情 
export function getDetailData(id)
{
  return request ({
    url:'/member/secondInterview/'+id,
    method:'get',
  })
} 
// 保存不满意客户详情 
export function addList(data)
{
  return request ({
    url:'/member/secondInterview',
    method:'post',
    data:data
  })
} 
 // 满意数据
 export function getSatisfactionList(query)
 {
  return request ({
    url:'/member/secondInterview/getFcmyListData',
    method:'get',
    params:query
  })
 }