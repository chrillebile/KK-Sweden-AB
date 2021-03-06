package server.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.DataResponse;
import server.Models.Pallet;
import server.Repositories.PalletRepository;
import server.Resources.PalletResource;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Controller for pallet.
 */
@RestController
@RequestMapping(value = "/pallets")
public class PalletController {
    private final PalletRepository palletRepository;

    @Autowired
    public PalletController(PalletRepository palletRepository) {
        this.palletRepository = palletRepository;
    }

    /**
     * Retrieve all pallets saved in the database.
     *
     * @return List of all pallets as an api response.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DataResponse> getAllPallets() {
        List<Pallet> palletList = palletRepository.getPallets();

        List<PalletResource> palletResources = new ArrayList<>();
        for (Pallet pallet : palletList) {
            palletResources.add(new PalletResource(pallet));
        }

        return new ResponseEntity<>(new DataResponse(palletResources), HttpStatus.OK);
    }

    /**
     * Retrieve all pallets between two dates in the database.
     *
     * @param startDate Date to search from.
     * @param endDate   Date to search to.
     * @return List of all pallets as an api response.
     */
    @RequestMapping(params = {"startDate", "endDate"}, method = RequestMethod.GET)
    public ResponseEntity<DataResponse> getPalletsBetweenDates(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) {
        List<Pallet> palletList = palletRepository.getPallets(LocalDate.parse(startDate), LocalDate.parse(endDate));
        List<PalletResource> palletResources = new ArrayList<>();
        for (Pallet pallet : palletList) {
            palletResources.add(new PalletResource(pallet));
        }

        return new ResponseEntity<>(new DataResponse(palletResources), HttpStatus.OK);
    }

    /**
     * Retrieve all pallets which are blocked or not.
     *
     * @param isBlocked Blocked status.
     * @return List of all pallets as an api response.
     */
    @RequestMapping(params = "isBlocked", method = RequestMethod.GET)
    public ResponseEntity<DataResponse> getPalletsWhichBlocked(@RequestParam("isBlocked") String isBlocked) {
        if (!isBlocked.equals("true") && !isBlocked.equals("false"))
            throw new IllegalArgumentException("This parameter should contain either 'true' or 'false'. " +
                    "For input boolean: isBlocked=" + isBlocked);
        boolean blocked = isBlocked.equals("true");
        List<Pallet> palletList = palletRepository.getPallets(blocked);
        List<PalletResource> palletResources = new ArrayList<>();
        for (Pallet pallet : palletList) {
            palletResources.add(new PalletResource(pallet));
        }
        return new ResponseEntity<>(new DataResponse(palletResources), HttpStatus.OK);
    }

    /**
     * Retrieve all pallets with a given recipe.
     *
     * @param id Recipe ID.
     * @return List of all pallets as an api response.
     */
    @RequestMapping(params = "recipeId", method = RequestMethod.GET)
    public ResponseEntity<DataResponse> getPalletsByRecipeId(@RequestParam("recipeId") String id) {
        List<Pallet> palletList = palletRepository.getPallets(Integer.parseInt(id));
        List<PalletResource> palletResources = new ArrayList<>();
        for (Pallet pallet : palletList) {
            palletResources.add(new PalletResource(pallet));
        }
        return new ResponseEntity<>(new DataResponse(palletResources), HttpStatus.OK);
    }

    /**
     * Retrieve all pallets which have been delivered to a customer.
     *
     * @param id ID of the customer.
     * @return List of all pallets as an api response.
     */
    @RequestMapping(params = "customerId", method = RequestMethod.GET)
    public ResponseEntity<DataResponse> getDeliveredPalletsForCustomer(@RequestParam("customerId") String id) {
        List<Pallet> palletList = palletRepository.getPalletsByBlockStatus(Integer.parseInt(id));
        List<PalletResource> palletResources = new ArrayList<>();
        for (Pallet pallet : palletList) {
            palletResources.add(new PalletResource(pallet));
        }
        return new ResponseEntity<>(new DataResponse(palletResources), HttpStatus.OK);
    }

    /**
     * Retrieve a single pallet, identified by it's id.
     *
     * @param id ID of the pallet.
     * @return List of all pallets as an api response.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataResponse> getPalletById(@PathVariable("id") String id) {
        Pallet pallet = palletRepository.getPallet(Integer.parseInt(id));
        return new ResponseEntity<>(new DataResponse(new PalletResource(pallet)), HttpStatus.OK);
    }

    /**
     * Update the blocked status for a given pallet.
     *
     * @param body What to change.
     * @return The changed pallet.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<DataResponse> updatePalletBlockStatus(@PathVariable("id") String id, @Valid @RequestBody DataResponse body) {
        this.validatePost((Map) body.getData(), Arrays.asList("isBlocked"));
        boolean blocked = ((Map) body.getData()).get("isBlocked").toString().equals("true");
        Pallet pallet = palletRepository.updatePalletBlockStatus(Integer.parseInt(id), blocked);
        return new ResponseEntity<>(new DataResponse(new PalletResource(pallet)), HttpStatus.OK);
    }

    /**
     * Update the block status for pallets.
     *
     * @param startDate Date to search from.
     * @param endDate   Date to search to.
     * @param body      Block status and recipe ID.
     * @return The changed pallets.
     */
    @RequestMapping(params = {"startDate", "endDate"}, method = RequestMethod.PATCH)
    public ResponseEntity<DataResponse> updateMultiplePalletBlockStatus(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
                                                                        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate,
                                                                        @Valid @RequestBody DataResponse body) {
        this.validatePost((Map) body.getData(), Arrays.asList("isBlocked", "recipeId"));
        boolean isBlocked = ((Map) body.getData()).get("isBlocked").toString().equals("true");
        int id = (Integer) ((Map) body.getData()).get("recipeId");
        int updatedPallets = palletRepository.updateMultipleBlockStatus(LocalDate.parse(startDate), LocalDate.parse(endDate), id, isBlocked);
        return new ResponseEntity<>(new DataResponse(updatedPallets), HttpStatus.OK);
    }

    /**
     * Create a pallet. Required fields are productionDate, blocked, recipeId and orderId.
     *
     * @param body
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataResponse> createPallet(@Valid @RequestBody DataResponse body) {
        this.validatePost((Map) body.getData(), Arrays.asList("productionDate", "blocked", "recipeId", "orderId"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        Pallet pallet = new Pallet();

        if (((Map) body.getData()).get("productionDate") == null) {
            pallet.setProductionDate(null);
        } else {
            pallet.setProductionDate(LocalDate.parse((String) ((Map) body.getData()).get("productionDate"), formatter));
        }
        pallet.setBlocked((Boolean) ((Map) body.getData()).get("blocked"));
        pallet.setLocation((String) ((Map) body.getData()).get("location"));

        if (((Map) body.getData()).get("deliveryTime") == null) {
            pallet.setDeliveryTime(null);
        } else {
            pallet.setDeliveryTime(new Timestamp((Long) ((Map) body.getData()).get("deliveryTime")));
        }

        // Create the pallet and return it.
        Pallet createdPallet = palletRepository.createPallet(pallet, (Integer) ((Map) body.getData()).get("recipeId"), (Integer) ((Map) body.getData()).get("orderId"));
        createdPallet = palletRepository.getPallet((int) createdPallet.getId());
        return new ResponseEntity<>(new DataResponse(new PalletResource(createdPallet)), HttpStatus.CREATED);
    }

    /**
     * Validates the incoming key => value pair of json (converted to a java object).
     *
     * @param body
     * @param notNullKeys A list of keys which are not allowed to be null (at the same time they must be present).
     * @throws RuntimeException
     */
    private void validatePost(Map body, List<String> notNullKeys) throws RuntimeException {
        for (String value : notNullKeys) {
            if (!body.containsKey(value) || body.get(value) == null) {
                throw new IllegalArgumentException("Parameter " + value + " is either not present or null");
            }
        }

        // If isBlocked is present, ensure it is either true or false.
        if (body.get("isBlocked") != null && !body.get("isBlocked").toString().equals("true") && !body.get("isBlocked").toString().equals("false")) {
            throw new IllegalArgumentException("This parameter should contain either 'true' or 'false'. " +
                    "For input boolean: isBlocked=" + body.get("isBlocked").toString());
        }
    }
}
