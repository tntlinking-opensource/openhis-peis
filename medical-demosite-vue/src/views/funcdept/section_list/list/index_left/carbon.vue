<!-- 碳13/14 开发人： 麦沃德科技 予安 -->
<template>
  <div class="app-container flex-direction-column" style="height: 100%; min-height: auto; padding: 0; overflow-y: auto">
    <div class="charge-item" v-for="(item, index) in leftData" :key="index" :style="{ 'pointer-events': mainDisabled ? 'none' : '' }">
      <h3>【{{ item.sfxmmc }}:{{ item.jcxmmc }}】</h3>
      <el-form :model="item" ref="queryForm2" size="small" :inline="true" @submit.native.prevent v-show="showSearch" label-width="130px">
        <el-form-item label="是否弃检" prop="qij">
          <el-select v-model="item.qij" placeholder="请选择" style="width: 220px">
            <el-option label="是" :value="1"> </el-option>
            <el-option label="否" :value="0"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="危急值" prop="wjzjb">
          <el-select v-model="item.wjzjb" placeholder="请选择" style="width: 220px">
            <el-option label="无" value="0"> </el-option>
            <el-option label="低" value="1"> </el-option>
            <el-option label="中" value="2"> </el-option>
            <el-option label="高" value="3"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="item.jcxmmc" prop="checkList" v-if="item.fentryonly == 'shuru'">
          <el-input-number v-model="item.inputResult" placeholder="请输入" clearable style="width: 580px" controls-position="right" @change="inputResultChange(index)" />
        </el-form-item>
        <el-form-item label=" " prop="checkList" v-else>
          <el-checkbox-group v-model="item.checkList" @change="itemChange(item, index)">
            <el-checkbox :label="value.sname" v-for="(value, index2) in item.optionList" :key="index2" style="min-width: 150px"> </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="检查描述" prop="jcms">
          <el-input v-model="item.jcms" placeholder="请输入" clearable style="width: 580px" type="textarea" rows="2" />
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  props: ['carbon', 'leftData', 'mainDisabled'],
  data() {
    return {
      // 显示筛选
      showSearch: true,
      // DOB值参数
      queryParams: {
        sfqj: undefined,
        wjz: undefined,
        dob: undefined,
        jcms: undefined,
        checkList: [],
      },
      // C14检查结果参数
      queryParams2: {
        sfqj: undefined,
        wjz: undefined,
        dob: undefined,
        jcms: undefined,
      },
    }
  },
  methods: {
    // DPM值改变
    inputResultChange(index) {
      this.$emit('inputResultChange', index)
    },
    // 修改阴阳性
    itemChange(item, index) {
      this.$emit('itemChange', item, index)
    },
  },
}
</script>

<style lang="scss" scoped>
h3 {
  margin: 5px 0 10px;
  font-weight: 600;
  font-size: 16px;
  line-height: 22px;
  color: #333333;
}

.charge-item {
  padding: 20px;
  padding-bottom: 0;
}
</style>
