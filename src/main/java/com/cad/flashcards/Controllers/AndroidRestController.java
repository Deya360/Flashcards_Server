package com.cad.flashcards.Controllers;

import com.cad.flashcards.Database.Entity.Account;
import com.cad.flashcards.Database.Entity.Data;
import com.cad.flashcards.Database.Repository.AccountRepository;
import com.cad.flashcards.Database.Repository.DataRepository;
import com.cad.flashcards.Services.StatusCodeCreator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;


@RestController
public class AndroidRestController {

    private StatusCodeCreator statusCodeCreator;
    private AccountRepository accountRepository;
    private DataRepository dataRepository;

    public AndroidRestController(StatusCodeCreator statusCodeCreator,
                                 AccountRepository accountRepository,
                                 DataRepository dataRepository) {
        this.statusCodeCreator = statusCodeCreator;
        this.accountRepository = accountRepository;
        this.dataRepository = dataRepository;
    }

    @PostMapping("/v1/app/create_account")
    public ResponseEntity createAccount (
            @RequestHeader String token) {

        try {
            //Check if authenticated
            String uuid = isAuthenticated(token);
            if (uuid==null) {
                return new ResponseEntity(statusCodeCreator.unauthorized());
            }

            //Check if account already created
            Account account = accountRepository.getByUuid(uuid);
            if (account!=null) {
                return new ResponseEntity<>(statusCodeCreator.accountExists());
            }

            account = new Account(uuid);
            accountRepository.saveAndFlush(account);

            return new ResponseEntity(statusCodeCreator.created());

        } catch (Exception e) {
            handleException(e);
            return new ResponseEntity(statusCodeCreator.internalServerError());
        }
    }

    @GetMapping("/v1/app/data")
    public ResponseEntity<HashMap<String, String>> data (
            @RequestHeader String token) {

        try {
            //Check if authenticated
            String uuid = isAuthenticated(token);
            if (uuid==null) {
                return new ResponseEntity<>(statusCodeCreator.unauthorized());
            }

            //Check if account exists
            Account account = accountRepository.getByUuid(uuid);
            if (account==null) {
                return new ResponseEntity<>(statusCodeCreator.UserNotFound());
            }

            Data data = dataRepository.getFirstByAccountIdOrderByBackupDateDesc(account.getId());
            if (data==null) {
                return new ResponseEntity<>(statusCodeCreator.notAcceptable());
            }

            return new ResponseEntity<>(data.pack(), statusCodeCreator.success());

        } catch (Exception e) {
            handleException(e);
            return new ResponseEntity<>(statusCodeCreator.internalServerError());
        }
    }

    @PostMapping("/v1/app/data")
    public ResponseEntity<String> data (
            @RequestHeader String token,
            @RequestBody HashMap<String, String> dataBody) {

        try {
            //Check if authenticated
            String uuid = isAuthenticated(token);
            if (uuid==null) {
                return new ResponseEntity<>(statusCodeCreator.unauthorized());
            }

            //Check if account exists
            Account account = accountRepository.getByUuid(uuid);
            if (account==null) {
                return new ResponseEntity<>(statusCodeCreator.UserNotFound());
            }

            String response = "";
            Data dataNew = new Data();
            if(dataBody.containsKey("categories")){
                dataNew.setCategories(dataBody.get("categories"));
                response += "Categories";
            }

            if(dataBody.containsKey("words")){
                dataNew.setWords(dataBody.get("words"));
                response += ", Words";
            }

            if(dataBody.containsKey("results")){
                dataNew.setResults(dataBody.get("results"));
                response += ", Results";
            }

            if (!response.isEmpty()) {
                /* Check if the most recent stored data matches exactly, in that case just update it's backup date to now() */
                Data dataExisting = dataRepository.getFirstByAccountIdOrderByBackupDateDesc(account.getId());
                if (dataExisting != null && dataNew.equalsPartial(dataExisting)) {
                    dataExisting.setBackupDate(new Date());
                    dataRepository.saveAndFlush(dataExisting);
                    response = "Data matched existing record, backup_date was updated to " + new Date() + ".";

                } else { //create a new data object
                    account.addUserData(dataNew);
                    dataRepository.saveAndFlush(dataNew);
                    accountRepository.saveAndFlush(account);
                    response += " backed up.";
                }

                return new ResponseEntity<>(response, statusCodeCreator.success());

            } else {
                return new ResponseEntity<>("Body must contain one of: categories, words, results.", statusCodeCreator.badRequest());
            }

        } catch (Exception e) {
            handleException(e);
            return new ResponseEntity<>(statusCodeCreator.internalServerError());
        }
    }



    @GetMapping("/v1/app/last_backup_date")
    public ResponseEntity<Long> lastBackupDate (
            @RequestHeader String token) {

        try {
            //Check if authenticated
            String uuid = isAuthenticated(token);
            if (uuid==null) {
                return new ResponseEntity<>(statusCodeCreator.unauthorized());
            }

            //Check if account exists
            Account account = accountRepository.getByUuid(uuid);
            if (account==null) {
                return new ResponseEntity<>(statusCodeCreator.UserNotFound());
            }

            Data data = dataRepository.getFirstByAccountIdOrderByBackupDateDesc(account.getId());
            if (data==null) {
                return new ResponseEntity<>(statusCodeCreator.notAcceptable());
            }

            Long date = data.getBackupDate().getTime();
            return new ResponseEntity<>(date, statusCodeCreator.success());

        } catch (Exception e) {
            handleException(e);
            return new ResponseEntity<>(statusCodeCreator.internalServerError());
        }
    }


    /**
     * @param token firebase token received from client
     * @return account uuid
     */
    private String isAuthenticated(String token) {
        FirebaseToken decodedToken = null;
        String uuid = null;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            uuid = decodedToken.getUid();

        } catch (Exception e) {
            System.out.println("===========================================================");
            System.out.println(new Date() + ": Firebase IllegalArgumentException");
        }
        return uuid;
    }

    private void handleException(Exception e) {
        System.out.println("===========================================================");
        System.out.println(new Date());
        e.printStackTrace();
    }
}
