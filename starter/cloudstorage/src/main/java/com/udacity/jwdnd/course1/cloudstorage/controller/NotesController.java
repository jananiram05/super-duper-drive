package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCrdntlValObj;
import com.udacity.jwdnd.course1.cloudstorage.model.UserNoteValObj;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NotesController {

    private NotesService noteService;
    private UserMapper userMapper;
    private FilesService fileService;
    private CredentialsService credentialsService;

    public NotesController(NotesService noteService, UserMapper userMapper, FilesService fileService, CredentialsService credentialsService) {
        this.noteService = noteService;
        this.userMapper = userMapper;
        this.fileService = fileService;
        this.credentialsService = credentialsService;
    }

    @PostMapping("/notes")
    public String postNote(@ModelAttribute("userCrdntlValObj") UserCrdntlValObj userCrdntlValObj, Model model, @ModelAttribute("userNoteValObj") UserNoteValObj userNoteValObj, Authentication authentication) {
        String username = authentication.getName();
        int userId = userMapper.findUser(username).getUserid();
        Notes notes = new Notes();

        notes.setUserid(userId);
        notes.setNotetitle(userNoteValObj.getNoteTitle());
        notes.setNotedescription(userNoteValObj.getNoteDescription());
        if (!userNoteValObj.getNoteId().isEmpty()) {
            notes.setNoteid(Integer.parseInt(userNoteValObj.getNoteId()));
            noteService.updateNote(notes);
        } else {
            noteService.createNote(notes);
        }
        model.addAttribute("success", true);
        model.addAttribute("tab", "nav-notes-tab");
        model.addAttribute("notes", noteService.getUserNotes(userId));
        model.addAttribute("files", fileService.getUserFiles(userId));
        model.addAttribute("credentials", credentialsService.getUserCredentials(userId));
        return "home";
    }


    @RequestMapping(value = "/delete/{id}")
    private String deleteNote(@PathVariable(name = "id") String id, RedirectAttributes redirectAttributes) {
        noteService.deleteNote(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("tab", "nav-notes-tab");
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/home";
    }
}
