package net.mahber.sykkelandkeys.web.rest;

import net.mahber.sykkelandkeys.SykkelAndKeysApp;

import net.mahber.sykkelandkeys.domain.Center;
import net.mahber.sykkelandkeys.repository.CenterRepository;
import net.mahber.sykkelandkeys.service.CenterService;
import net.mahber.sykkelandkeys.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static net.mahber.sykkelandkeys.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CenterResource REST controller.
 *
 * @see CenterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SykkelAndKeysApp.class)
public class CenterResourceIntTest {

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private CenterService centerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCenterMockMvc;

    private Center center;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CenterResource centerResource = new CenterResource(centerService);
        this.restCenterMockMvc = MockMvcBuilders.standaloneSetup(centerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Center createEntity(EntityManager em) {
        Center center = new Center()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return center;
    }

    @Before
    public void initTest() {
        center = createEntity(em);
    }

    @Test
    @Transactional
    public void createCenter() throws Exception {
        int databaseSizeBeforeCreate = centerRepository.findAll().size();

        // Create the Center
        restCenterMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(center)))
            .andExpect(status().isCreated());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeCreate + 1);
        Center testCenter = centerList.get(centerList.size() - 1);
        assertThat(testCenter.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCenter.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createCenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centerRepository.findAll().size();

        // Create the Center with an existing ID
        center.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCenterMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(center)))
            .andExpect(status().isBadRequest());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCenters() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList
        restCenterMockMvc.perform(get("/api/centers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(center.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }

    @Test
    @Transactional
    public void getCenter() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get the center
        restCenterMockMvc.perform(get("/api/centers/{id}", center.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(center.getId().intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCenter() throws Exception {
        // Get the center
        restCenterMockMvc.perform(get("/api/centers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCenter() throws Exception {
        // Initialize the database
        centerService.save(center);

        int databaseSizeBeforeUpdate = centerRepository.findAll().size();

        // Update the center
        Center updatedCenter = centerRepository.findOne(center.getId());
        // Disconnect from session so that the updates on updatedCenter are not directly saved in db
        em.detach(updatedCenter);
        updatedCenter
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restCenterMockMvc.perform(put("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCenter)))
            .andExpect(status().isOk());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
        Center testCenter = centerList.get(centerList.size() - 1);
        assertThat(testCenter.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCenter.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingCenter() throws Exception {
        int databaseSizeBeforeUpdate = centerRepository.findAll().size();

        // Create the Center

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCenterMockMvc.perform(put("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(center)))
            .andExpect(status().isCreated());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCenter() throws Exception {
        // Initialize the database
        centerService.save(center);

        int databaseSizeBeforeDelete = centerRepository.findAll().size();

        // Get the center
        restCenterMockMvc.perform(delete("/api/centers/{id}", center.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Center.class);
        Center center1 = new Center();
        center1.setId(1L);
        Center center2 = new Center();
        center2.setId(center1.getId());
        assertThat(center1).isEqualTo(center2);
        center2.setId(2L);
        assertThat(center1).isNotEqualTo(center2);
        center1.setId(null);
        assertThat(center1).isNotEqualTo(center2);
    }
}
