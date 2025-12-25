// 团检报告审批  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 人员构成
export function getPersonnelList(id) {
  return request({
    url: '/group/report_audit/peopleanalyze_zy/' + id,
    method: 'get'
  })
}
// 年龄分布
export function getAgeList(id) {
  return request({
    url: '/group/report_audit/ageanalyze_zy/' + id,
    method: 'get'
  })
}
// 日期分布
export function getDateList(id) {
  return request({
    url: '/group/report_audit/dateanalyze_zy/' + id,
    method: 'get'
  })
}
// 项目参检
export function getProjectList(id, groupId) {
  return request({
    url: `/group/report_audit/examanalyze/${id}/${groupId}`,
    method: 'get'
  })
}
// 检出统计
export function getDetectionList(id, groupId, params) {
  return request({
    url: `/group/report_audit/checkanalyze/${id}/${groupId}`,
    method: 'get',
    params
  })
}
// 异常前十--检出统计
export function getAnalyzeList(id, groupId, params) {
  return request({
    url: `/group/report_audit/loadcheckanalyze/${id}/${groupId}`,
    method: 'get',
    params
  })
}
// 完成情况
export function getFinishList(id) {
  return request({
    url: '/group/report_audit/finishanalyze_zy/' + id,
    method: 'get'
  })
}
// 团体小结
export function getGroupList(id, groupId, params) {
  return request({
    url: `/group/report_audit/groupanalyze/${id}/${groupId}`,
    method: 'get',
    params
  })
}
// 阳性结果
export function getPositiveList(id, groupId, params) {
  return request({
    url: `/group/report_audit/positiveanalyze/${id}/${groupId}`,
    method: 'get',
    params
  })
}
// 危害因素人员分布
export function getHazardList(id) {
  return request({
    url: '/group/report_audit/examanalyze_zy/' + id,
    method: 'get'
  })
}
// 疑似职业病人员一览表/职业禁忌证人员一览表/需复查人员一览表/其他疾病异常结果人员一览表/未见异常人员一览表
export function getBrowseList(id, flag) {
  return request({
    url: `/group/report_audit/getPersonnelData/${id}/${flag}`,
    method: 'get'
  })
}
// 复查情况
export function getReviewList(id) {
  return request({
    url: '/group/report_audit/getReviewData/' + id,
    method: 'get'
  })
}

// 保存所有图片(健康
export function saveAllImgJk(data) {
  return request({
    url: '/group/report_audit/saveAllImgJk',
    method: 'post',
    data
  })
}