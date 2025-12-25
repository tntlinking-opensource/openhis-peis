import { getPatientsForID, barcodePrinter } from '@/api/reception/register_list'
import { getLodop } from '@/utils/LodopFuncs.js'
import { getDate } from '@/utils/getDate.js'
import { getCookie } from '@/utils/getCookie.js'

export function printBarCode(query, fn, barNum) {
  var LODOP = getLodop()
  let counter = LODOP.GET_PRINTER_COUNT() // 获取打印机个数
  let printerList = []
  let typeList = []
  let printerName = ''
  barcodePrinter().then(({ data }) => {
    if (data && data.value && data.value.length) {
      typeList = data.value
    } else {
      return
    }
    for (let i = 0; i < counter; i++) {
      printerList.push({  // 将打印机存入printerList数组中 
        name: LODOP.GET_PRINTER_NAME(i),
        value: i
      })
    }
    printerList.forEach(el => {
      typeList.forEach(val => {
        if (!printerName && el.name.includes(val)) {
          printerName = el.name
        }
      })
    })
    let formatDate = getDate().split(' ')[0]
    getPatientsForID(query).then(({ data: dataMain }) => {
      // 解析
      var data = dataMain.data
      var confirm = dataMain.confirm
      const size = barNum ? barNum / 3 : getCookie('cid') != 'ff8080817ee18637017ee77dc0322d8c' ? 4 : 3 //如果是自助登记，这里始终是自主登记的配置数
      console.log("默认打印个数",size);
      
      for (var i = 0; i < data.length; i++) {
        LODOP.PRINT_INITA(0, 0, '108mm', '23mm', data[i].patientname + (i + 1) + '_条码')
        LODOP.SET_PRINT_PAGESIZE(1, '100mm', '19mm', 'CreateCustomPage')
        for (var j = 0; j < size; j++) {
          LODOP.NewPage()
          //下面方法参数不能省略，否则IE下报错，invalid param:
          LODOP.ADD_PRINT_TEXT(confirm.patientname_top1, confirm.patientname_left1, confirm.patientname_width1, confirm.patientname_height1, data[i].patientname)
          LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.ADD_PRINT_TEXT(confirm.sex_top1, confirm.sex_left1, confirm.sex_width1, confirm.sex_height1, data[i].sex)
          LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.ADD_PRINT_TEXT(confirm.age_top1, confirm.age_left1, confirm.age_width1, confirm.age_height1, data[i].age)
          LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          // 如果是最后一个条码，赋值note 
          // if (j == size - 1) {
          //   LODOP.ADD_PRINT_TEXT(confirm.note_top1, confirm.note_left1, confirm.note_width1, confirm.note_height1, data[i].note)
          //   LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          //   LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          // }
          // LODOP.ADD_PRINT_TEXT(confirm.note_top1, confirm.note_left1, confirm.note_width1, confirm.note_height1, data[i].note)
          // LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          // LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.ADD_PRINT_BARCODE(confirm.barcode_top1, confirm.barcode_left1, '24mm', confirm.barcode_height1, confirm.barcode_font1, data[i].patientCode?.slice(-8))
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.SET_PRINT_STYLEA(0, 'ShowBarText', 0)
          LODOP.ADD_PRINT_TEXT(confirm.intId_top1, confirm.intId_left1, confirm.intId_width1, confirm.intId_height1, data[i].intId + '*' + data[i].patientCode?.slice(-8))
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.SET_PRINT_STYLEA(0, 'Alignment', 2)
          //如果没配置此参数，会导致体检号样式错乱
          LODOP.SET_PRINT_STYLEA(0, 'FontSize', Number(confirm.intId_fontSize1))
          LODOP.ADD_PRINT_TEXT(confirm.date_top1, confirm.date_left1, confirm.date_width1, confirm.date_height1, (data[i].patientcode ? (data[i].patientcode.slice(0, 2) + '*') : '') + formatDate)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.SET_PRINT_STYLEA(0, 'Alignment', 2)

          LODOP.ADD_PRINT_TEXT(confirm.patientname_top2, confirm.patientname_left2, confirm.patientname_width2, confirm.patientname_height2, data[i].patientname)
          LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.ADD_PRINT_TEXT(confirm.sex_top2, confirm.sex_left2, confirm.sex_width2, confirm.sex_height2, data[i].sex)
          LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.ADD_PRINT_TEXT(confirm.age_top2, confirm.age_left2, confirm.age_width2, confirm.age_height2, data[i].age)
          LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          // 如果是最后一个条码，赋值note 
          // if (j == size - 1) {
          //   LODOP.ADD_PRINT_TEXT(confirm.note_top2, confirm.note_left2, confirm.note_width2, confirm.note_height2, data[i].note)
          //   LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          //   LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          // }
          // LODOP.ADD_PRINT_TEXT(confirm.note_top2, confirm.note_left2, confirm.note_width2, confirm.note_height2, data[i].note)
          // LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          // LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.ADD_PRINT_BARCODE(confirm.barcode_top2, confirm.barcode_left2, '24mm', confirm.barcode_height2, confirm.barcode_font2, data[i].patientCode?.slice(-8))
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.SET_PRINT_STYLEA(0, 'ShowBarText', 0)
          LODOP.ADD_PRINT_TEXT(confirm.intId_top2, confirm.intId_left2, confirm.intId_width2, confirm.intId_height2, data[i].intId + '*' + data[i].patientCode?.slice(-8))
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.SET_PRINT_STYLEA(0, 'Alignment', 2)
          LODOP.SET_PRINT_STYLEA(0, 'FontSize', Number(confirm.intId_fontSize2))
          LODOP.ADD_PRINT_TEXT(confirm.date_top2, confirm.date_left2, confirm.date_width2, confirm.date_height2, (data[i].patientcode ? (data[i].patientcode.slice(0, 2) + '*') : '') + formatDate)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.SET_PRINT_STYLEA(0, 'Alignment', 2)

          LODOP.ADD_PRINT_TEXT(confirm.patientname_top3, confirm.patientname_left3, confirm.patientname_width3, confirm.patientname_height3, data[i].patientname)
          LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.ADD_PRINT_TEXT(confirm.sex_top3, confirm.sex_left3, confirm.sex_width3, confirm.sex_height3, data[i].sex)
          LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.ADD_PRINT_TEXT(confirm.age_top3, confirm.age_left3, confirm.age_width3, confirm.age_height3, data[i].age)
          LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          // 如果是最后一个条码，赋值note
          if (j == size - 1) {
            LODOP.ADD_PRINT_TEXT(confirm.note_top3, confirm.note_left3, confirm.note_width3, confirm.note_height3, data[i].note)
            LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
            LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          }
          // LODOP.ADD_PRINT_TEXT(confirm.note_top3, confirm.note_left3, confirm.note_width3, confirm.note_height3, data[i].note)
          // LODOP.SET_PRINT_STYLEA(0, 'Bold', 1)
          // LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.ADD_PRINT_BARCODE(confirm.barcode_top3, confirm.barcode_left3, '24mm', confirm.barcode_height3, confirm.barcode_font3, data[i].patientCode?.slice(-8))
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.SET_PRINT_STYLEA(0, 'ShowBarText', 0)
          LODOP.ADD_PRINT_TEXT(confirm.intId_top3, confirm.intId_left3, confirm.intId_width3, confirm.intId_height3, data[i].intId + '*' + data[i].patientCode?.slice(-8))
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.SET_PRINT_STYLEA(0, 'Alignment', 2)
          LODOP.SET_PRINT_STYLEA(0, 'FontSize', Number(confirm.intId_fontSize3)) //必须转为NUMBER，否则IE下报错INVALID PARAM
          LODOP.ADD_PRINT_TEXT(confirm.date_top3, confirm.date_left3, confirm.date_width3, confirm.date_height3, (data[i].patientcode ? (data[i].patientcode.slice(0, 2) + '*') : '') + formatDate)
          LODOP.SET_PRINT_STYLEA(0, 'ItemType', 0)
          LODOP.SET_PRINT_STYLEA(0, 'Alignment', 2)
        }
        LODOP.SET_SHOW_MODE('HIDE_PAPER_BOARD', true) //隐藏走纸板
        LODOP.SET_PRINTER_INDEX(printerName) // 指定打印机（序号或者打印机名称）
        if (query.model == 0) {
          // 预览
          LODOP.PRINT_DESIGN()
          // LODOP.PREVIEW()
        } else if (query.model == 1) {
          // 打印
          LODOP.PRINT()
        } else if (query.model == 2) {
          // 设计
          LODOP.PRINT_DESIGN()
        }
      }
      fn()
    })
  })
}
