package com.mkm.springstatemachinefun.config;

import com.mkm.springstatemachinefun.consts.UnitPackEvents;
import com.mkm.springstatemachinefun.consts.UnitPackStates;
import com.mkm.springstatemachinefun.send.EndPackingTask;
import com.mkm.springstatemachinefun.send.SendConnect;
import com.mkm.springstatemachinefun.send.SendInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachine
public class UnitPackStateMachineConfig extends StateMachineConfigurerAdapter<UnitPackStates, UnitPackEvents> {



    @Override
    public void configure(StateMachineStateConfigurer<UnitPackStates, UnitPackEvents> states) throws Exception {
        states.withStates()
            .initial(UnitPackStates.NOT_CONNECTED)
            .states(EnumSet.allOf(UnitPackStates.class))
            .end(UnitPackStates.PACKING_TASK_DONE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<UnitPackStates, UnitPackEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(UnitPackStates.NOT_CONNECTED).target(UnitPackStates.CONNECTED).event(UnitPackEvents.CONNECT).action(sendConnect())
                .and()
                .withExternal()
                .source(UnitPackStates.CONNECTED).target(UnitPackStates.INIT).event(UnitPackEvents.INIT).action(init())
                .and()
                .withExternal()
                .source(UnitPackStates.INIT).target(UnitPackStates.PACKING_TASK_DONE).event(UnitPackEvents.FINISHING_TASK).action(new EndPackingTask())
                ;
    }




    @Override
    public void configure(StateMachineConfigurationConfigurer<UnitPackStates, UnitPackEvents> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener());
    }




//    @Bean
//    public EndPackingTask endPackingTask() {
//        return new EndPackingTask();
//    }

    @Bean
    public SendConnect sendConnect(){
        return new SendConnect();
    }

    @Bean
    public SendInit init(){
        return new SendInit();
    }
}
