package com.mkm.springstatemachinefun.utils.messagesUtils;

import com.mkm.springstatemachinefun.utils.Colours;
import com.mkm.springstatemachinefun.utils.socketUtils.SocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.List;


@Slf4j
//@Component
@WithStateMachine
public class MessageUtils {

//    @Autowired
//    UnitPackState unitPackState;

    @Autowired
    SocketUtils socketUtils;

    public void readAnswer(List<String> messages, Socket socket) throws IOException {
        String answer;
        while (!MessageUtils.findDN(messages)) {
            answer = socketUtils.read(socket);
            splitAndPrint(messages, answer);


        }
    }

    public void readAnswer(List<String> messages, Socket socket, String uid) throws IOException {
        String answer;
        while (!MessageUtils.findDNForUid(messages, uid)) {
            answer = socketUtils.read(socket);
            splitAndPrint(messages, answer);
        }
    }

    private static boolean findDNForUid(List<String> messages, String uid) {

        for (String msg : messages) {
            if ((msg.contains("&DN&") || msg.contains("&RE&"))&&(msg.contains(uid))

                    //po otrzymaniu banera musimy czekać na taki IF
                    || msg.contains("UNIT_PACK_CHECK_ERROR_DONE")

            ) return true;
        }
        return false;
    }

//    public synchronized void updateUnitPackState(String answer) {
//
//        if (answer != null) {
//            if (answer.contains("INIT_NOT_DONE")) {
//                unitPackState.setNotInitialized(true);
//                log.info("setting notInitialized: TRUE");
//            }
//
//            if(answer.contains("UNIT_PACK_WAITING_FOR_CONTINUE")){
//                unitPackState.setWaitingForContinue(true);
//                log.info(" setting unitPackState: waitingForContinue=true");
//            }
//
//            if(answer.contains("UNIT_PACK_READY_TO_PRINT")){
//                unitPackState.setReadyToPrint(true);
//                log.info(" setting unitPackState: readyToPrint=true");
//            }
//            if(answer.contains("OPEN_SACHET_FAULTY_OPENED")){
//                unitPackState.setFaultyOpened(true);
//                log.info(" setting FaultyOpened: faultyOpened=true");
//            }
//        }
//    }

    public static boolean findDN(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("&DN&")
                    || msg.contains("&RE&")
                    //po otrzymaniu banera musimy czekać na taki IF
                    || msg.contains("UNIT_PACK_CHECK_ERROR_DONE")

            ) return true;
        }
        return false;

    }

    public static boolean findRE(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("&RE&")) return true;
        }
        return false;
    }

    public static void showAnswer(String answer) {
        if (answer.contains("&ER")) {
            log.info(Colours.ANSI_RED + "answer: {}" + Colours.ANSI_RESET, answer);
        } else {
            log.info(Colours.ANSI_CYAN + "answer: {}" + Colours.ANSI_RESET, answer);
        }
    }

    public static boolean isSachetOpen(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("UNIT_PACK_SACHET_IS_OPEN")) return true;
        }
        return false;
    }


    public static boolean isReadyToPrint(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("UNIT_PACK_READY_TO_PRINT")) return true;
        }
        return false;
    }

    public static boolean isWaitingForContinue(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("UNIT_PACK_WAITING_FOR_CONTINUE")) return true;
        }
        return false;
    }

    public static boolean isTrainReadyForInspection(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("TRAIN_READY_FOR_INSPECTION")) return true;
        }
        return false;
    }

    public static boolean isReadyForDrug(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("UNIT_PACK_READY_FOR_DRUG")) return true;
        }
        return false;
    }

    public static boolean isSachetPushed(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("UNIT_PACK_SACHET_PUSHED_TO_CASSETTE")) return true;
        }
        return false;
    }

    public static boolean isFaultyOpened(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("OPEN_SACHET_FAULTY_OPENED")) return true;
            if (msg.contains("SACHET_FAULTY")) return true;
        }
        return false;
    }

    public static boolean isTrainDropSachet(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("TRAIN_DROP_SACHET")) return true;
        }
        return false;
    }

    public static boolean isNotInitialized(List<String> messages) {
        for (String msg : messages) {
            if (msg.contains("INIT_NOT_DONE")) return true;
        }
        return false;
    }


//    public synchronized void waitForReadyToPrint(List<String> messages, Socket socket) throws IOException {
//        String answer;
//        messages.clear();
//        while (!MessageUtils.isReadyToPrint(messages) || !unitPackState.isReadyToPrint()) {
//            answer = socketUtils.read(socket);
//
//            splitAndPrint(messages, answer);
//        }
//    }


    public  synchronized void waitForWaitingForContinue(List<String> messages, Socket socket) throws IOException {
        String answer;
        messages.clear();
        while (!MessageUtils.isWaitingForContinue(messages)) {
            answer = socketUtils.read(socket);
            splitAndPrint(messages, answer);
        }
    }

    public  void waitForReadyForDrug(List<String> messages, Socket socket) throws IOException {
        String answer;
        messages.clear();
        while (!MessageUtils.isReadyForDrug(messages)) {
            answer = socketUtils.read(socket);
            splitAndPrint(messages, answer);
            if (MessageUtils.isFaultyOpened(messages)) break;
        }
    }

    public  void waitForSachetDropped(List<String> messages, Socket socket) throws IOException {
        String answer;
        messages.clear();
        while (!MessageUtils.isTrainDropSachet(messages)) {
            answer = socketUtils.read(socket);
            splitAndPrint(messages, answer);
        }
    }

    public  void waitForSachetPushedToCassette(List<String> messages, Socket socket) throws IOException {
        String answer;
        messages.clear();
        while (!MessageUtils.isSachetPushed(messages)) {
            answer = socketUtils.read(socket);
            splitAndPrint(messages, answer);
        }
    }


    private static void splitAndPrint(List<String> messages, String answer) {
        String[] separate = answer.split("#");
        if (separate.length > 1) {
            for (int j = 0; j < separate.length; j++) {
                messages.add(separate[j] + "#");
                showAnswer("" + separate[j] + "#");
            }
        } else {
            messages.add(answer);
            showAnswer(answer);
        }
    }
}
