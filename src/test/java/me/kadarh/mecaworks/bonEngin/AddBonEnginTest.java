package me.kadarh.mecaworks.bonEngin;

import me.kadarh.mecaworks.domain.bons.BonEngin;
import me.kadarh.mecaworks.repo.bons.BonEnginRepo;
import me.kadarh.mecaworks.repo.bons.BonLivraisonRepo;
import me.kadarh.mecaworks.repo.others.AlerteRepo;
import me.kadarh.mecaworks.repo.others.ChantierRepo;
import me.kadarh.mecaworks.repo.others.EnginRepo;
import me.kadarh.mecaworks.repo.others.StockRepo;
import me.kadarh.mecaworks.service.bons.BonEnginService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by salah on 4/23/18 9:14 PM.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class AddBonEnginTest {

	BonEngin bonEngin;
	@Autowired
	private BonEnginRepo bonEnginRepo;
	@Autowired
	private EnginRepo enginRepo;
	@Autowired
	private AlerteRepo alerteRepo;
	@Autowired
	private StockRepo stockRepo;
	@Autowired
	private ChantierRepo chantierRepo;
	@Autowired
	private BonLivraisonRepo bonLivraisonRepo;
	@Autowired
	private BonEnginService service;

	@Before
	public void setUp() throws Exception {
		Assert.assertNotNull(bonEnginRepo);
		Assert.assertNotNull(service);
	}

	@Test
	public void Add() {
		/*
		Assert.assertNotNull(bonEngin);
		Assert.assertEquals(bonEngin.getChantierTravail(), chantierRepo.getOne(1L));
		Assert.assertEquals(bonEngin.getEngin(), enginRepo.getOne(1L));
		*/
	}

	@Test
	public void checkAlerts() {
		Assert.assertEquals(alerteRepo.count(), 0);
	}

	@Test
	public void checkStock() {
		Assert.assertEquals(stockRepo.count(), 0);
	}

	@Test
	public void checkCompteurH() {

	}

	@Test
	public void checkCompteurKM() {

	}

	@Test
	public void checkConsommation() {

	}

	@Test
	public void checkBonLivraison() {
		Assert.assertEquals(bonLivraisonRepo.count(), 30);
	}

	@Test
	public void checkChantierStock() {

	}
}
