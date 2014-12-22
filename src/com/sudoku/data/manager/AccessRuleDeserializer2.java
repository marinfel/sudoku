/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.manager;

import com.sudoku.data.model.AccessRule;
import java.io.IOException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 *
 * @author Jonathan
 */
class AccessRuleDeserializer2 extends JsonDeserializer {

    public AccessRuleDeserializer2() {
    }

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println(jp.nextTextValue());
        return new AccessRule();
    }
    
}
