package me.kadarh.mecaworks.service;

import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.Random;

/**
 * This class store a confirmation code in memory.
 * The code is used to confirm adding a new BonEngin
 * when there is a technical error (confirm a new counter).
 */
@Service
public class BonEnginConfirmation {

    private final ServletContext context;

    public BonEnginConfirmation(ServletContext context) {
        this.context = context;
        this.generateNewCode();
    }

    public void generateNewCode() {
        int confirmCode = new Random().nextInt(9000) + 1000;
        context.setAttribute("confirmCode", confirmCode);
    }

    public boolean isCorrect(Integer code) {
        return code.equals(context.getAttribute("confirmCode"));
    }
}
