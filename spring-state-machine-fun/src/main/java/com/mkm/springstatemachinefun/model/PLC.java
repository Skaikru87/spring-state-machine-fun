package com.mkm.springstatemachinefun.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component
@Data
public class PLC {
    Socket socket;
}
