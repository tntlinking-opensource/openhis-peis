// 在检人员信息  开发人：麦沃德科技 矢北
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/query/inspecting/loadExamData',
    method: 'get',
    params
  })
}


//  详情信息
export function getFormData(id) {
  return request({
    url: '/query/inspecting/loadForm/'+id,
    method: 'get',
    
  })
}
// 获取婚姻状况
export function getMarriageData() {
  return request({
    url: '/pacs/preregistration/getMarriageData',
    method: 'get',
  })
}