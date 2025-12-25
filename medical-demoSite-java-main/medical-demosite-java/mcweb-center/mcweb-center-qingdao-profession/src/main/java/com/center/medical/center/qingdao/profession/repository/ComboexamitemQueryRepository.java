package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.dto.ComboexamitemDto;

import java.util.List;

public interface ComboexamitemQueryRepository {
    List<ComboexamitemDto> list(String JHYS, String ZYTJLB);

    List<ComboexamitemDto> list(String patientcode, String JHYS, String ZYTJLB);
}
