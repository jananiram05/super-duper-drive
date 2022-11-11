package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilesService {
    private FilesMapper fileMapper;

    public FilesService(FilesMapper noteMapper) {
        this.fileMapper = noteMapper;
    }

    public void createFile(Files file) throws Exception {
        if (this.fileMapper.getFileByFilename(file.getUserid(), file.getFilename()).isEmpty()) {
            this.fileMapper.insert(file);
        } else {
            throw new Exception();
        }
    }

    public void deleteFile(int fileId) {
        this.fileMapper.delete(fileId);
    }

    public List<Files> getUserFiles(int userId) {
        return this.fileMapper.findFile(userId);
    }


    public Files getFileById(int fileId) {
        return this.fileMapper.getFileById(fileId);
    }
}
