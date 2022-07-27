package com.ersted.service.impl;

import com.ersted.model.File;
import com.ersted.model.Status;
import com.ersted.repository.FileRepository;
import com.ersted.service.FileService;

import java.util.List;
import java.util.stream.Collectors;

public class FileServiceImpl implements FileService {
    private final FileRepository repository;

    public FileServiceImpl(FileRepository repository) {
        this.repository = repository;
    }

    @Override
    public File create(File obj) {
        return repository.create(obj);
    }

    @Override
    public File getById(Long id) {
        File file = repository.getById(id);
        if(file == null || file.getStatus().equals(Status.DELETED))
            return null;
        return file;
    }

    @Override
    public File update(File obj) {
        return repository.update(obj);
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public List<File> getAll() {
        return repository.getAll()
                .stream()
                .filter(file -> file.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
