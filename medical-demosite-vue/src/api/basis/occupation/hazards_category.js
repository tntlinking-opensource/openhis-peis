// 职业危害因素分类 开发人：麦沃德科技 予安
import request from '@/utils/request'

// 查询职业危害因素分类列表
export function listClass(query) {
  return request({
    url: 'zyHarmClass/getHarmclassData',
    method: 'get',
    params: query
  })
}

// 查询职业危害因素分类详细
export function getClass(id) {
  return request({
    url: '/zyHarmClass/' + id,
    method: 'get'
  })
}

// 新增职业危害因素分类
export function addClass(data) {
  return request({
    url: '/zyHarmClass/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改职业危害因素分类
export function updateClass(data) {
  return request({
    url: '/zyHarmClass/edit',
    method: 'put',
    data: data
  })
}

// 删除职业危害因素分类
export function delClass(id) {
  return request({
    url: '/zyHarmClass/' + id,
    method: 'delete',
    // data: {
    //   ids: id
    // }
  })
}
