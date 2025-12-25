

## 查询报告内容


**接口地址**:`/report/reportContent/getData`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>查询报告内容</p>



**请求参数**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|analyzeId|样本id(团检报告查询使用)|query|false|string||
|compareReportId|对比报告表ID(对比报告使用)|query|false|string||
|idExamtype|体检类型,0.健康体检 1.职业体检 2.综合 3.复查|query|false|string||
|ksId|科室id(Pacs报告查询使用)|query|false|string||
|patientcode|体检号(个检报告查询使用等)|query|false|string||
|reportType|报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 7.Pacs报告 8.职业结果告知书 9.检验报告(除检验科的)|query|false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|R|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|code|状态码:[操作成功=200, 对象创建成功=201,请求已经被接受=202, 操作已经执行成功，但是没有返回数据=204, 资源已被移除=301, 重定向=303, 资源没有被修改=304,参数列表错误（缺少，格式不匹配）=400, 未授权=401, 访问受限，授权过期=403, 资源，服务未找到=404, 不允许的http方法=405,资源冲突，或者资源被锁=409, 不支持的数据，媒体类型=415, 系统内部错误=500, 接口未实现=501]|integer(int32)|integer(int32)|
|data|响应数据|object||
|msg|响应消息|string||


**响应示例**:
```javascript
{
	"code": 0,
	"data": {},
	"msg": ""
}
```

{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "head": {
      "patientname": "王四英",
      "pageNum": 3,
      "parameters": {
        "hotline": "15987458964",
        "numorgresv": "",
        "reportdate": "2025-10-11",
        "org_zipcode": "410000",
        "idPatientclass": "1",
        "head": "/newimage/images/avator/12/20250926/45deccccb9da49ba8ec194053ad1c31e.jpg",
        "patientarchive": "1202700007991",
        "org_address": "长沙市开福区三-大道500号沃德长华体检中心",
        "marriage": "",
        "totaloffer": "1、您本次体检所检项目未见明显异常\n  建议您定期体检。\n\n",
        "org_phone": "0731-88510000",
        "department": "",
        "barcode": "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\nHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\nMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAmAMoDASIA\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+vK/\nGmrala2viE2+oXcJi1O5SMxzMuxRojSgDB4Ak+fH97nrzXqleP8Ajv8A49PEv/YVuv8A0wNQBylr\n4l15rXVi2t6kSmmWToTdP8rNol1KxHPBMiq5PdlB6jNbf9u6x/wrv7V/at99o/56/aH3/wDIv+b1\nzn/WfP8A73zdea5Cz/49NZ/7BVh/6YLut/8A5pj/AJ/6FugCp4h8S69Do/jJ4tb1KN7fU9TSFkun\nBjVLvT1ULzwAsjgAdA7Dua2/iZrusWH/AAlX2PVb628jf5Xk3Dp5f/IJ+7g8f62Xp/z0f+8c8h4l\n/wCQH45/7Cuq/wDpbplb/wAV/wDmcP8Atp/7hqANDxTrusW/g/xvPBqt9FNbeX5EiXDq0WdWvIzt\nIPy/Iirx/CoHQCjTNd1iTxFqMT6rfNGniW6gVDcOQsY1DTUCAZ+6FkkUDph2HQms/wAX/wDIkeP/\nAPtl/wCnq+o0n/kZ9T/7Gu7/APTlpVAGh4W13WLjwf4Inn1W+lmufM8+R7h2aXGrWcY3En5vkdl5\n/hYjoTXEf8JZ4k/4Q/z/APhINV87/hGvP8z7bJu8z+1vL35z97Z8ueu3jpXT+EP+RI8Af9tf/T1Y\n1wH/ADJH/cqf+5qgD2vWdW1KLx1c28eoXaQDU4kEazMFCmXRwRjOMYllGP8Apo/945w/CGu6xc/2\np9o1W+l2f8Ivs8y4dtvneT5uMnjfk7v72TnNaGuf8lDuv+wrD/6N0Ouf8E/8xf8A7lH/ANoUAVLX\nxLrzWurFtb1IlNMsnQm6f5WbRLqViOeCZFVye7KD1Ga2/EOu6xB8Ur6zi1W+jtV8S6FAsKXDhBHJ\nBIZEC5xtYgEjocc1yFn/AMems/8AYKsP/TBd1v8Aib/kr2of9jX4e/8ASeWgDQ8La7rFx4w8EQT6\nrfSw3PmefG9w7LLjSbOQbgT83zuzc/xMT1JrE0bxLr0vgW2uJNb1J5zpkrmRrpyxYRawQc5znMUR\nz/0zT+6MW/CH/I7+AP8Atr/6ZbGsDQ/+SeWv/YKm/wDRWuUAdf4v13WLb+y/s+q30W//AISjf5dw\n67vJ87ys4POzA2/3cDGKxLrxLry2ukldb1IF9MvXci6f5mXRLWVSeeSJGZwezMT1Oat+Nv8AmEf9\nzd/7XrAvP+PTRv8AsFX/AP6YLSgDr9T13WI/DunSpqt8sj+GrWdnFw4LSHT9ScuTn7xaONieuUU9\nQKPFOu6xb+D/ABvPBqt9FNbeX5EiXDq0WdWvIztIPy/Iirx/CoHQCs/Vv+RY0z/sVLT/ANNuq0eL\n/wDkSPH/AP2y/wDT1fUAaH9u6x/wsT7L/at99n/55faH2f8AIweV0zj/AFfyf7vy9OKxPFniXXrb\nR7d4Nb1KJz4HsLosl06kzNdwq0nB++VJBbqQSKt/81O/z/0MlYHjL/kB23/ZP9N/9LYaAPS7XVtS\na61YNqF2Qmp2SIDM3yq2t3UTAc8AxqqEd1UDoMVxHgvxLr11deHhca3qUwl0y2eQSXTtvY62sRJy\neSY/kz/d46cV1dn/AMfes/8AYVsP/T/d1wHgT/j78Nf9gq1/9P60AfT9FFFABXj/AI7/AOPTxL/2\nFbr/ANMDV7BVOfSdNuhKLjT7SYSuXkEkKtvYx+UScjkmP5M/3eOnFAHzZZ/8ems/9gqw/wDTBd1v\n/wDNMf8AP/Qt17YPDWgqHC6JpoDoqOBap8yrGYlB45AjZkA7KxHQ4qT+wtH+y/Zf7Ksfs/8Azy+z\nps/1XldMY/1fyf7vy9OKAPnDxL/yA/HP/YV1X/0t0yt/4r/8zh/20/8AcNXtkvhrQZknSXRNNkS4\nd3mV7VCJGdlZi3HJLRoST1KKewqS70LR7/zvtmlWNz5/+t863R/M+597I5/1UXX/AJ5p/dGADw/x\nf/yJHj//ALZf+nq+o0n/AJGfU/8Asa7v/wBOWlV7hNoWj3EFxBPpVjLDc48+N7dGWXDtINwI+b53\nZuf4mJ6k0JoWjxytKmlWKyPKZ2cW6AtIWRy5OPvFo42J65RT1AoA8P8ACH/IkeAP+2v/AKerGuA/\n5kj/ALlT/wBzVfTdnN4NzdWNmNGCaGm+dIo4xHYgyFzkgbUIeHeRwQUDHHBrPjbwHLpczrodoIIn\n/s9rVtEdZSeJ/KWAx72HzCXAUjq3YkAHJ65/yUO6/wCwrD/6N0Ouf8E/8xf/ALlH/wBoV65e6n4U\nivbSWeO0nnvnhkinhtDOCZHjWJ2kRSEDNHEFZiATEuCdnEdvd+DrfUbuygtrG3kiiE08n2Ly4Slq\nVUHzSojbySVHDHyyMcEHAB4RZ/8AHprP/YKsP/TBd1v+Jv8Akr2of9jX4e/9J5a9QhufBL2Fzcx6\nTAqrKtlLAdGkWd3EGFjEJj8x8QSHACnEZb+HNWL678HR6ja3FxbWNxdX0ttPHcxWXn7nJ8u3leRF\nIXJYqjsR/FtPBoA8n8If8jv4A/7a/wDplsawND/5J5a/9gqb/wBFa5XvdtL4Qiv5PJt9Nt59LSRx\nObZYliVEWKUxyFQpCKqRuUJ2bVVsYAqvZv4Lk0a6mh0q0hs7J/sssEulNCyMykiMRNGGJYXLAKFO\n7ziBkuQQDyvxt/zCP+5u/wDa9YF5/wAemjf9gq//APTBaV7u134Ov/7MMltYzreRNNas9luVUus5\nLEriLziSvz7fMbK/McitQ+GtBYIG0TTSERkQG1T5VaMRMBxwDGqoR3VQOgxQB4nq3/IsaZ/2Klp/\n6bdVo8X/APIkeP8A/tl/6er6vcH0LR5IlifSrFo0iECobdCFjCugQDH3QskigdMOw6E0TaFo9xBc\nQT6VYyw3OPPje3Rllw7SDcCPm+d2bn+JiepNAHh//NTv8/8AQyVgeMv+QHbf9k/03/0thr6P/sLR\n/tX2r+yrH7R/z1+zpv8A9b5vXGf9Z8/+983Xmo5vDWg3KBJ9E02VBbrahXtUYCFWDLHyPuBgCF6A\ngGgDzOz/AOPvWf8AsK2H/p/u64DwJ/x9+Gv+wVa/+n9a+kxpOmqXK6faAu6u5EK/MyyGVSeOSJGZ\nwezMT1OarweGtBtTEbfRNNhMSBIzHaouxRJ5oAwOAJPnx/e5680AalFFFABRRRQAUUUUAFFFFABR\nRRQBydppuvWPirU9Qt9K0ZLO5t1ijVL51Ysj3EgdlEGAZGn+bk7fmOXPFU7LQfElnPBrbWulSa8Z\nZheqb6QQTxypECynycxspt4VVdrDYp3MzNuoooAkh0DXobXQtMeHTZtMt3S5vVF28ZWYSiRUhXyj\n+4ib7iZUkJGpIVWDyRaP4k/4SNdWcWKSQxSI+y9kdLz5AqqI5Im+yKWVHPlO33cMJDhgUUAV7LQf\nElnPBrbWulSa8ZZheqb6QQTxypECynycxspt4VVdrDYp3MzNurLtPAOuabexy2VzHCLhLeS6aHVr\nmJLWcOfNMMAUxugi8uONZPlAiQMrDIJRQBqWPh7xJZf2XbxJpSW+hxSRWMwuJDLdRjCRxTfuwEUx\njLlSw81I3CkJsMdl4Q15XkmbULS0T7RHPHYXDPqUXmKuPNeVvJlkfhNokZwmwEZwnllFAFPTPBfi\nTTPDy6CX0qe3u4rQXd2JpIpbaSNEiZ4F2MCyxwxFGJUiVS+MYVfSKKKACiiigAooooAKKKKACiii\ngD//2Q==",
        "org": "",
        "sex": "女",
        "patientcode": "120232060340",
        "idcardno": "430523197708152362",
        "topImg": "/www/wwwroot/XXX.XXX.XXX.8080/temp/file/wordmodel/ks-i/topImg.jpg",
        "pageNum": 2,
        "patientname": "王四英",
        "stampPic": "/newimage/images/common/12/20250804/7b2c66d766d64cbdaa91ce452c04d900.png",
        "phone": "18817148516",
        "signPic": "/newimage/images/common/12/20250823/f70d0682210a49af82e1c2954c969400.png",
        "background": "/www/wwwroot/XXX.XXX.XXX.8080/temp/file/wordmodel/ks-i/health_common_cover.jpg",
        "workunit": "",
        "totaldoctorid": "ff80808186e35d0f01870d3a869f2e61",
        "titleList": [
          {
            "title": "部    门：",
            "value": ""
          },
          {
            "title": "体检号码：",
            "value": "120232060340",
            "barcode": "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\nHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\nMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAeAMoDASIA\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+vH/\nAB3/AMeniX/sK3X/AKYGr2CvH/Hf/Hp4l/7Ct1/6YGoA4Cz/AOPTWf8AsFWH/pgu63/+aY/5/wCh\nbrAs/wDj01n/ALBVh/6YLut//mmP+f8AoW6AMDxL/wAgPxz/ANhXVf8A0t0yt/4r/wDM4f8AbT/3\nDVgeJf8AkB+Of+wrqv8A6W6ZW/8AFf8A5nD/ALaf+4agA8X/APIkeP8A/tl/6er6jSf+Rn1P/sa7\nv/05aVR4v/5Ejx//ANsv/T1fUaT/AMjPqf8A2Nd3/wCnLSqADwh/yJHgD/tr/wCnqxrgP+ZI/wC5\nU/8Ac1Xf+EP+RI8Af9tf/T1Y1wH/ADJH/cqf+5qgD1/XP+Sh3X/YVh/9G6HXP+Cf+Yv/ANyj/wC0\nK6DXP+Sh3X/YVh/9G6HXP+Cf+Yv/ANyj/wC0KAMCz/49NZ/7BVh/6YLut/xN/wAle1D/ALGvw9/6\nTy1gWf8Ax6az/wBgqw/9MF3W/wCJv+Svah/2Nfh7/wBJ5aADwh/yO/gD/tr/AOmWxrA0P/knlr/2\nCpv/AEVrlb/hD/kd/AH/AG1/9MtjWBof/JPLX/sFTf8AorXKAN/xt/zCP+5u/wDa9YF5/wAemjf9\ngq//APTBaVv+Nv8AmEf9zd/7XrAvP+PTRv8AsFX/AP6YLSgDf1b/AJFjTP8AsVLT/wBNuq0eL/8A\nkSPH/wD2y/8AT1fUat/yLGmf9ipaf+m3VaPF/wDyJHj/AP7Zf+nq+oAP+anf5/6GSsDxl/yA7b/s\nn+m/+lsNb/8AzU7/AD/0MlYHjL/kB23/AGT/AE3/ANLYaAO/s/8Aj71n/sK2H/p/u64DwJ/x9+Gv\n+wVa/wDp/Wu/s/8Aj71n/sK2H/p/u64DwJ/x9+Gv+wVa/wDp/WgD6fooooAK8f8AHf8Ax6eJf+wr\ndf8ApgavYK8f8d/8eniX/sK3X/pgagDgLP8A49NZ/wCwVYf+mC7rf/5pj/n/AKFusCz/AOPTWf8A\nsFWH/pgu63/+aY/5/wChboAwPEv/ACA/HP8A2FdV/wDS3TK3/iv/AMzh/wBtP/cNWB4l/wCQH45/\n7Cuq/wDpbplb/wAV/wDmcP8Atp/7hqADxf8A8iR4/wD+2X/p6vqNJ/5GfU/+xru//TlpVHi//kSP\nH/8A2y/9PV9RpP8AyM+p/wDY13f/AKctKoAPCH/IkeAP+2v/AKerGuA/5kj/ALlT/wBzVd/4Q/5E\njwB/21/9PVjXAf8AMkf9yp/7mqAPX9c/5KHdf9hWH/0bodc/4J/5i/8A3KP/ALQroNc/5KHdf9hW\nH/0bodc/4J/5i/8A3KP/ALQoAwLP/j01n/sFWH/pgu63/E3/ACV7UP8Asa/D3/pPLWBZ/wDHprP/\nAGCrD/0wXdb/AIm/5K9qH/Y1+Hv/AEnloAPCH/I7+AP+2v8A6ZbGsDQ/+SeWv/YKm/8ARWuVv+EP\n+R38Af8AbX/0y2NYGh/8k8tf+wVN/wCitcoA3/G3/MI/7m7/ANr1gXn/AB6aN/2Cr/8A9MFpW/42\n/wCYR/3N3/tesC8/49NG/wCwVf8A/pgtKAN/Vv8AkWNM/wCxUtP/AE26rR4v/wCRI8f/APbL/wBP\nV9Rq3/IsaZ/2Klp/6bdVo8X/APIkeP8A/tl/6er6gA/5qd/n/oZKwPGX/IDtv+yf6b/6Ww1v/wDN\nTv8AP/QyVgeMv+QHbf8AZP8ATf8A0thoA7+z/wCPvWf+wrYf+n+7rgPAn/H34a/7BVr/AOn9a7+z\n/wCPvWf+wrYf+n+7rgPAn/H34a/7BVr/AOn9aAPp+iiigD//2Q=="
          },
          {
            "title": "姓    名：",
            "value": "王四英"
          },
          {
            "title": "性    别：",
            "value": "女"
          },
          {
            "title": "年    龄：",
            "value": "48"
          },
          {
            "title": "电    话：",
            "value": "18817148516"
          }
        ],
        "header": "/www/wwwroot/XXX.XXX.XXX.8080/temp/file/wordmodel/ks-i/tjbg_logo.jpg",
        "zxdh": "0731-88510000",
        "age": "48",
        "dateregister": "2025-10-10 07:37:12",
        "totaldoctor": "总检老师"
      },
      "idPatientClass": "1"
    },
    "ksList": [
      {
        "head": {
          "part": 0,
          "sjbggs": 1,
          "inspectionFooter": "本次检验结果获山东省通用认证(“一单通”)。",
          "yblx": "检验科"
        },
        "summary": {
          "P_CONCLUSIONS": "未见异常",
          "CONCLUSIONS": [
            {
              "conclu": "未见异常"
            }
          ],
          "timeName": 0,
          "part": 2,
          "sjbggs": 1,
          "reviewer": 0,
          "inspectionFooter": "本次检验结果获山东省通用认证(“一单通”)。"
        },
        "item": [
          {
            "DATE": "2025-10-10 19:55:39",
            "jy_rummager_path": "/newimage/images/common/12/20240806/f18f4041-2fb7-443d-9e57-2ba450dc51be.png",
            "RECEIVE_DATE": "2025-10-10 19:55:39",
            "part": 1,
            "sjbggs": 1,
            "TXT_VALUES": "\n\n\n\n",
            "jy_audit_path": "/newimage/images/common/12/20240806/320e6fe2-1f42-4673-aa02-89fcd5988bb7.png",
            "EXAMLIST": [
              {
                "red": "0",
                "ITEM": "C-肽释放试验(1h)CS",
                "UNIT": "",
                "SIGN": "ng/ml",
                "RESULT": "2.00",
                "CONSULT": ""
              },
              {
                "red": "0",
                "ITEM": "C-肽释放试验(0.5h)CS",
                "UNIT": "",
                "SIGN": "ng/ml",
                "RESULT": "1.30",
                "CONSULT": ""
              },
              {
                "red": "0",
                "ITEM": "C-肽释放试验(3h)CS",
                "UNIT": "",
                "SIGN": "ng/ml",
                "RESULT": "2.10",
                "CONSULT": ""
              },
              {
                "red": "0",
                "ITEM": "C-肽释放试验(2h)",
                "UNIT": "",
                "SIGN": "ng/ml",
                "RESULT": "2.10",
                "CONSULT": ""
              },
              {
                "red": "0",
                "ITEM": "C-肽释放试验(空腹)",
                "UNIT": "1.0-4.8",
                "SIGN": "ng/ml",
                "RESULT": "1.0",
                "CONSULT": ""
              }
            ],
            "inspectionFooter": "本次检验结果获山东省通用认证(“一单通”)。",
            "ITEM_NAME": "C-肽释放试验                      "
          }
        ],
        "picture": []
      }
    ],
    "end": {
      "miniProgramCode": "/newimage/images/common/12/20250723/d84c35e5487c406f9763fdf49502feeb.jpg",
      "qrcode": "/newimage/images/common/12/20250723/7288bf0d7e744d6d8e164dcf18b3e1e5.png",
      "logo": "/newimage/images/common/12/20250723/aee92fad69454507aa3ff270e17839bb.png"
    },
    "endPicture": [],
    "threeReport": []
  }
}

{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "head": {
      "patientname": "孙新玲",
      "pageNum": 3,
      "parameters": {
        "hotline": "15987458964",
        "numorgresv": "",
        "reportdate": "2025-10-10",
        "org_zipcode": "410000",
        "idPatientclass": "1",
        "head": "/newimage/images/avator/12/20250927/4479aad222cf4f6eb1139360cf5d7900.jpg",
        "patientarchive": "1202100008053",
        "org_address": "长沙市开福区三-大道500号沃德长华体检中心",
        "marriage": "",
        "totaloffer": "1、右侧颈动脉斑块形成\n  动脉斑块形成跟年龄、血压、血脂、血糖、还有饮食生活习惯有很大的关系，以上因素造成人体动脉硬化，血管弹性下降，血黏度增高，从而在动脉血管的分叉处，膨大处形成漩涡，损伤血管壁，使血细胞、血脂在此沉积，日积月累形成颈动脉斑块。如斑块脱落，易并发心脑血管危象。建议至心血管内科咨询诊治，软化血管，稳定斑块治疗，防止并发症。定期复查。请建立并养成良好的生活方式与习惯，低盐、低脂、低糖、高纤维饮食，戒烟酒；适量运动，保持心情舒畅，保证充足睡眠。\n\n",
        "org_phone": "0731-88510000",
        "department": "",
        "barcode": "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\nHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\nMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAmAMoDASIA\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+vK/\nGmrala2viE2+oXcJi1O5SMxzMuxRojSgDB4Ak+fH97nrzXqleP8Ajv8A49PEv/YVuv8A0wNQBylr\n4l15rXVi2t6kSmmWToTdP8rNol1KxHPBMiq5PdlB6jNbf9u6x/wrv7V/at99o/56/aH3/wDIv+b1\nzn/WfP8A73zdea5Cz/49NZ/7BVh/6YLut/8A5pj/AJ/6FugCp4h8S69Do/jJ4tb1KN7fU9TSFkun\nBjVLvT1ULzwAsjgAdA7Dua2/iZrusWH/AAlX2PVb628jf5Xk3Dp5f/IJ+7g8f62Xp/z0f+8c8h4l\n/wCQH45/7Cuq/wDpbplb/wAV/wDmcP8Atp/7hqANDxTrusW/g/xvPBqt9FNbeX5EiXDq0WdWvIzt\n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",
        "org": "",
        "sex": "女",
        "patientcode": "120232060359",
        "idcardno": "430104196507103024",
        "topImg": "/www/wwwroot/XXX.XXX.XXX.8080/temp/file/wordmodel/ks-i/topImg.jpg",
        "pageNum": 2,
        "patientname": "孙新玲",
        "stampPic": "/newimage/images/common/12/20250804/7b2c66d766d64cbdaa91ce452c04d900.png",
        "phone": "13875806480",
        "signPic": "/newimage/images/common/12/20250823/f70d0682210a49af82e1c2954c969400.png",
        "background": "/www/wwwroot/XXX.XXX.XXX.8080/temp/file/wordmodel/ks-i/health_common_cover.jpg",
        "workunit": "",
        "totaldoctorid": "ff80808186e35d0f01870d3a869f2e61",
        "titleList": [
          {
            "title": "部    门：",
            "value": ""
          },
          {
            "title": "体检号码：",
            "value": "120232060359",
            "barcode": "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\nHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\nMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAeAMoDASIA\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+vH/\nAB3/AMeniX/sK3X/AKYGr2CvH/Hf/Hp4l/7Ct1/6YGoA4Cz/AOPTWf8AsFWH/pgu63/+aY/5/wCh\nbrAs/wDj01n/ALBVh/6YLut//mmP+f8AoW6AMDxL/wAgPxz/ANhXVf8A0t0yt/4r/wDM4f8AbT/3\nDVgeJf8AkB+Of+wrqv8A6W6ZW/8AFf8A5nD/ALaf+4agA8X/APIkeP8A/tl/6er6jSf+Rn1P/sa7\nv/05aVR4v/5Ejx//ANsv/T1fUaT/AMjPqf8A2Nd3/wCnLSqADwh/yJHgD/tr/wCnqxrgP+ZI/wC5\nU/8Ac1Xf+EP+RI8Af9tf/T1Y1wH/ADJH/cqf+5qgD1/XP+Sh3X/YVh/9G6HXP+Cf+Yv/ANyj/wC0\nK6DXP+Sh3X/YVh/9G6HXP+Cf+Yv/ANyj/wC0KAMCz/49NZ/7BVh/6YLut/xN/wAle1D/ALGvw9/6\nTy1gWf8Ax6az/wBgqw/9MF3W/wCJv+Svah/2Nfh7/wBJ5aADwh/yO/gD/tr/AOmWxrA0P/knlr/2\nCpv/AEVrlb/hD/kd/AH/AG1/9MtjWBof/JPLX/sFTf8AorXKAN/xt/zCP+5u/wDa9YF5/wAemjf9\ngq//APTBaVv+Nv8AmEf9zd/7XrAvP+PTRv8AsFX/AP6YLSgDf1b/AJFjTP8AsVLT/wBNuq0WX/NQ\nf+5n/wDbOjVv+RY0z/sVLT/026rRZf8ANQf+5n/9s6ADQP8AmCf9w/8A9wNYF5/yQXRv+wVf/wDp\n0tK39A/5gn/cP/8AcDWBef8AJBdG/wCwVf8A/p0tKAO/s/8Aj71n/sK2H/p/u64DwJ/x9+Gv+wVa\n/wDp/Wu/s/8Aj71n/sK2H/p/u64DwJ/x9+Gv+wVa/wDp/WgD6fooooAK8f8AHf8Ax6eJf+wrdf8A\npgavYK8f8d/8eniX/sK3X/pgagDgLP8A49NZ/wCwVYf+mC7rf/5pj/n/AKFusCz/AOPTWf8AsFWH\n/pgu63/+aY/5/wChboAwPEv/ACA/HP8A2FdV/wDS3TK3/iv/AMzh/wBtP/cNWB4l/wCQH45/7Cuq\n/wDpbplb/wAV/wDmcP8Atp/7hqADxf8A8iR4/wD+2X/p6vqNJ/5GfU/+xru//TlpVHi//kSPH/8A\n2y/9PV9RpP8AyM+p/wDY13f/AKctKoAPCH/IkeAP+2v/AKerGuA/5kj/ALlT/wBzVd/4Q/5EjwB/\n21/9PVjXAf8AMkf9yp/7mqAPX9c/5KHdf9hWH/0bodc/4J/5i/8A3KP/ALQroNc/5KHdf9hWH/0b\nodc/4J/5i/8A3KP/ALQoAwLP/j01n/sFWH/pgu63/E3/ACV7UP8Asa/D3/pPLWBZ/wDHprP/AGCr\nD/0wXdb/AIm/5K9qH/Y1+Hv/AEnloAPCH/I7+AP+2v8A6ZbGsDQ/+SeWv/YKm/8ARWuVv+EP+R38\nAf8AbX/0y2NYGh/8k8tf+wVN/wCitcoA3/G3/MI/7m7/ANr1gXn/AB6aN/2Cr/8A9MFpW/42/wCY\nR/3N3/tesC8/49NG/wCwVf8A/pgtKAN/Vv8AkWNM/wCxUtP/AE26rRZf81B/7mf/ANs6NW/5FjTP\n+xUtP/TbqtFl/wA1B/7mf/2zoANA/wCYJ/3D/wD3A1gXn/JBdG/7BV//AOnS0rf0D/mCf9w//wBw\nNYF5/wAkF0b/ALBV/wD+nS0oA7+z/wCPvWf+wrYf+n+7rgPAn/H34a/7BVr/AOn9a7+z/wCPvWf+\nwrYf+n+7rgPAn/H34a/7BVr/AOn9aAPp+iiigD//2Q=="
          },
          {
            "title": "姓    名：",
            "value": "孙新玲"
          },
          {
            "title": "性    别：",
            "value": "女"
          },
          {
            "title": "年    龄：",
            "value": "60"
          },
          {
            "title": "电    话：",
            "value": "13875806480"
          }
        ],
        "header": "/www/wwwroot/XXX.XXX.XXX.8080/temp/file/wordmodel/ks-i/tjbg_logo.jpg",
        "zxdh": "0731-88510000",
        "age": "60",
        "dateregister": "2025-10-10 10:04:17",
        "totaldoctor": "总检老师"
      },
      "idPatientClass": "1"
    },
    "ksList": [
      {
        "head": {
          "part": 0,
          "sjbggs": 7,
          "DEP_NAME": "彩超"
        },
        "summary": {
          "P_CONCLUSIONS": "[双侧颈动脉彩超]所见：\n双侧颈总动脉、颈内动脉、颈外动脉内径正常，双侧颈动脉内-中膜稍毛糙不增厚，于右侧颈总动脉窦部后壁可见混合回声斑块附着，范围约9.4×1.5mm，内光点粗，分布欠均匀。\r\n双侧椎动脉内径分别约： 3.8mm（左侧）、4.0mm（右侧）。\nCDFI：上述斑块附着处血流绕行，余血管管腔血流充盈完整，频谱未见明显异常。\n\n\n\n[双侧颈动脉彩超]提示：\n1、右侧颈动脉斑块形成\n",
          "audit_time": "2025-10-10",
          "ksId": "143",
          "CONCLUSIONS": [
            {
              "conclu": "[双侧颈动脉彩超]所见："
            },
            {
              "conclu": "双侧颈总动脉、颈内动脉、颈外动脉内径正常，双侧颈动脉内-中膜稍毛糙"
            },
            {
              "conclu": "不增厚，于右侧颈总动脉窦部后壁可见混合回声斑块附着，范围约9.4×1"
            },
            {
              "conclu": ".5mm，内光点粗，分布欠均匀。"
            },
            {
              "conclu": "双侧椎动脉内径分别约： 3.8mm（左侧）、4.0mm（右侧）。"
            },
            {
              "conclu": "CDFI：上述斑块附着处血流绕行，余血管管腔血流充盈完整，频谱未见"
            },
            {
              "conclu": "明显异常。"
            },
            {
              "conclu": "[双侧颈动脉彩超]提示："
            },
            {
              "conclu": "1、右侧颈动脉斑块形成"
            }
          ],
          "timeName": 0,
          "audit_path": "/newimage/images/common/12/20240806/782636bc-f8f9-465d-8859-a9949e462d64.png",
          "part": 2,
          "sjbggs": 7,
          "reviewer": 0,
          "rummager_path": "/newimage/images/common/12/20240806/782636bc-f8f9-465d-8859-a9949e462d64.png"
        },
        "picture": [
          {
            "image": "/newimage/images/image/12/CC/20251010/497b1d8a61b54b4198dbcdc51b1ff16c.png",
            "part": 3,
            "picSize": 2,
            "image2": "/newimage/images/image/12/CC/20251010/a296824ece7842bbb6c52c30f62ee566.png"
          }
        ]
      }
    ],
    "end": {
      "miniProgramCode": "/newimage/images/common/12/20250723/d84c35e5487c406f9763fdf49502feeb.jpg",
      "qrcode": "/newimage/images/common/12/20250723/7288bf0d7e744d6d8e164dcf18b3e1e5.png",
      "logo": "/newimage/images/common/12/20250723/aee92fad69454507aa3ff270e17839bb.png"
    },
    "endPicture": [],
    "threeReport": []
  }
}