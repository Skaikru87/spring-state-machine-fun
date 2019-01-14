package com.mkm.springstatemachinefun.model.udosms.consts;

/**
 * L_ERRORED - Logic error, Already sending/no AA for last sent message
 * IO_ERRORED - input/output error
 */
public enum SendStatus {
    DONE, TODO, L_ERRORED, IO_ERRORED, CANCELLED, RECEIVED, SENDING
}
