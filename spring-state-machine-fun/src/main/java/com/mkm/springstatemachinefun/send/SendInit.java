package com.mkm.springstatemachinefun.send;

import com.mkm.springstatemachinefun.consts.UnitPackEvents;
import com.mkm.springstatemachinefun.consts.UnitPackStates;
import com.mkm.springstatemachinefun.model.socket.PLC;
import com.mkm.springstatemachinefun.model.udosms.UdoSMSMessage;
import com.mkm.springstatemachinefun.model.udosms.consts.MessageState;
import com.mkm.springstatemachinefun.model.udosms.consts.ModuleCommand;
import com.mkm.springstatemachinefun.model.udosms.consts.ModuleName;
import com.mkm.springstatemachinefun.utils.messagesUtils.IDGenerator;
import com.mkm.springstatemachinefun.utils.messagesUtils.MessageUtils;
import com.mkm.springstatemachinefun.utils.socketUtils.SocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@WithStateMachine
public class SendInit implements Action<UnitPackStates, UnitPackEvents> {

    @Autowired
    MessageUtils messageUtils;

    @Autowired
    PLC plc;

    @Autowired
    IDGenerator idGenerator;

    @Override
    public void execute(StateContext<UnitPackStates, UnitPackEvents> stateContext) {

        log.info("initializing unit pack v2");

//        String msg = "$089b6bf&0000077&PAKOWARKA&INITIALIZATION&QQ&#";

        UdoSMSMessage udoSMSMessage = new UdoSMSMessage();
        udoSMSMessage.setCommandId(idGenerator.cycleIdGenerated());
        udoSMSMessage.setCycleId(idGenerator.getNewCommandID());
        udoSMSMessage.setModuleName(ModuleName.PAKOWARKA);
        udoSMSMessage.setModuleCommand(ModuleCommand.INITIALIZATION);
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(Boolean.TRUE.toString().toUpperCase());
        udoSMSMessage.setModuleParameters(parameters);

//        udoSMSMessage.setModuleParameters(new ArrayList<>(Collections.singletonList(Boolean.TRUE.toString().toUpperCase())));
        udoSMSMessage.setMessageState(MessageState.QQ);

        try {
            List<String> messages = new LinkedList<>();
            Socket socket = plc.getSocket();
//            SocketUtils.write(msg, socket);
            SocketUtils.write(udoSMSMessage.toUdoSMS(),socket);
//            messageUtils.readAnswer(messages, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        StateMachine<UnitPackStates, UnitPackEvents> stateMachine = stateContext.getStateMachine();
//
//        stateMachine.sendEvent(UnitPackEvents.FINISHING_TASK);
    }


}
