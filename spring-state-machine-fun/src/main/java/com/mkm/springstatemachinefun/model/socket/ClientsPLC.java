package com.mkm.springstatemachinefun.model.socket;

import com.mkm.springstatemachinefun.reactive.ObservableList;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class ClientsPLC {

    ObservableList<PLC> plcClients = new ObservableList<>() ;
}
