package com.ersted.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FilesUtil {
    private static final String PATH = "src/main/resources/uploads/";
    private static final int fileMaxSize = 100 * 1024;
    private static final int memMaxSize = 100 * 1024;

    static {
        File directory = new File(PATH);

        if (!directory.exists())
            directory.mkdir();
    }

    public static List<com.ersted.model.File> loadFile(HttpServletRequest request) {
        List<com.ersted.model.File> files = new ArrayList<>();
        String dirPath = PATH + request.getParameter("login");

        File directory = new File(dirPath);

        if (!directory.exists())
            directory.mkdir();

        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setRepository(directory);
        diskFileItemFactory.setSizeThreshold(memMaxSize);

        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setSizeMax(fileMaxSize);

        try {
            List fileItems = upload.parseRequest(request);

            for (Object item : fileItems) {
                FileItem fileItem = (FileItem) item;
                if (!fileItem.isFormField()) {

                    String fileName = fileItem.getName();
                    String pathToFile = dirPath + "/" + UUID.randomUUID() + fileName;
                    File file = new File(pathToFile);

                    fileItem.write(file);
                    files.add(new com.ersted.model.File(null, fileName, pathToFile, null, null));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    public static void sendFile(HttpServletResponse response, com.ersted.model.File modelFiles) throws IOException {
        File file = new File(modelFiles.getPath());

        InputStream fis = new FileInputStream(file);

        ServletOutputStream os = response.getOutputStream();
        byte[] bufferData = new byte[1024];
        int read = 0;
        while ((read = fis.read(bufferData)) != -1) {
            os.write(bufferData, 0, read);
        }
        os.flush();
        os.close();
        fis.close();
        System.out.println("File downloaded at client successfully");
    }
}
