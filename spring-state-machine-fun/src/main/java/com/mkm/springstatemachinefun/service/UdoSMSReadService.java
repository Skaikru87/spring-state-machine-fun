package com.mkm.springstatemachinefun.service;

import com.mkm.springstatemachinefun.model.socket.ClientsPLC;
import com.mkm.springstatemachinefun.model.socket.PLC;
import com.mkm.springstatemachinefun.model.udosms.UdoSMSMessage;
import com.mkm.springstatemachinefun.model.udosms.UdoSMSParser;
import com.mkm.springstatemachinefun.utils.Colours;
import com.mkm.springstatemachinefun.utils.socketUtils.SocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class UdoSMSReadService {

    @Autowired
    @Qualifier("taskExecutorBean")
    Executor taskExecutorBean;

    @Autowired
    ClientsPLC clientsPLC;

    @Autowired
    SocketUtils socketUtils;

    @Async
    @EventListener(ContextRefreshedEvent.class)
    public void socketListener() {
        log.info("start async socketListener");

        clientsPLC.getPlcClients().getObservable()
                .onErrorReturn(e -> null)
                .subscribe(plc -> handleSocket(plc));
    }

    private void handleSocket(PLC plc) {
        log.info("handling socket: {}", plc.getSocket());

        Runnable listen = () -> {
            while (true) {
                try {

                    String retrieveMessage = socketUtils.read(plc.getSocket());
                    List<UdoSMSMessage> udoSMSMessage = UdoSMSParser.parseMessage(retrieveMessage, plc.getSocket());
                    for (UdoSMSMessage sms : udoSMSMessage) {

                        log.info(Colours.ANSI_CYAN + "received: {}" + Colours.ANSI_RESET, sms.toUdoSMS() != null ? sms.toUdoSMS() : "not udo sms: " + retrieveMessage);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        taskExecutorBean.execute(listen);
    }

}
