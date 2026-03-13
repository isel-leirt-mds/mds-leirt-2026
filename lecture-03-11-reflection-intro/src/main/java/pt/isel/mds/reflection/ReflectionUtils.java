package pt.isel.mds.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static pt.isel.mds.utils.Utils.TODO;

public class ReflectionUtils {

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
     * fields
     */
    public static List<Field> getAllFields(Class<?> cls) {
        TODO();
        return null;
    }

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

    public static boolean setObjectField(Object obj, String fieldName, Object value) {
        TODO();
        return false;
    }
}
