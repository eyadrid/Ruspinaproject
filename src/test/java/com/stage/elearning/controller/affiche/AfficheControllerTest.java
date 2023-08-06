package com.stage.elearning.controller.affiche;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.stage.elearning.dto.affiche.AfficheDTO;
import com.stage.elearning.dto.affiche.AfficheDTOMapper;
import com.stage.elearning.model.affiche.Affiche;
import com.stage.elearning.model.file.FileData;
import com.stage.elearning.repository.AfficheRepository;
import com.stage.elearning.repository.FileDataRepository;
import com.stage.elearning.service.affiche.AfficheService;
import com.stage.elearning.service.affiche.AfficheServiceImpl;
import com.stage.elearning.service.file.FileServiceImpl;
import com.stage.elearning.utility.CustomResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {AfficheController.class})
@ExtendWith(SpringExtension.class)
class AfficheControllerTest {
    @Autowired
    private AfficheController afficheController;

    @MockBean
    private AfficheService afficheService;

    @Test
    void testFetchAfficheImage() throws Exception {
        when(afficheService.fetchAfficheImage(anyLong())).thenThrow(new IOException("foo"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/affiche/all/get_affiche_image/id/{afficheId}", "Uri Variables", "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(afficheController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testModifyAfficheById2() throws IOException {
        AfficheService afficheService = mock(AfficheService.class);
        when(afficheService.modifyAfficheById(Mockito.<MultipartFile>any(), anyLong(), Mockito.<String>any()))
                .thenReturn(null);
        AfficheController afficheController = new AfficheController(afficheService);
        assertNull(afficheController.modifyAfficheById(
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), 1L,
                "Json Affiche Details"));
        verify(afficheService).modifyAfficheById(Mockito.<MultipartFile>any(), anyLong(), Mockito.<String>any());
    }

    @Test
    void testFetchAfficheById() {
        FileData image = new FileData();
        image.setFilePath("/directory/foo.txt");
        image.setId(1L);
        image.setName("Name");
        image.setType("Type");

        Affiche affiche = new Affiche();
        affiche.setId(1L);
        affiche.setImage(image);
        affiche.setTitle("Dr");
        AfficheRepository afficheRepository = mock(AfficheRepository.class);
        when(afficheRepository.fetchAfficheById(anyLong())).thenReturn(Optional.of(affiche));
        AfficheDTOMapper afficheDTOMapper = new AfficheDTOMapper();
        ResponseEntity<CustomResponseEntity<AfficheDTO>> actualFetchAfficheByIdResult = (new AfficheController(
                new AfficheServiceImpl(afficheRepository, afficheDTOMapper,
                        new FileServiceImpl(mock(FileDataRepository.class))))).fetchAfficheById(1L);
        assertTrue(actualFetchAfficheByIdResult.hasBody());
        assertTrue(actualFetchAfficheByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchAfficheByIdResult.getStatusCodeValue());
        CustomResponseEntity<AfficheDTO> body = actualFetchAfficheByIdResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        AfficheDTO data = body.getData();
        assertEquals(1L, data.id());
        assertEquals("Dr", data.title());
        assertSame(image, data.image());
        verify(afficheRepository).fetchAfficheById(anyLong());
    }


    @Test
    void testFetchAllAffiche() {
        AfficheRepository afficheRepository = mock(AfficheRepository.class);
        when(afficheRepository.fetchAllAffiche()).thenReturn(new ArrayList<>());
        AfficheDTOMapper afficheDTOMapper = new AfficheDTOMapper();
        ResponseEntity<CustomResponseEntity<List<AfficheDTO>>> actualFetchAllAfficheResult = (new AfficheController(
                new AfficheServiceImpl(afficheRepository, afficheDTOMapper,
                        new FileServiceImpl(mock(FileDataRepository.class))))).fetchAllAffiche(1L);
        assertTrue(actualFetchAllAfficheResult.hasBody());
        assertTrue(actualFetchAllAfficheResult.getHeaders().isEmpty());
        assertEquals(200, actualFetchAllAfficheResult.getStatusCodeValue());
        CustomResponseEntity<List<AfficheDTO>> body = actualFetchAllAfficheResult.getBody();
        assertTrue(body.isStatusString());
        assertEquals(HttpStatus.OK, body.getStatus());
        assertTrue(body.getData().isEmpty());
        verify(afficheRepository).fetchAllAffiche();
    }



    @Test
    void testDeleteAfficheById() throws Exception {
        when(afficheService.deleteAfficheById(anyLong())).thenThrow(new IOException("foo"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/affiche/admin/delete/id/{afficheId}", "Uri Variables", "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(afficheController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

