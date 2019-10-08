package com.manews.warbot.services;

import com.manews.warbot.core.KillerKilledEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KillService {

    @Autowired
    private RandomService randomService;

    @Autowired
    private FileService fileService;

    @Value("${max.people}")
    private String maxPeople;

    private KillerKilledEntity getSomeoneKilled() throws KillServiceException {
        try {
            checkIf2Alive();
//            String killer = Integer.toString(randomService.getRandomNumberInRange());
//            String killed = Integer.toString(randomService.getRandomNumberInRange());
//            while (fileService.isDead(killer)) {
//                killer = Integer.toString(randomService.getRandomNumberInRange());
//            }
//            while (fileService.isDead(killed)) {
//                killed = Integer.toString(randomService.getRandomNumberInRange());
//            }
//            while (killer.equals(killed)) {
//                while (fileService.isDead(killer)) {
//                    killer = Integer.toString(randomService.getRandomNumberInRange());
//                }
//                while (fileService.isDead(killed)) {
//                    killed = Integer.toString(randomService.getRandomNumberInRange());
//                }
//            }
            String[] people = getDifferentIds(new String[2]);
            return new KillerKilledEntity(people[0], people[1]);
        } catch (FileServiceException | RandomServiceException ex) {
            throw new KillServiceException(ex.getMessage());
        }
    }

    private String[] getDifferentIds(String [] people) throws RandomServiceException, FileServiceException {
        people[0] = Integer.toString(randomService.getRandomNumberInRange());
        people[1] = Integer.toString(randomService.getRandomNumberInRange());
        if(people[0].equals(people[1])||fileService.isDead(people[0])||fileService.isDead(people[1])){
            getDifferentIds(people);
        }
        return people;
    }

    private String[] checkDiffIdsAreAlive(String[] people) throws FileServiceException, RandomServiceException {
        while (fileService.isDead(people[0])){
            people[0] = Integer.toString(randomService.getRandomNumberInRange());
        }
        while (fileService.isDead(people[1])){
            people[1] = Integer.toString(randomService.getRandomNumberInRange());
        }
        return people;
    }

    private void checkIf2Alive() throws KillServiceException {
        try {
            if ((Integer.parseInt(maxPeople) - 1) == fileService.getAllKilled().size()) {
                String winner = "";
                for (int i = 0; i <= Integer.parseInt(maxPeople); i++) {
                    if (!fileService.getAllKilled().contains(Integer.toString(i))) {
                        winner = Integer.toString(i);
                    }
                }
                throw new KillServiceException(String.format("Number %s is the WINNER", winner));
            }
        } catch (FileServiceException e) {
            throw new KillServiceException(e.getMessage());
        }

    }

    public KillerKilledEntity killSteps() throws KillServiceException {
        try {
            KillerKilledEntity killerKilledEntity = getSomeoneKilled();
            fileService.writeKilled(killerKilledEntity);
            fileService.addNewKillerInfo(killerKilledEntity);
            fileService.addLogToFile(killerKilledEntity);
            return killerKilledEntity;
        } catch (FileServiceException e) {
            throw new KillServiceException(e.getMessage());
        }
    }
}
