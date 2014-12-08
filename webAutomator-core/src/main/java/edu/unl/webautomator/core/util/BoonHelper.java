package edu.unl.webautomator.core.util;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.boon.core.reflection.FastStringUtils;
import org.boon.json.serializers.JsonSerializerInternal;
import org.boon.primitive.CharBuf;

/**
 * Created by gigony on 12/8/14.
 */
public class BoonHelper {

    private static void serializeFieldName ( String name, CharBuf builder ) {
        builder.addJsonFieldName ( FastStringUtils.toCharArray(name) );
    }

    public static <K,V> void serializeMultiMap(JsonSerializerInternal serializer, Multimap<K,V> map, CharBuf builder){
        builder.add("{");
        for(K key:map.keys()){
            serializeFieldName(key.toString(),builder);
            serializer.serializeCollection(map.get(key),builder);
            builder.add(",");
        }
        builder.removeLastChar();
        builder.add("}");
    }


}
