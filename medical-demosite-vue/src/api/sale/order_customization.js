// 订单定制 开发人：麦沃德科技 予安
import request from '@/utils/request'

// 查询订单定制列表
export function listOrder(query) {
  return request({
    url: '/sell/createorder/page',
    method: 'get',
    params: query
  })
}
// 判断当前登录者是否为领导
export function isLeader(query) {
  return request({
    url: '/sell/createorder/isLeader',
    method: 'get',
  })
}

// 查询异常数据
export function dataEnList(query) {
  return request({
    url: '/sell/dataException/page',
    method: 'get',
    params: query
  })
}

// 是否需要选择审批人
export function needChoose(id,type) {
  return request({
    url: '/sell/createorder/needChoose?id=' + id + '&type=' + type,
    method: 'get',
  })
}

// 忽略异常数据
export function ignore(query) {
  return request({
    url: '/sell/dataException/ignore',
    method: 'post',
    params: {
      ids:query
    }
  })
}


// #region 添加或更新*****************
// 返回客户从未使用过的套餐和客户单位电话
export function getKhdwdhAndTcs(params) {
  return request({
    url: '/sell/createorder/getKhdwdhAndTcs',
    method: 'get',
    params
  })
}
// 获取分中心信息 
export function getfzxList() {
  return request({
    url: '/sell/createorder/getBranchData',
    method: 'get',
    params: {
      current: 1,
      size: 99999,
    }
  })
}
// 获取通知方式 
export function getOrderNotifycation() {
  return request({
    url: '/sell/createorder/getOrderNotifycationMethods',
    method: 'get',
  })
}
// 计划检期内已有检查数据
export function checkDate(query) {
  return request({
    url: '/sell/createorder/checkDate',
    method: 'get',
    params: query
  })
}
// 获取收费项目 
export function getcombo(tcId, tcstate) {
  return request({
    url: '/sell/createorder/getItemData',
    method: 'get',
    params: {
      current: 1,
      size: 99999,
      tcId,
      tcstate
    }
  })
}
// 保存或编辑订单数据
export function saveOrUpdateOrder(query) {
  return request({
    url: '/sell/createorder/saveOrUpdate',
    method: 'POST',
    data: query
  })
}
// 查询订单定制详情 
export function getOrderDetails(id) {
  return request({
    url: '/sell/createorder/' + id,
    method: 'get',
    params: {
      id,
    }
  })
}
// 查询订单定制编辑默认加载订单关联的套餐
export function getTjtcData(query) {
  return request({
    url: '/sell/createorder/getTjtcData',
    method: 'get',
    params: query
  })
}
// 查看订单定隐藏展示操作
export function showOrHide(query) {
  return request({
    url: '/sell/createorder/showOrHide',
    method: 'put',
    params: query
  })
}
// #endregion

// 编辑判断
export function isTjOrShtg(query) {
  return request({
    url: '/sell/createorder/isTjOrShtg',
    method: 'get',
    params: query
  })
}

// 删除判断
export function isRemove(query) {
  return request({
    url: '/sell/createorder/isRemove',
    method: 'get',
    params: query
  })
}
// 假删掉选择的订单
export function removeApi(ids) {
  return request({
    url: '/sell/createorder/remove/' + ids,
    method: 'DELETE',
    params: {
      ids
    }
  })
}

// 提交判断
export function isCommitAndSptg(query) {
  return request({
    url: '/sell/createorder/isCommitAndSptg',
    method: 'get',
    params: query
  })
}
// 提交订单
export function orderCommitApi(query) {
  return request({
    url: '/sell/createorder/commit',
    method: 'PUT',
    params: query
  })
}

// 撤回判断
export function isWithdrawApi(query) {
  return request({
    url: '/sell/createorder/isCh',
    method: 'get',
    params: query
  })
}
// 撤回订单
export function withdrawApi(ddid) {
  return request({
    url: '/sell/createorder/undo/' + ddid,
    method: 'PUT',
    params: {
      ddid
    }
  })
}

// 订单审核前验证订单是否为已提交
export function isCheckApi(query) {
  return request({
    url: '/sell/createorder/isCheck',
    method: 'get',
    params: query
  })
}
// 订单审核
export function checkOrder(query) {
  return request({
    url: '/sell/createorder/checkOrder',
    method: 'PUT',
    params: query
  })
}
// 获取审核订单下关联的套餐
export function viewCombo(query) {
  return request({
    url: '/sell/createorder/viewCombo',
    method: 'get',
    params: query
  })
}

// 订单反审核
export function undoOrder(query) {
  return request({
    url: '/sell/createorder/undoOrder',
    method: 'get',
    params: query
  })
}

// 订单总结详情
export function addWithOrder(query) {
  return request({
    url: '/sell/createorder/addWithOrder',
    method: 'get',
    params: query
  })
}
// 订单总结保存
export function saOrUpSummary(query) {
  return request({
    url: '/sell/createorder/saOrUpSummary',
    method: 'post',
    data: query
  })
}

// 变更提交
export function commitChange(query) {
  return request({
    url: '/sell/createorder/commitChange',
    method: 'PUT',
    params: query
  })
}
// 变更撤回
export function undoChange(ids) {
  return request({
    url: '/sell/createorder/undoChange',
    method: 'PUT',
    params: {
      ids
    }
  })
}
// 变更审批详情
export function approveChange(query) {
  return request({
    url: '/sell/createorder/approveChange',
    method: 'get',
    params: query
  })
}
// 变更反审批
export function unauditChange(query) {
  return request({
    url: '/sell/createorder/unauditChange',
    method: 'PUT',
    params: query
  })
}

// 材料通过
export function clpassOrUmpass(query) {
  return request({
    url: '/sell/createorder/clpassOrUmpass',
    method: 'PUT',
    params: query
  })
}
// 材料驳回
export function rejectSave(query) {
  return request({
    url: '/sell/createorder/rejectSave',
    method: 'PUT',
    params: query
  })
}

// 材料路径保存
export function saveClUrl(query) {
  return request({
    url: '/sell/createorder/saveClUrl',
    method: 'PUT',
    params: query
  })
}


// 修改发放方式-保存
export function saveInfo(query) {
  return request({
    url: '/sell/createorder/saveInfo',
    method: 'PUT',
    params: query
  })
}

// 获取前台须知
export function testQtxz(query) {
  return request({
    url: '/sell/createorder/testQtxz',
    method: 'get',
    params: query
  })
}
// 变更前台须知-保存
export function saveQtxz(query) {
  return request({
    url: '/sell/createorder/saveQtxz',
    method: 'post',
    data: query
  })
}

// 编辑开单助理数据
export function addKdzl(query) {
  return request({
    url: '/sell/createorder/addKdzl',
    method: 'get',
    params: query
  })
}
// 编辑开单助理-保存
export function saveKdzl(query) {
  return request({
    url: '/sell/createorder/saveKdzl',
    method: 'get',
    params: query
  })
}

// 获取审核订单下关联的套餐
export function getApproveTjtcData(params) {
  return request({
    url: '/sell/createorder/getApproveTjtcData',
    method: 'get',
    params
  })
}

//上传名单
export function upLoadList(data){
  return request({
    url:'/sell/createorder/saveUpload',
    method:'post',
    data:data
  })
}
//下载名单
export function downLoad (data){
  return request({
    url:'/sell/createorder/filesDownload',
    method:'post',
    data:data
  })
}

// 导入名单-列表数据
export function getImportData(params) {
  return request({
    url: '/sell/createorder/getListData',
    method: 'get',
    params
  })
}
// 导入名单-保存
export function saOrUpNameList(data) {
  return request({
    url: '/sell/createorder/saOrUpNameList',
    method: 'post',
    data
  })
}
// 导入名单-删除
export function removeAll(data) {
  return request({
    url: '/sell/createorder/removeAll',
    method: 'delete',
    params:data
  })
}
// 导入名单-获取体检者任务分组
export function getGroup(params) {
  return request({
    url: '/sell/createorder/getGroup',
    method: 'GET',
    params
  })
}
//分组信息
export function getGroupDetail(query){
  return request({
    url:'/sell/itemListOnline/getPatientData',
    method:'get',
    params:query
  })
}

//根据id查订单表详情
export function editOrder(query){
  return request({
    url: '/sell/itemListOnline/edit',
    method: "get",
    params: query
  })
}
//保存右下
export function saveOrUpdatePatientBcOrder(query){
  return request({
    url: '/sell/itemListOnline/saveOrUpdatePatientBc',
    method: "post",
    data: query
  })
}

//获取婚姻下拉
export function getMarriageDataOrder(query){
  return request({
    url: '/sell/itemListOnline/getMarriageData',
    method: "get",
    params: query
  })
}
//获取民族下拉
export function getNationDataOrder(query){
  return request({
    url: '/sell/itemListOnline/getNationData',
    method: "get",
    params: query
  })
}

// 当前登录人关联分中心
export function getUserCid(params){
  return request({
    url: '/branch/getUserCid',
    method: "get",
    params
  })
}