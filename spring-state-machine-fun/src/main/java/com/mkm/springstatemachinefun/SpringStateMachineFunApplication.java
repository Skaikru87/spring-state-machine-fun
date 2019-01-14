package com.mkm.springstatemachinefun;

import com.mkm.springstatemachinefun.consts.UnitPackEvents;
import com.mkm.springstatemachinefun.consts.UnitPackStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;

//@EnableStateMachine
@SpringBootApplication
public class SpringStateMachineFunApplication  {

//	public final StateMachine<UnitPackStates, UnitPackEvents> stateMachine;
//
//	@Autowired
//	public SpringStateMachineFunApplication(StateMachine<UnitPackStates, UnitPackEvents> stateMachine) {
//		this.stateMachine = stateMachine;
//	}




	public static void main(String[] args) {
		SpringApplication.run(SpringStateMachineFunApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
////		stateMachine.start();
////
////		stateMachine.sendEvent(UnitPackEvents.CONNECT);
////		stateMachine.sendEvent(UnitPackEvents.INITIALIZED);
////		stateMachine.sendEvent(UnitPackEvents.FINISHING_TASK);
//	}

}

