/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.manager;

import com.sudoku.data.manager.AccessManager;
import com.sudoku.data.model.Grid;
import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;

/**
 *
 * @author Jonathan
 */
public class AccessGridSerializer2 extends JsonSerializer<AccessManager>
{
 

    @Override
    public void serialize(AccessManager t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        //jg.writeFieldName(String.valueOf(t.getRules()));
        //jg.write
    }
}