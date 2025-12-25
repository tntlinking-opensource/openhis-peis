package com.center.medical.finance.bean.dto;

import com.center.medical.common.annotation.Excel;
import com.center.medical.finance.bean.vo.AccountsInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 导出体检人员合计数据
 */
@Data
public class AccountsTotalDto implements Serializable {

    @Excel(name = "标题")
    @ApiModelProperty(value = "统计值")
    private String tjz;

    @Excel(name = "体检者人数")
    @ApiModelProperty(value = "体检者人数")
    private Double totalCount = 0.0;

    @Excel(name = "体检者年龄")
    @ApiModelProperty(value = "体检者年龄")
    private Double avgAge;

    @Excel(name = "未检人数")
    @ApiModelProperty(value = "未检人数")
    private Double noExamCount = 0.0;

    @Excel(name = "部分完成人数")
    @ApiModelProperty(value = "部分完成人数")
    private Double someExamCount = 0.0;

    @Excel(name = "全部完成人数")
    @ApiModelProperty(value = "全部完成人数")
    private Double allExamCount = 0.0;

    @Excel(name = "原价")
    @ApiModelProperty(value = "原价")
    private Double price;

    @Excel(name = "套餐原价")
    @ApiModelProperty(value = "套餐原价")
    private Double tcyj;

    @Excel(name = "套餐优惠价")
    @ApiModelProperty(value = "套餐优惠价")
    private Double tcyhj;

    @Excel(name = "统收实收")
    @ApiModelProperty(value = "统收实收")
    private Double ssts;

    @Excel(name = "康淘实收")
    @ApiModelProperty(value = "康淘实收")
    private Double sskt;

    @Excel(name = "其他实收")
    @ApiModelProperty(value = "其他实收")
    private Double ssqt;

    @Excel(name = "合计实收")
    @ApiModelProperty(value = "合计实收")
    private Double sshj;

    @Excel(name = "加项实收")
    @ApiModelProperty(value = "加项实收")
    private Double ssAdd;

    @Excel(name = "记账")
    @ApiModelProperty(value = "记账")
    private Double jz;


    public String getTjz() {
        return tjz;
    }

    public void setTjz(String tjz) {
        this.tjz = tjz;
    }

    public Double getTotalCount() {
        if (ObjectUtils.isEmpty(totalCount)){
            setTotalCount(0.0);
        }
        return totalCount;
    }

    public void setTotalCount(Double totalCount) {
        this.totalCount = totalCount;
    }

    public Double getAvgAge() {
        if (ObjectUtils.isEmpty(avgAge)){
            setAvgAge(0.0);
        }
        return avgAge;
    }

    public void setAvgAge(Double avgAge) {
        this.avgAge = avgAge;
    }

    public Double getNoExamCount() {
        if (ObjectUtils.isEmpty(noExamCount)){
            setNoExamCount(0.0);
        }
        return noExamCount;
    }

    public void setNoExamCount(Double noExamCount) {
        this.noExamCount = noExamCount;
    }

    public Double getSomeExamCount() {
        if (ObjectUtils.isEmpty(someExamCount)){
            setSomeExamCount(0.0);
        }
        return someExamCount;
    }

    public void setSomeExamCount(Double someExamCount) {
        this.someExamCount = someExamCount;
    }

    public Double getAllExamCount() {
        if (ObjectUtils.isEmpty(allExamCount)){
            setAllExamCount(0.0);
        }
        return allExamCount;
    }

    public void setAllExamCount(Double allExamCount) {
        this.allExamCount = allExamCount;
    }

    public Double getPrice() {
        if (ObjectUtils.isEmpty(price)){
            setPrice(0.0);
        }
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTcyj() {
        if (ObjectUtils.isEmpty(tcyj)){
            setTcyj(0.0);
        }
        return tcyj;
    }

    public void setTcyj(Double tcyj) {
        this.tcyj = tcyj;
    }

    public Double getTcyhj() {
        if (ObjectUtils.isEmpty(tcyhj)){
            setTcyhj(0.0);
        }
        return tcyhj;
    }

    public void setTcyhj(Double tcyhj) {
        this.tcyhj = tcyhj;
    }

    public Double getSsts() {
        if (ObjectUtils.isEmpty(ssts)){
            setSsts(0.0);
        }
        return ssts;
    }

    public void setSsts(Double ssts) {
        this.ssts = ssts;
    }

    public Double getSskt() {
        if (ObjectUtils.isEmpty(sskt)){
            setSskt(0.0);
        }
        return sskt;
    }

    public void setSskt(Double sskt) {
        this.sskt = sskt;
    }

    public Double getSsqt() {
        if (ObjectUtils.isEmpty(ssqt)){
            setSsqt(0.0);
        }
        return ssqt;
    }

    public void setSsqt(Double ssqt) {
        this.ssqt = ssqt;
    }

    public Double getSshj() {
        if (ObjectUtils.isEmpty(sshj)){
            setSshj(0.0);
        }
        return sshj;
    }

    public void setSshj(Double sshj) {
        this.sshj = sshj;
    }

    public Double getSsAdd() {
        if (ObjectUtils.isEmpty(ssAdd)){
            setSsAdd(0.0);
        }
        return ssAdd;
    }

    public void setSsAdd(Double ssAdd) {
        this.ssAdd = ssAdd;
    }

    public Double getJz() {
        if (ObjectUtils.isEmpty(jz)){
            setJz(0.0);
        }
        return jz;
    }

    public void setJz(Double jz) {
        this.jz = jz;
    }

    public AccountsTotalDto() {
    }

    public AccountsTotalDto(String tjz) {
        this.tjz = tjz;
    }


    public void addSelf(AccountsInfoVo item) {
        this.setTotalCount(getTotalCount() + 1);
        this.setAvgAge(getAvgAge() + getDoubleObj(item.getAge()));
        String line = item.getLine() == null ? "" : item.getLine().toString();
        if ("未检".equals(line)) {
            this.setNoExamCount(getNoExamCount() + 1);
        } else if ("部分".equals(line)) {
            this.setSomeExamCount(getSomeExamCount() + 1);
        } else if ("完成".equals(line)) {
            this.setAllExamCount(getAllExamCount() + 1);
        }
        this.setPrice(getPrice() + getDoubleObj(item.getPrice()));
        this.setTcyj(getTcyj() + getDoubleObj(item.getTcyj()));
        this.setTcyhj(getTcyhj() + getDoubleObj(item.getTcyhj()));
        this.setSsts(getSsts() + getDoubleObj(item.getSsts()));
        this.setSskt(getSskt() + getDoubleObj(item.getSskt()));
        this.setSsqt(getSsqt() + getDoubleObj(item.getSsqt()));
        this.setSshj(getSshj() + getDoubleObj(item.getSshj()));
        this.setSsAdd(getSsAdd() + getDoubleObj(item.getSsAdd()));
        this.setJz(getJz() + getDoubleObj(item.getJz()));
    }



    public double getDoubleObj(Object obj) {
        return obj == null ? 0.0 : Double.parseDouble(obj.toString());
    }


    public void avg(AccountsTotalDto item) {
        if (ObjectUtils.isNotEmpty(item.getTotalCount())) {
            this.setAvgAge(this.dev(item.getAvgAge(), item.getTotalCount()));
            this.setPrice(this.dev(item.getPrice(), item.getTotalCount()));
            this.setTcyj(this.dev(item.getTcyj(), item.getTotalCount()));
            this.setTcyhj(this.dev(item.getTcyhj(), item.getTotalCount()));
            this.setSsts(this.dev(item.getSsts(), item.getTotalCount()));
            this.setSskt(this.dev(item.getSskt(), item.getTotalCount()));
            this.setSsqt(this.dev(item.getSsqt(), item.getTotalCount()));
            this.setSshj(this.dev(item.getSshj(), item.getTotalCount()));
            this.setSsAdd(this.dev(item.getSsAdd(), item.getTotalCount()));
            this.setJz(this.dev(item.getJz(), item.getTotalCount()));
        }
    }

    public double dev(Object obj1, Object obj2) {
        if ("0.0".equals(obj1.toString()) || "0.0".equals(obj2.toString())) return 0.0;
        BigDecimal b1 = new BigDecimal(obj1.toString());
        BigDecimal b2 = new BigDecimal(obj2.toString());

        //除法，四舍五入
        BigDecimal aa = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
        return aa.doubleValue();
    }



    // 转换对象字段值为 ArrayList
    public ArrayList<Object> toArrayList() {
        ArrayList<Object> fieldValues = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // 允许访问私有字段
            try {
                fieldValues.add(field.get(this)); // 获取字段值并添加到 ArrayList
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldValues;
    }
}
