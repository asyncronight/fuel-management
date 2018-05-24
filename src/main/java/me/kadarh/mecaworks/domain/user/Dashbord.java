package me.kadarh.mecaworks.domain.user;

import lombok.Data;

import java.util.List;

@Data
public class Dashbord {

    private List<ChantierBatch> chantierBatch;
    private List<Quantite> quantites;

}
