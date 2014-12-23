/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.sudoku.comm.generated;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Grid extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Grid\",\"namespace\":\"com.sudoku.comm.generated\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"title\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"description\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"difficulty\",\"type\":\"int\"},{\"name\":\"published\",\"type\":\"boolean\"},{\"name\":\"comments\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Comment\",\"fields\":[{\"name\":\"author\",\"type\":{\"type\":\"record\",\"name\":\"User\",\"fields\":[{\"name\":\"pseudo\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"salt\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"birthDate\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"profilePicturePath\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"createDate\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"updateDate\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"ipAddress\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"comment\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"grade\",\"type\":\"double\"},{\"name\":\"createDate\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}}]},{\"name\":\"tags\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]},{\"name\":\"line1\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},{\"name\":\"line2\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},{\"name\":\"line3\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},{\"name\":\"line4\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},{\"name\":\"line5\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},{\"name\":\"line6\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},{\"name\":\"line7\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},{\"name\":\"line8\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},{\"name\":\"line9\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"int\"}]},{\"name\":\"createUser\",\"type\":\"User\"},{\"name\":\"createDate\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"updateDate\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.String id;
  @Deprecated public java.lang.String title;
  @Deprecated public java.lang.String description;
  @Deprecated public int difficulty;
  @Deprecated public boolean published;
  @Deprecated public java.util.List<com.sudoku.comm.generated.Comment> comments;
  @Deprecated public java.util.List<java.lang.String> tags;
  @Deprecated public java.util.List<java.lang.Integer> line1;
  @Deprecated public java.util.List<java.lang.Integer> line2;
  @Deprecated public java.util.List<java.lang.Integer> line3;
  @Deprecated public java.util.List<java.lang.Integer> line4;
  @Deprecated public java.util.List<java.lang.Integer> line5;
  @Deprecated public java.util.List<java.lang.Integer> line6;
  @Deprecated public java.util.List<java.lang.Integer> line7;
  @Deprecated public java.util.List<java.lang.Integer> line8;
  @Deprecated public java.util.List<java.lang.Integer> line9;
  @Deprecated public com.sudoku.comm.generated.User createUser;
  @Deprecated public java.lang.String createDate;
  @Deprecated public java.lang.String updateDate;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public Grid() {}

  /**
   * All-args constructor.
   */
  public Grid(java.lang.String id, java.lang.String title, java.lang.String description, java.lang.Integer difficulty, java.lang.Boolean published, java.util.List<com.sudoku.comm.generated.Comment> comments, java.util.List<java.lang.String> tags, java.util.List<java.lang.Integer> line1, java.util.List<java.lang.Integer> line2, java.util.List<java.lang.Integer> line3, java.util.List<java.lang.Integer> line4, java.util.List<java.lang.Integer> line5, java.util.List<java.lang.Integer> line6, java.util.List<java.lang.Integer> line7, java.util.List<java.lang.Integer> line8, java.util.List<java.lang.Integer> line9, com.sudoku.comm.generated.User createUser, java.lang.String createDate, java.lang.String updateDate) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.difficulty = difficulty;
    this.published = published;
    this.comments = comments;
    this.tags = tags;
    this.line1 = line1;
    this.line2 = line2;
    this.line3 = line3;
    this.line4 = line4;
    this.line5 = line5;
    this.line6 = line6;
    this.line7 = line7;
    this.line8 = line8;
    this.line9 = line9;
    this.createUser = createUser;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return title;
    case 2: return description;
    case 3: return difficulty;
    case 4: return published;
    case 5: return comments;
    case 6: return tags;
    case 7: return line1;
    case 8: return line2;
    case 9: return line3;
    case 10: return line4;
    case 11: return line5;
    case 12: return line6;
    case 13: return line7;
    case 14: return line8;
    case 15: return line9;
    case 16: return createUser;
    case 17: return createDate;
    case 18: return updateDate;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.String)value$; break;
    case 1: title = (java.lang.String)value$; break;
    case 2: description = (java.lang.String)value$; break;
    case 3: difficulty = (java.lang.Integer)value$; break;
    case 4: published = (java.lang.Boolean)value$; break;
    case 5: comments = (java.util.List<com.sudoku.comm.generated.Comment>)value$; break;
    case 6: tags = (java.util.List<java.lang.String>)value$; break;
    case 7: line1 = (java.util.List<java.lang.Integer>)value$; break;
    case 8: line2 = (java.util.List<java.lang.Integer>)value$; break;
    case 9: line3 = (java.util.List<java.lang.Integer>)value$; break;
    case 10: line4 = (java.util.List<java.lang.Integer>)value$; break;
    case 11: line5 = (java.util.List<java.lang.Integer>)value$; break;
    case 12: line6 = (java.util.List<java.lang.Integer>)value$; break;
    case 13: line7 = (java.util.List<java.lang.Integer>)value$; break;
    case 14: line8 = (java.util.List<java.lang.Integer>)value$; break;
    case 15: line9 = (java.util.List<java.lang.Integer>)value$; break;
    case 16: createUser = (com.sudoku.comm.generated.User)value$; break;
    case 17: createDate = (java.lang.String)value$; break;
    case 18: updateDate = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.String value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'title' field.
   */
  public java.lang.String getTitle() {
    return title;
  }

  /**
   * Sets the value of the 'title' field.
   * @param value the value to set.
   */
  public void setTitle(java.lang.String value) {
    this.title = value;
  }

  /**
   * Gets the value of the 'description' field.
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * Sets the value of the 'description' field.
   * @param value the value to set.
   */
  public void setDescription(java.lang.String value) {
    this.description = value;
  }

  /**
   * Gets the value of the 'difficulty' field.
   */
  public java.lang.Integer getDifficulty() {
    return difficulty;
  }

  /**
   * Sets the value of the 'difficulty' field.
   * @param value the value to set.
   */
  public void setDifficulty(java.lang.Integer value) {
    this.difficulty = value;
  }

  /**
   * Gets the value of the 'published' field.
   */
  public java.lang.Boolean getPublished() {
    return published;
  }

  /**
   * Sets the value of the 'published' field.
   * @param value the value to set.
   */
  public void setPublished(java.lang.Boolean value) {
    this.published = value;
  }

  /**
   * Gets the value of the 'comments' field.
   */
  public java.util.List<com.sudoku.comm.generated.Comment> getComments() {
    return comments;
  }

  /**
   * Sets the value of the 'comments' field.
   * @param value the value to set.
   */
  public void setComments(java.util.List<com.sudoku.comm.generated.Comment> value) {
    this.comments = value;
  }

  /**
   * Gets the value of the 'tags' field.
   */
  public java.util.List<java.lang.String> getTags() {
    return tags;
  }

  /**
   * Sets the value of the 'tags' field.
   * @param value the value to set.
   */
  public void setTags(java.util.List<java.lang.String> value) {
    this.tags = value;
  }

  /**
   * Gets the value of the 'line1' field.
   */
  public java.util.List<java.lang.Integer> getLine1() {
    return line1;
  }

  /**
   * Sets the value of the 'line1' field.
   * @param value the value to set.
   */
  public void setLine1(java.util.List<java.lang.Integer> value) {
    this.line1 = value;
  }

  /**
   * Gets the value of the 'line2' field.
   */
  public java.util.List<java.lang.Integer> getLine2() {
    return line2;
  }

  /**
   * Sets the value of the 'line2' field.
   * @param value the value to set.
   */
  public void setLine2(java.util.List<java.lang.Integer> value) {
    this.line2 = value;
  }

  /**
   * Gets the value of the 'line3' field.
   */
  public java.util.List<java.lang.Integer> getLine3() {
    return line3;
  }

  /**
   * Sets the value of the 'line3' field.
   * @param value the value to set.
   */
  public void setLine3(java.util.List<java.lang.Integer> value) {
    this.line3 = value;
  }

  /**
   * Gets the value of the 'line4' field.
   */
  public java.util.List<java.lang.Integer> getLine4() {
    return line4;
  }

  /**
   * Sets the value of the 'line4' field.
   * @param value the value to set.
   */
  public void setLine4(java.util.List<java.lang.Integer> value) {
    this.line4 = value;
  }

  /**
   * Gets the value of the 'line5' field.
   */
  public java.util.List<java.lang.Integer> getLine5() {
    return line5;
  }

  /**
   * Sets the value of the 'line5' field.
   * @param value the value to set.
   */
  public void setLine5(java.util.List<java.lang.Integer> value) {
    this.line5 = value;
  }

  /**
   * Gets the value of the 'line6' field.
   */
  public java.util.List<java.lang.Integer> getLine6() {
    return line6;
  }

  /**
   * Sets the value of the 'line6' field.
   * @param value the value to set.
   */
  public void setLine6(java.util.List<java.lang.Integer> value) {
    this.line6 = value;
  }

  /**
   * Gets the value of the 'line7' field.
   */
  public java.util.List<java.lang.Integer> getLine7() {
    return line7;
  }

  /**
   * Sets the value of the 'line7' field.
   * @param value the value to set.
   */
  public void setLine7(java.util.List<java.lang.Integer> value) {
    this.line7 = value;
  }

  /**
   * Gets the value of the 'line8' field.
   */
  public java.util.List<java.lang.Integer> getLine8() {
    return line8;
  }

  /**
   * Sets the value of the 'line8' field.
   * @param value the value to set.
   */
  public void setLine8(java.util.List<java.lang.Integer> value) {
    this.line8 = value;
  }

  /**
   * Gets the value of the 'line9' field.
   */
  public java.util.List<java.lang.Integer> getLine9() {
    return line9;
  }

  /**
   * Sets the value of the 'line9' field.
   * @param value the value to set.
   */
  public void setLine9(java.util.List<java.lang.Integer> value) {
    this.line9 = value;
  }

  /**
   * Gets the value of the 'createUser' field.
   */
  public com.sudoku.comm.generated.User getCreateUser() {
    return createUser;
  }

  /**
   * Sets the value of the 'createUser' field.
   * @param value the value to set.
   */
  public void setCreateUser(com.sudoku.comm.generated.User value) {
    this.createUser = value;
  }

  /**
   * Gets the value of the 'createDate' field.
   */
  public java.lang.String getCreateDate() {
    return createDate;
  }

  /**
   * Sets the value of the 'createDate' field.
   * @param value the value to set.
   */
  public void setCreateDate(java.lang.String value) {
    this.createDate = value;
  }

  /**
   * Gets the value of the 'updateDate' field.
   */
  public java.lang.String getUpdateDate() {
    return updateDate;
  }

  /**
   * Sets the value of the 'updateDate' field.
   * @param value the value to set.
   */
  public void setUpdateDate(java.lang.String value) {
    this.updateDate = value;
  }

  /** Creates a new Grid RecordBuilder */
  public static com.sudoku.comm.generated.Grid.Builder newBuilder() {
    return new com.sudoku.comm.generated.Grid.Builder();
  }
  
  /** Creates a new Grid RecordBuilder by copying an existing Builder */
  public static com.sudoku.comm.generated.Grid.Builder newBuilder(com.sudoku.comm.generated.Grid.Builder other) {
    return new com.sudoku.comm.generated.Grid.Builder(other);
  }
  
  /** Creates a new Grid RecordBuilder by copying an existing Grid instance */
  public static com.sudoku.comm.generated.Grid.Builder newBuilder(com.sudoku.comm.generated.Grid other) {
    return new com.sudoku.comm.generated.Grid.Builder(other);
  }
  
  /**
   * RecordBuilder for Grid instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Grid>
    implements org.apache.avro.data.RecordBuilder<Grid> {

    private java.lang.String id;
    private java.lang.String title;
    private java.lang.String description;
    private int difficulty;
    private boolean published;
    private java.util.List<com.sudoku.comm.generated.Comment> comments;
    private java.util.List<java.lang.String> tags;
    private java.util.List<java.lang.Integer> line1;
    private java.util.List<java.lang.Integer> line2;
    private java.util.List<java.lang.Integer> line3;
    private java.util.List<java.lang.Integer> line4;
    private java.util.List<java.lang.Integer> line5;
    private java.util.List<java.lang.Integer> line6;
    private java.util.List<java.lang.Integer> line7;
    private java.util.List<java.lang.Integer> line8;
    private java.util.List<java.lang.Integer> line9;
    private com.sudoku.comm.generated.User createUser;
    private java.lang.String createDate;
    private java.lang.String updateDate;

    /** Creates a new Builder */
    private Builder() {
      super(com.sudoku.comm.generated.Grid.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.sudoku.comm.generated.Grid.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.title)) {
        this.title = data().deepCopy(fields()[1].schema(), other.title);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.description)) {
        this.description = data().deepCopy(fields()[2].schema(), other.description);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.difficulty)) {
        this.difficulty = data().deepCopy(fields()[3].schema(), other.difficulty);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.published)) {
        this.published = data().deepCopy(fields()[4].schema(), other.published);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.comments)) {
        this.comments = data().deepCopy(fields()[5].schema(), other.comments);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.tags)) {
        this.tags = data().deepCopy(fields()[6].schema(), other.tags);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.line1)) {
        this.line1 = data().deepCopy(fields()[7].schema(), other.line1);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.line2)) {
        this.line2 = data().deepCopy(fields()[8].schema(), other.line2);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.line3)) {
        this.line3 = data().deepCopy(fields()[9].schema(), other.line3);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.line4)) {
        this.line4 = data().deepCopy(fields()[10].schema(), other.line4);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.line5)) {
        this.line5 = data().deepCopy(fields()[11].schema(), other.line5);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.line6)) {
        this.line6 = data().deepCopy(fields()[12].schema(), other.line6);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.line7)) {
        this.line7 = data().deepCopy(fields()[13].schema(), other.line7);
        fieldSetFlags()[13] = true;
      }
      if (isValidValue(fields()[14], other.line8)) {
        this.line8 = data().deepCopy(fields()[14].schema(), other.line8);
        fieldSetFlags()[14] = true;
      }
      if (isValidValue(fields()[15], other.line9)) {
        this.line9 = data().deepCopy(fields()[15].schema(), other.line9);
        fieldSetFlags()[15] = true;
      }
      if (isValidValue(fields()[16], other.createUser)) {
        this.createUser = data().deepCopy(fields()[16].schema(), other.createUser);
        fieldSetFlags()[16] = true;
      }
      if (isValidValue(fields()[17], other.createDate)) {
        this.createDate = data().deepCopy(fields()[17].schema(), other.createDate);
        fieldSetFlags()[17] = true;
      }
      if (isValidValue(fields()[18], other.updateDate)) {
        this.updateDate = data().deepCopy(fields()[18].schema(), other.updateDate);
        fieldSetFlags()[18] = true;
      }
    }
    
    /** Creates a Builder by copying an existing Grid instance */
    private Builder(com.sudoku.comm.generated.Grid other) {
            super(com.sudoku.comm.generated.Grid.SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.title)) {
        this.title = data().deepCopy(fields()[1].schema(), other.title);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.description)) {
        this.description = data().deepCopy(fields()[2].schema(), other.description);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.difficulty)) {
        this.difficulty = data().deepCopy(fields()[3].schema(), other.difficulty);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.published)) {
        this.published = data().deepCopy(fields()[4].schema(), other.published);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.comments)) {
        this.comments = data().deepCopy(fields()[5].schema(), other.comments);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.tags)) {
        this.tags = data().deepCopy(fields()[6].schema(), other.tags);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.line1)) {
        this.line1 = data().deepCopy(fields()[7].schema(), other.line1);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.line2)) {
        this.line2 = data().deepCopy(fields()[8].schema(), other.line2);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.line3)) {
        this.line3 = data().deepCopy(fields()[9].schema(), other.line3);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.line4)) {
        this.line4 = data().deepCopy(fields()[10].schema(), other.line4);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.line5)) {
        this.line5 = data().deepCopy(fields()[11].schema(), other.line5);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.line6)) {
        this.line6 = data().deepCopy(fields()[12].schema(), other.line6);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.line7)) {
        this.line7 = data().deepCopy(fields()[13].schema(), other.line7);
        fieldSetFlags()[13] = true;
      }
      if (isValidValue(fields()[14], other.line8)) {
        this.line8 = data().deepCopy(fields()[14].schema(), other.line8);
        fieldSetFlags()[14] = true;
      }
      if (isValidValue(fields()[15], other.line9)) {
        this.line9 = data().deepCopy(fields()[15].schema(), other.line9);
        fieldSetFlags()[15] = true;
      }
      if (isValidValue(fields()[16], other.createUser)) {
        this.createUser = data().deepCopy(fields()[16].schema(), other.createUser);
        fieldSetFlags()[16] = true;
      }
      if (isValidValue(fields()[17], other.createDate)) {
        this.createDate = data().deepCopy(fields()[17].schema(), other.createDate);
        fieldSetFlags()[17] = true;
      }
      if (isValidValue(fields()[18], other.updateDate)) {
        this.updateDate = data().deepCopy(fields()[18].schema(), other.updateDate);
        fieldSetFlags()[18] = true;
      }
    }

    /** Gets the value of the 'id' field */
    public java.lang.String getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public com.sudoku.comm.generated.Grid.Builder setId(java.lang.String value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'id' field has been set */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'id' field */
    public com.sudoku.comm.generated.Grid.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'title' field */
    public java.lang.String getTitle() {
      return title;
    }
    
    /** Sets the value of the 'title' field */
    public com.sudoku.comm.generated.Grid.Builder setTitle(java.lang.String value) {
      validate(fields()[1], value);
      this.title = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'title' field has been set */
    public boolean hasTitle() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'title' field */
    public com.sudoku.comm.generated.Grid.Builder clearTitle() {
      title = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'description' field */
    public java.lang.String getDescription() {
      return description;
    }
    
    /** Sets the value of the 'description' field */
    public com.sudoku.comm.generated.Grid.Builder setDescription(java.lang.String value) {
      validate(fields()[2], value);
      this.description = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'description' field has been set */
    public boolean hasDescription() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'description' field */
    public com.sudoku.comm.generated.Grid.Builder clearDescription() {
      description = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'difficulty' field */
    public java.lang.Integer getDifficulty() {
      return difficulty;
    }
    
    /** Sets the value of the 'difficulty' field */
    public com.sudoku.comm.generated.Grid.Builder setDifficulty(int value) {
      validate(fields()[3], value);
      this.difficulty = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'difficulty' field has been set */
    public boolean hasDifficulty() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'difficulty' field */
    public com.sudoku.comm.generated.Grid.Builder clearDifficulty() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'published' field */
    public java.lang.Boolean getPublished() {
      return published;
    }
    
    /** Sets the value of the 'published' field */
    public com.sudoku.comm.generated.Grid.Builder setPublished(boolean value) {
      validate(fields()[4], value);
      this.published = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'published' field has been set */
    public boolean hasPublished() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'published' field */
    public com.sudoku.comm.generated.Grid.Builder clearPublished() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'comments' field */
    public java.util.List<com.sudoku.comm.generated.Comment> getComments() {
      return comments;
    }
    
    /** Sets the value of the 'comments' field */
    public com.sudoku.comm.generated.Grid.Builder setComments(java.util.List<com.sudoku.comm.generated.Comment> value) {
      validate(fields()[5], value);
      this.comments = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'comments' field has been set */
    public boolean hasComments() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'comments' field */
    public com.sudoku.comm.generated.Grid.Builder clearComments() {
      comments = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'tags' field */
    public java.util.List<java.lang.String> getTags() {
      return tags;
    }
    
    /** Sets the value of the 'tags' field */
    public com.sudoku.comm.generated.Grid.Builder setTags(java.util.List<java.lang.String> value) {
      validate(fields()[6], value);
      this.tags = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'tags' field has been set */
    public boolean hasTags() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'tags' field */
    public com.sudoku.comm.generated.Grid.Builder clearTags() {
      tags = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'line1' field */
    public java.util.List<java.lang.Integer> getLine1() {
      return line1;
    }
    
    /** Sets the value of the 'line1' field */
    public com.sudoku.comm.generated.Grid.Builder setLine1(java.util.List<java.lang.Integer> value) {
      validate(fields()[7], value);
      this.line1 = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'line1' field has been set */
    public boolean hasLine1() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'line1' field */
    public com.sudoku.comm.generated.Grid.Builder clearLine1() {
      line1 = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /** Gets the value of the 'line2' field */
    public java.util.List<java.lang.Integer> getLine2() {
      return line2;
    }
    
    /** Sets the value of the 'line2' field */
    public com.sudoku.comm.generated.Grid.Builder setLine2(java.util.List<java.lang.Integer> value) {
      validate(fields()[8], value);
      this.line2 = value;
      fieldSetFlags()[8] = true;
      return this; 
    }
    
    /** Checks whether the 'line2' field has been set */
    public boolean hasLine2() {
      return fieldSetFlags()[8];
    }
    
    /** Clears the value of the 'line2' field */
    public com.sudoku.comm.generated.Grid.Builder clearLine2() {
      line2 = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /** Gets the value of the 'line3' field */
    public java.util.List<java.lang.Integer> getLine3() {
      return line3;
    }
    
    /** Sets the value of the 'line3' field */
    public com.sudoku.comm.generated.Grid.Builder setLine3(java.util.List<java.lang.Integer> value) {
      validate(fields()[9], value);
      this.line3 = value;
      fieldSetFlags()[9] = true;
      return this; 
    }
    
    /** Checks whether the 'line3' field has been set */
    public boolean hasLine3() {
      return fieldSetFlags()[9];
    }
    
    /** Clears the value of the 'line3' field */
    public com.sudoku.comm.generated.Grid.Builder clearLine3() {
      line3 = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /** Gets the value of the 'line4' field */
    public java.util.List<java.lang.Integer> getLine4() {
      return line4;
    }
    
    /** Sets the value of the 'line4' field */
    public com.sudoku.comm.generated.Grid.Builder setLine4(java.util.List<java.lang.Integer> value) {
      validate(fields()[10], value);
      this.line4 = value;
      fieldSetFlags()[10] = true;
      return this; 
    }
    
    /** Checks whether the 'line4' field has been set */
    public boolean hasLine4() {
      return fieldSetFlags()[10];
    }
    
    /** Clears the value of the 'line4' field */
    public com.sudoku.comm.generated.Grid.Builder clearLine4() {
      line4 = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    /** Gets the value of the 'line5' field */
    public java.util.List<java.lang.Integer> getLine5() {
      return line5;
    }
    
    /** Sets the value of the 'line5' field */
    public com.sudoku.comm.generated.Grid.Builder setLine5(java.util.List<java.lang.Integer> value) {
      validate(fields()[11], value);
      this.line5 = value;
      fieldSetFlags()[11] = true;
      return this; 
    }
    
    /** Checks whether the 'line5' field has been set */
    public boolean hasLine5() {
      return fieldSetFlags()[11];
    }
    
    /** Clears the value of the 'line5' field */
    public com.sudoku.comm.generated.Grid.Builder clearLine5() {
      line5 = null;
      fieldSetFlags()[11] = false;
      return this;
    }

    /** Gets the value of the 'line6' field */
    public java.util.List<java.lang.Integer> getLine6() {
      return line6;
    }
    
    /** Sets the value of the 'line6' field */
    public com.sudoku.comm.generated.Grid.Builder setLine6(java.util.List<java.lang.Integer> value) {
      validate(fields()[12], value);
      this.line6 = value;
      fieldSetFlags()[12] = true;
      return this; 
    }
    
    /** Checks whether the 'line6' field has been set */
    public boolean hasLine6() {
      return fieldSetFlags()[12];
    }
    
    /** Clears the value of the 'line6' field */
    public com.sudoku.comm.generated.Grid.Builder clearLine6() {
      line6 = null;
      fieldSetFlags()[12] = false;
      return this;
    }

    /** Gets the value of the 'line7' field */
    public java.util.List<java.lang.Integer> getLine7() {
      return line7;
    }
    
    /** Sets the value of the 'line7' field */
    public com.sudoku.comm.generated.Grid.Builder setLine7(java.util.List<java.lang.Integer> value) {
      validate(fields()[13], value);
      this.line7 = value;
      fieldSetFlags()[13] = true;
      return this; 
    }
    
    /** Checks whether the 'line7' field has been set */
    public boolean hasLine7() {
      return fieldSetFlags()[13];
    }
    
    /** Clears the value of the 'line7' field */
    public com.sudoku.comm.generated.Grid.Builder clearLine7() {
      line7 = null;
      fieldSetFlags()[13] = false;
      return this;
    }

    /** Gets the value of the 'line8' field */
    public java.util.List<java.lang.Integer> getLine8() {
      return line8;
    }
    
    /** Sets the value of the 'line8' field */
    public com.sudoku.comm.generated.Grid.Builder setLine8(java.util.List<java.lang.Integer> value) {
      validate(fields()[14], value);
      this.line8 = value;
      fieldSetFlags()[14] = true;
      return this; 
    }
    
    /** Checks whether the 'line8' field has been set */
    public boolean hasLine8() {
      return fieldSetFlags()[14];
    }
    
    /** Clears the value of the 'line8' field */
    public com.sudoku.comm.generated.Grid.Builder clearLine8() {
      line8 = null;
      fieldSetFlags()[14] = false;
      return this;
    }

    /** Gets the value of the 'line9' field */
    public java.util.List<java.lang.Integer> getLine9() {
      return line9;
    }
    
    /** Sets the value of the 'line9' field */
    public com.sudoku.comm.generated.Grid.Builder setLine9(java.util.List<java.lang.Integer> value) {
      validate(fields()[15], value);
      this.line9 = value;
      fieldSetFlags()[15] = true;
      return this; 
    }
    
    /** Checks whether the 'line9' field has been set */
    public boolean hasLine9() {
      return fieldSetFlags()[15];
    }
    
    /** Clears the value of the 'line9' field */
    public com.sudoku.comm.generated.Grid.Builder clearLine9() {
      line9 = null;
      fieldSetFlags()[15] = false;
      return this;
    }

    /** Gets the value of the 'createUser' field */
    public com.sudoku.comm.generated.User getCreateUser() {
      return createUser;
    }
    
    /** Sets the value of the 'createUser' field */
    public com.sudoku.comm.generated.Grid.Builder setCreateUser(com.sudoku.comm.generated.User value) {
      validate(fields()[16], value);
      this.createUser = value;
      fieldSetFlags()[16] = true;
      return this; 
    }
    
    /** Checks whether the 'createUser' field has been set */
    public boolean hasCreateUser() {
      return fieldSetFlags()[16];
    }
    
    /** Clears the value of the 'createUser' field */
    public com.sudoku.comm.generated.Grid.Builder clearCreateUser() {
      createUser = null;
      fieldSetFlags()[16] = false;
      return this;
    }

    /** Gets the value of the 'createDate' field */
    public java.lang.String getCreateDate() {
      return createDate;
    }
    
    /** Sets the value of the 'createDate' field */
    public com.sudoku.comm.generated.Grid.Builder setCreateDate(java.lang.String value) {
      validate(fields()[17], value);
      this.createDate = value;
      fieldSetFlags()[17] = true;
      return this; 
    }
    
    /** Checks whether the 'createDate' field has been set */
    public boolean hasCreateDate() {
      return fieldSetFlags()[17];
    }
    
    /** Clears the value of the 'createDate' field */
    public com.sudoku.comm.generated.Grid.Builder clearCreateDate() {
      createDate = null;
      fieldSetFlags()[17] = false;
      return this;
    }

    /** Gets the value of the 'updateDate' field */
    public java.lang.String getUpdateDate() {
      return updateDate;
    }
    
    /** Sets the value of the 'updateDate' field */
    public com.sudoku.comm.generated.Grid.Builder setUpdateDate(java.lang.String value) {
      validate(fields()[18], value);
      this.updateDate = value;
      fieldSetFlags()[18] = true;
      return this; 
    }
    
    /** Checks whether the 'updateDate' field has been set */
    public boolean hasUpdateDate() {
      return fieldSetFlags()[18];
    }
    
    /** Clears the value of the 'updateDate' field */
    public com.sudoku.comm.generated.Grid.Builder clearUpdateDate() {
      updateDate = null;
      fieldSetFlags()[18] = false;
      return this;
    }

    @Override
    public Grid build() {
      try {
        Grid record = new Grid();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.String) defaultValue(fields()[0]);
        record.title = fieldSetFlags()[1] ? this.title : (java.lang.String) defaultValue(fields()[1]);
        record.description = fieldSetFlags()[2] ? this.description : (java.lang.String) defaultValue(fields()[2]);
        record.difficulty = fieldSetFlags()[3] ? this.difficulty : (java.lang.Integer) defaultValue(fields()[3]);
        record.published = fieldSetFlags()[4] ? this.published : (java.lang.Boolean) defaultValue(fields()[4]);
        record.comments = fieldSetFlags()[5] ? this.comments : (java.util.List<com.sudoku.comm.generated.Comment>) defaultValue(fields()[5]);
        record.tags = fieldSetFlags()[6] ? this.tags : (java.util.List<java.lang.String>) defaultValue(fields()[6]);
        record.line1 = fieldSetFlags()[7] ? this.line1 : (java.util.List<java.lang.Integer>) defaultValue(fields()[7]);
        record.line2 = fieldSetFlags()[8] ? this.line2 : (java.util.List<java.lang.Integer>) defaultValue(fields()[8]);
        record.line3 = fieldSetFlags()[9] ? this.line3 : (java.util.List<java.lang.Integer>) defaultValue(fields()[9]);
        record.line4 = fieldSetFlags()[10] ? this.line4 : (java.util.List<java.lang.Integer>) defaultValue(fields()[10]);
        record.line5 = fieldSetFlags()[11] ? this.line5 : (java.util.List<java.lang.Integer>) defaultValue(fields()[11]);
        record.line6 = fieldSetFlags()[12] ? this.line6 : (java.util.List<java.lang.Integer>) defaultValue(fields()[12]);
        record.line7 = fieldSetFlags()[13] ? this.line7 : (java.util.List<java.lang.Integer>) defaultValue(fields()[13]);
        record.line8 = fieldSetFlags()[14] ? this.line8 : (java.util.List<java.lang.Integer>) defaultValue(fields()[14]);
        record.line9 = fieldSetFlags()[15] ? this.line9 : (java.util.List<java.lang.Integer>) defaultValue(fields()[15]);
        record.createUser = fieldSetFlags()[16] ? this.createUser : (com.sudoku.comm.generated.User) defaultValue(fields()[16]);
        record.createDate = fieldSetFlags()[17] ? this.createDate : (java.lang.String) defaultValue(fields()[17]);
        record.updateDate = fieldSetFlags()[18] ? this.updateDate : (java.lang.String) defaultValue(fields()[18]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
