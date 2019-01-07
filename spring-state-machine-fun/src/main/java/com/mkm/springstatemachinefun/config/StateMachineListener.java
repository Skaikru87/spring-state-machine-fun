package com.mkm.springstatemachinefun.config;

import com.mkm.springstatemachinefun.consts.UnitPackEvents;
import com.mkm.springstatemachinefun.consts.UnitPackStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Slf4j
public class StateMachineListener extends StateMachineListenerAdapter<UnitPackStates, UnitPackEvents> {

    @Override
    public void stateChanged(State<UnitPackStates, UnitPackEvents> from, State<UnitPackStates, UnitPackEvents> to) {
        log.info("state changed from: {} to {}", from==null ? "null" : from.getId().name(), to.getId().name());
    }

    @Override
    public void transitionStarted(Transition<UnitPackStates, UnitPackEvents> transition) {
        log.info("transition started! target state: {}", transition.getTarget().getId().name());
    }
}
