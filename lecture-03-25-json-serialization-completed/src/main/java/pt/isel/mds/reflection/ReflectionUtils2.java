package pt.isel.mds.reflection;

import org.json.JSONObject;
import pt.isel.mds.reflection.exceptions.ClassAccessException;
import pt.isel.mds.reflection.exceptions.ConstructorAccessException;
import pt.isel.mds.reflection.exceptions.FieldAccessException;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pt.isel.mds.utils.Utils.TODO;

public class ReflectionUtils2 {

    /**
     * Check if a class is the same or a subclass of another class
     * @param cls
     * @param superCls
     * @return
     */
    public static boolean isSameOrSubClass(Class<?> cls, Class<?> superCls) {
       Class<?> clsCurr = cls;

       while(clsCurr != null) {
           if (clsCurr == superCls)
               return true;
           clsCurr = clsCurr.getSuperclass();
       }
       return  false;
    }

    /**
     * Check if "type" (class or interface)
     * implements (directly or indirectly) the interface represented by "interfaceType"
     * (or extends if "type" represents itself an interface)
     * @param type
     * @param interfaceType
     * @return
     */
    public static boolean implementsInterface(Class<?> type, Class<?> interfaceType) {
       Class<?> currType = type;

       while(currType != null) {
           // this is what we are trying to find
           if (currType == interfaceType) return true;

           // now try through the directed implemented interfaces
           // (or extended interfaces, if currType represents itself an interface)
           var superInterfaces = currType.getInterfaces();
           for(var t : superInterfaces) {
               if (implementsInterface(t, interfaceType))
                   return true;
           }
           // if currType is an interface (represents an interface),
           // then getSuperclass will always return null
           // if it is not, try now with super class
           // if currType represents Object class, the call will
           // return null, too.
           currType = currType.getSuperclass();
       }
       return false;
    }



    /**
     * Check if an object class is compatible with
     * a certain type (class or interface)
     * This is similar to the Java instanceof operator
     * @param obj
     * @param type
     * @return
     */
    public static boolean isInstanceOf(Object obj, Class<?> type) {
        Class<?> objClass = obj.getClass();
        if (!type.isInterface()) {
            return isSameOrSubClass(objClass, type);
        }
        return implementsInterface(objClass, type);
    }

    /**
     * general members characteristics
     * (now just "isStatic" but of  courese it can be extended for your needs)
     */

    public static boolean isStatic(Member member) {
        return Modifier.isStatic(member.getModifiers());
    }


    /**
     * Methods
     */

    public static List<Method> getMethods(Class<?> cls) {
        TODO();
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T invokeStaticMethod(String clsName, String methodName, Class<?>[] params, Object[] args) {
        TODO();
        return null;
    }

    /**
     * fields
     */


    /**
     * get all fields (private, public, protected, static or from instance),
     * declared in the class or inherited
     * @param cls
     * @return
     */
    public static List<Field> getAllFields(Class<?> cls) {
        Class<?> currClass = cls;
        List<Field> fields = new ArrayList<>();
        while(currClass != Object.class) {
            var f = currClass.getDeclaredFields();
            fields.addAll(Arrays.asList(f));
            currClass = currClass.getSuperclass();
        }
        return fields;
    }


    public static Object getField(Field f, Object obj)  {
        try {
            f.setAccessible(true);
            return f.get(obj);
        }
        catch(IllegalAccessException e) {
            throw new FieldAccessException(e);
        }
    }

    public static void setField(Field f, Object obj, Object value) {
        try {
            f.setAccessible(true);
            f.set(obj, value);
        }
        catch(IllegalAccessException e) {
            throw new FieldAccessException(e);
        }
    }

    public static Class<?> clsForName(String clsName)  {
        try {
            return Class.forName(clsName);
        }
        catch( ClassNotFoundException e) {
            throw new ClassAccessException();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInstance(Class<T> cls) {
        try {
            return (T) cls.getConstructor().newInstance();
        }
        catch( NoSuchMethodException |
               IllegalAccessException | InvocationTargetException  |InstantiationException e) {
            throw new ConstructorAccessException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T createInstance(String clsName) {

        return (T) createInstance(clsForName(clsName));
    }

    // serialization

    @SuppressWarnings("unchecked")
    private static void saveFieldToJson(Field f, Object obj, JSONObject jo) {
        if (isStatic(f)) return;

        var fType = f.getType();
        var fValue = getField(f, obj);
        var fName = f.getName();

        if (fType == int.class) jo.put(fName, (int) fValue);
        else if (fType == double.class) jo.put(fName, (double) fValue);
        else if (fType == boolean.class) jo.put(fName, (boolean) fValue);
        else { // reference types
            JSONObject jfo = null;
            if (fValue == null) {
                jfo = new JSONObject();
                jfo.put("--isNull", true);
            }
            else if (fType == String.class) {
                jfo = new JSONObject();
                jfo.put("--value", fValue.toString());

            }
            else if (Enum.class.isAssignableFrom(fType)) {
                jfo = new JSONObject();
                // we can do the cast safely since we determined that is indeed an Enum
                jfo.put("--value", ((Enum)fValue).name());
            }
            else {
                jfo = saveToJson(fValue);
            }
            jo.put(fName, jfo);
        }
    }

    @SuppressWarnings("unchecked")
    public static void getFieldFromJson(Field f, Class<?> objClass, Object obj, JSONObject jo) {
        if (isStatic(f)) return;
        var fType = f.getType();
        var fName = f.getName();
        f.setAccessible(true);
        Object fValue = null;

        if (fType == int.class) fValue = jo.getInt(fName);
        else if (fType == double.class) fValue = jo.getDouble(fName);
        else if (fType == boolean.class) fValue = jo.getBoolean(fName);
        else { // reference types
            var fjo = jo.getJSONObject(fName);
            if (!fjo.has("--isNull")) {
                if (fType == String.class) {
                    fValue = fjo.getString("--value");
                }
                else if (Enum.class.isAssignableFrom(fType)) {
                    fValue = fjo.getEnum((Class<Enum>) fType, "--value");
                }
                else {
                    fValue = loadFromJson(jo.getJSONObject(fName));
                }
            }
        }
        setField(f, obj, fValue);
    }

    public static JSONObject saveToJson(Object obj) {
        Class<?> objClass = obj.getClass();
        JSONObject jo = new JSONObject();

        // save object type
        jo.put("--type", objClass.getName());

        var fields = getAllFields(objClass);

        for(var f : fields) {
            saveFieldToJson(f, obj, jo);
        }
        return jo;
    }

    public static Object loadFromJson(JSONObject jo) {
        var typeName = jo.getString("--type");
        var objClass = clsForName(typeName);
        var obj = createInstance(objClass);
        System.out.println(typeName);

        var fields = getAllFields(objClass);

        for(var f : fields) {
            getFieldFromJson(f, objClass, obj, jo);
        }

        return obj;
    }
}
