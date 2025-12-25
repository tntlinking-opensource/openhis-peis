// 文化程度
export function getCultural() {
  return [
    { label: '小学', value: 0 },
    { label: '初中', value: 1 },
    { label: '技校', value: 2 },
    { label: '职高', value: 3 },
    { label: '高中', value: 4 },
    { label: '中专', value: 5 },
    { label: '大专', value: 6 },
    { label: '大学', value: 7 },
    { label: '研究生及以上', value: 8 },
  ]
}

// 体检状态
export function physicalStatus() {
  return [
    { label: '未检', value: 0 },
    { label: '已检', value: 1 },
  ]
}

// 体检类型
export function physicalType() {
  return [
    { label: '健康体检', value: 0 },
    { label: '职业体检', value: 1 },
    { label: '综合', value: 2 },
    { label: '复查', value: 3 },
  ]
}

// 体检类别
export function physicalType1() {
  return [
    { value: 0, label: '上岗前' },
    { value: 1, label: '在岗期间' },
    { value: 2, label: '离职时' },
    { value: 3, label: '离岗后' },
    { value: 4, label: '应急' },
  ]
}

// 消费类型
export function consumeType() {
  return [
    { id: '0', text: '体检' },
    { id: '1', text: '药房' },
    { id: '2', text: '口腔科' },
    { id: '3', text: '眼镜店' },
    { id: '4', text: '合作' },
    { id: '5', text: '保健品' }
  ]
}

// 婚姻类型
export function marriageType() {
  return [
    { label: '未婚', value: 1 },
    { label: '已婚', value: 2 },
    { label: '离异', value: 3 },
    { label: '丧偶', value: 4 },
    { label: '其他', value: 5 },
  ]
}

// 报告状态
export function reportType() {
  return [
    { text: '总检开始', id: 0 },
    { text: '总检完成', id: 1 },
    { text: '报告已打印', id: 2 },
    { text: '报告一审通过', id: 3 },
    { text: '报告一审不通过', id: 4 },
    { text: '报告二审通过', id: 5 },
    { text: '报告二审不通过', id: 6 },
    { text: '终审通过', id: 7 },
    { text: '终审不通过', id: 8 },
    { text: '报告已交接', id: 9 },
    { text: '报告已通知', id: 10 },
    { text: '报告已领取', id: 11 },
    { text: '总检未开始', id: -1 },
  ]
}

// 快递方式
export function expressType() {
  return [
    { id: 1, text: '顺丰' },
    { id: 2, text: 'EMS' },
    { id: 3, text: '圆通' },
    { id: 4, text: '申通' },
    { id: 5, text: '韵达' },
    { id: 6, text: '中通' },
    { id: 7, text: '其他' }
  ]
}

// 内科-科室列表
export function sectionList() {
  return [
    { label: '检验科', value: 1 },
    { label: '一般检查', value: 2 },
    { label: '内科', value: 3 },
    { label: '外科', value: 4 },
    { label: '彩超', value: 5 },
    { label: '放射科(DR)', value: 6 },
    { label: '放射科(CT)', value: 7 },
    { label: '动脉硬化室', value: 8 },
    { label: '心电图室', value: 9 },
    { label: '骨密度检测', value: 10 },
    { label: '经颅多普勒室', value: 11 },
  ]
}

// 检验科id
export function getJykId() {
  return 19
}

// 沃德体检实验室检查项目正常参考值范围（成人）
export function getReferData() {
  return [
    { id: 1, name: "白细胞计数（WBC）", unit: "10<sup style='font-size: 10px;'>9</sup>/L", range: "3.50-9.50 注：接触苯（甲苯、二甲苯）危害因素的不低于4.0", },
    { id: 2, name: "红细胞计数（RBC）", unit: "10<sup style='font-size: 10px;'>12</sup>/L", range: "<span>女3.8-5.1</span><span>男4.3-5.8</span>", },
    { id: 3, name: "血红蛋白（HGB）", unit: "g/L", range: "<span>女115-150</span><span>男120-175</span>", },
    { id: 4, name: "血小板（PLT）", unit: "10<sup style='font-size: 10px;'>9</sup>/L", range: "100-300", },
    { id: 5, name: "淋巴细胞#（LY#）", unit: "10<sup style='font-size: 10px;'>9</sup>/L", range: "1.10-3.20", },
    { id: 6, name: "淋巴细胞百分比（LY%）	%	20.0-50.0", unit: "%", range: "20.0-50.0", },
    { id: 7, name: "粒细胞#（NE#）", unit: "10<sup style='font-size: 10px;'>9</sup>/L", range: "1.80-6.30", },
    { id: 8, name: "丙氨酸氨基转移酶（ALT）", unit: "U/L", range: "5.0-40.0", },
    { id: 9, name: "门冬氨酸氨基转移酶（AST）", unit: "U/L", range: "8.0-40.0", },
    { id: 10, name: "总蛋白（TP）", unit: "g/L", range: "65.00-85.00", },
    { id: 11, name: "白蛋白（ALB）", unit: "g/L", range: "40.00-50.00", },
    { id: 12, name: "直接胆红素（DBIL）", unit: "umol/L", range: "3.4-20.0", },
    { id: 13, name: "总胆红素（TBLL）", unit: "umol/L", range: "0.00-6.80", },
    { id: 14, name: "γ-谷氨酰转肽酶（GGT）", unit: "U/L", range: "<span>女7.0-45.0</span><span>男10.0-60.0</span>", },
    { id: 15, name: "间接胆红素（IBIL）", unit: "umol/L", range: "0.00-17.00", },
    { id: 16, name: "葡萄糖（GLU）", unit: "umol/L", range: "3.80-6.10", },
    { id: 17, name: "尿素氮（BUN）", unit: "umol/L", range: "<span>女2.6-7.5</span><span>男3.1-8.0</span>", },
    { id: 18, name: "肌酐（CR）", unit: "umol/L", range: "<span>女41.0-98.0</span><span>男57.0-112.0</span>", },
    { id: 19, name: "尿酸（UA）", unit: "umol/L", range: "<span>女142-339</span><span>男202-406</span>", },
    { id: 20, name: "胆固醇（CHO）", unit: "umol/L", range: "2.9-5.6", },
    { id: 21, name: "甘油三酯（TG）", unit: "umol/L", range: "0.38-1.70", },
    { id: 22, name: "高密度脂蛋白（HDL）", unit: "umol/L", range: "1.16-1.91", },
    { id: 23, name: "低密度脂蛋白（LDL）", unit: "umol/L", range: "2.07-3.60", },
  ]
}

// 沃德体检主要技术人员资质一览表
export function getCredentialData() {
  return [
    { name: "刘涛", sex: "男", duties: "主治医师", dept: "外科", major: "临床医学", year: "33", num: "鲁职诊断(2019)02129" },
    { name: "姜英淑", sex: "女", duties: "副主任医师", dept: "内科", major: "临床医学", year: "36", num: "鲁职诊断(2018)01081" },
    { name: "刘红", sex: "女", duties: "主任医师", dept: "内科", major: "临床医学", year: "34", num: "鲁职诊断(2018)03166" },
    { name: "赵国安", sex: "男", duties: "副主任医师", dept: "放射科", major: "医学影像", year: "38", num: "鲁职诊断(2018)01074" },
    { name: "高波", sex: "男", duties: "主治医师", dept: "五官科", major: "临床医学", year: "39", num: "199837110230303600816461" },
    { name: "周启军", sex: "男", duties: "主治医师", dept: "内科", major: "临床医学", year: "41", num: "199862110622101540820351" },
    { name: "李克实", sex: "男", duties: "副主任医师", dept: "外科", major: "临床医学", year: "45", num: "199815110152126580928151" },
    { name: "沈肖椿", sex: "男", duties: "主治医师", dept: "内科", major: "临床医学", year: "51", num: "199837110370211541030003" },
    { name: "王闻", sex: "男", duties: "主治医师", dept: "口腔科", major: "口腔医学", year: "43", num: "199837120220225620408413" },
    { name: "徐萍", sex: "女", duties: "主治医师", dept: "彩超", major: "医学影像", year: "35", num: "2010371103370302196712061427" },
    { name: "岳文艺", sex: "女", duties: "技士", dept: "放射科", major: "医学影像", year: "6", num: "20171380200510400006" },
    { name: "管丽丽", sex: "女", duties: "检验师", dept: "检验科", major: "医学检验", year: "11", num: "20103802000001368" },
  ]
}
// 锦都
export function getCredentialData2() {
  return [
    { name: "刘涛", sex: "男", duties: "主治医师", dept: "外科", major: "临床医学", year: "33", num: "鲁职诊断0619号" },
    { name: "祝超", sex: "男", duties: "副主任医师", dept: "外科", major: "临床医学", year: "18", num: "鲁职诊断0469号" },
    { name: "李庆海", sex: "男", duties: "副主任医师", dept: "预防保健科", major: "临床医学", year: "40", num: "鲁职诊断1209号" },
    { name: "赵国安", sex: "男", duties: "副主任医师", dept: "放射科", major: "医学影像", year: "42", num: "199823110152104581120523" },
    { name: "李爱华", sex: "女", duties: "主治医师", dept: "五官科", major: "临床医学", year: "36", num: "199837110370919196412203542" },
    { name: "李祥文", sex: "男", duties: "主治医师", dept: "内科", major: "临床医学", year: "19", num: "200937110239005198011040075" },
    { name: "祝超", sex: "男", duties: "副主任医师", dept: "外科", major: "临床医学", year: "18", num: "201037110370283198204047014" },
    { name: "李蕙", sex: "女", duties: "主治医师", dept: "眼耳鼻咽喉科", major: "临床医学", year: "45", num: "199837110370223480214002" },
    { name: "严凤琴", sex: "女", duties: "主治医师", dept: "口腔科", major: "口腔医学", year: "16", num: "201912120632123198403021523" },
    { name: "高禅侠", sex: "男", duties: "医师", dept: "彩超", major: "医学影像", year: "46", num: "199837110370727511220747" },
    { name: "耿养龙", sex: "男", duties: "检验士", dept: "检验科", major: "医学检验", year: "12", num: "1415021023741003" },
    { name: "王成霞", sex: "女", duties: "检验师", dept: "检验科", major: "医学检验", year: "42", num: "100201106" },
  ]
}