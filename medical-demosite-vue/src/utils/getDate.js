// 获取当前时间
export function getDate(date) {
  var myDate = undefined
  myDate = date ? new Date(date) : new Date()
  var year = myDate.getFullYear() //获取当前年
  var mon = myDate.getMonth() + 1 //获取当前月
  mon = mon < 10 ? ('0' + mon) : mon
  var date = myDate.getDate() //获取当前日
  date = date < 10 ? ('0' + date) : date
  var hours = myDate.getHours() //获取当前小时
  hours = hours < 10 ? ('0' + hours) : hours
  var minutes = myDate.getMinutes() //获取当前分钟
  minutes = minutes < 10 ? ('0' + minutes) : minutes
  var seconds = myDate.getSeconds() //获取当前秒
  seconds = seconds < 10 ? ('0' + seconds) : seconds
  var now = year + '-' + mon + '-' + date + ' ' + hours + ':' + minutes + ':' + seconds
  return now
}