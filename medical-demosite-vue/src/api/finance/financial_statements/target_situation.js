//开发人：麦沃德矢北
import request from '@/utils/request' 
export function getList(query){
  return request(
    {
      url:'/finance/indexSituation/getData',
      method:'get',
      params:query,
    }
  )
}