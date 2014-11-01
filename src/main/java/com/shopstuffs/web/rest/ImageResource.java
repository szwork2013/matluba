package com.shopstuffs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shopstuffs.domain.Image;
import com.shopstuffs.domain.Product;
import com.shopstuffs.repository.ImageRepository;
import com.shopstuffs.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Image.
 */
@RestController
@RequestMapping("/app")
public class ImageResource {

    private final Logger log = LoggerFactory.getLogger(ImageResource.class);

    @Inject
    private ImageRepository imageRepository;
    @Inject
    private ProductRepository productRepository;

    /**
     * POST  /rest/images -> Create a new image.
     */
    @RequestMapping(value = "/rest/images",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Image image) {
        log.debug("REST request to save Image : {}", image);
        imageRepository.save(image);
    }

    /**
     * GET  /rest/images -> get all the images.
     */
    @RequestMapping(value = "/rest/products/{productId}/images",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Image> getAll(@PathVariable Long productId) {
        log.debug("REST request to get all Images");
        return imageRepository.findByProduct_Id(productId);
    }

    /**
     * GET  /rest/images/:id -> get the "id" image.
     */
    @RequestMapping(value = "/rest/products/{productId}/images/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Image> get(@PathVariable Long productId, @PathVariable Long id) {
        log.debug("REST request to get Image : {}", id);
        return imageRepository.findByProduct_IdAndId(productId, id);
    }

    /**
     * DELETE  /rest/images/:id -> delete the "id" image.
     */
    @RequestMapping(value = "/rest/images/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Image : {}", id);
        imageRepository.delete(id);
    }


    @RequestMapping(value = "/rest/images/upload", method = RequestMethod.POST)
    public ResponseEntity<Image> handleFileUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("productId") String productId) {
        if (!multipartFile.isEmpty()) {
            final String name = multipartFile.getOriginalFilename();
            try {
                final String baseUrl = "D:\\Project\\matluba\\src\\main\\webapp\\images\\uploaded\\";
                final File file = new File(baseUrl + name);
                multipartFile.transferTo(file);
                String thumbPath = createThumbnail(baseUrl,file);
                Image image = new Image();
                image.setIsMain(false);
                final Product product = productRepository.findOne(Long.valueOf(productId));
                image.setProduct(product);
                image.setFullImagePath("/images/uploaded/"+name);
                image.setThumbnailImagePath("/images/uploaded/thumbs/"+name);
                image = imageRepository.save(image);

                return new ResponseEntity<Image>(image, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<Image>(Image.EMPTY, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<Image>(Image.EMPTY, HttpStatus.BAD_REQUEST);
        }
    }

    private String createThumbnail(String baseUrl, File file) {
        String path = file.getPath();
        try {
            BufferedImage img = new BufferedImage(250, 160, BufferedImage.TYPE_INT_RGB);
            img.createGraphics().drawImage(ImageIO.read(file).getScaledInstance(250, 160, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
            final File thumbnail = new File(baseUrl+"thumbs\\" + file.getName());
            path = thumbnail.getPath();
            ImageIO.write(img, "jpg", thumbnail);
        } catch (IOException e) {
            log.error("thumbnail image cannot be created", e);
        }
        return path;
    }

}
