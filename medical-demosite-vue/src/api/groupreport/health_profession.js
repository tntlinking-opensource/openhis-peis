// 健康团检样本  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 健康团检样本分页查询
export function getListData(query) {
  return request({
    url: '/group/groupHealth/loadBallCheckData',
    method: 'get',
    params: query
  })
}

// 联动团检报告订单数据
export function checkReportOrder(groupId) {
  return request({
    url: `/group/groupHealth/addBallCheckReportOrderData/${groupId}`,
    method: 'get',
    data: {
      groupId,
    }
  })
}

// 根据订单号判断体检团体类型
export function checkType(orderId) {
  return request({
    url: `/group/groupHealth/addBallCheckReportData/` + orderId,
    method: 'get',
    params: {
      orderId,
    }
  })
}

// 增加-编辑接口
export function addOrUpdate(query) {
  return request({
    url: `/group/groupHealth`,
    method: 'post',
    data: query
  })
}

// 保存接口
export function saveData(query) {
  return request({
    url: `/group/groupHealth/saveData`,
    method: 'POST',
    data: query
  })
}

// 删除接口
export function removeData(ids) {
  return request({
    url: `/group/groupHealth/remove/` + ids,
    method: 'get',
    data: {
      ids,
    }
  })
}

// 移除接口
export function removePersons(ids) {
  return request({
    url: `/group/groupHealth/removePersons/` + ids,
    method: 'DELETE',
    data: {
      ids,
    }
  })
}

// 详情接口
export function getDetails(id) {
  return request({
    url: `/group/groupHealth/` + id,
    method: 'get',
    data: {
      id,
    }
  })
}

// 加入人员接口
export function addPerson(id) {
  return request({
    url: `/group/groupHealth/addpeople/` + id,
    method: 'get',
    data: {
      id,
    }
  })
}

// 加载右侧人员信息
export function loadPersonData(item, pageInfo) {
  return request({
    url: `/group/groupHealth/loadSamplePersonData`,
    method: 'get',
    params: {
      current: pageInfo.current,
      size: pageInfo.size,
      patientcode: pageInfo.patientcode,
      groupId: item.groupId,
      orderId: item.orderId,
      reportId: item.reportId,
    }
  })
}
