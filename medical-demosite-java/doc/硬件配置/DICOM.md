# DICOM接收图片配置及设置说明

## 1. 转化jpeg报错

- 问题

```text
java.lang.RuntimeException: No Reader for format: jpeg-cv registered
```

- 解决办法

```text
更改硬件的图片输出压缩格式（联系厂商解决）
```

## 2. 收费项目不存在

- 问题

```text
com.world,medical.comon,exception,ServiceException: dicon接收收费项目不存在,体检号: 010230000099部位: BRAN接口类型:MR  
```

- 解决办法

```text
配置pacs收费项目的检查部位，如上报错信息中，配置该收费的检查部位为MR(核磁)的BRAN(头)部位
```