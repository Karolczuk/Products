package MK.converter;

import MK.model.EGuarantee;

import java.util.List;

public class GuarnateeJsonConverter extends JsonConverter<List<EGuarantee>> {
    public GuarnateeJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
