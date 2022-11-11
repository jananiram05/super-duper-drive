package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCrdntlValObj;
import com.udacity.jwdnd.course1.cloudstorage.model.UserNoteValObj;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialsController {
    @Autowired
    private FilesService fileService;
    private UserMapper userMapper;
    private NotesService noteService;
    private CredentialsService credentialsService;


    public CredentialsController(FilesService fileService, UserMapper userMapper, NotesService noteService,
                                 CredentialsService credentialsService) {
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.noteService = noteService;
        this.credentialsService = credentialsService;
    }

    @PostMapping("/credentials")
    public String addCredential(@ModelAttribute("userCrdntlValObj") UserCrdntlValObj userCrdntlValObj, @ModelAttribute("userNoteValObj") UserNoteValObj userNoteValObj, Model model, Authentication authentication) {
        String username = authentication.getName();
        int userId = userMapper.findUser(username).getUserid();
        Credentials credential = new Credentials();
        credential.setUserid(userId);
        credential.setUsername(userCrdntlValObj.getUsername());
        credential.setUrl(userCrdntlValObj.getUrl());
        credential.setUnencodedPassword(userCrdntlValObj.getPassword());

        if (userCrdntlValObj.getCredentialId() != null && !userCrdntlValObj.getCredentialId().isBlank()) {
            credential.setCredentialid(Integer.parseInt(userCrdntlValObj.getCredentialId()));
            credentialsService.updateCredential(credential);
        } else {
            credentialsService.createCredential(credential);
        }
        model.addAttribute("success", true);
        model.addAttribute("tab", "nav-credentials-tab");
        model.addAttribute("credentials", credentialsService.getUserCredentials(userId));
        model.addAttribute("notes", noteService.getUserNotes(userId));
        model.addAttribute("files", fileService.getUserFiles(userId));
        return "home";
    }


    @RequestMapping(value = "credentials/delete/{id}")
    private String deleteCredential(@PathVariable(name = "id") String id, RedirectAttributes redirectAttributes) {
        credentialsService.deleteCredential(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("tab", "nav-credentials-tab");
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/home";
    }

}
