### PACS src/views/PACS
- PACS登记/register/index.vue
  > PACS登记信息查询 -读取身份证(PACS:register:read) -清空(PACS:register:clear) -刷新(PACS:register:flash)   
    -完成预约(PACS:register:booking) -完成注册(PACS:register:register) -新增(PACS:register:add) -删除(PACS:register:delete) 
    -保存(PACS:register:reserve)  -刷新(PACS:register:flash)
- PACS登记信息查询 /select/index.vue
  > PACS登记 -导出(PACS:register:export) -导入(PACS:register:import) -提取(PACS:register:extract)   -导出DCM(PACS:register:exportDCM)
- PACS数字钼靶 /molybdenumTarget/index.vue 
  > PACS放射科(CT) -高清屏(PACS:molybdenumTarget:HDScreen) -单张删除(PACS:molybdenumTarget:signDelete) -看大图          (PACS:molybdenumTarget:bigImg) -单张删除(PACS:molybdenumTarget:signDelete) -清除(PACS:molybdenumTarget:clear)
    -上传DICOM(PACS:molybdenumTarget:uploadDICOM) -复制(PACS:molybdenumTarget:copy)
    -历史(PACS:molybdenumTarget:history) -获取平安接口(PACS:molybdenumTarget:getInterface) 
    -保存(PACS:molybdenumTarget:reserve) -审核(PACS:molybdenumTarget:review) 
    -反审核(PACS:molybdenumTarget:antiReview) -预览(PACS:molybdenumTarget:preview) 
    -生成报告(PACS:molybdenumTarget:report)
- PACS磁共振 /magneticResonance/index.vue 
  > PACS放射科(CT) -高清屏(PACS:magneticResonance:HDScreen) -单张删除(PACS:magneticResonance:signDelete) -看大图          (PACS:magneticResonance:bigImg) -单张删除(PACS:magneticResonance:signDelete) -清除(PACS:magneticResonance:clear)
    -上传DICOM(PACS:magneticResonance:uploadDICOM) -复制(PACS:magneticResonance:copy)
    -历史(PACS:magneticResonance:history) -获取平安接口(PACS:magneticResonance:getInterface) 
    -保存(PACS:magneticResonance:reserve) -审核(PACS:magneticResonance:review) 
    -反审核(PACS:magneticResonance:antiReview) -预览(PACS:magneticResonance:preview) 
    -生成报告(PACS:magneticResonance:report)  
- PACS放射科(CT)  /radiology_department/index.vue 
  > PACS放射科(CT) -高清屏(PACS:radiologyDepartment:HDScreen) -单张删除(PACS:radiologyDepartment:signDelete) -看大图          (PACS:radiologyDepartment:bigImg) -单张删除(PACS:radiologyDepartment:signDelete) -清除(PACS:radiologyDepartment:clear)
    -上传DICOM(PACS:radiologyDepartment:uploadDICOM) -复制(PACS:radiologyDepartment:copy)
    -历史(PACS:radiologyDepartment:history) -获取平安接口(PACS:radiologyDepartment:getInterface) 
    -保存(PACS:radiologyDepartment:reserve) -审核(PACS:radiologyDepartment:review) 
    -反审核(PACS:radiologyDepartment:antiReview) -预览(PACS:radiologyDepartment:preview) 
    -生成报告(PACS:radiologyDepartment:report) 
- PACS放射科(CR)  /radiology_department/index.vue 
  > PACS放射科(CT) -高清屏(PACS:radiologyDepartment:HDScreen) -单张删除(PACS:radiologyDepartment:signDelete) -看大图          (PACS:radiologyDepartment:bigImg) -单张删除(PACS:radiologyDepartment:signDelete) -清除(PACS:radiologyDepartment:clear)
    -上传DICOM(PACS:radiologyDepartment:uploadDICOM) -复制(PACS:radiologyDepartment:copy)
    -历史(PACS:radiologyDepartment:history) -获取平安接口(PACS:radiologyDepartment:getInterface) 
    -保存(PACS:radiologyDepartment:reserve) -审核(PACS:radiologyDepartment:review) 
    -反审核(PACS:radiologyDepartment:antiReview) -预览(PACS:radiologyDepartment:preview) 
    -生成报告(PACS:radiologyDepartment:report) 
- PACS放射科(DR)  /radiology_department/index.vue 
  > PACS放射科(CT) -高清屏(PACS:radiologyDepartment:HDScreen) -单张删除(PACS:radiologyDepartment:signDelete) -看大图          (PACS:radiologyDepartment:bigImg) -单张删除(PACS:radiologyDepartment:signDelete) -清除(PACS:radiologyDepartment:clear)
    -上传DICOM(PACS:radiologyDepartment:uploadDICOM) -复制(PACS:radiologyDepartment:copy)
    -历史(PACS:radiologyDepartment:history) -获取平安接口(PACS:radiologyDepartment:getInterface) 
    -保存(PACS:radiologyDepartment:reserve) -审核(PACS:radiologyDepartment:review) 
    -反审核(PACS:radiologyDepartment:antiReview) -预览(PACS:radiologyDepartment:preview) 
    -生成报告(PACS:radiologyDepartment:report)    
- PACS基础配置 /basicPACS
  - 部位维护 /place_maintain/index.vue
    > 权限配置 -添加(basicPACS:placeMaintain:add) -编辑(basicPACS:placeMaintain:edit) -删除(basicPACS:placeMaintain:delete)
  - PACS收费项目 /charge/index.vue
    > 权限配置 -添加(basicPACS:charge:add) -编辑(basicPACS:charge:edit) -删除(basicPACS:charge:delete)
  - PACS检查项目 /inspect/index.vue
    > 权限配置 -添加(basicPACS:inspect:add) -编辑(basicPACS:inspect:edit) -查看(basicPACS:inspect:view)   -删除(basicPACS:inspect:remove)
  - 图像存储配置 /storage_config/index.vue
    > 权限配置 -添加(basicPACS:storageConfig:add) -编辑(basicPACS:storageConfig:edit) -删除(basicPACS:storageConfig:delete)   -应用(basicPACS:storageConfig:apply)