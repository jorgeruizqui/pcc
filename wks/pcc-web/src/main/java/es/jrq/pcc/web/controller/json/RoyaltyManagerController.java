package es.jrq.pcc.web.controller.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.jrq.pcc.core.model.Studio;
import es.jrq.pcc.core.service.RoyaltyManagerService;
import es.jrq.pcc.web.http.json.JSONPayment;
import es.jrq.pcc.web.http.json.JSONViewing;

/**
 * REST Controller implemented for concrete JSON API
 * It will map all <code>/royaltymanager</code> endpoints requests.
 *
 * @author JRQ
 *
 */
@RestController
@RequestMapping("/royaltymanager")
public class RoyaltyManagerController {

    /**
     * This method will reset the internal state of the system settings
     * all viewing counters to 0
     *
     * @return {@link ResponseEntity} with empty body and HTTP 202 response code
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    ResponseEntity<String> reset() {
        RoyaltyManagerService.getInstance().reset();
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }

    /**
     * This method receives a JSON object in Request Body. Must be parsed
     * and converted into business logic object.
     *
     * @param viewing JSON mapping viewing object
     *        Fields "episode" and "customer" Global Unique IDentifiers are both
     *        mandatory.
     *        Episode must be registered in the system to make sure the viewing
     *        will be tracked
     *        Customer may not be registered
     * @return {@link ResponseEntity} with empty body and HTTP 202 response code
     */
    @RequestMapping(value = "/viewing", method = RequestMethod.POST)
    ResponseEntity<String> viewing(@RequestBody JSONViewing viewing) {
        RoyaltyManagerService.getInstance().viewing(JSONViewing.json2model(viewing));
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }

    /**
     *
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    ResponseEntity<List<JSONPayment>> showPayments() {
        List<JSONPayment> payments = new ArrayList<JSONPayment>();
        RoyaltyManagerService.getInstance().getStudios().forEach(new Consumer<Studio>() {

            public void accept(Studio studio) {
                payments.add(JSONPayment.model2json(studio));
            };
        });
        return new ResponseEntity<List<JSONPayment>>(payments, HttpStatus.OK);
    }

    /**
     * This end point provides the Payment for a concrete Right Owner (studio)
     *
     * @param rightsOwner Rights Owner (Studio Identifier)
     * @return {@link ResponseEntity} including the {@link JSONPayment} data
     *         object if the Right Owner with code <code>rightsOwner</code> is
     *         found in the system including HTTP 200 Response code (ok).
     *         Empty body with HTTP 404 code (not found) response if Rights Owner is
     *         not present in the system.
     *
     */
    @RequestMapping(value = "/payments/{rightsOwner}", method = RequestMethod.GET)
    ResponseEntity<JSONPayment> showPaymentsByRightsOwner(@PathVariable("rightsOwner") String rightsOwner) {

        ResponseEntity<JSONPayment> rtoEntity = new ResponseEntity<JSONPayment>(HttpStatus.NOT_FOUND);

        Studio studio = RoyaltyManagerService.getInstance().getStudioById(rightsOwner);
        if (studio != null) {
            rtoEntity = new ResponseEntity<JSONPayment>(JSONPayment.model2json(studio), HttpStatus.OK);
        }

        return rtoEntity;
    }

}
