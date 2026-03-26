package pt.isel.mds.reflection;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import pt.isel.mds.reflection.entities.A;
import pt.isel.mds.reflection.entities.B;
import pt.isel.mds.reflection.entities.C;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.isel.mds.reflection.ReflectionUtils1.*;

public class ReflectionUtils1Tests {

    @Test
    public void getAllFieldsForClassATest() {
        var fields = getAllFields(C.class);
        //assertEquals(2, fields.size());
        for(var f : fields) {
            System.out.println(f.getName());
        }
    }

    @Test
    public void saveBWithEnumToJsonSimpleTest() {
        A a = new A(10);

        var jsonObj = saveToJson(a);
        System.out.println(jsonObj.toString(4));
    }

    @Test
    public void saveBWithNullEnumToJsonSimpleTest() {
        B b = new B(10);

        var jsonObj = saveToJson(b);
        System.out.println(jsonObj.toString(4));
    }

    @Test
    public void saveToJsonSimpleTest() {
        C c0 = new C(10, 20.0, null);
        A c = new C(4, 6.5, c0);

        var jsonObj = saveToJson(c);
        System.out.println(jsonObj.toString(4));
    }

    @Test
    public void loadFromSimpleTest() {
        var jsonText = """
                {
                      "next": {"next": {
                          "next": {"__isNull": true},
                          "__type": "pt.isel.mds.reflection.entities.C",
                          "v2": 20,
                          "type": {"__isNull": true},
                          "value": 10
                      }},
                      "__type": "pt.isel.mds.reflection.entities.C",
                      "v2": 6.5,
                      "type": {"__isNull": true},
                      "value": 4
                  }
                """;
        JSONObject jo = new JSONObject(jsonText);

        var obj = loadFromJson(jo);
        System.out.println(obj);
    }

    @Test
    public void loadObjectWithNullTest() {
        var jsonText = """
                {
                     "__type": "pt.isel.mds.reflection.entities.B",
                     "type": {"__isNull": true},
                     "value": 10
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
                      "__type": "pt.isel.mds.reflection.entities.B",
                      "type": {"__value": "LARGE"},
                      "value": 10
                  }
                """;
        JSONObject jo = new JSONObject(jsonText);

        var obj = loadFromJson(jo);
        System.out.println(obj);
    }

}
