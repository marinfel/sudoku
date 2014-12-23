/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.manager;

import com.sudoku.data.model.Grid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 *
 * @author Jonathan
 */
class AccessMgrSerializer extends JsonSerializer<HashMap>  {


    @Override
    public void serialize(HashMap t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        /*Set<Grid> keySet =AccessManager.getInstance().getRules().keySet();
        
        Iterator<Grid> it=keySet.iterator();
          //  jg.writeStartArray();
            
        for ( ;it.hasNext();){
            Grid g=it.next();
            
            jg.writeArrayFieldStart(g.getId().toString());
           // jg.writeStringField("rules",(AccessManager.getInstance().getAllAccessRulesForGrid(g).toString()));
            
        }*/
    
    }
    
}
