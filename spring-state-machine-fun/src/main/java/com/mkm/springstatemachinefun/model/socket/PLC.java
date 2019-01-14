package com.mkm.springstatemachinefun.model.socket;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.LinkedList;

@Component
@Data
public class PLC {
    Socket socket;
    LinkedList<String> buffer;
}
