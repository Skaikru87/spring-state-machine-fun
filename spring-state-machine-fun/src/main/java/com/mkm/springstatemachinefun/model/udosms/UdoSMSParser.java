package com.mkm.springstatemachinefun.model.udosms;

import com.mkm.springstatemachinefun.model.udosms.consts.*;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * https://unitdoseone.atlassian.net/wiki/spaces/UDO/pages/56725365/Komunikacja+Protok+UnitDoseSMS
 */
@Slf4j
public class UdoSMSParser {

    private static final String MESSAGE_DELIMITER = "#";
    private static final String FIELD_DELIMITER = "&";
    private static final int MINIMUM_FIELDS_NUMBER = 5;

    public static ArrayList<UdoSMSMessage> parseMessage(String message, Socket socket/*, PLCAddress[] plcAddresses*/) {

        try {

            ArrayList<UdoSMSMessage> udoSMSMessages = new ArrayList<>();

            message = message.replace("\n", "");

            String[] rawMessages = message.split(MESSAGE_DELIMITER);

            for (String rawMessage : rawMessages) {


                UdoSMSMessage udoSMSMessage = new UdoSMSMessage();

                if (!(rawMessage.charAt(0) == '$')) {
//                log.warn("This message has no start marker. Skipping.");
//                notValidMessage(messages, udoSMSMessage, "This message has no start marker at position 0. Skipping.");
                    continue;
                }

                rawMessage = rawMessage.substring(1, rawMessage.length() - 1);
                String[] fields = rawMessage.split(FIELD_DELIMITER);

                if (fields.length < MINIMUM_FIELDS_NUMBER) {
                    notValidMessage(udoSMSMessages, udoSMSMessage, "Total fields number is fewer than minimum.");
                    continue;
                }

                try {
                    udoSMSMessage.setCycleId(fields[0]);
                    udoSMSMessage.setCommandId(fields[1]);
                    udoSMSMessage.setModuleName(ModuleName.valueOf(fields[2]));
                    udoSMSMessage.setModuleCommand(ModuleCommand.valueOf(fields[3]));
                    udoSMSMessage.setMessageState(MessageState.valueOf(fields[fields.length - 1]));
                } catch (IllegalArgumentException e) {
                    log.warn("Could not match. {} for message: \n {}", e.getMessage(), message);
                    notValidMessage(udoSMSMessages, udoSMSMessage, "Could not match value of field to defined ones. Reason:" + '\n' + e.getMessage());
                    continue;
                }

                if (fields.length > 5) {

                    int additionalFields = fields.length - 5;
                    String[] params = new String[additionalFields];
                    for (int a = 0; a < additionalFields; a++) {
                        params[a] = fields[4 + a];
                    }
                    ArrayList<String> paramsList = new ArrayList<>();
                    paramsList.addAll(Arrays.asList(params));
                    udoSMSMessage.setModuleParameters(paramsList);
                } else
                    udoSMSMessage.setModuleParameters(new ArrayList<>());

                udoSMSMessage.setValidMessage(true);
                udoSMSMessage.setMessageStatus(MessageStatus.NOT_PROCESSED);
                udoSMSMessage.setCreationTimestamp(System.currentTimeMillis());

                udoSMSMessage.setMessageSource(MessageSource.RECEIVED);
                udoSMSMessage.setSendStatus(SendStatus.RECEIVED);

                try {
                    udoSMSMessage.setSourceHostAddr(socket.getInetAddress().getHostAddress());
                    udoSMSMessage.setSourceHostPort(Integer.toString(socket.getPort()));

                    log.trace("Set received message source port as: {}", udoSMSMessage.getSourceHostPort());
                    log.trace("Set received message source addr as: {}", udoSMSMessage.getSourceHostAddr());

//                for (PLCAddress plcAddress : plcAddresses) {
//                    if (plcAddress.getAddress().equals(socket.getInetAddress().getHostAddress()) &&
//                            plcAddress.getPort().equals(Integer.toString(socket.getPort()))) {
//                        udoSMSMessage.setTargetPLCType(plcAddress.getPlcType());
//                    }
//                }
                } catch (NullPointerException e) {
                    log.trace("Passed null socket to parser.");
                }

                udoSMSMessages.add(udoSMSMessage);

            }

            log.trace("Parsed {} valid messages.", udoSMSMessages.stream().filter(s -> s.isValidMessage()).collect(Collectors.toList()).size());
            log.trace("Parsed {} not valid messages.", udoSMSMessages.stream().filter(s -> !s.isValidMessage()).collect(Collectors.toList()).size());
            return udoSMSMessages;

        } catch (Exception e) {
            log.trace("Exception: {} while parsing.", e.getClass().getSimpleName());
            return new ArrayList<>();
        }

    }

    private static void notValidMessage(ArrayList<UdoSMSMessage> messages, UdoSMSMessage udoSMSMessage, String error) {
        udoSMSMessage.setValidMessage(false);
        udoSMSMessage.setMessageStatus(MessageStatus.NOT_PROCESSED);
        udoSMSMessage.setCreationTimestamp(System.currentTimeMillis());
        udoSMSMessage.setParsingError(error);
        messages.add(udoSMSMessage);
        log.warn("Just set valid message to: {}", udoSMSMessage.isValidMessage());
    }
}
