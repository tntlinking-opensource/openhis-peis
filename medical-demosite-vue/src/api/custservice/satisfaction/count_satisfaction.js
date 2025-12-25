//  开发人：麦沃德科技矢北
import request from '@/utils/request'
export function countList(query)
{
  return request(
    {
      url:'/totalCount/page',
      method:'get',
      params:query
    }
  )
}   