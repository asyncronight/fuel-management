package me.kadarh.mecaworks.domain.user;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Dashbord {

    private List<ChantierBatch> chantierBatch;
    private Map<String, Quantite> map;

}
