// 团检加/弃项  开发人：麦沃德科技 予安
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 分页查询
export function getListData(query) {
  return request({
    url: '/reception/groupAdd/page',
    method: 'get',
    params: query
  })
}

// 禁检、反禁检操作
export function savePausedStatus(data) {
  return request({
    url: '/reception/groupAdd/savePausedStatus',
    method: 'POST',
    data
  })
}

// 获取右侧收费项目
export function getExamfeeByPatientCode(query) {
  return request({
    url: '/reception/groupAdd/getExamfeeByPatientCode',
    method: 'get',
    params: query
  })
}
// 右侧保存
export function saveOrUpdateItems2(data) {
  return request2({
    url: '/reception/groupAdd/saveOrUpdateItems2',
    method: 'POST',
    data
  })
}
// 右侧退项时团检退项匹配最小套餐
export function compareMinTcContent(data) {
  return request({
    url: '/reception/groupAdd/compareMinTcContent',
    method: 'POST',
    data
  })
}