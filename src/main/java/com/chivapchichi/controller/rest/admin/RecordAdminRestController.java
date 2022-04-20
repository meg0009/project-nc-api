package com.chivapchichi.controller.rest.admin;

import com.chivapchichi.model.Record;
import com.chivapchichi.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("admin/api/record")
public class RecordAdminRestController {

    private final RecordRepository recordRepository;

    @Autowired
    public RecordAdminRestController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @DeleteMapping("/delete-record/{id}")
    public Map<String, Boolean> deleteRecord(@PathVariable("id") Integer id) {
        Optional<Record> record = recordRepository.findById(id);
        Map<String, Boolean> res = new HashMap<>();

        if (record.isPresent()) {
            recordRepository.deleteById(id);
            res.put("deleted", true);
        } else {
            res.put("deleted", false);
        }
        return res;
    }
}
