package com.mkm.springstatemachinefun.send;

import com.mkm.springstatemachinefun.consts.UnitPackEvents;
import com.mkm.springstatemachinefun.consts.UnitPackStates;
import com.mkm.springstatemachinefun.model.socket.ClientsPLC;
import com.mkm.springstatemachinefun.model.socket.PLC;
import com.mkm.springstatemachinefun.utils.messagesUtils.MessageUtils;
import com.mkm.springstatemachinefun.utils.socketUtils.SocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@WithStateMachine
public class SendConnect implements Action<UnitPackStates, UnitPackEvents> {

//    @Autowired
//    StateMachine <UnitPackStates, UnitPackEvents> stateMachine;

    @Autowired
    MessageUtils messageUtils;

    @Autowired
    SocketUtils socketUtils;

    @Autowired
    PLC plc;

    @Autowired
    ClientsPLC clientsPLC;

    @Value("${PAKOWARKA_HOST}")
    String host = "10.71.31.159";
    @Value("${PAKOWARKA_PORT}")
    String port = "1500";
    String timeout = "0";
//    @Getter
//    Socket socket = null;

    @Override
    public void execute(StateContext<UnitPackStates, UnitPackEvents> stateContext) {

        Socket socket = new Socket();
        log.info("connecting to unit pack v2");
        List<String> messages = new LinkedList<>();
//        if (socket == null) {
            try {
                socket = SocketUtils.connect(host, port, timeout);
                plc.setSocket(socket);
                clientsPLC.getPlcClients().add(plc);

                log.info("connected to: {}", socket.getInetAddress());
                log.info("waiting for check error done");
//                messageUtils.readAnswer(messages, socket);

//                StateMachine<UnitPackStates, UnitPackEvents> stateMachine = stateContext.getStateMachine();
//                stateMachine.sendEvent(UnitPackEvents.INITIALIZED);
            } catch (IOException e) {
//                throw new UnitPackNotConnectedException(e.getMessage());
                e.printStackTrace();
            }
//        }
    }

}



