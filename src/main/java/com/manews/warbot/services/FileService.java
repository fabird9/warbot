package com.manews.warbot.services;

import com.manews.warbot.core.KillerKilledEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileService {

    @Value("${killed.info.path}")
    private String killedInfoPath;

    @Value("${killer.info.path}")
    private String killerInfoPath;

    @Value("${log.info.path}")
    private String logInfoPath;

    public File getFile(String path) throws FileServiceException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileServiceException(String.format("File %s not found", path));
        }
        return file;
    }

    public void addNewKillerInfo(KillerKilledEntity killerKilledEntity) throws FileServiceException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile(killerInfoPath)));
            String newLine = bufferedReader.readLine();
            boolean isContained = false;
            String wholeDocument = "";
            String killerLine = "";
            while (newLine != null) {
                wholeDocument += newLine + "\n";
                if (newLine.startsWith(killerKilledEntity.getKiller() + ":")) {
                    isContained = true;
                    killerLine = newLine;
                }
                newLine = bufferedReader.readLine();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getFile(killerInfoPath)));
            if (isContained) {
                wholeDocument = StringUtils.replace(wholeDocument, killerLine, killerLine + "," + killerKilledEntity.getKilled());
            } else {
                wholeDocument += killerKilledEntity.getKiller() + ":" + killerKilledEntity.getKilled() + "\n";
            }
            bufferedWriter.write(wholeDocument);
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new FileServiceException("Error writing/reading killer info file");
        }
    }

    public void addLogToFile(KillerKilledEntity killerKilledEntity) throws FileServiceException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(getFile(logInfoPath)));
            String newLine = bufferedReader.readLine();
            String wholeDocument = "";
            while (newLine != null) {
                wholeDocument += newLine + "\n";
                newLine = bufferedReader.readLine();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getFile(logInfoPath)));
            bufferedWriter.write(killerKilledEntity.getKiller() + " ha asesinado a " + killerKilledEntity.getKilled() + "\n" + wholeDocument);
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new FileServiceException("Error writing/reading log file");
        }
    }

    public void writeKilled(KillerKilledEntity killerKilledEntity) throws FileServiceException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile(killedInfoPath)));
            String newLine = bufferedReader.readLine();
            String wholeDocument = "";
            while (newLine != null) {
                wholeDocument += newLine + "\n";
                newLine = bufferedReader.readLine();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getFile(killedInfoPath)));
            bufferedWriter.write(wholeDocument + killerKilledEntity.getKilled());
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new FileServiceException("Error writing/reading killed file");
        }
    }

    public List<String> getAllKilled() throws FileServiceException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile(killedInfoPath)));
            String newLine = bufferedReader.readLine();
            List<String> killedList = new ArrayList<>();
            while (newLine != null) {
                killedList.add(newLine);
                newLine = bufferedReader.readLine();
            }
            bufferedReader.close();
            return killedList;
        } catch (IOException e) {
            throw new FileServiceException("Error writing/reading killed file");
        }
    }

    public boolean isDead(String killed) throws FileServiceException {
        if (getAllKilled().contains(killed)) {
            return true;
        } else {
            return false;
        }
    }

    private String getFullFileAsString(String filePath) throws FileServiceException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile(filePath)));
            String newLine = bufferedReader.readLine();
            String wholeDocument = "";
            while (newLine != null) {
                wholeDocument += newLine + "\n";
                newLine = bufferedReader.readLine();
            }
            bufferedReader.close();
            return wholeDocument;
        } catch (IOException e) {
            throw new FileServiceException(e.getMessage());
        }
    }

    public String getLog() throws FileServiceException {
        return getFullFileAsString(logInfoPath);
    }

    public String getKilled() throws FileServiceException {
        return getFullFileAsString(killedInfoPath);
    }

    public String getKillerInfo() throws FileServiceException {
        return getFullFileAsString(killerInfoPath);
    }

}
