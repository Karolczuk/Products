package MK.dto;

import MK.model.EGuarantee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuaranteeComponentsDto {
    private Long id;
    private EGuarantee eGuarantee;
}

