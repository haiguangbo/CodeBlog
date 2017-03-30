package org.codingblog.web.rest;

import org.codingblog.CodeBlogApp;

import org.codingblog.domain.Gallery;
import org.codingblog.repository.GalleryRepository;
import org.codingblog.service.GalleryService;
import org.codingblog.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.codingblog.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GalleryResource REST controller.
 *
 * @see GalleryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeBlogApp.class)
public class GalleryResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_LABEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_MSG = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_MSG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_RESOURCE_TYPE = 1;
    private static final Integer UPDATED_RESOURCE_TYPE = 2;

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGalleryMockMvc;

    private Gallery gallery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GalleryResource galleryResource = new GalleryResource(galleryService);
        this.restGalleryMockMvc = MockMvcBuilders.standaloneSetup(galleryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gallery createEntity(EntityManager em) {
        Gallery gallery = new Gallery()
            .userId(DEFAULT_USER_ID)
            .labelName(DEFAULT_LABEL_NAME)
            .category(DEFAULT_CATEGORY)
            .imgUrl(DEFAULT_IMG_URL)
            .shortMsg(DEFAULT_SHORT_MSG)
            .createTime(DEFAULT_CREATE_TIME)
            .resourceType(DEFAULT_RESOURCE_TYPE);
        return gallery;
    }

    @Before
    public void initTest() {
        gallery = createEntity(em);
    }

    @Test
    @Transactional
    public void createGallery() throws Exception {
        int databaseSizeBeforeCreate = galleryRepository.findAll().size();

        // Create the Gallery
        restGalleryMockMvc.perform(post("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gallery)))
            .andExpect(status().isCreated());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeCreate + 1);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testGallery.getLabelName()).isEqualTo(DEFAULT_LABEL_NAME);
        assertThat(testGallery.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testGallery.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
        assertThat(testGallery.getShortMsg()).isEqualTo(DEFAULT_SHORT_MSG);
        assertThat(testGallery.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testGallery.getResourceType()).isEqualTo(DEFAULT_RESOURCE_TYPE);
    }

    @Test
    @Transactional
    public void createGalleryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = galleryRepository.findAll().size();

        // Create the Gallery with an existing ID
        gallery.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGalleryMockMvc.perform(post("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gallery)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGalleries() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList
        restGalleryMockMvc.perform(get("/api/galleries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].labelName").value(hasItem(DEFAULT_LABEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL.toString())))
            .andExpect(jsonPath("$.[*].shortMsg").value(hasItem(DEFAULT_SHORT_MSG.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].resourceType").value(hasItem(DEFAULT_RESOURCE_TYPE)));
    }

    @Test
    @Transactional
    public void getGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get the gallery
        restGalleryMockMvc.perform(get("/api/galleries/{id}", gallery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gallery.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.labelName").value(DEFAULT_LABEL_NAME.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.imgUrl").value(DEFAULT_IMG_URL.toString()))
            .andExpect(jsonPath("$.shortMsg").value(DEFAULT_SHORT_MSG.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.resourceType").value(DEFAULT_RESOURCE_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingGallery() throws Exception {
        // Get the gallery
        restGalleryMockMvc.perform(get("/api/galleries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGallery() throws Exception {
        // Initialize the database
        galleryService.save(gallery);

        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Update the gallery
        Gallery updatedGallery = galleryRepository.findOne(gallery.getId());
        updatedGallery
            .userId(UPDATED_USER_ID)
            .labelName(UPDATED_LABEL_NAME)
            .category(UPDATED_CATEGORY)
            .imgUrl(UPDATED_IMG_URL)
            .shortMsg(UPDATED_SHORT_MSG)
            .createTime(UPDATED_CREATE_TIME)
            .resourceType(UPDATED_RESOURCE_TYPE);

        restGalleryMockMvc.perform(put("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGallery)))
            .andExpect(status().isOk());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testGallery.getLabelName()).isEqualTo(UPDATED_LABEL_NAME);
        assertThat(testGallery.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testGallery.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
        assertThat(testGallery.getShortMsg()).isEqualTo(UPDATED_SHORT_MSG);
        assertThat(testGallery.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testGallery.getResourceType()).isEqualTo(UPDATED_RESOURCE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingGallery() throws Exception {
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Create the Gallery

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGalleryMockMvc.perform(put("/api/galleries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gallery)))
            .andExpect(status().isCreated());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGallery() throws Exception {
        // Initialize the database
        galleryService.save(gallery);

        int databaseSizeBeforeDelete = galleryRepository.findAll().size();

        // Get the gallery
        restGalleryMockMvc.perform(delete("/api/galleries/{id}", gallery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gallery.class);
    }
}
