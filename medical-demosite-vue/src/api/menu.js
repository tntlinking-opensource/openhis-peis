import request from '@/utils/request'

// 获取路由
export const getRouters = () => {
  return request({
    url: '/getRouters',
    method: 'get'
  })
}
// 获取是否过期
export const isExpired = () => {
  return request({
    url: '/system/auth/isExpired',
    method: 'post'
  })
}