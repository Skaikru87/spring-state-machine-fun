package com.mkm.springstatemachinefun.send;

import com.mkm.springstatemachinefun.consts.UnitPackEvents;
import com.mkm.springstatemachinefun.consts.UnitPackStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
@WithStateMachine
public class EndPackingTask implements Action<UnitPackStates, UnitPackEvents> {
    @Override
    public void execute(StateContext<UnitPackStates, UnitPackEvents> stateContext) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
