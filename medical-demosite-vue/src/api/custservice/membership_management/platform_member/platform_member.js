// 平台会员 开发人：麦沃德科技暴雨
import request from '@/utils/request'


// 沟通记录列表
export function getListData(query) {
  return request({
    url: '/member/online/getMemberListData',
    method: 'get',
    params: query
  })
}
