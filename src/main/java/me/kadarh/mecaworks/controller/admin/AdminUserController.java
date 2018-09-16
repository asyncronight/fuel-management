package me.kadarh.mecaworks.controller.admin;

import me.kadarh.mecaworks.config.security.AuthoritiesConstants;
import me.kadarh.mecaworks.domain.User;
import me.kadarh.mecaworks.repo.UserRepo;
import me.kadarh.mecaworks.service.exceptions.OperationFailedException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@Transactional
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    public AdminUserController(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @GetMapping
    public String list() {
        return "redirect:/admin/users/add";
    }

    @GetMapping("/add")
    public String add(Model model, Pageable pageable) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", AuthoritiesConstants.getRoles());
        model.addAttribute("page", userRepo.findAll(pageable));
        return "admin/users/add";
    }

    @PostMapping("/add")
    public String addPost(Model model, Pageable pageable, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("page", userRepo.findAll(pageable));
            model.addAttribute("roles", AuthoritiesConstants.getRoles());
            return "admin/users/add";
        }
        if (userRepo.findByUsername(user.getUsername()).isPresent())
            throw new OperationFailedException("Nom d'utilisateur déja utilisé");
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/admin/users/add";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Principal principal) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent() && !user.get().getUsername().equals(principal.getName()))
            userRepo.deleteById(id);
        return "redirect:/admin/users/add";
    }
}
