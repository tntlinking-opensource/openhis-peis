# 一、DICOM接收图片配置及设置说明

## 1.新机器配置

- 新配置文件`application-xx.yml`，并根据现场环境配置以下信息

```text
dicom:
    ip: #默认为本机IP则此字段不用设置
    port: 104 #监听的端口号
    callingAET: ARISTOSCR #AETitle
    mianApiUrl: http://XXX.XXX.XXX.XXX:8080 # 体检系统接口地址
```

# 二、问题汇总

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