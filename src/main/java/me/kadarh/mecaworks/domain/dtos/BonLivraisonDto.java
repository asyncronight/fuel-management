package me.kadarh.mecaworks.domain.dtos;

import lombok.Data;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 21/07/18
 */

@Data
public class BonLivraisonDto {

    private String dateFrom = "";
    private String dateTo = "";
    private String chantierDepart = "";
    private String chantierArrivee = "";
    private String transporteur = "";
    private String pompiste = "";

}
