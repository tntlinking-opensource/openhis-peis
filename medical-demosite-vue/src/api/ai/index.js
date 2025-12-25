import request from '@/utils/request'

// AI服务的基础URL
const AI_SERVICE_BASE_URL = '/aiapi' //'/aiapi'线下体检系统 'http://XXX.XXX.XXX:8084' //http://localhost:6039' // AI服务域名

/**
 * AI接口 - 使用原生Fetch API处理流式响应
 * @param {Object} data - 请求参数
 * @returns {Promise} - 返回Promise对象
 */
export function aiApi(data) {
  const url = `${AI_SERVICE_BASE_URL}/open/v1/ai/medical/chat`;
  return fetch(url, {
    method: 'POST',
    headers: { 
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  });
}


