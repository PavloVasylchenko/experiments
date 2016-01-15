package org.vasylchenko;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvatarController {

    AvatarModel model;

    public AvatarController() {
        model = new AvatarModel();
    }

    @RequestMapping(value = {"/", "/new"})
    public ResponseEntity<byte[]> index(HttpServletRequest request) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(model.getImageBuffer(true), headers, HttpStatus.CREATED);
    }

    @RequestMapping("/old")
    public ResponseEntity<byte[]> old(HttpServletRequest request) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(model.getImageBuffer(false), headers, HttpStatus.CREATED);
    }

}
