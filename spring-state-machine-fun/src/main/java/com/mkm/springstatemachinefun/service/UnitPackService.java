package com.mkm.springstatemachinefun.service;

import com.mkm.springstatemachinefun.consts.UnitPackEvents;
import com.mkm.springstatemachinefun.consts.UnitPackStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.ws.ServiceMode;

//@Service
@Slf4j
@WithStateMachine
public class UnitPackService{

    @Autowired
    StateMachine<UnitPackStates, UnitPackEvents> stateMachine;

//    @PostConstruct
    public void connect(){

        stateMachine.sendEvent(UnitPackEvents.CONNECT);


    }

    public void startPacking() {
        stateMachine.sendEvent(UnitPackEvents.INIT);
//        stateMachine.sendEvent(UnitPackEvents.SEND_CASETTE_POSITION);
//        stateMachine.sendEvent(UnitPackEvents.FINISHING_TASK);
    }


    public void startPacking(String casettePosition) {
        stateMachine.getExtendedState().getVariables().put("casettePosition", casettePosition);
        stateMachine.sendEvent(UnitPackEvents.INIT);
        stateMachine.sendEvent(UnitPackEvents.SEND_CASETTE_POSITION);
        stateMachine.sendEvent(UnitPackEvents.FINISHING_TASK);
    }

    public void reset() {
        stateMachine.sendEvent(UnitPackEvents.RESSETING);
    }
}
