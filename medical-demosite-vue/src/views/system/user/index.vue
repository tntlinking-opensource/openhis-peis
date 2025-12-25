<template>
  <div class="app-container" style="height: 100%">
    <el-row :gutter="20" style="height: 100%">
      <!--部门数据-->
      <el-col :span="4" :xs="24" style="height: 100%">
        <div class="head-container">
          <el-input v-model="deptName" placeholder="请输入部门名称" clearable size="small" prefix-icon="el-icon-search" style="margin-bottom: 20px" />
        </div>
        <div class="head-container" style="height: calc(100% - 55px); overflow-y: auto">
          <el-tree :data="deptOptions" :props="defaultProps" :expand-on-click-node="false" :filter-node-method="filterNode" ref="tree" default-expand-all highlight-current @node-click="handleNodeClick" />
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24" style="height: 100%; display: flex; flex-direction: column">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="用户名称" prop="userName">
            <el-input v-model="queryParams.userName" placeholder="请输入用户名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="手机号码" prop="phonenumber">
            <el-input v-model="queryParams.phonenumber" placeholder="请输入手机号码" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="用户状态" clearable style="width: 240px">
              <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['system:user:add']" v-if="isOnline">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['system:user:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['system:user:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImport" v-hasPermi="['system:user:import']">导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-upload2" size="mini" @click="handleExport" v-hasPermi="['system:user:export']">导出</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-refresh" size="mini" @click="handleKingDeeUserUpdate(1)" v-hasPermi="['system:user:kingDeeUser']">金蝶同步用户更新</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-refresh" size="mini" @click="handleKingDeeUserUpdate(2)" v-hasPermi="['system:user:kingDeeUser']">刷新金蝶变更内容</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>
        <div style="flex: 1">
          <el-table ref="userList" v-loading="loading" :data="userList" @selection-change="handleSelectionChange" @row-click="rowClick" height="100%">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="用户编号" align="center" key="userId" prop="userId" v-if="columns[0].visible" />
            <el-table-column label="用户名称" width="150" align="center" key="userName" prop="userName" v-if="columns[1].visible" show-overflow-tooltip />
            <el-table-column label="输入码" align="center" width="150" prop="inputCode" show-overflow-tooltip />
            <el-table-column label="用户昵称" align="center" width="150" key="nickName" prop="nickName" v-if="columns[2].visible" show-overflow-tooltip />
            <el-table-column align="center" label="基本信息">
              <el-table-column label="邮箱" align="center" prop="email" show-overflow-tooltip />
              <el-table-column label="折扣" align="center" prop="sdiscount" show-overflow-tooltip />
              <el-table-column label="领导折扣" align="center" prop="ldiscount" show-overflow-tooltip />
              <el-table-column label="是否为领导" align="center" prop="isleader" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="scope.row.isleader == 1">是</span>
                  <span v-else>否</span>
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column label="系统账号状态" align="center" key="status" v-if="columns[5].visible">
              <template slot-scope="scope">
                <el-switch v-model="scope.row.status" active-value="0" inactive-value="1" @change="handleStatusChange(scope.row)"></el-switch>
              </template>
            </el-table-column>
            <el-table-column label="金蝶账号编号" align="center" width="150" prop="kingdeeAccountNo" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.kingdeeAccountNo || '未维护' }}
              </template>
            </el-table-column>
            <el-table-column label="部门" align="center" key="deptName" prop="dept.deptName" v-if="columns[3].visible" show-overflow-tooltip />
            <el-table-column label="手机号码" align="center" key="phonenumber" prop="phonenumber" v-if="columns[4].visible" width="120" />
            <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[6].visible" width="160">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.createTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="190" fixed="right" class-name="small-padding">
              <template slot-scope="scope">
                <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:user:edit']">修改</el-button>
                <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['system:user:remove']">删除</el-button>
                <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
                  <el-button size="mini" type="text" class="el-dropdown-link" icon="el-icon-d-arrow-right">更多</el-button>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="handleResetPwd" icon="el-icon-key" v-hasPermi="['system:user:resetPwd']"> 重置密码</el-dropdown-item>
                    <el-dropdown-item command="handleAuthRole" icon="el-icon-circle-check" v-hasPermi="['system:user:edit']">分配角色</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <pagination :total="total" :page.sync="queryParams.current" :limit.sync="queryParams.size" @pagination="getList" />
      </el-col>
    </el-row>
    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" destroy-on-close append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户昵称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="用户名称" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="用户密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户性别" prop="sex">
              <el-select v-model="form.sex" placeholder="请选择性别">
                <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value"> {{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="岗位" prop="postIds">
              <el-select v-model="form.postIds" multiple collapse-tags placeholder="请选择岗位">
                <el-option v-for="item in postOptions" :key="item.postId" :label="item.postName" :value="item.postId" :disabled="item.status == 1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色" prop="roleIds">
              <el-select v-model="form.roleIds" multiple collapse-tags placeholder="请选择角色">
                <el-option v-for="(item, index) in roleOptions" :key="index" :label="item.roleName" :value="item.roleId" :disabled="item.status == 1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-tag type="success" style="width: 100%; margin-bottom: 10px">用户基本信息</el-tag>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="nickName" label="用户名称">
          <el-input v-model="form.nickName" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="userNo" label="老系统对应id">
          <el-input v-model="form.userNo" placeholder="请对照老系统填写对应用户id"></el-input>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="userCode" label="用户接口代码">
          <el-input v-model="form.userCode" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="kingdeeAccountNo" label="金蝶账号">
          <userSearchSelect :selectData="kingDeeData" @change="selectChangeJD" :initialValue="form.kingdeeAccountNo" style="width: 100%"> </userSearchSelect>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="sdiscount" label="折扣">
          <el-input-number v-model="form.sdiscount" controls-position="right" style="width: 100%" :min="0.01" :max="100000"></el-input-number>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="ldiscount" label="领导折扣">
          <el-input-number v-model="form.ldiscount" controls-position="right" style="width: 100%" :min="0.01" :max="1000000"></el-input-number>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="isleader" label="是否为领导">
          <el-select v-model="form.isleader" style="width: 100%" placeholder="">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="isDoc" label="是否为医师">
          <el-select v-model="form.isDoc" style="width: 100%" placeholder="">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="entryDate" label="入职日期">
          <el-date-picker v-model="form.entryDate" type="datetime" value-format="yyyy-MM-dd HH:MM:SS" clearable style="width: 100%" placeholder="选择日期"> </el-date-picker>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="harmClass" label="职业危害因素">
          <user-search-select :selectData="zyselectData" :initialValue="this.form.harmClassName" :multiple="true" @change="change1" style="width: 100%"> </user-search-select>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="superiorId" label="上级">
          <input-select :selectData="selectDataLQR" :initialValue="form.superiorName" style="width: 393px" @change="selectChangeLQR"></input-select>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="isbj" label="是否为补检账号">
          <el-select v-model="form.isbj" style="width: 100%" placeholder="">
            <el-option label="是" :value="1"></el-option>
            <el-option label="否" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="secretPassword" label="涉密密码">
          <el-input v-model="form.secretPassword" placeholder=" "></el-input>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="cid" label="默认分中心">
          <el-select v-model="form.cid" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in fzxOptions" :key="item.id" :label="item.fzx" :value="item.branchId" />
          </el-select>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="useBranches" label="兼职分中心">
          <jzSelectsearch :multiple="true" :initialValue="form.useBranchesName" :selectData="fzxData" style="width: 100%" @change="fzxChange"></jzSelectsearch>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" prop="ksIds" label="科室">
          <depSearchSelect :multiple="true" :initialValue="form.deptListName" :selectData="ksData" style="width: 100%" @change="ksChange"></depSearchSelect>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" label="图片">
          <div class="setimg">
            <image-upload id="upload-p" @submitFile="submitFileP" ref="uploadP" :uploadData="uploadDataP" :typeChoose="1" @uploadFinish="uploadFinishP" uploadWidth="100%" />
          </div>
        </el-form-item>
        <el-form-item label-width="120px" style="padding-right: 40px" label="图片签名">
          <div class="setimg">
            <image-upload id="upload-s" @submitFile="submitFileS" ref="uploadS" :uploadData="uploadDataS" :typeChoose="1" @uploadFinish="uploadFinishS" uploadWidth="100%" />
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body :close-on-click-modal="false">
      <file-upload ref="upload" :uploadData="uploadData" @uploadFinish="uploadFinish" uploadWidth="100%"></file-upload>
      <div style="width: 100%; text-align: center; margin: 8px 0">
        <el-checkbox v-model="uploadData.data.updateSupport"></el-checkbox>是否更新已经存在的用户数据 <br />
        <span>仅允许导入xls、xlxs格式文件.</span>
        <el-link type="primary" :underline="false" style="font-size: 12px; vertical-align: baseline" @click="importTemplate">下载模板</el-link>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, resetUserPwd, changeUserStatus, deptTreeSelect, upgradeSaleer, updateUserSaleer, listSection } from '@/api/system/user'
import { getBranchData } from '@/api/subscribe/appointment_details/appointment_details'
import { getToken } from '@/utils/auth'
import depSearchSelect from '@/components/depSearchSelect'
import jzSelectsearch from '@/components/jzSelectsearch'
import userSearchSelect from '@/components/userSearchSelect'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import Cookies from 'js-cookie'
export default {
  name: 'User',
  dicts: ['sys_normal_disable', 'sys_user_sex'],
  components: { Treeselect, depSearchSelect, jzSelectsearch, userSearchSelect },
  data() {
    return {
      // 遮罩层 
      loading: true,
      // 选中数组 
      ids: [],
      // 非单个禁用 
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: '',
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      departList: [],
      // 表单参数
      form: {
        secretPassword: '',
        password: '',
      },
      defaultProps: {
        children: 'children',
        label: 'label',
      },
      // 职业危害因素数据
      zyselectData: {
        placeholder: '请选择',
        inputTitle: '输入码', // 搜索标题
        inputPlaceholder: '', // 搜索placeholder
        key: '输入码', //第一列标题
        value: '危害因素名称', //第二列标题
        url: '/harm/findAllHarmclass', //请求连接
        firstName: 'inputCode', //接口返回值对应第一列的参数名
        secondName: 'harmClass', //接口返回值对应第二列的参数名
        queryData: 'inputCode',
        params: {},
        bindValue: [],
      },
      //领取人套餐数据
      selectDataLQR: {
        placeholder: '请输入输入码选择',
        key: '拼音码',
        value: '客户经理',
        url: '/report/healthGetReport/getAllUserData', //请求连接
        // url: '/finance/sendCard/getLqrData', //请求连接
        bindValue: '',
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'username', //接口返回值对应第二列的参数名(不传默认为'name')
        // queryData: 'srm',
                queryData: 'key',
      },
      //分中心数据
      fzxData: {
        placeholder: '请输入输入码选择',
        key: '分中心ID', //第一列标题
        value: '分中心', //第二列标题
        url: '/system/branch/getAll', //请求连接
        bindValue: '',
        firstName: 'branchId', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'fzx', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',
      },

      kingDeeData: {
        placeholder: '请输入输入码选择',
        key: '金蝶ID', //第一列标题 
        value: '金蝶账户名', //第二列标题
        thirdData: 'useStatusName',
        fourthData: 'centerorgname',
        url: '/system/user/getKingdeeData', //请求连接
        bindValue: '',
        firstName: 'accountNo', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'accountName', //接口返回值对应第二列的参数名(不传默认为'name')
        thirdName: '金蝶状态',
        fourthName: '所属组织',
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        openP: false,
        openS: false,
        // 弹出层标题（用户导入）
        title: '',
        titleP: '',
        titleS: '',
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: 'Bearer ' + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + '/system/user/importData',
      },
      // 查询参数
      queryParams: {
        current: 1,
        size: 20,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined,
      },
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true },
      ],
      // 表单校验
      rules: {
        userName: [
          { required: true, message: '用户名称不能为空', trigger: 'blur' },
          { min: 2, max: 20, message: '用户名称长度必须介于 2 和 20 之间', trigger: 'blur' },
        ],
        nickName: [{ required: true, message: '用户昵称不能为空', trigger: 'blur' }],
        password: [
          { required: true, message: '用户密码不能为空', trigger: 'blur' },
          { min: 3, max: 20, message: '用户密码长度必须介于 3 和 20 之间', trigger: 'blur' },
        ],
        email: [
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change'],
          },
        ],
        // 2024-08-29 去掉手机号校验
        // phonenumber: [
        //   {
        //     pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
        //     message: '请输入正确的手机号码',
        //     trigger: 'blur',
        //   },
        // ],
        name: [{ required: true, message: '用户名称不能为空', trigger: 'blur' }],
        phone: [{ required: true, message: '用户名称不能为空', trigger: 'blur' }],
        roleIds: [{ required: true, message: '用户角色不能为空', trigger: 'blur' }],
        userName: [{ required: true, message: '用户名称不能为空', trigger: 'blur' }],
        deptId: [{ required: true, message: '请选择用户归属部门', trigger: 'change' }],
      },
      // 上传组件参数
      uploadData: {
        url: '/system/user/importData', //文件上传地址
        multiple: false, //是否可以上传多个
        fileType: ['xls', 'xlsx'], //文件类型
        data: {
          //上传时附带的额外参数
          updateSupport: false,
        },
      },
      //上传图片
      uploadDataP: {
        url: '/common/upload', //文件上传地址
        multiple: false, //是否可以上传多个
        limit: 1,
        // fileType: ['xls', 'xlsx'],//文件类型
        data: {
          //上传时附带的额外参数
        },
        fileList: [],
        picture: '',
      },
      imgPath: '',
      //科室数据
      ksData: {
        placeholder: '请进行选择',
        key: '拼音简码', //第一列标题
        value: '科室名称', //第二列标题
        url: '/reception/register/getAllks', //请求连接
        bindValue: '',
        firstName: 'inputCode', //接口返回值对应第一列的参数名(不传默认为'inputCode')
        secondName: 'name', //接口返回值对应第二列的参数名(不传默认为'name')
        queryData: 'key',
        changeId: true,
      },
      //上传图片签名
      uploadDataS: {
        url: '/common/upload', //文件上传地址
        limit: 1,
        multiple: false, //是否可以上传多个
        // fileType: ['xls', 'xlsx'],//文件类型
        data: {
          //上传时附带的额外参数
        },
        picture: '',
        fileList: [],
      },
      // 分中心列表
      fzxOptions: [],
      // 是否为线上登记
      isOnline: false,
    }
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val)
    },
  },
  created() {
    if (this.$getCookie('isOnline') == '1' || this.$getCookie('cid') == 'ff8080817ee18637017ee77dc0322d8c') {
      this.isOnline = true
    }
    this.imgPath = Cookies.get('imgPath')
    // 获取分中心列表
    getBranchData().then(({ data }) => {
      this.fzxOptions = data
    })
    this.getListSection()
    this.getList()
    this.getDeptTree()
    this.getConfigKey('sys.user.initPassword').then((response) => {
      this.initPassword = response.msg
    })
  },
  methods: {
    //打开上传图片
    uploadPicture() {
      this.upload.titleP = '上传'
      this.upload.openP = true
    },
    //确认上传图片
    submitFileP() {
      var msg = this.$refs.uploadP.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.uploadP.handelUpload()
      } else if (msg === false) {
        this.uploadFinishP(0)
      } else {
        this.uploadFinishP(1)
      }
    },
    getListSection() {
      listSection().then((response) => {
        if (response) {
          this.departList = response.data.listQD
        }
      })
    },
    //接收图片返回地址
    uploadFinishP(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.upload.openP = false
      this.loading.close()
      this.loading = false
      this.form.picture = res.data
      return
    },
    //打开上传图片签名
    uploadPictureSignature() {
      this.upload.titleS = '上传'
      this.upload.openS = true
    },
    //确认上传图片签名
    submitFileS() {
      var msg = this.$refs.uploadS.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.uploadS.handelUpload()
      } else if (msg === false) {
        this.uploadFinishS(0)
      } else {
        this.uploadFinishS(1)
      }
    },
    //接收图片签名返回地址
    uploadFinishS(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.upload.openS = false
      this.loading.close()
      this.loading = false
      console.log(res)
      this.uploadDataS.picture = ''
      this.form.signPic = res.data
      return
    },

    /** 查询用户列表 */
    getList() {
      this.loading = true
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then((response) => {
        this.userList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then((response) => {
        this.deptOptions = response.data
      })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id
      this.handleQuery()
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === '0' ? '启用' : '停用'
      this.$modal
        .confirm('确认要"' + text + '""' + row.userName + '"用户吗？')
        .then(function () {
          return changeUserStatus(row.userId, row.status)
        })
        .then(() => {
          this.$modal.msgSuccess(text + '成功')
        })
        .catch(function () {
          row.status = row.status === '0' ? '1' : '0'
        })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        userId: undefined,
        deptId: undefined,
        userName: undefined,
        nickName: undefined,
        secretPassword: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: '0',
        remark: undefined,
        postIds: [],
        roleIds: [],
        signPic: undefined,
        picture: undefined,
      }
      this.uploadDataP.picture = undefined
      this.uploadDataS.picture = undefined
      this.$nextTick(() => {
        this.$refs.uploadP.resetUpload()
        this.$refs.uploadS.resetUpload()
      })
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.current = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.userId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 表格单击事件
    rowClick(row, col) {
      if (col.type != 'selection') this.$refs.userList.clearSelection()
      this.$refs.userList.toggleRowSelection(row)
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case 'handleResetPwd':
          this.handleResetPwd(row)
          break
        case 'handleAuthRole':
          this.handleAuthRole(row)
          break
        default:
          break
      }
    },
    //领取人改变方法
    selectChangeLQR(value) {
      console.log("上级的值",value);
      
      this.form.superiorId = value.id
    },
    //金蝶账号改变方法 
    selectChangeJD(value) {
      console.log("金蝶改变值",value);
      
      if (value.accountNo) {
        this.form.kingdeeAccountNo = value.accountNo
        this.form.kingdeeUseStatus = value.useStatusId
      } else {
        this.form.kingdeeAccountNo = ''
        this.form.kingdeeUseStatus = ''
      }
      console.log("kingdeeAccountNo值",this.form.kingdeeAccountNo);
      console.log("kingdeeUseStatus值",this.form.kingdeeUseStatus);
      

    },

    //职业危害因素值改变
    change1(value) {
      this.form.harmName = ''
      this.zyselectData.bindValue = value
      const data = []
      const name = []
      for (var i in value) {
        data.push(value[i].id)
        name.push(value[i].label)
      }
      this.form.harmClass = data
      this.form.harmName = name.join(',')
    },
    fzxChange(value) {
      this.fzxData.bindValue = value
      let useBranches = []
      this.useBranchesName = ''
      for (var i in value) {
        useBranches.push(value[i].branchId)
      }
      this.form.useBranches = useBranches
    },
    /** 新增按钮操作 */
    handleAdd() {
      getUser().then((response) => {
        this.postOptions = response.posts
        this.roleOptions = response.roles
        this.open = true

        this.title = '添加用户'
        this.form.password = this.initPassword
        this.form.secretPassword = ''
      })
    },
    ksChange(value) {
      this.form.ksIds = []
      this.form.ksIds = value
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const userId = row.userId || this.ids
      getUser(userId).then((response) => {
        this.form = response.data
        if (this.form.secretPassword) {
          this.form.secretPassword = '******'
        }
        if (!this.form.sdiscount) {
          this.form.sdiscount = undefined
        }
        if (!this.form.ldiscount) {
          this.form.ldiscount = undefined
        }

        if (response.deptList) {
          //中心数据
          let id = [],
            name = []
          for (var i in response.deptList) {
            id.push(response.deptList[i].id)
            name.push(response.deptList[i].name)
          }
          this.form.ksIds = id
          this.form.deptListName = name
        }

        this.postOptions = response.posts
        this.roleOptions = response.roles
        this.form.postIds = response.postIds
        this.form.roleIds = response.roleIds

        if (response.harmClass) {
          //危害因素数据
          let id = [],
            name = []
          for (var i in response.harmClass) {
            id.push(response.harmClass[i].id)
            name.push(response.harmClass[i].name)
          }
          this.form.harmClass = id
          this.form.harmClassName = name
        }
        //分中心数据
        if (response.useBranches) {
          //危害因素数据
          let id = [],
            name = []
          for (var i in response.useBranches) {
            id.push(response.useBranches[i].id)
            name.push(response.useBranches[i].name)
          }
          this.form.useBranches = id
          this.form.useBranchesName = name
        }
        if (this.form.picture) {
          this.uploadDataP.picture = this.imgPath + this.form.picture
        }
        if (this.form.signPic) {
          this.uploadDataS.picture = this.imgPath + this.form.signPic
        }
        this.open = true
        this.title = '修改用户'
        this.form.password = ''
      })
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.userName + '"的新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnClickModal: false,
        inputPattern: /^.{3,20}$/,
        inputErrorMessage: '用户密码长度必须介于 3 和 20 之间',
      })
        .then(({ value }) => {
          resetUserPwd(row.userId, value).then((response) => {
            this.$modal.msgSuccess('修改成功，新密码是：' + value)
          })
        })
        .catch(() => {})
    },
    /** 分配角色操作 */
    handleAuthRole: function (row) {
      const userId = row.userId
      this.$router.push('/system/user-auth/role/' + userId)
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.userId != undefined) {
            updateUser(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.uploadDataP.fileList = []
              this.uploadDataS.fileList = []
              this.open = false
              this.getList()
            })
          } else {
            addUser(this.form).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.uploadDataP.fileList = []
              this.uploadDataS.fileList = []
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userIds = row.userId || this.ids
      this.$modal
        .confirm('是否确认删除用户编号为"' + userIds + '"的数据项？')
        .then(function () {
          return delUser(userIds)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        'system/user/export',
        {
          ...this.queryParams,
        },
        `用户列表.xlsx`
        // user_${new Date().getTime()}
      )
    },
    // 金蝶同步用户更新
    handleKingDeeUserUpdate(type) {
      if (type == 1) {
        upgradeSaleer().then((res) => {
          this.$modal.msgSuccess(res.msg)
        })
      } else {
        updateUserSaleer({ cid: this.$getCookie('cid') }).then((res) => {
          this.$modal.msgSuccess(res.msg)
        })
      }
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '用户导入'
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/user/importTemplate', {}, `user_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + '</div>', '导入结果', { dangerouslyUseHTMLString: true })
      this.getList()
    },

    // 提交上传文件
    submitFileForm() {
      var msg = this.$refs.upload.isUpload()
      this.loading = this.$loading({
        lock: true,
        text: '加载中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      if (msg === true) {
        this.$refs.upload.handelUpload()
      } else {
        this.$alert(msg, '提示')
        this.loading.close()
      }
    },
    // 上传文件成功
    uploadFinish(value, res) {
      if (value == 1) {
        this.$modal.msgSuccess('上传成功!')
      } else {
        this.$modal.msgSuccess('上传失败!')
      }
      this.upload.open = false
      this.loading.close()
      this.loading = false
      this.getList()
      return
      if (value == 1) {
        var data = this.form
        if (res && res.data.urlList) data.filePath = res.data.urlList.join()
        if (data.id) {
          editListData(data).then(() => {
            this.$modal.msgSuccess('修改成功!')
            this.open = false
            this.resetting()
            this.$emit('getList')
            this.loading.close()
          })
        } else {
          addListData(data).then(() => {
            this.$modal.msgSuccess('添加成功!')
            this.open = false
            this.resetting()
            this.$emit('getList')
            this.loading.close()
          })
        }
      } else {
        this.loading.close()
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.setimg {
  display: flex;
  flex-direction: column;
  align-items: center;

  img {
    width: 100px;
    height: 100px;
    background: rgba($color: #000000, $alpha: 0.3);
  }

  .btn {
    margin: 8px;
    width: 70px;
    height: 36px;
  }
}
</style>

<style scoped>
#upload-p /deep/ .el-upload-dragger {
  padding: 0;
  border: 0;
  max-width: 80px;
}

#upload-s /deep/ .el-upload-dragger {
  padding: 0;
  border: 0;
  max-width: 80px;
}

#upload-p /deep/.el-upload-list {
  max-width: 80px;
}

#upload-s /deep/.el-upload-list {
  max-width: 80px;
}
</style>
