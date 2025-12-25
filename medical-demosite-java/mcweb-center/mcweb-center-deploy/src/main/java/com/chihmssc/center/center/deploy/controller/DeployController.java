package com.center.medical.center.deploy.controller;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.center.deploy.bean.dto.DeployVersionDto;
import com.center.medical.center.deploy.service.DeployService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动更新
 * @author xhp
 * @since 2023-11-22 14:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
//@Api(tags = "自动更新")
@RequestMapping("/open/api/deploy")
//@Validated
public class DeployController {
    private final DeployService deployService;

    @PostMapping("/start")
    @ResponseBody
    public String start(@RequestBody DeployVersionDto deployVersionDto){
        log.info(JSONUtil.toJsonStr(deployVersionDto));
        Thread thread=new Thread(
                () -> {
                    log.info("开始更新");
                    log.info("等待服务关闭...");
                    ThreadUtil.sleep(1000*5);

                    try{

                        try{
                            //将下载的jar包复制到指定位置
                            deployService.copyJar(deployVersionDto);

                            //启动服务
                            deployService.startService(deployVersionDto);

                            //调用服务接口，判断是否更新成功
                            deployService.checkService(deployVersionDto);
                        }catch (Exception e){
                            //调用主服务接口，保存更新结果
                            deployService.saveRecord(deployVersionDto,0,e.getMessage());
                            throw e;
                        }

                        //调用主服务接口，保存更新结果
                        deployService.saveRecord(deployVersionDto,1,null);

                    }catch (Exception e){
                        log.error("更新失败",e);
                    }finally {
                        //关闭自己，本服务只有在更新的时候才启动
                        deployService.shutdown();
                    }
                }
        );
        thread.start();

        Map<String,Object> map=new HashMap<>();
        map.put("code",200);
        map.put("msg","启动成功");
        return JSONUtil.toJsonStr(map);
    }
}
