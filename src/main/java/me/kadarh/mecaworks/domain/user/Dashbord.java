package me.kadarh.mecaworks.domain.user;

import lombok.Data;
import me.kadarh.mecaworks.domain.AbstractDomain;

import java.util.List;
import java.util.Map;

@Data
public class Dashbord extends AbstractDomain {

    private List<ChantierBatch> chantierBatch;
    private Map<String, Quantite> map;

    public List<ChantierBatch> getChantierBatch() {
        return chantierBatch;
    }

    public void setChantierBatch(List<ChantierBatch> chantierBatch) {
        this.chantierBatch = chantierBatch;
    }

    public Map<String, Quantite> getMap() {
        return map;
    }

    public void setMap(Map<String, Quantite> map) {
        this.map = map;
    }
}
