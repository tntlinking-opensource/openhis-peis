// 家庭卡消费记录 开发人：麦沃德科技暴雨
import request from '@/utils/request'

//分页查询
export function getListData(query) {
  return request({
    url: '/member/familyCardCharge/page',
    method: 'get',
    params: query
  })
}


