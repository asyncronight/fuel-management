package me.kadarh.mecaworks.domain.dtos;

import lombok.Data;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 21/07/18
 */

@Data
public class BonFournisseurDto {

    private String dateFrom = "";
    private String dateTo = "";
    private String chantier = "";
    private String fournisseur = "";

}
