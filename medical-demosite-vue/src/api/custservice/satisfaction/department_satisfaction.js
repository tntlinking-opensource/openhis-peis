//  开发人：麦沃德科技矢北
import request from '@/utils/request'
export function listDepartmentSatisfaction(query)
{
  return request(
    {
      url:'/satisfaction/getListData',
      method:'get',
      params:query
    }
  )
}  
//取消不满意
export function cancelDissatisfactory (ids)
{
  return request(
    {
      url:'/satisfaction/cancelDissat/'+ids,
      method:'put',
    }
  )
} 
//详情           
export function detailData(id)
{
  return request({
   
      url:'/satisfaction/bmyHf/'+id,
      method:'get',
   
  })
}  
 //保存
export function saveData(data)
{
  return request({
    url:'/satisfaction/saveOrUpdateQt',
    method:'post',
    data:data
  })
}                                                                                                                        