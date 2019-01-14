package com.mkm.springstatemachinefun.model.udosms;

import com.google.gson.Gson;
import com.mkm.springstatemachinefun.model.udosms.consts.MessageState;
import com.mkm.springstatemachinefun.model.udosms.consts.ModuleCommand;
import com.mkm.springstatemachinefun.model.udosms.consts.ModuleName;
import com.mkm.springstatemachinefun.utils.Colours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mkm.springstatemachinefun.utils.Colours.ANSI_RESET;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UdoSMSMessage implements Cloneable {

    public static final int MAX_CYCLE_ID_LENGTH = 36;
    public static final int MAX_COMMAND_ID_LENGTH = 48;
    public static final int MAX_MODULE_NAME_LENGTH = 48;
    public static final int MAX_MODULE_COMMAND_LENGTH = 48;
    public static final int MAX_MODULE_PARAMETERS_LENGTH = 48;
    public static final int MAX_STATE_LENGTH = 2;

    public static final char START_SIGN = '$';
    public static final char FIELD_SEPARATOR = '&';
    public static final char END_SIGN = '#';

    public static final int CYCLE_ID_INDEX = 0;
    public static final int COMMAND_ID_INDEX = 1;
    public static final int MODULE_NAME_INDEX = 2;
    public static final int MODULE_COMMAND_INDEX = 3;

//    PLCType targetPLCType;

    String cycleId;
    String commandId;
    ModuleName moduleName;
    @Wither
    ModuleCommand moduleCommand;
    @Wither
    MessageState messageState;
    @Wither
    ArrayList<String> moduleParameters;

    public UdoSMSMessage clone() {
        try {
            return (UdoSMSMessage) super.clone();
        } catch (CloneNotSupportedException e) {
           log.debug( "{} while clonning UdoSMSMessage", e.getMessage());
            return null;
        }
    }

    public String toUdoSMS() {

        try {
            String[] messageInfo = {cycleId, commandId, moduleName.toString(), moduleCommand.toString()};
            return build(messageInfo, Colours.DEFAULT, Colours.DEFAULT);
        } catch (NullPointerException e) {
            log.warn("Exception while toUdoSMS(): {}", e);
            return "Catched NullPointerException.";
        }
    }

    public String toUdoSMSColoured(String color) {

        try {
            String[] messageInfo = {cycleId, commandId, color + moduleName.toString() + ANSI_RESET,
                    color + moduleCommand.toString() + ANSI_RESET};
            return build(messageInfo, color, ANSI_RESET);
        } catch (NullPointerException e) {
            log.warn("Exception while toUdoSMS(): {}", e);
            return "Catched NullPointerException.";
        }
    }

    public String build(String[] messageInfo, String color, String reset) {

        try {

            final int BUFFER_SIZE = Arrays.stream(messageInfo).mapToInt(s -> s.length()).sum()
                    + messageState.toString().length() + moduleParameters.stream().mapToInt(s -> s.length()).sum();
            final int SPECIAL_CHARS = 5 + moduleParameters.size();

            StringBuilder sb = new StringBuilder(BUFFER_SIZE + SPECIAL_CHARS);

            for (int a = 0; a < messageInfo.length; a++) {
                if (a == 0)
                    sb.append(START_SIGN);
                sb.append(messageInfo[a]);
                sb.append(FIELD_SEPARATOR);
            }

            for (int a = 0; a < moduleParameters.size(); a++) {
                sb.append(color + moduleParameters.get(a) + reset);
                sb.append(FIELD_SEPARATOR);
            }

            sb.append(color + messageState + reset);
            sb.append(FIELD_SEPARATOR);

            sb.append(END_SIGN);
            return sb.toString();

        } catch (Exception e) {
            log.warn("Exception while building UdoSMS message: {}", e);
        }

        return null;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this, UdoSMSMessage.class);
    }
}
