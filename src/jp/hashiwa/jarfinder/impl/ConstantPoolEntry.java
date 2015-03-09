package jp.hashiwa.jarfinder.impl;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstantPoolEntry {
  private static final boolean CHECK_MODE = Boolean.getBoolean("jp.hashiwa.jarfinder.debug");
  
  public static ConstantPoolEntry createFrom(DataInputStream in) throws IOException {
    ConstantPoolEntry e;
    byte tag = in.readByte();
    char c1, c2;
    int i1, i2;
    byte b1, b2;
    byte[] ba;
    
    switch(tag & 0x000000ff) {
    case 7:
      c1 = in.readChar();
      e = new Class_info(tag, c1);
      break;
    case 9:
      c1 = in.readChar();
      c2 = in.readChar();
      e = new Fieldref_info(tag, c1, c2);
      break;
    case 10:
      c1 = in.readChar();
      c2 = in.readChar();
      e = new Methodref_info(tag, c1, c2);
      break;
    case 11:
      c1 = in.readChar();
      c2 = in.readChar();
      e = new InterfaceMethodref_info(tag, c1, c2);
      break;
    case 8:
      c1 = in.readChar();
      e = new String_info(tag, c1);
      break;
    case 3:
      i1 = in.readInt();
      e = new Integer_info(tag, i1);
      break;
    case 4:
      i1 = in.readInt();
      e = new Float_info(tag, i1);
      break;
    case 5:
      i1 = in.readInt();
      i2 = in.readInt();
      e = new Long_info(tag, i1, i2);
      break;
    case 6:
      i1 = in.readInt();
      i2 = in.readInt();
      e = new Double_info(tag, i1, i2);
      break;
    case 12:
      c1 = in.readChar();
      c2 = in.readChar();
      e = new NameAndType_info(tag, c1, c2);
      break;
    case 1:
      c1 = in.readChar();
      ba = new byte[0x0000ffff & c1];
      in.readFully(ba);
      e = new Utf8_info(tag, c1, ba);
      break;
    case 15:
      b1 = in.readByte();
      c1 = in.readChar();
      e = new MethodHandle_info(tag, b1, c1);
      break;
    case 16:
      c1 = in.readChar();
      e = new MethodType_info(tag, c1);
      break;
    case 18:
      c1 = in.readChar();
      c2 = in.readChar();
      e = new InvokeDynamic_info(tag, c1, c2);
      break;
      
    default:
      int xyz = 0;
      xyz = xyz + 1;
      throw new Error("Unknown type : " + tag);
      
    }
    
    return e;
  }
  
  byte tag;
  private ConstantPoolEntry(byte tag) {
    this.tag = tag;
  }
  
  public int getOccupyingEntryCount() {
    return 1;
  }
  
  static public class Class_info extends ConstantPoolEntry {
    char name_index;
    private Class_info(byte b, char c) {
      super(b);
      this.name_index = c;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          ((int)name_index & 0x0000ffff);
    }
  }
  static public class Fieldref_info extends ConstantPoolEntry {
    char class_index;
    char name_and_type_index;
    private Fieldref_info(byte b, char c1, char c2) {
      super(b);
      this.class_index = c1;
      this.name_and_type_index = c2;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          ((int)class_index & 0x0000ffff) + ":" + 
          ((int)name_and_type_index & 0x0000ffff);
    }
  }
  static public class Methodref_info extends ConstantPoolEntry {
    char class_index;
    char name_and_type_index;
    private Methodref_info(byte b, char c1, char c2) {
      super(b);
      this.class_index = c1;
      this.name_and_type_index = c2;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          ((int)class_index & 0x0000ffff) + ":" + 
          ((int)name_and_type_index & 0x0000ffff);
    }
  }
  static public class InterfaceMethodref_info extends ConstantPoolEntry {
    char class_index;
    char name_and_type_index;
    private InterfaceMethodref_info(byte b, char c1, char c2) {
      super(b);
      this.class_index = c1;
      this.name_and_type_index = c2;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          ((int)class_index & 0x0000ffff) + ":" + 
          ((int)name_and_type_index & 0x0000ffff);
    }
  }
  static public class String_info extends ConstantPoolEntry {
    char string_index;
    private String_info(byte b, char c) {
      super(b);
      this.string_index = c;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          ((int)string_index & 0x0000ffff);
    }
  }
  static public class Integer_info extends ConstantPoolEntry {
    int bytes;
    private Integer_info(byte b, int i) {
      super(b);
      this.bytes = i;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + bytes;
    }
  }
  static public class Float_info extends ConstantPoolEntry {
    int bytes;
    private Float_info(byte b, int i) {
      super(b);
      this.bytes = i;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + bytes;
    }
  }
  static public class Long_info extends ConstantPoolEntry {
    int high_bytes, low_bytes;
    private Long_info(byte b, int i1, int i2) {
      super(b);
      this.high_bytes = i1;
      this.low_bytes = i2;
    }
    @Override
    public int getOccupyingEntryCount() {
      return 2;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          high_bytes + ":" + low_bytes;
    }
  }
  static public class Double_info extends ConstantPoolEntry {
    int high_bytes, low_bytes;
    private Double_info(byte b, int i1, int i2) {
      super(b);
      this.high_bytes = i1;
      this.low_bytes = i2;
    }
    @Override
    public int getOccupyingEntryCount() {
      return 2;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          high_bytes + ":" + low_bytes;
    }
  }
  static public class NameAndType_info extends ConstantPoolEntry {
    char name_index;
    char descriptor_index;
    private NameAndType_info(byte b, char c1, char c2) {
      super(b);
      this.name_index = c1;
      this.descriptor_index = c2;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          (0x0000ffff & (int)name_index) + ":" + 
          (0x0000ffff & (int)descriptor_index);
    }
  }
  static public class Utf8_info extends ConstantPoolEntry {
    char length;
    byte[] bytes;
    String cachedStr;
    private Utf8_info(byte b, char c, byte[] ba) {
      super(b);
      this.length = c;
      this.bytes = ba;
      
      if (CHECK_MODE) {
        if ((0x0000ffff & length) != bytes.length) {
          throw new Error("illegal byte length");
        }
      }
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          (0x0000ffff & length) + ":" + new String(bytes);
    }
    
    public String getActualString() {
      if (cachedStr==null) cachedStr = new String(bytes);
      return cachedStr;
    }
  }
  static public class MethodHandle_info extends ConstantPoolEntry {
    byte reference_kind;
    char reference_index;
    private MethodHandle_info(byte b, byte b2, char c) {
      super(b);
      this.reference_kind = b2;
      this.reference_index = c;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          (0x000000ff & (int)reference_kind) + ":" + 
          (0x0000ffff & (int)reference_index);
    }
  }
  static public class MethodType_info extends ConstantPoolEntry {
    char descriptor_index;
    private MethodType_info(byte b, char c) {
      super(b);
      this.descriptor_index = c;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          (0x0000ffff & (int)descriptor_index);
    }
  }
  static public class InvokeDynamic_info extends ConstantPoolEntry {
    char bootstrap_method_attr_index;
    char name_and_type_index;
    private InvokeDynamic_info(byte b, char c1, char c2) {
      super(b);
      this.bootstrap_method_attr_index = c1;
      this.name_and_type_index = c2;
    }
    public String toString() {
      return getClass().getSimpleName() + ":" + 
          (0x0000ffff & (int)bootstrap_method_attr_index) + ":" + 
          (0x0000ffff & (int)name_and_type_index);
    }
  }
}
