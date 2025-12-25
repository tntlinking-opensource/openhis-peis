// 危机值管理  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 分页查询
export function listRisk(query) {
	return request({
		url: '/sell/riskclient/page',
		method: 'get',
		params: query
	})
}

// 查询详情
export function getRisk(id) {
	return request({
		url: '/sell/riskclient/' + id,
		method: 'get'
	})
}

// 处理
export function handleIsCl(data) {
	return request({
		url: '/sell/riskclient/isCl',
		method: 'GET',
		params: data
	})
}

// 处理
export function handleRisk(data) {
	return request({
		url: '/sell/riskclient/handle',
		method: 'PUT',
		params: data
	})
}