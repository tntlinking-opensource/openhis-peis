// 会员积分 开发人：麦沃德科技暴雨
import request from '@/utils/request'


// 会员积分列表
export function getListData(query) {
  return request({
    url: '/member/integral/page',
    method: 'get',
    params: query
  })
}
