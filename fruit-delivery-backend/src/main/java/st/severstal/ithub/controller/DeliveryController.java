package st.severstal.ithub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import st.severstal.ithub.dto.DeliveryReportItem;
import st.severstal.ithub.dto.DeliveryRequest;
import st.severstal.ithub.entity.Delivery;
import st.severstal.ithub.service.DeliveryService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/create")
    public ResponseEntity<Delivery> createDelivery(@RequestBody DeliveryRequest request) {
        return new ResponseEntity<>(deliveryService.createDelivery(request), HttpStatus.CREATED);
    }

    @GetMapping("/report")
    public ResponseEntity<List<DeliveryReportItem>> getReport(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return new ResponseEntity<>(deliveryService.getReport(start, end), HttpStatus.OK);
    }
}
