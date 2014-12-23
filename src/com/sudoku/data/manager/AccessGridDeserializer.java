/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.data.manager;

import static com.sudoku.data.manager.AccessManager.getInstance;
import com.sudoku.data.model.AccessRule;
import com.sudoku.data.model.Grid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.KeyDeserializer;

/**
 *
 * @author Jonathan
 */
class AccessGridDeserializer extends KeyDeserializer{

    public AccessGridDeserializer() {
        AccessManager accMgr =AccessManager.getInstance();
        //on récupère toutes les clés
        Set<Grid> sKey= accMgr.getRules().keySet();
        Iterator<Grid> it= sKey.iterator();
      
        
       
        }

    
    @Override
    public Object deserializeKey(String string, DeserializationContext dc) throws IOException, JsonProcessingException {
        
        UUID id;
        id = java.util.UUID.fromString(string);
        Grid key=GridManager.getInstance().getGridById(id);
        
        return key;
    }
    
    
}
