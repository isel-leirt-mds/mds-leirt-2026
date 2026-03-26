package pt.isel.mds.reflection;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import pt.isel.mds.reflection.entities.A;
import pt.isel.mds.reflection.entities.B;
import pt.isel.mds.reflection.entities.C;

import static java.lang.IO.println;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isel.mds.reflection.ReflectionUtils2.*;

public class ReflectionUtils2Tests {

    private String normalize(String str) {
        return str.replaceAll("\\s", "");
    }

    @Test
    public void getAllFieldsForClassATest() {
        var fields = getAllFields(C.class);
        assertEquals(5, fields.size());
        for(var f : fields) {
            System.out.println(f.getName());
        }
    }



    @Test
    public void saveAToJsonSimpleTest() {
        A a = new A(10);

        var jsonObj = saveToJson(a);
        System.out.println(jsonObj.toString(4));
    }

    @Test
    public void saveBWithEnumToJsonSimpleTest() {
        B b = new B(10, B.Size.LARGE);

        var jsonObj = saveToJson(b);
        System.out.println(jsonObj.toString(4));
    }

    @Test
    public void saveBWithNullEnumToJsonSimpleTest() {
        B b = new B(12);

        var jsonObj = saveToJson(b);
        System.out.println(jsonObj.toString(4));
    }

    @Test
    public void saveCToJsonTest() {
        C c0 = new C(10, 20.0, null);
        A c = new C(4, 6.5, c0);

        var jsonObj = saveToJson(c);
        System.out.println(jsonObj.toString(4));
    }



    @Test
    public void loadObjectWithNullTest() {
        var jsonText = """
                {
                      "--type": "pt.isel.mds.reflection.entities.B",
                      "type": {"--isNull": true},
                      "value": 12
                }
                """;
        JSONObject jo = new JSONObject(jsonText);

        var obj = loadFromJson(jo);
        System.out.println(obj);
    }

    @Test
    public void loadBWithEnumSimpleTest() {
        var jsonText = """
                {
                      "--type": "pt.isel.mds.reflection.entities.B",
                      "type": {"--value": "LARGE"},
                      "value": 10
                  }
                """;
        JSONObject jo = new JSONObject(jsonText);

        var obj = loadFromJson(jo);
        System.out.println(obj);
    }

    @Test
    public void loadCFromJsonTest() {
        C c0 = new C(10, 20.0, null);
        C c = new C(4, 6.5, c0);

        var jo = saveToJson(c);
        var jsonText = jo.toString(4);
        println(jsonText);
        jsonText=normalize(jsonText);

        C obj = (C) loadFromJson(jo);

        assertEquals(obj, c);
    }
}
