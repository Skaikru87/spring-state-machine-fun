package com.mkm.springstatemachinefun.utils.messagesUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Uuuidy będą generowane dość często, stąd mechanizm sprawdzający czy w ciągu 1000 ostatnich uuidów trafił się taki,
 * który byłby równy nowowygenerowanemu.
 * https://stackoverflow.com/questions/2513573/how-good-is-javas-uuid-randomuuid
 * <p>
 * 992105a
 */
@Slf4j
@Service
public class IDGenerator {

    long currentNumber = 0;

    private static final int MAX_SIZE = 500;
    public static LinkedList<String> uuids = new LinkedList<>();

    public String getNewCommandID() {

        int counter = 9999999;

        do {

            if (currentNumber >= 9999999)
                currentNumber = 0;

            counter--;
            String uuid = StringUtils.leftPad(Long.toString(currentNumber), 7, '0');
            if (!checkIfUuidExists(uuid)) {
                uuids.add(0, uuid);
                if (uuids.size() > MAX_SIZE)
                    uuids.removeLast();
                return uuid;
            }

            currentNumber++;
        } while (counter > 0);

        return null;
    }

    private boolean checkIfUuidExists(String uuid) {
        for (String u : uuids) {
            if (u.toString().substring(0, 7).equals(uuid.toString().substring(0, 7)))
                return true;
        }
        return false;
    }

    public String cycleIdGenerated(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 7);
        return uuid;
    }
}
