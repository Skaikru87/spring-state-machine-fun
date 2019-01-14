package com.mkm.springstatemachinefun.model.udosms;


import com.mkm.springstatemachinefun.model.udosms.consts.MessageSource;
import com.mkm.springstatemachinefun.model.udosms.consts.MessageStatus;
import com.mkm.springstatemachinefun.model.udosms.consts.SendStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @sourceHostPort W przypadku odebranej wiadomości, jest portem zewnętrznego socketu, z którego odczytano wiadomość.
 * W przypadku wysyłanej wiadomości, pole może przyjąć wartości:
 * - null, wtedy wysłane jest z każdego socketu który spełnia pozostałe warunku,
 * - numer lokalnego portu, z którego wiadomość ma być wysłana.
 * @sourceHostAddr W przypadku odebranej wiadomości, jest adresem  zewnętrznego socketu, z którego odczytano wiadomość.
 * W przypadku wysyłanej wiadomości, pole może przyjąć dowolną wartość, ponieważ jest ignorowane.
 * Dla czytelności zawsze ustawiam null.
 * @targetHostAddr Dokąd wiadomość ma być nadana - adres. Jeśli nullowe, wysłane do każdego podłączonego socketu.
 * W przypadku odebranej wiadomości, wartość jest bez znaczenia. Dla czytelności ustawiam nullowe.
 * @targetHostPort Dokąd wiadomość ma być nadana - port. Jeśli nullowe, wysłane do każdego podłączonego socketu.
 * W przypadku odebranej wiadomości, wartość jest bez znaczenia. Dla czytelności ustawiam nullowe.
 * <p>
 * W przypadku mocku, jeśli mamy info z wiadomości skąd została przysłana, w odpowiedzi zwrotnej wystarczy
 * ustawić targetAddr/targetPort zabrany z sourceAddr/sourcePort przysłanej wiadomości.
 * Jeśli nie mamy takiej wiadomości z której można zapożyczyć adres zwrotny, to zostaje nam ustawić sourceHostPort
 * jako np. w przypadku wiadomości wychodzącej z modułu pakowarki, port pakowarki z propertiesów.
 * <p>
 * W przypadku clientów, zawsze mamy target (z propertiesów), więc ustawianie sourceHostPort nigdy nie jest potrzebne.
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Message implements Cloneable {

    protected MessageSource messageSource;
    protected SendStatus sendStatus;
    protected MessageStatus messageStatus;

    protected String sourceHostPort;
    protected String sourceHostAddr;

    protected String targetHostAddr;
    protected String targetHostPort;

    protected String parsingError;

    protected boolean validMessage;
    protected long creationTimestamp;
    protected long sendAttemptTimestamp;

}
