package com.mkm.springstatemachinefun.service;

import com.mkm.springstatemachinefun.consts.UnitPackEvents;
import com.mkm.springstatemachinefun.consts.UnitPackStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.ws.ServiceMode;

@Service
public class Start {

    @Autowired
    StateMachine<UnitPackStates, UnitPackEvents> stateMachine;

    @PostConstruct
    public void start(){
        stateMachine.sendEvent(UnitPackEvents.CONNECT);
    }

}
