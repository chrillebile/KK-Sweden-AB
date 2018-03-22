package server.Controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.Models.Pallet;

@RestController
public class PalletController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/pallets")
    public ResponseEntity<Pallet> greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new ResponseEntity<>(new Pallet(counter.incrementAndGet(),
                0, new Date(), false, String.format(template, name) + "nej", new Timestamp(1521660285)), HttpStatus.OK);
    }
}
