package com.sudoku.data.manager;


import com.sudoku.data.model.AccessAction;
import com.sudoku.data.model.AccessRule;
import com.sudoku.data.model.ContactCategory;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import com.sudoku.util.*;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.KeyDeserializer;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;
import java.util.HashSet;


public class AccessManager {

  private static AccessManager instance;


  private HashMap<ContactCategory, HashSet<AccessAction>> accessRules = new HashMap<>();

  private AccessManager(){}

  public static AccessManager getInstance(){
    if(instance == null){
      instance = new AccessManager();
    }
    return instance;
  }

  public void setAccessRulesForGroup(ContactCategory group, HashSet<AccessAction> allowedActions){
    if(group == null){return;}
    this.accessRules.put(group, allowedActions);
  }

  public void addAccessRuleForGroup(ContactCategory group, AccessAction action){
    HashSet<AccessAction> actions = this.accessRules.get(group);
    if(actions == null){

    }
  }
}
