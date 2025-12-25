package com.center.medical.center.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.center.common.bean.model.RilinTotalVerdict;
import com.center.medical.center.common.dao.RilinTotalVerdictMapper;
import com.center.medical.center.common.service.RilinTotalVerdictService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ZJ总检结论词表(TotalVerdict)服务实现类
 * @since 2025-03-24 11:21:22
 */
@Slf4j
@Service("rilinTotalVerdictService")
@RequiredArgsConstructor
public class RilinTotalVerdictServiceImpl extends ServiceImpl<RilinTotalVerdictMapper, RilinTotalVerdict> implements RilinTotalVerdictService {

	private final RilinTotalVerdictMapper rilinTotalVerdictMapper;

	/**
	 * 按体检号删除
	 * @param patientcodes
	 */
	@Override
	@DataSource(value = DataSourceType.RILIN)
	public void removeByPatientcodesRilin(@Param("patientcodes") List<String> patientcodes){
		rilinTotalVerdictMapper.removeByPatientcodes(patientcodes);
	}

}
