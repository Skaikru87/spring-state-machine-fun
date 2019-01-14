package com.mkm.springstatemachinefun.controler;

import com.mkm.springstatemachinefun.service.UnitPackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
@Slf4j
public class StartMachineControler {

    @Autowired
    UnitPackService unitPackService;

    @GetMapping("/connect")
    public String connectUnitPack(){
        log.info("starting from controler");
        unitPackService.connect();
        return "connected";
    }

    @GetMapping("/start")
    public String startPacking(){
        log.info("started packing");
        unitPackService.startPacking();
        return "started";
    }

    @GetMapping("/start/{casettePosition}")
    public String startPacking(@PathVariable String casettePosition){
        log.info("started packing");
        unitPackService.startPacking(casettePosition);
        return "started";
    }

}
