// 预检跟踪 麦沃德科技 暴雨
import request from '@/utils/request'

export function getList(query) {
  return request({
    url: '/sell/teamremind/page',
    method: 'get',
    params: query
  })
}

//客户预检跟踪、设为已检
export function setExamed(id) {
  return request({
    url: '/sell/teamremind/setExamed/'+ id,
    method: 'get',
  })
}

//保存
export function saveKhgtData(query) {
  return request({
    url: '/sell/teamremind/saveKhgtData',
    method: 'POST',
    data: query
  })
}
