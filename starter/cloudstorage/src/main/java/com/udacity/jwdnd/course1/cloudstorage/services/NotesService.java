package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public void createNote(Notes notes) {
        this.notesMapper.insert(notes);
    }

    public void updateNote(Notes note) {
        this.notesMapper.update(note);
    }

    public void deleteNote(int noteid) {
        this.notesMapper.delete(noteid);
    }

    public List<Notes> getUserNotes(int userid) {
        return this.notesMapper.findNotes(userid);
    }


    public Notes getNoteById(int noteid) {
        return this.notesMapper.getNoteById(noteid);
    }
}
