package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCrdntlValObj;
import com.udacity.jwdnd.course1.cloudstorage.model.UserNoteValObj;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NotesService noteService;
    private FilesService fileService;
    private CredentialsService credentialsService;
    private UserMapper userMapper;

    public HomeController(NotesService noteService, FilesService fileService, CredentialsService credentialsService, UserMapper userMapper) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialsService = credentialsService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String home(@ModelAttribute("userNoteValObj") UserNoteValObj note, @ModelAttribute("userCrdntlValObj") UserCrdntlValObj userCrdntlValObj, Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userMapper.findUser(username);
        if (user != null) {
            int userid = user.getUserid();
            model.addAttribute("notes", noteService.getUserNotes(userid));
            model.addAttribute("files", fileService.getUserFiles(userid));
            model.addAttribute("credentials", credentialsService.getUserCredentials(userid));
            return "home";
        }
        return "signup";
    }
}