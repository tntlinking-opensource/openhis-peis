//  开发人：麦沃德科技矢北
import request from '@/utils/request'

//分页查询
export function listReturnSatisfaction(query)  
{
  return request(
    {
      url:'/member/firstInterview/page',
      method:'get',
      params:query
    }
  )
}
//不满意列表
 export function exportUnSatisfactionList(query)
 {
    return request ({
      url:'/member/firstInterview/bmyHf',
      method:'get',
      params:query
    })
 }
 //保存或者更新
  export function addList(data)
  {
    return request({
      url:'/member/firstInterview',
      method:'post',
      data:data
    })
  }
 // 满意数据
 export function getSatisfactionList(query)
 {
  return request ({
    url:'/member/firstInterview/getFcmyListData',
    method:'get',
    params:query
  })
 }
