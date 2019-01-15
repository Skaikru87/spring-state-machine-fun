package com.mkm.springstatemachinefun.config;

import com.mkm.springstatemachinefun.consts.UnitPackEvents;
import com.mkm.springstatemachinefun.consts.UnitPackStates;
import com.mkm.springstatemachinefun.send.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

    @Autowired
    SendConnect sendConnect;
    @Autowired
    SendInit sendInit;
    @Autowired
    EndPackingTask endPackingTask;
    @Autowired
    SendCasettePosition sendCasettePosition;
    @Autowired
    SendReset sendReset;


    @Override
    public void configure(StateMachineStateConfigurer<UnitPackStates, UnitPackEvents> states) throws Exception {
        states.withStates()
                .initial(UnitPackStates.NOT_CONNECTED)
                .states(EnumSet.allOf(UnitPackStates.class));
//            .end(UnitPackStates.PACKING_TASK_DONE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<UnitPackStates, UnitPackEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(UnitPackStates.NOT_CONNECTED).target(UnitPackStates.CONNECTED).event(UnitPackEvents.CONNECT).action(sendConnect)
                .and()
                .withExternal()
                .source(UnitPackStates.CONNECTED).target(UnitPackStates.INITIALIZED).event(UnitPackEvents.INIT).action(sendInit)
                .and()
                .withExternal().source(UnitPackStates.PACKING_TASK_DONE).target(UnitPackStates.INITIALIZED).event(UnitPackEvents.INIT).action(sendInit)
                .and()
                .withExternal().source(UnitPackStates.INITIALIZED).target(UnitPackStates.CASSETTE_POSITION).event(UnitPackEvents.SEND_CASETTE_POSITION).action(sendCasettePosition)
                .and()
                .withExternal()
                .source(UnitPackStates.CASSETTE_POSITION).target(UnitPackStates.PACKING_TASK_DONE).event(UnitPackEvents.FINISHING_TASK).action(endPackingTask)
                .and().withExternal().target(UnitPackStates.RESET_COLD).event(UnitPackEvents.RESSETING).action(sendReset)

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

//    @Bean
//    public SendConnect sendConnect(){
//        return new SendConnect();
//    }

//    @Bean
//    public SendInit init(){
//        return new SendInit();
//    }
}
