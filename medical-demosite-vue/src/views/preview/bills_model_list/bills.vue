<template>
  <div class="bills-model-list container">
    <div ref="tm" style="width: 100%" id="tm">
      <div v-for="(item, index) in selection" :key="index" class="content">
        <div v-for="(i, index2) in indexNumber" class="content-child" :key="index2">
          <div class="setCl">
            <div>{{ item.patientname }}</div>
            <div v-if="item.idSex == '0'">男</div>
            <div v-else>女</div>
            <div>{{ item.age }}</div>
          </div>
          <img class="info-barcode" :id="'barcode' + index" />
          <div>{{ item.patientcode }}</div>
        </div>
      </div>
    </div>

    <!-- 打印按钮 -->
    <el-tag class="print-fixed" @click="printMethods" v-if="showBtn">打 印</el-tag>
  </div>
</template>

<script>
import JsBarcode from 'jsbarcode'
export default {
  name: 'Bills',
  data() {
    return {
      bills: [],
      selection: undefined,
      indexNumber: [1, 2, 3],
      choose: true,
      showBtn: true,
    }
  },

  mounted() {
    let script = document.createElement('script')
    script.type = 'text/javascript'
    script.src = 'http://localhost:8000/CLodopfuncs.js?'
    document.body.appendChild(script)
    this.bills = this.$route.query
    this.selection = JSON.parse(this.$route.query.selection)
    if (typeof (this.selection == 'object') && !this.$route.query.barcode) {
      this.selection = [this.selection]
    }
    this.getBills()
  },
  methods: {
    printMethods() {
      this.showBtn = false
      this.$nextTick(() => {
        // var strHTML = "<body style='margin:0;background-color: white'>" + document.getElementById('tm').innerHTML + '</body>'

        var strHTML = document.getElementsByTagName('html')[0].innerHTML
        let printHeight = 90 * this.selection.length
        // LODOP.PRINT_INITA(0, 0, '100%', printHeight + 'mm')
        // LODOP.ADD_PRINT_HTM('5mm', 34, 'RightMargin:0.9cm', 'BottomMargin:9mm', strHTML)

        LODOP.ADD_PRINT_HTM('2mm', '1mm', '100%', printHeight + 'px', strHTML)
        // LODOP.ADD_PRINT_HTM('5mm', '4mm', '100%',  '20mm', strHTML)
        LODOP.PREVIEW()
        this.showBtn = true
      })
    },
    getBills() {
      this.$nextTick(() => {
        for (var i = 0; i < this.selection.length; i++) {
          JsBarcode('#barcode' + i, this.selection[i].patientcode, {
            format: 'CODE39', //条形码的格式
            width: 1, //线宽
            height: 50, //条码高度
            fontSize: 14,
            lineColor: '#000', //线条颜色
            displayValue: false, //是否显示文字
            margin: 0, //设置条形码周围的空白区域
          })
        }
        // this.printMethods()
      })
    },
  },
}
</script>

<style lang="scss" scope>
// .bills-model-list-bills {
  // body {
  //   width: 103mm;
  //   height: auto;
  //   /* background: #c1c1c1; */
  // }
  // #app {
  //   width: 103mm;
  //   height: auto;
  // }
  .bills-model-list.container {
    width: 103mm;
    text-align: center;
    background: white;
  }

  .content {
    width: 100%;
    display: block;
    &:first-child {
      margin-top: 0;
    }
  }
  .content-child {
    box-sizing: border-box;
    width: 30mm;
    height: 20mm;
    display: inline-block;
    text-align: center;
    margin: auto;
    margin: 8px;
  }

  .setCl {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .print-fixed {
    position: fixed;
    left: 50%;
    bottom: 10px;
    transform: translateX(-50%);
    text-align: center;
    width: 120px;
    height: 35px;
    line-height: 35px;
    cursor: pointer;
  }

  .info-barcode {
    width: 100px;
  }

  @media print {
    .print-fixed {
      display: none;
    }

    body {
      background: white;
    }
  }
// }
</style>
