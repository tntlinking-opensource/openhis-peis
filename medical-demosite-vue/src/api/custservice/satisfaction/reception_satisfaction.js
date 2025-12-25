//  开发人：麦沃德科技矢北
import request from '@/utils/request'

// 分页查询体检满意度列表
export function listTestSatisfaction(query) {
  return request({
    url: '/satisfaction/page',
    method: 'get',
    params: query
  })
}
// 详情
  export function getDetail(id)
  {
    return request({
      url:'/satisfaction/bmyHfQt/'+id,
      method:'get',
    })
  }
  //保存 
  export function saveData(data)
  {
    return request({
      url:'/satisfaction/saveOrUpdateQt',
      method:'post',
      data:data,
    })
  }
