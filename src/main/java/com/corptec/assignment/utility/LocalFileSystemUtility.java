package com.corptec.assignment.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Profile({"dev"})
public class LocalFileSystemUtility implements FileSystemUtility {

    @Value("${resource.folder}")
    private String resourcePath;

    @Override
    public File getFilesLocation() {
        return new File(resourcePath);
    }
}
