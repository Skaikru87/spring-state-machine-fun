package com.mkm.springstatemachinefun.send;

import com.mkm.springstatemachinefun.consts.UnitPackEvents;
import com.mkm.springstatemachinefun.consts.UnitPackStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Slf4j

public class SendConnect implements Action<UnitPackStates, UnitPackEvents> {

    @Autowired
    StateMachine <UnitPackStates, UnitPackEvents> stateMachine;

    @Override
    public void execute(StateContext<UnitPackStates, UnitPackEvents> stateContext) {

        log.info("connecting to unit pack v2");
        log.info("sleeping");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stateMachine.sendEvent(UnitPackEvents.INIT);
    }


}
