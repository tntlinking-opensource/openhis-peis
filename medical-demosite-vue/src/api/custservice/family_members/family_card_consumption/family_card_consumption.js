// 家庭卡消费 开发人：麦沃德科技暴雨
import request from '@/utils/request'

//分页查询
export function getListData(query) {
  return request({
    url: '/member/familyCard/page',
    method: 'get',
    params: query
  })
}

//家庭卡消费
export function saveConsumex(query) {
  return request({
    url: '/member/familyCard/saveConsumex',
    method: 'POST',
    data: query
  })
}

// 获取登录用户姓名
export function getLoginName() {
  return request({
    url: '/total/health/getLoginName ',
    method: 'GET'
  })
}
