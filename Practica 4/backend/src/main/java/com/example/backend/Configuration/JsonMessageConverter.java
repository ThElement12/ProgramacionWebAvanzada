package com.example.backend.Configuration;


import com.example.backend.Models.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JsonMessageConverter implements MessageConverter {

    private final ObjectMapper mapper;

    public JsonMessageConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public javax.jms.Message toMessage(Object object, Session session) throws MessageConversionException {
        try {
            // send class=<json content>
            return session.createTextMessage(object.getClass().getName() + "=" + mapper.writeValueAsString(object));
        } catch (Exception e) {
            throw new MessageConversionException("Message cannot be serialized", e);
        }
    }

    @Override
    public Object fromMessage(javax.jms.Message message) throws JMSException, MessageConversionException {
        try {
            BytesMessage bm = (BytesMessage) message;
            byte data[] = new byte[(int) bm.getBodyLength()];
            bm.readBytes(data);
            String s = new String(data);
            Sensor sensor = new ObjectMapper().readValue(s,Sensor.class);
            Matcher matcher = Pattern.compile("^([^=]+)=(.+)$").matcher(((TextMessage) message).getText());
            if (!matcher.find())
            {
                throw new MessageConversionException("Message is not of the expected format: class=<json content>");
            }
            return sensor;
        } catch (Exception e) {
            throw new MessageConversionException("Message cannot be deserialized", e);
        }
    }
}