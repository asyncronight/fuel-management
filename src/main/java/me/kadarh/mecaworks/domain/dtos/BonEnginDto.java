package me.kadarh.mecaworks.domain.dtos;

import lombok.Data;

/**
 * PROJECT mecaworks
 *
 * @author kadarH
 * Created at 24/06/18
 */

@Data
public class BonEnginDto {

    private String dateFrom = "";
    private String dateTo = "";
    private String codeEngin = "";
    private String famille = "";
    private String sousFamille = "";
    private String marque = "";
    private String groupe = "";
    private String classe = "";
    private String chantierDepart = "";
    private String chantierArrivee = "";
    private String chauffeur = "";
    private String pompiste = "";
    private String locataire = "";

}
