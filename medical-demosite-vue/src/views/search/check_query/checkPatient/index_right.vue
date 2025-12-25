<template>
  <el-col :span="15" style="overflow-y: auto; height: 795px">
    <div class="flex-direction-column">
      <!-- 上方统计数据 -->
      <el-row type="flex" justify="space-between" style="width: 100%">
        <el-col :span="24" style="border: 1px solid #d4d6d9; margin-bottom: 8px">
          <div class="statistics">
            <div class="item" v-for="(item, index) in statisticsList" :key="index">
              <div class="title">{{ item.title }}</div>
              <div class="number" :style="{ '--theme': theme }">{{ item.count }}</div>
            </div>
          </div>
        </el-col>
      </el-row>
      <!-- 中间表格信息 -->
      <div class="table-box flex-direction-column">
        <el-table border :data="tableList" height="100%" stripe>
          <el-table-column fixed label="序列" width="55" type="index" align="center" />
          <el-table-column fixed label="收费项目" prop="examfeeitemName" align="center" width="255" show-overflow-tooltip> </el-table-column>
          <el-table-column label="价格" align="center">
            <el-table-column label="原价" prop="price" align="center" show-overflow-tooltip min-width="120" />
            <el-table-column label="优惠价" prop="factprice" align="center" width="140px"> </el-table-column>
          </el-table-column>
          <el-table-column label="付款方式" prop="payWayName" align="center" show-overflow-tooltip min-width="100" />
          <el-table-column label="加项医师" prop="name" align="center" width="125"> </el-table-column>
          <el-table-column label="登记" prop="fRegistered" align="center" width="60">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fRegistered == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="已收" prop="fFeecharged" align="center" width="60">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fFeecharged == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="已发" prop="fLabsendtolis" align="center" width="60">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fLabsendtolis == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="已检" prop="fExaminated" align="center" width="60">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fExaminated == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="弃检" prop="fGiveup" align="center" width="60">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fGiveup == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="迟检" prop="fDelayed" align="center" width="60">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.fDelayed == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="拒检" prop="sfjj" align="center" width="60">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.sfjj == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="加项" prop="sfjx" align="center" width="60">
            <template slot-scope="scope">
              <el-checkbox :value="scope.row.sfjx == 1"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="feeitemdesc" align="center" width="185px" show-overflow-tooltip> </el-table-column>
          <el-table-column label="科室" prop="ksmc" align="center" show-overflow-tooltip width="140" />
          <el-table-column label="登记人" prop="doctorregR" align="center" width="100"> </el-table-column>
          <el-table-column label="创建时间" prop="createdate" align="center" show-overflow-tooltip width="155" />
          <el-table-column label="查看图片" align="center" width="120">
            <template slot-scope="scope">
              <span class="check-pic" @click="checkPic(scope.row)">查看图片</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 查看图片对话框 -->
      <el-dialog title="查看图片" :visible.sync="openPic" width="380px" append-to-body style="overflow: hidden"> 暂无 </el-dialog>
    </div>
  </el-col>
</template>

<script>
export default {
  props: ['tableList'],
  data() {
    return {
      // 数据列表
      statisticsList: [
        { title: '总数', count: 0 },
        { title: '原始单价合计', count: 0 },
        { title: '优惠单价', count: 0 },
        { title: '待收费合计', count: 0 },
        { title: '合计', count: 0 },
      ],

      // 收费项目参数
      selectData: {
        placeholder: '',
        key: '拼音码', //第一列标题
        value: '收费项目', //第二列标题
        third: '价格', //第三列标题
        url: '', //请求连接
        selectWidth: 230, //选择器宽度（选填，默认230）不加px
        bindValue: '', //初始值(不传为空)
      },

      // 折扣弹窗
      openDiscount: false,
      // 查看图片弹窗
      openPic: false,
     
      // 修改弹窗
      openEidt: false,
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  watch: {
    tableList: {
      handler(newValue) {
        let num = newValue.length
        let ysdj = 0
        let yhdj = 0
        let dsf = 0
        newValue.forEach((el) => {
          if (el.price) {
            ysdj += Number(el.price)
          }
          if (el.factprice) {
            yhdj += Number(el.factprice)
          }
          if (el.factprice && !el.fFeecharged) {
            dsf += Number(el.factprice)
          }
        })
        this.statisticsList[0].count = num
        this.statisticsList[1].count = ysdj.toFixed(1)
        this.statisticsList[2].count = yhdj.toFixed(1)
        this.statisticsList[3].count = dsf.toFixed(1)
        this.statisticsList[4].count = (ysdj + yhdj + dsf).toFixed(1)
      },
    },
  },
  created() {},
  methods: {
    // 新增/加项
    handleAdd(num) {
      if (num == 1) {
        let obj = {
          examfeeitemName: '',
          // 加项的参数赋值true
        }
        this.tableList.push(obj)
      } else {
        if (this.tableList[this.tableList.length - 1].examfeeitemName != '') {
          let obj = {
            examfeeitemName: '',
          }
          this.tableList.push(obj)
        }
      }
    },
    // 删除
    handleRemove() {
      this.selectData.forEach((el) => {
        if (el.FGiveup == 0) {
          this.$alert(el.examfeeitemName + '已完成收费，不可删除', '提示', {
            confirmButtonText: '确定',
            F,
          })
          return
        } else {
          // 删除选定项
          // this.tableList.forEach((val, index) => {
          //   if (el.examfeeitemName == val.examfeeitemName) {
          //     this.tableList.slice(index, 1)
          //   }
          // })
        }
      })
    },
    // 折扣
    handleDiscount() {
      this.openDiscount = true
    },
    // 保存
    handleSave() {
      this.$modal.msg('保存')
    },
    // 刷新
    handleRefresh() {
      this.$modal.msg('刷新')
    },
    // 套餐卡
    handleCard() {},
    // 导出
    handleExport() {},

    // 表格选中
    handleSelectionChange(val) {
      this.selectData = val.map((item) => item)
    },
    selectChange(event, value) {
      // this.参数 = value
    },
    // 查看图片弹窗
    checkPic() {
      this.openPic = true
    },
 
  
    // 确认修改
    editConfirm() {
      this.$modal.msgSuccess('修改成功')
      this.openEidt = false
    },
  },
}
</script>

<style lang="scss" scoped>
.table-show {
  flex: 1;
  overflow-y: auto;
}

.statistics {
  display: flex;
  justify-content: space-between;
  height: 96px;
  padding: 10px 20px;
  margin-right: 30px;

  .item {
    min-width: 150px;
    height: 100%;
    padding: 12px 12px;
    background: #f7f8fa;
    border-radius: 10px;

    .title {
      font-size: 12px;
      line-height: 17px;
      color: #858586;
      margin-bottom: 5px;
    }

    .number {
      font-weight: 600;
      font-size: 24px;
      line-height: 34px;
      color: #{'var(--theme)'} !important;
    }
  }
}

.operation {
  display: flex;
  margin-top: 8px;

  .item {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100px;
    padding: 10px 0;
    margin-left: 16px;
    box-shadow: 0px 0px 10px rgba(0, 89, 255, 0.5);
    border-radius: 5px;

    &:hover {
      cursor: pointer;
    }

    .name {
      line-height: 20px;
      color: #ffffff;
    }
  }
}

.check-pic {
  color: #0059ff;

  &:hover {
    cursor: pointer;
  }
}
</style>
