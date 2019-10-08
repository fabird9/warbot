package com.manews.warbot.api;


import com.manews.warbot.core.KillerKilledEntity;
import com.manews.warbot.services.FileService;
import com.manews.warbot.services.FileServiceException;
import com.manews.warbot.services.KillService;
import com.manews.warbot.services.KillServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private KillService killService;
    
    @Autowired
    private FileService fileService;
    //Kills to rndom people
    @RequestMapping(value = "/kill", method = RequestMethod.POST)
    public ResponseEntity<KillerKilledEntity> getSomeoneKilled() throws KillServiceException {
            return new ResponseEntity<>(killService.killSteps(), HttpStatus.ACCEPTED);
    }
    //Return logs
    @RequestMapping(value = "/info/log", method = RequestMethod.GET)
    public ResponseEntity<String> getLog() throws FileServiceException {
        return new ResponseEntity<>(fileService.getLog(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/info/killer", method = RequestMethod.GET)
    public ResponseEntity<String> getKiller() throws FileServiceException {
        return new ResponseEntity<>(fileService.getKillerInfo(), HttpStatus.ACCEPTED);
    }
    //Return info killed
    @RequestMapping(value = "/info/killed", method = RequestMethod.GET)
    public ResponseEntity<String> getKilled() throws FileServiceException {
        return new ResponseEntity<>(fileService.getKilled(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/info/isdead", method = RequestMethod.GET)
    public ResponseEntity<Boolean> isDead(@RequestParam(required = true) String id) throws FileServiceException {
        return new ResponseEntity<>(fileService.isDead(id), HttpStatus.ACCEPTED);
    }

}
