package com.supermap.imobilelite.data;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public abstract class Enum {
    private final int m_value;
    private final int m_ugcValue;
    
    private static boolean m_isCustom;
    
    protected static HashMap<Class<?>, ArrayList<Enum>> m_hashMap = new HashMap<Class<?>, ArrayList<Enum>>();

    public static final String[] getNames(Class type) {
        if (type == null) {
            return new String[0];
        }
        if (!Enum.isValidEnumClass(type)) {
            return new String[0];
        }

        ArrayList names = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, names, null, null);
        String[] nameArr = new String[names.size()];
        names.toArray(nameArr);

        return nameArr;
    }

    public static int[] getValues(Class type) {
        if (type == null) {
            return new int[0];
        }

        if (!Enum.isValidEnumClass(type)) {
            return new int[0];
        }

        ArrayList values = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, null, values, null);

        int[] valueArr = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            valueArr[i] = Integer.parseInt(values.get(i).toString());
        }
        return valueArr;

    }


    public static Enum[] getEnums(Class type) {
        if (type == null) {
            return new Enum[0];
        }

        if (!Enum.isValidEnumClass(type)) {
            return new Enum[0];
        }

        ArrayList entries = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, null, null, entries);

        Enum[] enums = new Enum[entries.size()];
        entries.toArray(enums);
        return enums;
    }


    public static String getNameByValue(Class type, int value) {
        ArrayList names = new ArrayList();
        ArrayList values = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, names, values, null);

        Integer valueObject =Integer.valueOf(value);
        if (!values.contains(valueObject)) {
            String message = InternalResource.loadString("ugcValue", InternalResource.GlobalEnumValueIsError, InternalResource.BundleName);
            throw new RuntimeException(message);

        }
        int index = values.indexOf(valueObject);

        return (String) names.get(index);
    }


    public static int getValueByName(Class type, String name) {
        ArrayList names = new ArrayList();
        ArrayList values = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, names, values, null);

        if (!names.contains(name)) {
            String message = InternalResource.loadString("ugcValue", InternalResource.GlobalEnumValueIsError, InternalResource.BundleName);
            throw new RuntimeException(message);

        }
        int index = names.indexOf(name);
        return Integer.parseInt(values.get(index).toString());

    }

  
    public static Enum parse(Class type, int value) {
        ArrayList values = new ArrayList();
        ArrayList entries = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, null, values, entries);

        Integer valueObject = Integer.valueOf(value);
        if (!values.contains(valueObject)) {
            String message = InternalResource.loadString("ugcValue", InternalResource.GlobalEnumValueIsError, InternalResource.BundleName);
            throw new RuntimeException(message);

        }
        int index = values.indexOf(valueObject);
        return (Enum) entries.get(index);

    }


    public static Enum parse(Class type, String name) {
        if (type == null || name == null) {
            return null;
        }

        ArrayList names = new ArrayList();
        ArrayList entries = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, names, null, entries);

        if (!names.contains(name)) {
            String message = InternalResource.loadString("ugcValue", InternalResource.GlobalEnumValueIsError, InternalResource.BundleName);
            throw new RuntimeException(message);

        }
        int index = names.indexOf(name);
        return (Enum) entries.get(index);

    }


    public static boolean isDefined(Class type, int value) {
        ArrayList values = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, null, values, null);
        Integer valueObject = Integer.valueOf(value);
        return values.contains(valueObject);
    }

 
    public static boolean isDefined(Class type, String name) {
        ArrayList names = new ArrayList();
        Enum.getEnumNameValueAndEntry(type, names, null, null);
        return names.contains(name);
    }


    protected Enum(int value, int ugcValue) {
        this.m_value = value;
        this.m_ugcValue = ugcValue;
    }



   protected static final int internalGetUGCValue(Enum e) {
       return e.getUGCValue();
   }

 
   protected static final Enum internalParseUGCValue(Class type, int ugcValue) {
       return Enum.parseUGCValue(type, ugcValue);
   }

 
   public static Enum parseUGCValue(Class type, int ugcValue) {
       ArrayList entries = new ArrayList();
       ArrayList values = new ArrayList();
       Enum.getEnumNameValueAndEntry(type, null, values, entries);

       Integer value = Integer.valueOf(ugcValue);
       if (!values.contains(value)) {
           String message = InternalResource.loadString("ugcValue:"+ugcValue, InternalResource.GlobalEnumValueIsError, InternalResource.BundleName);
           throw new RuntimeException(message);
       }
       int index = values.indexOf(value);
       return (Enum) entries.get(index);
   }

  
    private static final boolean isValidEnumField(Field field) {
        if (field == null) {
            return false;
        }

        Class type = field.getDeclaringClass();
        if (!field.getType().equals(type)) {
            return false;
        }

        int moidifiers = field.getModifiers();
        if (!Modifier.isPublic(moidifiers) || !Modifier.isStatic(moidifiers) || !Modifier.isFinal(moidifiers)) {
            return false;
        }

        return true;
    }


    private static void getEnumNameValueAndEntry(Class type, ArrayList names, ArrayList values, ArrayList entries) {
        if (type == null) {
            return;
        }

        if (names == null && values == null && entries == null) {
            return;
        }

        Field[] fields = type.getFields();
        if (fields == null || fields.length == 0) {
            return;
        }

        int len = fields.length;
        for (int i = 0; i < len; i++) {
            Field field = fields[i];

            if (!Enum.isValidEnumField(field)) {
                continue;
            }

            Enum e = null;
            try {
               
                e = (Enum) field.get(null);
            } catch (IllegalAccessException ex) {
                continue;
            } catch (IllegalArgumentException ex) {
                continue;
            }
            if (e != null) {
                if (names != null) {
                    names.add(field.getName());
                }
                if (values != null) {
                    Integer value = Integer.valueOf(e.value());
                    values.add(value);
                }
                if (entries != null) {
                    entries.add(e);
                }
            }
        }
        
        if (m_isCustom) {
        	for (Iterator<Map.Entry<Class<?>, ArrayList<Enum>>> i = m_hashMap
        			.entrySet().iterator(); i.hasNext();) {
        		Map.Entry<Class<?>, ArrayList<Enum>> e = i.next();
        		Class<?> tempClass = e.getKey();
        		if (tempClass.getName().equals(type.getName())) {
        			ArrayList<Enum> customEnums = e.getValue();
        			for (int j = 0; j < customEnums.size(); j++) {
        				Enum customEnum = customEnums.get(j);
        				if (names != null) {
							names.add(String.valueOf(customEnum.value()));
						}
        				if (values != null) {
        					values.add(customEnum.value());
						}
        				if (entries != null) {
        					entries.add(customEnum);
						}
        			}
        			break;
        		}
        	}
		}
    }

    private static boolean isValidEnumClass(Class type) {
        if (type == null) {
            return false;
        }
        if (!(type.getSuperclass().equals(Enum.class))) {
            return false;
        }
        return true;
    }


    public final String name() {
        return Enum.getNameByValue(this.getClass(), value());
    }


    public final int value() {
        return this.m_value;
    }


    public String toString() {
        return String.valueOf(name());
    }


    public final boolean equals(Object other) {
        if(other == null){
            return false;
        }

        if(!this.getClass().equals(other.getClass())){
            return false;
        }

        Enum eOther = (Enum)other;
        if(this.value()!= eOther.value()){
            return false;
        }

        return true;
    }


    public final int hashCode() {
        return System.identityHashCode(this);
    }


    final int getUGCValue() {
        return this.m_ugcValue;
    }
    
    protected void setCustom(boolean value) {
		m_isCustom = value;
	}
}
