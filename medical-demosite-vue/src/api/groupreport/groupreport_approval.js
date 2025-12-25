// 团检报告审批  开发人：麦沃德科技半夏
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 团检报告审批分页查询
export function getListData(query) {
  return request({
    url: '/group/report_audit/loadAuditBallCheckData',
    method: 'get',
    params: query
  })
}

// 团检报告审批分页查询
export function getViewData(id) {
  return request({
    url: '/group/groupHealth/loadSamplePersonViewData/' + id,
    method: 'get',
  })
}

// 团检报告审核
export function reportFinish(id, spyj, spjg) {
  return request({
    url: `/group/report_audit/finish/${id}/${spyj}/${spjg}`,
    method: 'get',
  })
}

// 团检报告反审核
export function reportUnFinish(id) {
  return request({
    url: "/group/report_audit/unfinish/" + id,
    method: 'get',
  })
}

// 团检报告提交
export function reportCommit(id) {
  return request({
    url: "/group/report_audit/commit/" + id,
    method: 'get',
  })
}

// 团检报告撤回
export function reportRecall(id) {
  return request({
    url: "/group/report_audit/recall/" + id,
    method: 'get',
  })
}

// 团检报告修正
export function reportUndo(id) {
  return request({
    url: "/group/report_audit/undo/" + id,
    method: 'get',
  })
}

// 生成样本分析
export function generateAnalyze(id, flag) {
  return request2({
    url: `/group/report_audit/generateAnalyse/${id}/${flag}`,
    method: 'get',
  })
}

// 主检审核
export function mainAudit(id) {
  return request({
    url: "/group/report_audit/mainAudit?id=" + id,
    method: 'put',
  })
}
