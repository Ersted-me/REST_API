package com.ersted.controller.impl;

import com.ersted.controller.FileController;
import com.ersted.model.File;
import com.ersted.service.FileService;

import java.util.List;

public class FileControllerImpl implements FileController {
    private final FileService fileService;

    public FileControllerImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public File create(File obj) {
        return fileService.create(obj);
    }

    @Override
    public File getById(Long id) {
        return fileService.getById(id);
    }

    @Override
    public File update(File obj) {
        return fileService.update(obj);
    }

    @Override
    public boolean deleteById(Long id) {
        return fileService.deleteById(id);
    }

    @Override
    public List<File> getAll() {
        return fileService.getAll();
    }
}
