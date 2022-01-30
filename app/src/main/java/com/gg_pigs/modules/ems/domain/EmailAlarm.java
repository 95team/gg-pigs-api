package com.gg_pigs.modules.ems.domain;

import java.io.Serializable;
import java.util.List;

public interface EmailAlarm extends Serializable {
    String getSubject();
    String getMessage();
    String getFrom();
    List<String> getTo();
    List<String> getCc();
    List<String> getBcc();
}
